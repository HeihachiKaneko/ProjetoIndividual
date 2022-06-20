BAKERY.user = new Object();

$(document).ready(function() {
	
	//Cadastra no BD o produto informado
	BAKERY.user.register = function(){
		
		var user = new Object();
		user.nome = document.formAddUser.nome.value;
		user.email = document.formAddUser.email.value;
		user.senha = document.formAddUser.senha.value;
		
		
		if((user.nome=="")||(user.email=="")||(user.senha=="")){
			BAKERY.exibirAviso("Preencha todos os campos!");
		} else {
			
			$.ajax({
				type: "POST",
				url: BAKERY.PATH + "user/inserir",
				data: JSON.stringify(user),
				success: function (msg) {
					BAKERY.exibirAviso(msg);
					$("#addUser").trigger("reset");
				},
				error: function (info) {
					BAKERY.exibirAviso("Erro ao cadastrar um novo usuario: " + info.status + " - " + info.statusText);
				}
			});
		}
	}

	//Busca no BD e exibe na pagina os produtos que atendam a solicitacao do usuario
	BAKERY.user.buscar = function(){
		var valorBusca = $("#campoBuscaUsuario").val();
		
		$.ajax({
			type: "GET",
			url: BAKERY.PATH + "user/buscar",
			data: "valorBusca="+valorBusca,
			success: function(dados){
				
				dados = JSON.parse(dados);
				console.log(dados);
				$("#listaUsuarios").html(BAKERY.user.exibir(dados));
				
			},
			error: function(info){
				BAKERY.exibirAviso("Erro ao consultar os usuarios registrados: " + info.status + " - " + info.statusText);	
			}
		});
		
		
	};
	
	BAKERY.user.exibir = function(listaDeUsuarios){
		
		var tabela = "<table>" +
			"<tr>" +
				"<th>Id</th>" +
				"<th>Nome</th>" +
				"<th>Email</th>" +
				"<th class='acoes'>Acoes</th>" +
			"</tr>";
		
		if (listaDeUsuarios != undefined && listaDeUsuarios.length > 0){
			console.log(listaDeUsuarios.length);
			
			for (var i=0; i<listaDeUsuarios.length; i++){
				tabela += "<tr>" +
							"<td>" +listaDeUsuarios[i].id+"</td>" +
							"<td>" +listaDeUsuarios[i].nome+"</td>" +
							"<td>" +listaDeUsuarios[i].email+"</td>" +
							"<td>" +
								"<a onclick=\"BAKERY.user.exibirEdicao('"+listaDeUsuarios[i].id+"')\"><img src='../../../img/edit.png' alt='Editar registro'></a> " +
								"<a onclick=\"BAKERY.user.excluir('"+listaDeUsuarios[i].id+"')\"><img src='../../../img/delete.png' alt='Apagar registro'></a> " +
							"</td>" +
						"</tr>"
			}
			
		}else if (listaDeUsuarios == ""){
			tabela += "<tr><td colspan='6'>Nenhum registro encontrado</td></tr>";
		}
		tabela += "</table>";
		
		return tabela;
	};
	
	
	//Executa a funcao de busca ao carregar a pagina
	BAKERY.user.buscar();
	
	//Exclui o produto selecionado
	BAKERY.user.excluir = function(id){
		$.ajax({
			type:"DELETE",
			url: BAKERY.PATH + "user/excluir/"+ id,
			success: function(msg){
				BAKERY.exibirAviso(msg);
				BAKERY.user.buscar();
			},
			error: function(info){
				BAKERY.exbirAviso("Erro ao excluir o usuario: "+ info.stats + " - " + info.statusText);
			}
		});
	};
	
	BAKERY.user.exibirEdicao = function(id){
		
		alert(id);
		
		$.ajax({
			type: "GET",
			url: BAKERY.PATH + "user/buscarPorId",
			data: "id=" + id,
			success: function(usuario){
				
				document.frmEditaUsuario.identificacao.value = usuario.id;
				document.frmEditaUsuario.nome.value = usuario.nome;
				document.frmEditaUsuario.email.value = usuario.email;
				
				var modalEditaUsuario = {
					title: "Editar Usuario",
					height: 400,
					width: 550,
					modal: true,
					buttons:{
						"Salvar": function(){
							BAKERY.user.editar();
						},
						"Cancelar": function(){
							$(this).dialog("close");
						}
					},
					Close: function(){
						//caso o usuario simplesmente fecha a caixa de edicao nao deve acontecer nada
					}
				};
				$("#modalEditaUsuario").dialog(modalEditaUsuario);
				
			},
			error: function(info){
				BAKERY.exibirAviso("Erro ao buscar usuario para edicao: " + info.status + " - " + info.statusText);
			}
		});
	};
	
	BAKERY.user.editar = function(){
		
		var usuario = new Object();
		
		usuario.nome = document.frmEditaUsuario.nome.value;
		usuario.email = document.frmEditaUsuario.email.value;
		usuario.id = document.frmEditaUsuario.identificacao.value;
		
		$.ajax({
			type: "PUT",
			url: BAKERY.PATH + "user/alterar",
			data: JSON.stringify(usuario),
			success: function(msg){
				BAKERY.exibirAviso(msg);
				BAKERY.user.buscar();
				$("#modalEditaUsuario").dialog("close");
				
			},
			error: function(info){
				BAKERY.exibirAviso("Erro ao editar usuario: " + info.status + " - " + info.statusText);
				
			}
		})
	};
	
	
});





