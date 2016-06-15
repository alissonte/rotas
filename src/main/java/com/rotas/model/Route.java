package com.rotas.model;

import java.io.Serializable;
import java.util.Date;

public class Route implements Serializable{
	
	private static final long serialVersionUID = 777583361564048876L;
	
	private Date date;
	
	private String name;
	
	private String origin;
	
	private String destino;	
	
	private Step step;
	
	public Route() {
	}

	public Date getDate() {
		return date;
	}

	public void setData(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setOrigin(String origem) {
		this.origin = origem;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setDestino(String destino) {
		this.destino = destino;
	}
	
	public String getDestino() {
		return destino;
	}	
	
	public void setStep(Step parada) {
		this.step = parada;
	}
	
	public Step getStep() {
		return step;
	}
}
