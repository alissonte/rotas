package com.rotas.model;

public interface RouteService {
	
	Iterable<Route> findAll();
	
	Route save(final Route route);

}
