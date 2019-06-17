package jdbc_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/mybatis_study?serverTimezone=UTC&useSSL=false";
		
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, "root", "root");
		
		String sql = "select * from	user where  addr = ? and school = ?";
		
		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, "武汉");
		statement.setString(2, "华科");
		
		statement.executeQuery();
		
		ResultSet resultSet = statement.getResultSet();
		while(resultSet.next()) {
			System.out.println(resultSet.getString("name"));
		}
		ResultSet resultSet1 = statement.getResultSet();
		while(resultSet1.next()) {
			System.out.println(resultSet1.getString("name"));
		}
	}
}
