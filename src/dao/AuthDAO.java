package dao;

import java.sql.SQLException;

public interface AuthDAO {
	boolean login(String username,String password)throws SQLException;

}
