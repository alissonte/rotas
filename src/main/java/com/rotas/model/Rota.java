package com.rotas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Rota implements Serializable{
	
	private static final long serialVersionUID = 777583361564048876L;
	
	private Date data;
	
	private String nome;
	
	private String origem;
	
	private String destino;
	
	//private List<Parada> paradas;
	
	private Parada parada;
	
	public Rota() {
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public String getOrigem() {
		return origem;
	}
	
	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public String getDestino() {
		return destino;
	}

	/*public List<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(List<Parada> paradas) {
		this.paradas = paradas;
	}*/
	
	public void setParada(Parada parada) {
		this.parada = parada;
	}
	
	public Parada getParada() {
		return parada;
	}
}
