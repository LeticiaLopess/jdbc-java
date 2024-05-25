package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		/* INSERIR DADOS */
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				    + "VALUES "
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Marie Curie"); // preenche a primeira interrogação, respectiva a Name, que é string
			st.setString(2, "marie@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("07/11/1867").getTime())); // muita gente se confunde nessa parte mas quando vamos salvar a data no banco SQL ela deve estar nesse formato java.sql.Date
			st.setDouble(4, 10000.0);
			st.setInt(5, 4);
			// o primeiro parâmetro de cada set corresponde aos "?"
			
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys(); // retorna um ResultSet
				while (rs.next()) {
					int id = rs.getInt(1); // quero o valor da primeira coluna
					System.out.println("Done! Id = " + id);
				}
			} else {
				System.out.println("No rows affected!");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st);
		}
		
		/* RECUPERAR DADOS */
		/* Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from department");
			
			while (rs.next()) {
				System.out.println(rs.getInt("Id") // "pega o inteiro que está no campo 'Id'."
						+ ", " + rs.getString("Name")); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
		*/
	}

}
