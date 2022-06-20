package br.com.bakery.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.bakery.bd.Conexao;
import br.com.bakery.jdbc.JDBCUserDAO;
import br.com.bakery.model.User;


@Path("user")
public class UserRest extends UtilRest {

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String userParam) {
		
		try {
			User user = new Gson().fromJson(userParam,  User.class);
			Conexao connec = new Conexao();
			Connection conexao = connec.openConnection();
			
			JDBCUserDAO jdbcUser = new JDBCUserDAO(conexao);
			boolean retorno = jdbcUser.inserir(user);
			String msg = "";
			
			if(retorno) {
				msg = "Usuario cadastrado com sucesso!";
			}else {
				msg = "Erro ao cadastrar o usuario.";
			}
			
			connec.closeConnection();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String nome) {
		
		try {
			
			List<JsonObject> listaUsuarios = new ArrayList<JsonObject>();
			Conexao connec = new Conexao();
			Connection conexao = connec.openConnection();
			JDBCUserDAO jdbcUser = new JDBCUserDAO(conexao);
			listaUsuarios = jdbcUser.buscarPorNome(nome);
			connec.closeConnection();
			
			String json = new Gson().toJson(listaUsuarios);
			return this.buildResponse(json);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
			
		}
	}
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {
		
		try {
			Conexao connec = new Conexao();
			Connection conexao = connec.openConnection();
			JDBCUserDAO jdbcUser = new JDBCUserDAO(conexao);
			
			boolean retorno = jdbcUser.deletar(id);
			
			String msg = "";
			if(retorno) {
				msg = "Usuario excluido com sucesso!";
			}else {
				msg = "Erro ao excluir o usuario.";
			}
			
			connec.closeConnection();
			
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}
	
	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {
		
		try {
			User usuario = new User();
			Conexao connec = new Conexao();
			Connection conexao = connec.openConnection();
			JDBCUserDAO jdbcUser = new JDBCUserDAO(conexao);
			
			usuario = jdbcUser.buscarPorId(id);
			
			connec.closeConnection();
			
			return this.buildResponse(usuario);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String userParam) {
		
		try {
			User usuario = new Gson().fromJson(userParam,  User.class);
			Conexao connec = new Conexao();
			Connection conexao = connec.openConnection();
			JDBCUserDAO jdbcUser = new JDBCUserDAO(conexao);
		
			boolean retorno = jdbcUser.alterar(usuario);
			
			String msg = "";
			if (retorno) {
				msg = "Usuario alterado com sucesso!";
			}else {
				msg = "Erro ao alterar usuario.";
			}
			connec.closeConnection();
			return this.buildResponse(msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
		
	}
	
}


















