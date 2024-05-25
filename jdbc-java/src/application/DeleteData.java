package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class DeleteData {

public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"DELETE FROM department "
					+ "WHERE " // não bota o where não pra você ver... (coloca)
					+ "Id = ?"); 
		
			st.setInt(1, 2); // st.setInt(1, 5); deletou normalmente, porque não tinha nenhum vendedor associado a esse departamento, mas se eu tiver um departamento que está associado a vendedores, eu terei um MySQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowsAffected);
		
		} catch(SQLException e) {
			throw new DbIntegrityException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

}
