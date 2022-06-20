package br.com.bakery.bd;

import java.sql.Connection;

public class Conexao {

	private Connection conexao;
	
	public Connection openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conexao = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/mydb?" + "user=root&password=Heihachi@123*789&useTimezone=true&serverTimezone=UTC");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conexao;
	}
	
	public void closeConnection() {
		try {
			conexao.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
