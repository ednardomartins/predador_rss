function Hello($scope, $http) {
    $http.get('http://localhost:8080/informacoesClube').
        success(function(data) {
            $scope.informacoes = data;
        });
}