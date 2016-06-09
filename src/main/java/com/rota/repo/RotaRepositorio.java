package com.rota.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rota.model.Rota;

public interface RotaRepositorio extends MongoRepository<Rota, String>{
	
	

}
