package br.com.bakery.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.bakery.model.User;

public interface UserDAO {
	
	public boolean inserir(User user);
	public List<JsonObject> buscarPorNome(String nome);
	public boolean deletar(int id);
	public User buscarPorId(int id);
	public boolean alterar(User usuario);
}
