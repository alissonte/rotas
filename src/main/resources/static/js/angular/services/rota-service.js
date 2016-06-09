rotaApp.factory('recursoRota', ['$http', '$q', function($http, $q) {
	var listarRota = function(callback) {
		 return $http.get('/rotas').then(function(response){
			 if (callback) callback(response.data);
		 }, function(error){
	     	return $q.reject(error);
	   	});
	};

	var salvarRota = function(rota, callback) {
		 return $http.post('/rotas/salvar', rota).then(function(response){
			 if (callback) callback(response.data);
		 }, function(error){
	     	return $q.reject(error);
	   	});
	};

	return {
      listarRota: listarRota,
      salvarRota:salvarRota
    };
}]);