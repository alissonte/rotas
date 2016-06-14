package com.rotas.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Transient;

public class Parada implements Serializable{
	
	private static final long serialVersionUID = 413449609222225598L;
	
	@Transient
	private String nome;	
	
	private String color;
	
	private Integer weight;
	
	private List<LatLng> latlngs;
	
	@Transient
	private double lat;
	
	@Transient
	private double lng;
	
	public Parada() {
	}
	
	public Parada(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}
	
	public Parada(String nome, double lat, double lng){
		this(lat, lng);
		this.nome = nome;
	}
	
	public void setLatlngs(List<LatLng> pontos) {
		this.latlngs = pontos;
	}
	
	public List<LatLng> getLatlngs() {
		return latlngs;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double latitude) {
		this.lat = latitude;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double longitude) {
		this.lng = longitude;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}	
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public Integer getWeight() {
		return weight;
	}
}
