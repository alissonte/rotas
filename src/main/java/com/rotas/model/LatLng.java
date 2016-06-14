package com.rotas.model;

import java.io.Serializable;

public class LatLng implements Serializable{	
	
	private static final long serialVersionUID = 2123272890781591300L;
	
	private double lat;
	
	private double lng;
	
	public LatLng(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}
}
