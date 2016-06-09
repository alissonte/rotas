rotaApp.controller('IndexController', ['$scope','$location','recursoRota',
	function($scope,$location, recursoRota) {


	$scope.inicio = {
		london: {
            lat: -3.7070023,
            lng: -38.5917931,
            zoom: 15
        }
	};
	$scope.paths = {
		europeanPaths: {
            p1: {
                color: 'red',
                weight: 6,
                latlngs: [
                    { lat: -3.7073342, lng: -38.5895768 },
                    { lat: -3.7156839, lng: -38.5420259 },
                    { lat: -3.7226889, lng: -38.5412603 }
                ]
            }
        }
	};


	angular.extend($scope, {
        london: {
            lat: -3.7070023,
            lng: -38.5917931,
            zoom: 15
        },
        europeanPaths: {
            p1: {
                color: 'red',
                weight: 6,
                latlngs: [
                    { lat: -3.7073342, lng: -38.5895768 },
                    { lat: -3.7156839, lng: -38.5420259 },
                    { lat: -3.7226889, lng: -38.5412603 }
                ]
            }
        }
    });

	$scope.toggle = false;

	recursoRota.listarRota(function(rotas) {
		$scope.rotas = rotas;
		angular.forEach($scope.rotas, function(value, key){
			parada(value.paradas);
		});
	},function(error){
		console.log(error);
	});

	var parada = function(trecho){
		console.log(trecho);
		$scope.inicio = trecho. 


	}


	$scope.addRota = function(rota) {
		$scope.toggle = !$scope.toggle;
		recursoRota.salvarRota(rota, function(rota) {
			$scope.rotas.push(rota);
		});
	};
}]);