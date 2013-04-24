
var app;

angular.module('wsTestes', ['ui.bootstrap']);

function Principal($scope, $http, orderByFilter) {

    $scope.selectedModules = [];

}



$(document).ready(function() {

    //wsocket.iniciar();
    app = new App();

    // Definir eventos
    $("#btnEnviarMensagem").click(function(){
         app.enviarMensagem($("#mensagemEnviar").val());
        $("#mensagemEnviar").val("");
    })


});

App = function() {
    var wsocket = null;
    var nomeUsuario =null;
    var idUsuario = null;
    var url = 'ws://localhost/ws' ;
    este = this;
    this.iniciar = function() {
        console.info("iniciando chat");
    }
    this.entrarChat = function(_nomeUsuario) {
        $("#chatInput").show();
        $("#entrarChat").hide();
        $("#mensagensChat").toggleClass("Logado");


        nomeUsuario = _nomeUsuario;
        wsocket = new Wsocket(url + "?nomeUsuario=" + nomeUsuario);
        wsocket.iniciar();
    }
    this.usuarioEntrou = function(_idUsuario, _nomeUsuario ) {
        $("#listaUsuarios").append("<div class='usuario' idUsuario='" + _idUsuario+ "'><span>" + _nomeUsuario + "</span></div>");
        console.info("Usuario entrou com o id: " + idUsuario )
    }


    this.usuarioSaiu = function (_idUsuario, _nomeUsuario) {
        $("#listaUsuarios div.usuario[idUsuario='" + _idUsuario + "']").remove();
    }

    this.processaMensagemTexto = function(mensagem) {
         if(mensagem.substr(0,1)=="@") {
            this.processarComando(mensagem);
         } else {
            this.novaMensagemChat(mensagem);
         }
    }

    this.processarComando = function(mensagem) {
        dados = mensagem.split(";");
        comando = dados[0];
        console.info("Comando: " +comando);
        switch(comando) {
            case "@defId":
                idUsuario = dados[1];
            break;
            case "@entrou":
                este.usuarioEntrou(dados[1], dados[2]);
            break;
            case "@saiu":
                este.usuarioSaiu(dados[1], dados[2]);
            break;
            case "@usuariosON":
                $("#listaUsuarios").append(dados[1]);
            break;
        }
    }
    this.novaMensagemChat = function(mensagem) {
        dados = mensagem.split(";");
        nomeAutor = dados[0];
        textoMensagem = dados[1];
        $("#mensagensChat").prepend("<div class='mensagem'><span class='autorNome'>" + nomeAutor
            +":</span><span class='textoMensagem'>" + textoMensagem + "</span></div>");
        console.info("nova mensagem chat de " + nomeAutor + ", " + textoMensagem);
    }
    this.getIdUsuario = function() {
        return idUsuario;
    }

    this.enviarMensagem = function(mensagem) {
        wsocket.enviarMensagem(mensagem);
    }

}

Wsocket = function(url) {
    this.ws = null;
    var este = this;
    this.iniciar = function() {
        este.ws = new WebSocket(url);
        este.ws.onopen = este.onopen;
        este.ws.onmessage = este.onmessage;
        este.ws.onerror = este.onerror;
        este.ws.onclose = este.onclose;
    };

    this.onopen = function(evento) {
        console.info("Conexão estabelecida!");
        este.ws.send("@pegarId");
    };
    this.onmessage = function(evento) {
        if(typeof evento.data == "string") {
            console.info("Mensagem do tipo String: " + evento.data);
            app.processaMensagemTexto(evento.data);

        } else {
            if(evento.data instanceof Blob) {
                console.info("Blob recebido: " + evento.data);
                var blob = new Blob(evento.data);
            } else {
                console.info("ArrayBuffer recebido: " + evento.data);
                var arrayBuffer = new Uint8Array(evento.data);
            }
        }
    }
    this.onerror = function(evento) {
        console.error(["Erro na conexão WebSocket: " + evento])
    }

    this.onclose = function(code, reason, wasClean) {
        console.info("Conexão fechada: [" + code + "] " + reason);
        if(!wasClean) {
            console.inf("Fechada devido a algum erro");
        }
    }
    this.enviarMensagem = function(mensagem) {
        este.ws.send(mensagem);
    }


};

 // Abaixo algumas rotinas de exemplo da api websocket não usadas nesta app
  /*
    // Fechar a conexão sem parâmetros
    var fecharConexaoSemParametro = function() {
        ws.close()
    }

    // Fechar a conexão com parâmetros
    var fecharConexao = function() {
        ws.close(1000, "Fechando normalmente");
    }


    // Enviar o posicionamento em um mapa de 256 x 256
    var enviarPosicao = function(posicaoX, posicaoY) {
        var posicao = new Uint8Array([posicaoX,posicaoY]);
        ws.send(posicao.buffer);
    }

    var criarArrayBuffer = function() {
        // criar uma ArrayBuffer de 8 bytes
        var b = new ArrayBuffer(8);

        // cria uma view v1 referenciando para b, do tipo Int32,
        // iniciando no byte 0 e extendendo até o final
        var v1 = new Int32Array(b);

        // cria uma view v2 referenciando para b, do tipo Int16,
        // iniciando no byte 2 e tendo o tamanho 2
        var v2 = new Int16Array(b, 2, 2);

    }

    // Exemplo de uso do polling
    polling = function() {
        setTimeout(function() {
            $.ajax({
                type: 'post',
                url: '/polling',
                success: function(dados) {
                    atualizarChat(dados);
                },
                complete: polling()
            });
        },
        2000);
    }


  var atualizarChat = function(dados) {
      console.info("Dados chegando: " + dados);
  }

   */
