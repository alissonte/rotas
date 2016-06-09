package com.rota.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.rota.model.Veiculo;

public interface VeiculoRepo extends MongoRepository<Veiculo, String>{
	
	Page<Veiculo> findByMarca(@Param("marca") String marca, Pageable pageable);

}
