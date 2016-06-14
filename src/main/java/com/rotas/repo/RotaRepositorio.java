package com.rotas.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rotas.model.Rota;

public interface RotaRepositorio extends MongoRepository<Rota, String>{
	
	

}
