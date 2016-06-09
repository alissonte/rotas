package com.rota.model;

import java.io.Serializable;

public class Parada implements Serializable{
	
	private static final long serialVersionUID = 413449609222225598L;
	
	private String nome;
	
	private double latitude;
	
	private double longitude;
	
	public Parada() {
	}
	
	public Parada(double lat, double lng){
		this.latitude = lat;
		this.longitude = lng;
	}
	
	public Parada(String nome, double lat, double lng){
		this(lat, lng);
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
