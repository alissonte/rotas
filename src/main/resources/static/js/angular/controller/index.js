rotaApp.controller('IndexController', ['$scope','$location','recursoRota',
	function($scope,$location, recursoRota) {

    $scope.paths = {};
    $scope.path = {};

	recursoRota.listarRota(function(rotas) {
        angular.forEach(rotas, function(value, key){
            value.paths = {};
            value.paths.p1 = value.parada;
        
        });

        angular.forEach(rotas, function(value, key){
            value.inicio = {};

            value.inicio.lat = value.parada.latlngs[0].lat;
            value.inicio.lng = value.parada.latlngs[0].lng;
            value.inicio.zoom = 16;
        });

        $scope.rotas = rotas;
	},function(error){
		console.log(error);
	});

	$scope.addRota = function(rota) {
		$scope.toggle = !$scope.toggle;
		recursoRota.salvarRota(rota, function(rota) {
			$scope.rotas.push(rota);
		});
	};
}]);