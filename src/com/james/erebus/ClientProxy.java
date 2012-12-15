package com.james.erebus;

import com.google.gson.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import java.net.URI;
import repr.models.Tournament;

public class ClientProxy {
	
	private String serverURLTournys;
	private Client restClient;
        
	public ClientProxy(String serverURLBase)
	{
		serverURLTournys = serverURLBase + "tournaments";
		restClient = new Client();
	}
	
	public URI postTourny(Tournament t) throws UniformInterfaceException
	{
		ClientResponse response = getTournyPostResource().post(ClientResponse.class, t);
		if(response.getStatus() < 400)
		{
			System.out.println(response.toString());
			return response.getLocation();
		}
		else
		{
			throw new UniformInterfaceException(response);
		}
	}
	/*
	 * "entry_reqs",
 "format",
 "links",
 "location",
 "name",
 "prizes",
 "sponsor",
 "start_date",
 "status"
	 */

	public URI postTourny(String entryReqs, String format, String links, String location, String name,
			String prizes, String sponsor, String startDate, String status) 
	{
		Tournament t = new Tournament();
		t.setEntryReqs(entryReqs);
		t.setFormat(format);
		t.setLinks(links);
		t.setLocation(location);
		t.setName(name);
		t.setPrizes(prizes);
		t.setSponsor(sponsor);
		t.setStartDate(startDate);
		t.setStatus(status);
		return postTourny(t);
		
	}
	
	public JsonObject getTourny(String tournyID, FormatType ft) throws UniformInterfaceException
	{
		Gson gson = new Gson();
		JsonElement response = gson.fromJson(getTournyResourceAsJson(tournyID).get(String.class), JsonElement.class);
		System.out.println(response.toString());
		return response.getAsJsonObject();
	}
	
	public WebResource getTournyResourceAsXML(String tournyID)
	{
		String tournyURL = serverURLTournys + "/" + tournyID + ".xml";
		return restClient.resource(tournyURL);
	}
	
	public WebResource getTournyResourceAsJson(String tournyID)
	{
		String tournyURL = serverURLTournys + "/" + tournyID + ".json";
		return restClient.resource(tournyURL);
	}
	
	public WebResource getTournyPostResource()
	{
		String tournyPostURL = serverURLTournys + ".xml";
		return restClient.resource(tournyPostURL);
	}
	
	/*
	
	
	public WebResource getRouteResource(String routeID)
	{
		String routeURL = serverURLRoutes + "/" + routeID;
		return restClient.resource(routeURL);
	}
	
	public Route getRoute(String routeID) throws UniformInterfaceException
	{
		System.out.println("getRoute(" + routeID + ")");
		Route route = getRouteResource(routeID).get(Route.class);
		if(route != null)
			return route;
		else
			throw new UniformInterfaceException(null);
	}
	
	
	public URI addRoute(String routeID, String origin, String destination) throws UniformInterfaceException
	{
		Route r = new Route();
		r.setId(routeID);
		r.setDestination(destination);
		r.setOrigin(origin);
		return addRoute(r);
		
	}
	
	public URI addRoute(Route route) throws UniformInterfaceException
	{
		ClientResponse response = routesResource.post(ClientResponse.class, route);
		if(response.getStatus() < 400)
		{
			return response.getLocation();
		}
		else
		{
			throw new UniformInterfaceException(response);
		}
	}
	
	public URI addLanguageVersionByTranslation(URI turn, LanguageCode lc)
	{
		URI langVersCol = UriBuilder.fromPath(turn + "/auto_transl").build();
		ClientResponse response = restClient.resource(langVersCol).post(ClientResponse.class, lc.toString().toLowerCase());
		if(response.getStatus() < 400)
		{
			return response.getLocation();
		}
		else
		{
			throw new UniformInterfaceException(response);
		}
	}
	
	public URI addLanguageVersion(URI turn, LanguageCode lc, String text)
	{
		LangVers lv = new LangVers();
		lv.setLanguageCode(lc.toString());
		lv.setText(text);
		return addLanguageVersion(turn, lv);
	}
	
	public URI addLanguageVersion(URI turn, LangVers lv) throws UniformInterfaceException
	{
		URI langVersCol = UriBuilder.fromPath(turn + "/transl").build();
		ClientResponse response = restClient.resource(langVersCol).post(ClientResponse.class, lv);
		if(response.getStatus() < 400)
		{
			return response.getLocation();
		}
		else
		{
			throw new UniformInterfaceException(response);
		}
	}
	
	public URI addTurn(URI route, int distanceFromLast) throws UniformInterfaceException
	{
		Turn turn = new Turn();
		turn.setDistanceFromPreviousInMiles(distanceFromLast);
		ClientResponse response = getTurnsCollectionResource(route).post(ClientResponse.class, turn);
		if(response.getStatus() < 400)
		{
			return response.getLocation();
		}
		else
		{
			throw new UniformInterfaceException(response);
		}
	}
	
	public Route getRoute(String routeID) throws UniformInterfaceException
	{
		System.out.println("getRoute(" + routeID + ")");
		Route route = getRouteResource(routeID).get(Route.class);
		if(route != null)
			return route;
		else
			throw new UniformInterfaceException(null);
	}
	
	public String getRoutes() throws UniformInterfaceException
	{
		String routes = routesResource.get(String.class);
		if(routes != "")
			return routes;
		else
			throw new UniformInterfaceException(null);
	}
	
	public Turn getTurn(String turnID, String routeID) throws UniformInterfaceException
	{
		Turn turn = getTurnResource(turnID, routeID).get(Turn.class);
		if(turn != null)
			return turn;
		else
			throw new UniformInterfaceException(null);
	}
	
	public ClientResponse removeRoute(String routeID)
	{
		ClientResponse response = getRouteResource(routeID).delete(ClientResponse.class);
		if(response.getStatus() < 400)
		{
			return response;
		}
		else
		{
			throw new UniformInterfaceException(response);
		}
	}
	
	public WebResource getTurnResource(String turnID, String routeID)
	{
		String turnURL = getRouteResource(routeID).toString() + "/turnID";
		return restClient.resource(turnURL);
	}
	
	public WebResource getRouteResource(String routeID)
	{
		String routeURL = serverURLRoutes + "/" + routeID;
		return restClient.resource(routeURL);
	}
	
	public WebResource getTurnsCollectionResource(URI route)
	{
		URI turnsCollectionURI = UriBuilder.fromPath(route.toString() + "/turns").build();
		return restClient.resource(turnsCollectionURI);
	}

*/
}
