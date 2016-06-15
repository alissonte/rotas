package com.rotas.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rotas.model.Route;

public interface RouteRepository extends MongoRepository<Route, String>{
	
	

}
