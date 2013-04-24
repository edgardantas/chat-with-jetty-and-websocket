<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="pt-br" ng-app="wsTestes">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <script type="text/javascript" src="/libs/jquery/jquery-1.9.1.js"></script>
    <!-- <script type="text/javascript" src="/libs/bootstrap/js/bootstrap.min.js"></script> -->
    <script type="text/javascript" src="/libs/angular/angular.js"></script>
    <script type="text/javascript" src="/libs/angular/angular-resource.js"></script>
    <script type="text/javascript" src="/libs/angular-ui/bootstrap/ui-bootstrap-tpls-0.2.0.js"></script>
     <script type="text/javascript" src="/js/app.js"></script>
    <script type="text/javascript" src="/js/usuario.js"></script>
    <link type="text/css" href="/css/estilo.css" rel="stylesheet" media="screen">
    <link href="/libs/bootstrap/css/bootstrap.css" rel="stylesheet" media="screen">
</head>

<body  class="c" ng-controller="Principal">
<div id="pagina" role="main">
    <div class="hero-unit" id="overview">
        <div class="container">
            <h1>WebSocket e HTML5</h1>
            <p>Demonstração do uso da API e Protocolo WebSocket na comunicação em tempo real. Foi implementada uma aplicação de bate papo simples.</p>
        </div>
    </div>
    <div class="container">
        <div id="entrarChat" class="page-header" ng-controller="DialogUsuario">
            <h1>Chat utilizando Polling</h1>
            <p><button class="btn btn-primary" ng-click="openDialog()">Entrar no Chat</button></p>
        </div>
        <div class="row">
            <div class="span2">
                <h3>Usuarios</h3>
                <div class="blocos" id="listaUsuarios">

                </div>
            </div>
            <div class="span10">
                <h3>Chat</h3>
                <div class="blocos" id="chatInput">
                    <form>
                        <fieldset>
                            <input id="mensagemEnviar" type="text" placeholder="Texto da mensagem...">
                            <button id="btnEnviarMensagem" type="submit" class="btn btn-primary">Enviar</button>
                        </fieldset>
                    </form>
                </div>
                <div class="blocos" id="mensagensChat">

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
