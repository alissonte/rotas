package com.rotas.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Transient;

public class Step implements Serializable{
	
	private static final long serialVersionUID = 413449609222225598L;	
	
	private String name;	
	
	private String color;
	
	private Integer weight;
	
	private List<LatLng> latlngs;	
	
	private double lat;
	
	private double lng;
	
	public Step() {
	}
	
	public Step(double lat, double lng){
		this.lat = lat;
		this.lng = lng;
	}
	
	public Step(String name, double lat, double lng){
		this(lat, lng);
		this.name = name;
	}
	
	public void setLatlngs(List<LatLng> latlngs) {
		this.latlngs = latlngs;
	}
	
	public List<LatLng> getLatlngs() {
		return latlngs;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
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
