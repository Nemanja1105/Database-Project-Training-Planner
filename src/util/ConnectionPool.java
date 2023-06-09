package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.*;

public class ConnectionPool {
	private static final String CONFIG_PATH = "resources/ConnectionPool.properties";
	
	private String jdbcURL;
	private String username;
	private String password;
	private int preconnectCount;
	private int maxIdleConnections;
	private int maxConnections;

	private int connectCount;
	private List<Connection> usedConnections;
	private List<Connection> freeConnections;

	private static ConnectionPool instance;

	public static ConnectionPool getInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}

	private ConnectionPool() {
		readConfiguration();
		try {
			freeConnections = new ArrayList<Connection>();
			usedConnections = new ArrayList<Connection>();

			for (int i = 0; i < preconnectCount; i++) {
				Connection conn = DriverManager.getConnection(jdbcURL, username, password);
				freeConnections.add(conn);
			}
			connectCount = preconnectCount;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readConfiguration() {
	    Properties prop = new Properties();
	    
	    try (InputStream input=new FileInputStream(CONFIG_PATH))
	    {
	    	prop.load(input);
	    }
	    catch(IOException e) {
	    	e.printStackTrace();
	    }
		jdbcURL = prop.getProperty("jdbcURL");
		username = prop.getProperty("username");
		password = prop.getProperty("password");
		preconnectCount = 0;
		maxIdleConnections = 10;
		maxConnections = 10;
		
		try {
			preconnectCount = Integer.parseInt(prop.getProperty("preconnectCount"));
			maxIdleConnections = Integer.parseInt(prop.getProperty("maxIdleConnections"));
			maxConnections = Integer.parseInt(prop.getProperty("maxConnections"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized Connection checkOut() throws SQLException {
		Connection conn = null;
		if (freeConnections.size() > 0) {
			conn = freeConnections.remove(0);
			usedConnections.add(conn);
		} else {
			if (connectCount < maxConnections) {
				conn = DriverManager.getConnection(jdbcURL, username, password);
				usedConnections.add(conn);
				connectCount++;
			} else {
				try {
					wait();
					conn = freeConnections.remove(0);
					usedConnections.add(conn);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return conn;
	}

	public synchronized void checkIn(Connection conn) {
		if (conn == null)
			return;
		if (usedConnections.remove(conn)) {
			freeConnections.add(conn);
			while (freeConnections.size() > maxIdleConnections) {
				int lastOne = freeConnections.size() - 1;
				Connection c = freeConnections.remove(lastOne);
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			notify();
		}
	}
	

}
