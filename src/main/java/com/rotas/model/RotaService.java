package com.rotas.model;

public interface RotaService {
	
	Iterable<Rota> findAll();
	
	Rota save(final Rota rota);

}
