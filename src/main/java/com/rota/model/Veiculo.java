package com.rota.model;

import java.io.Serializable;

public class Veiculo implements Serializable{
	
	private static final long serialVersionUID = -838570543449833811L;
	
	
	private String marca;
	private String modelo;
	private String tipo;
	
	public Veiculo() {
		// TODO Auto-generated constructor stub
	}	

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
