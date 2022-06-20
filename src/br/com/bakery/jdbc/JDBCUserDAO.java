package br.com.bakery.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.bakery.jdbcinterface.UserDAO;
import br.com.bakery.model.User;

public class JDBCUserDAO implements UserDAO {
	
	private Connection conexao;
	
	public JDBCUserDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean inserir(User user) {
		
		String comando = "INSERT INTO usuario "
				+ "(id, nome, email, senha) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement p;
		
		try {
			//Prepara o comando para execucao no BD em que nos conectamos
			p = this.conexao.prepareStatement(comando);
			
			//Substitui no comando os "?" pelos valores do produto
			p.setInt(1, user.getId());
			p.setString(2, user.getNome());
			p.setString(3, user.getEmail());
			p.setString(4, user.getSenha());
			
			//Executa o comando no BD
			p.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<JsonObject> buscarPorNome(String nome){
		//Inicia criacao do comando SQL de busca
		String comando = "SELECT * FROM mydb.usuario ";
		
		if (!nome.equals("")) {
			comando += "WHERE nome LIKE '%" + nome + "%' ";
		}
		
		comando += "ORDER BY id ASC, nome ASC, email ASC";
		
		System.out.println(comando);
		
		List<JsonObject> listaUsuarios = new ArrayList<JsonObject>();
		JsonObject usuario = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String nomeUsuario = rs.getString("nome");
				String email = rs.getString("email");
				
				usuario = new JsonObject();
				usuario.addProperty("id", id);
				usuario.addProperty("nome", nomeUsuario);
				usuario.addProperty("email", email);
			
				listaUsuarios.add(usuario);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaUsuarios;
	}
	
	public boolean deletar(int id) {
		String comando = "DELETE FROM mydb.usuario WHERE id = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1,  id);
			p.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public User buscarPorId(int id) {
		String comando = "SELECT * FROM mydb.usuario WHERE usuario.id = ?";
		User usuario = new User();
		
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1,  id);
			
			System.out.println(p);
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				
				usuario.setId(id);
				usuario.setNome(nome);
				usuario.setEmail(email);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	public boolean alterar(User usuario) {
		
		String comando = "UPDATE mydb.usuario "
				+ "SET nome=?, email=? "
				+ "WHERE id=?";
		PreparedStatement p;
		
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, usuario.getNome());
			p.setString(2,usuario.getEmail());
			p.setInt(3, usuario.getId());
			p.executeUpdate();
			
			System.out.println(p);
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}











