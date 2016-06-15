package com.rotas.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rotas.repo.RouteRepository;
import com.rotas.utils.RouteUtil;

@Component
public class RouteServiceImpl implements RouteService{

	@Autowired
	private RouteRepository routeRepository;
	
	final String GOOGLE_PARADA = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	final String GOOGLE_DIRECTION = "http://maps.googleapis.com/maps/api/directions/json?sensor=false";
	
	@Override
	public Iterable<Route> findAll() {
		return routeRepository.findAll();
	}

	@Override
	public Route save(final Route rota){
		Step origem, destino;
		try {
			origem = receiveLatLng(rota.getOrigin());
			destino = receiveLatLng(rota.getDestino());
			Step p1 = newStep(origem, destino);
			rota.setStep(p1);
			return routeRepository.save(rota);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @return
	 * @throws Exception
	 */
	private Step newStep(Step origem, Step destino)throws Exception {
		Step p1 = new Step();
		p1.setLatlngs(calculateRoute(origem, destino));
		p1.setColor(RouteUtil.COLOR_RED);
		p1.setWeight(RouteUtil.WEIGHT);
		return p1;
	}
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @return
	 * @throws Exception
	 */
	private List<LatLng> calculateRoute(Step origem, Step destino) throws Exception {		
		List<LatLng> latLngs = new ArrayList<>();
			
		String url = GOOGLE_DIRECTION + "&origin="+origem.getLat()+"," + origem.getLng()+"&destination="+destino.getLat() + "," + destino.getLng();
		String directionJson = formatJson(url);
		
		JsonArray steps = getAllSteps(directionJson);
        
        JsonObject inicio, fim;
        double lat, lng;
        
        latLngs.add(new LatLng(origem.getLat(), origem.getLng()));	
        for(JsonElement step : steps){
        	inicio = (JsonObject)step.getAsJsonObject().get("start_location");
        	lat = inicio.get("lat").getAsDouble();
        	lng = inicio.get("lng").getAsDouble();
        	
        	LatLng latLng = new LatLng(lat, lng);
        	latLngs.add(latLng);
        	
        	fim = (JsonObject)step.getAsJsonObject().get("end_location");
        	lat = fim.get("lat").getAsDouble();
        	lng = fim.get("lng").getAsDouble();
        	
        	latLng = new LatLng(lat, lng);
        	
        	latLngs.add(latLng);
        }
        latLngs.add(new LatLng(destino.getLat(), destino.getLng()));
		return latLngs;
	}

	/**
	 * 
	 * @param directionJson
	 * @return
	 */
	private JsonArray getAllSteps(String directionJson) {
		JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(directionJson);
		
        JsonArray jsonArr = jo.getAsJsonArray("routes");
        JsonObject jo2 = (JsonObject)jsonArr.get(0);
        
        JsonArray legs = jo2.getAsJsonArray("legs");
        JsonObject objectLeg = (JsonObject)legs.get(0);
        
        JsonArray steps = objectLeg.getAsJsonArray("steps");
		return steps;
	}
	
	
	/**
	 * 
	 * @param endereco
	 * @return
	 * @throws Exception
	 */
	private Step receiveLatLng(String endereco) throws Exception{
		String url = GOOGLE_PARADA + URLEncoder.encode(endereco, "UTF-8") + "&sensor=true";
		String googleJson = formatJson(url);
		
		JsonObject locations = getLocationPoint(googleJson);
    	
    	double lat = locations.get("lat").getAsDouble();
    	double lng = locations.get("lng").getAsDouble();
    	
    	return new Step(endereco, lat, lng);
	}
	
	/**
	 * 
	 * @param googleJson
	 * @return
	 */
	private JsonObject getLocationPoint(String googleJson) {
		JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(googleJson);
       
        JsonArray jsonArr = jo.getAsJsonArray("results");
        
    	JsonObject ele = (JsonObject)jsonArr.get(0);
    	JsonObject ob = (JsonObject)ele.get("geometry");
    	JsonObject locations = (JsonObject)ob.get("location");
		return locations;
	}

	/**
	 * 
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	private String formatJson(String urlStr)throws Exception {		
		URL url = new URL(urlStr);		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if (conn.getResponseCode() != 200) {
		    throw new RuntimeException("Falhou : Erro : " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String saida = "", strJson = "";
		while ((saida = br.readLine()) != null) {
		    strJson += saida;
		}
		br.close();
		
		return strJson;
	}

}
