function DialogUsuario($scope, $dialog){

    var t = '<div class="modal-header">'+
        '<h1>Seu nome?</h1>'+
        '</div>'+
        '<div class="modal-body">'+
        '<p>Nome: <input ng-model="nomeUsuario" /></p>'+
        '</div>'+
        '<div class="modal-footer">'+
        '<button ng-click="close(nomeUsuario)" class="btn btn-primary" >Entrar</button>'+
        '</div>';

    $scope.opts = {
        backdrop: true,
        keyboard: true,
        width: 200,
        backdropClick: true,
        template:  t,
        controller: 'DialogUsuarioController'
    };

    $scope.openDialog = function(){
        console.info("dialogo aberto pelo usuário");
        var d = $dialog.dialog($scope.opts);
        d.open().then(function(result){
            if(result)
            {
               app.entrarChat(result);
            }
        });
    };


}

// controle para o dialogo
function DialogUsuarioController($scope, dialog){
    $scope.close = function(result){
        dialog.close(result);
    };
}
