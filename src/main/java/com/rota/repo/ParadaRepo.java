package com.rota.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rota.model.Parada;

public interface ParadaRepo extends MongoRepository<Parada, String>{
	
}
