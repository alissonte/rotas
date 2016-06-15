package com.rotas.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
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
		} catch (Exception e){//TODO tratar exceções mais especificas
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
		Step step = new Step();
		step.setLatlngs(calculateRoute(origem, destino));
		step.setColor(RouteUtil.COLOR_RED);
		step.setWeight(RouteUtil.WEIGHT);
		return step;
	}
	
	/**
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	private Step receiveLatLng(String address) throws Exception{
		String url = GOOGLE_PARADA + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
		String googleJson = formatJson(url);
		
		JsonObject locations = getLocationPoint(googleJson);
    	
    	double lat = locations.get("lat").getAsDouble();
    	double lng = locations.get("lng").getAsDouble();
    	
    	return new Step(address, lat, lng);
	}
	
	/**
	 * 
	 * @param origem
	 * @param destino
	 * @return
	 * @throws Exception
	 */
	private List<LatLng> calculateRoute(Step origem, Step destino) throws Exception {
		JsonObject start, end;
        double lat, lng;
		List<LatLng> latLngs = new LinkedList<>();
			
		String url = GOOGLE_DIRECTION + "&origin="+origem.getLat()+"," + origem.getLng()+"&destination="+destino.getLat() + "," + destino.getLng();
		String directionJson = formatJson(url);
		
		JsonArray steps = getAllSteps(directionJson);
        
        latLngs.add(new LatLng(origem.getLat(), origem.getLng()));	
        for(JsonElement step : steps){
        	start = (JsonObject)step.getAsJsonObject().get("start_location");
        	lat = start.get("lat").getAsDouble();
        	lng = start.get("lng").getAsDouble();
        	
        	LatLng latLng = new LatLng(lat, lng);
        	latLngs.add(latLng);
        	
        	end = (JsonObject)step.getAsJsonObject().get("end_location");
        	lat = end.get("lat").getAsDouble();
        	lng = end.get("lng").getAsDouble();
        	
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
