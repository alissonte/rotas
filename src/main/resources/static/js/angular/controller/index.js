rotaApp.controller('IndexController', ['$scope','$location','recursoRota', function($scope,$location, recursoRota) {

	recursoRota.listarRota(function(rotas) {
        parsePaths(rotas);
        parserPathInicio(rotas);
        $scope.rotas = rotas;
	},function(error){
		console.log(error);
	});

	$scope.addRota = function(rota) {
		$scope.toggle = !$scope.toggle;
		recursoRota.salvarRota(rota, function(rota) {
			$scope.rotas.push(rota);
            parsePaths($scope.rotas);
            parserPathInicio($scope.rotas);
		});
	};

    var parsePaths = function(rotas){
        angular.forEach(rotas, function(value, key){
            value.paths = {};
            value.paths.p1 = value.step;
        
        });        
    };

    var parserPathInicio = function(rotas){
        angular.forEach(rotas, function(value, key){
            value.inicio = {};

            value.inicio.lat = value.step.latlngs[0].lat;
            value.inicio.lng = value.step.latlngs[0].lng;
            value.inicio.zoom = 16;
        });
    };
    
}]);