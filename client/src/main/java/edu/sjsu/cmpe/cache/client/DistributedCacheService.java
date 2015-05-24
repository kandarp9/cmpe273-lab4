package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.Future;

/**
 * Distributed cache service
 * 
 */
public class DistributedCacheService implements CacheServiceInterface {
	private final String cacheServerUrl;

	public DistributedCacheService(String serverUrl) {
		this.cacheServerUrl = serverUrl;
	}

	/**
	 * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#get(long)
	 */
	@Override
	public String get(long key) {
		HttpResponse<JsonNode> response = null;
		try {
			response = Unirest.get(this.cacheServerUrl + "/cache/{key}")
					.header("accept", "application/json")
					.routeParam("key", Long.toString(key)).asJson();
		} catch (UnirestException e) {
			System.err.println(e);
		}
		String value = response.getBody().getObject().getString("value");

		return value;
	}

	/**
	 * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#put(long,
	 *      java.lang.String)
	 */
	@Override
	public void put(long key, String value) {
		// Asynchronous calls added
		Future<HttpResponse<JsonNode>> future = null;

		future = Unirest.put(this.cacheServerUrl + "/cache/{key}/{value}")
				.header("accept", "application/json")
				.routeParam("key", Long.toString(key))
				.routeParam("value", value)
				.asJsonAsync(new Callback<JsonNode>() {

					public void failed(UnirestException e) {
						System.out.println("PUT request has failed.");
					}

					public void completed(HttpResponse<JsonNode> response) {
						System.out.println("PUT request succeedded.");

					}

					public void cancelled() {
						System.out.println("PUT request is cancelled");
					}
				});

		if (future.isDone()) {
			System.out.println("Failed to add to the cache.");
		}
	}

	/**
	 * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#delete(long)
	 * 
	 */
	@Override
	public void delete(long key) {
		Future<HttpResponse<JsonNode>> response = null;
		response = Unirest.delete(this.cacheServerUrl + "/cache/{key}")
				.header("accept", "application/json")
				.routeParam("key", Long.toString(key))
				.asJsonAsync(new Callback<JsonNode>() {

					public void failed(UnirestException e) {
						System.out.println("Delete request Failed");
					}

					public void completed(HttpResponse<JsonNode> response) {
						System.out.println("Delete request run successfully");
					}

					public void cancelled() {
						System.out.println("Delete request has been cancelled");
					}

				});

		if (response.isDone()) {
			System.out.println("Failed to delete from cache.");
		}
	}
}
