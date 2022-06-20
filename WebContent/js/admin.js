/**
 * 
 */

//Cria o objeto BAKERY, que sera usado como identificador do projeto
BAKERY = new Object();

$(document).ready(function() {
	
	//Cria uma constante com o valor da URI raiz do REST
	BAKERY.PATH = "/Projeto_Individual/rest/";
	
	//Carrega cabeçalho, menu e rodapé aos respectivos locais
	$("header").load("/Projeto_Individual/pages/general/header.html");
	//$("nav").load("/Projeto_Individual/pages/general/menu.html");
	$("footer").load("/Projeto_Individual/pages/general/footer.html");
	
	BAKERY.loadPage = function(pagename) {
		//Remove o conteudo criado na abertuda re uma janela modal pelo JqueryUI
		if ($(".ui-dialog"))
			$(".ui-dialog").remove();
		
		//Limpa a tag section, excluindo todo o conteudo de dentro dela
		$("section").empty();
		//Carrega a pagina solicitada dentro da tag section
		$("section").load(pagename+"/", function(response, status, info) {
			if (status == "error") {
				var msg = "Houve um erro ao encontrar a pagina: " + info.status + " - " + info.statusText;
				$("section").html(msg);
			}
		});
	}
	
	BAKERY.exibirAviso = function(aviso){
		var modal = {
				tittle: "Mensagem",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"OK": function(){
						$(this).dialogo("close");
					}
				}
		};
		$("#modalAviso").html(aviso);
		$("#modalAviso").dialog(modal);
	};
	
});




