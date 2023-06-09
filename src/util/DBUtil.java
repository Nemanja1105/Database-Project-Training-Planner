package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	public static void close(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	public static void close(Statement s) throws SQLException {
		if (s != null) {
			s.close();
		}
	}

	public static void close(ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
	}

	public static void close(Connection conn, Statement s) throws SQLException {
		close(s);
		close(conn);
	}

	public static void close(Connection conn, ResultSet rs) throws SQLException {
		close(rs);
		close(conn);
	}

	public static void close(Statement s, ResultSet rs) throws SQLException {
		close(rs);
		close(s);
	}

	public static void close(Connection conn, Statement s, ResultSet rs) throws SQLException {
		close(rs);
		close(s);
		close(conn);
	}
}
