package com.rota.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
import com.rota.repo.RotaRepositorio;

@Component
public class RotaServiceImpl implements RotaService{

	@Autowired
	private RotaRepositorio rotaRepositorio;
	
	final String GOOGLE_PARADA = "http://maps.googleapis.com/maps/api/geocode/json?address=";
	final String GOOGLE_DIRECTION = "http://maps.googleapis.com/maps/api/directions/json?sensor=false";
	
	@Override
	public Iterable<Rota> findAll() {
		return rotaRepositorio.findAll();
	}

	@Override
	public Rota save(final Rota rota){
		Parada origem, destino;
		try {
			origem = retornaLatitudeLongitude(rota.getOrigem());
			destino = retornaLatitudeLongitude(rota.getDestino());
			
			List<Parada> paradas = calculaRota(origem, destino);
			rota.setParadas(paradas);
			
			return rotaRepositorio.save(rota);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private List<Parada> calculaRota(Parada origem, Parada destino) throws MalformedURLException, UnsupportedEncodingException, IOException, ProtocolException {
		List<Parada> resultado = new ArrayList<Parada>();
		resultado.add(origem);
		String directionJson = retornaJsonDirection(origem, destino);
		
		JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(directionJson);
		
        JsonArray jsonArr = jo.getAsJsonArray("routes");
        JsonObject jo2 = (JsonObject)jsonArr.get(0);
        
        JsonArray legs = jo2.getAsJsonArray("legs");
        JsonObject objectLeg = (JsonObject)legs.get(0);
        
        JsonArray steps = objectLeg.getAsJsonArray("steps");
        
        JsonObject inicio, fim;
        double lat, lng;
        for(JsonElement step : steps){
        	inicio = (JsonObject)step.getAsJsonObject().get("start_location");
        	lat = inicio.get("lat").getAsDouble();
        	lng = inicio.get("lng").getAsDouble();
        	resultado.add(new Parada(lat, lng));
        	
        	fim = (JsonObject)step.getAsJsonObject().get("end_location");
        	lat = fim.get("lat").getAsDouble();
        	lng = fim.get("lng").getAsDouble();
        	resultado.add(new Parada(lat, lng));
        }
        resultado.add(destino);
		return resultado;
	}
	
	private Parada retornaLatitudeLongitude(String endereco) 
			throws MalformedURLException, UnsupportedEncodingException, IOException, ProtocolException{
		String googleJson = retornaJson(endereco);
		
		JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(googleJson);
       
        JsonArray jsonArr = jo.getAsJsonArray("results");
        
    	JsonObject ele = (JsonObject)jsonArr.get(0);
    	JsonObject ob = (JsonObject)ele.get("geometry");
    	JsonObject locations = (JsonObject)ob.get("location");
    	
    	double lat = locations.get("lat").getAsDouble();
    	double lng = locations.get("lng").getAsDouble();
    	
    	return new Parada(endereco, lat, lng);
	}
	
	private String retornaJsonDirection(Parada origem, Parada destino)
			throws MalformedURLException, UnsupportedEncodingException, IOException, ProtocolException {
		URL url = new URL(GOOGLE_DIRECTION + "&origin="+origem.getLatitude()+"," + origem.getLongitude()+"&destination="+destino.getLatitude() + "," + destino.getLongitude());
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

	private String retornaJson(String endereco)
			throws MalformedURLException, UnsupportedEncodingException, IOException, ProtocolException {
		
		URL url = new URL(GOOGLE_PARADA + URLEncoder.encode(endereco, "UTF-8") + "&sensor=true");
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
