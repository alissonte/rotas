package com.rotas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rotas.model.Rota;
import com.rotas.model.RotaService;

@RestController
public class RotaController {	
	
	@Autowired
	private RotaService rotaService;
	
	@ResponseBody
	@RequestMapping(value="/rotas", method = RequestMethod.GET)
	public Iterable<Rota> listar(){
		return rotaService.findAll();
	}
	
	
	@RequestMapping(value="/rotas/salvar", method = RequestMethod.POST)
	public Rota salvar(@RequestBody final Rota novaRota){		
		return rotaService.save(novaRota);		
	}

}
