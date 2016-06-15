package com.rotas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rotas.model.Route;
import com.rotas.model.RouteService;

@RestController
public class RouteController {	
	
	@Autowired
	private RouteService rotaService;
	
	@ResponseBody
	@RequestMapping(value="/rotas", method = RequestMethod.GET)
	public Iterable<Route> list(){
		return rotaService.findAll();
	}
	
	
	@RequestMapping(value="/rotas/salvar", method = RequestMethod.POST)
	public Route save(@RequestBody final Route novaRota){		
		return rotaService.save(novaRota);		
	}

}
