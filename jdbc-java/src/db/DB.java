package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	
	private static Connection conn = null;
	
	public static Connection getConnection( ) {
		
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
				
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		return conn; // conectar com o BD é instanciar um objeto Connection (18)
	}
	
	public static void closeConnection() {
		if (conn != null) {
			
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
			/*
			  SQLException é derivado de Exception e e obrigatório tratar (try - catch)
			  A DbException é derivada de RuntimeException, ai não precisamos ficar colocando sempre um try - catch
			*/
		} 
	}
	
	private static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}

}
