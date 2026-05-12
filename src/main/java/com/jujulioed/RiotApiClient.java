package com.jujulioed;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class RiotApiClient {

    private final String GET_PUUID_URL = "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s";
    private final String GET_MATCHES_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?start=0&count=100";
    private final String HEADER_APP = "X-Riot-Token";

    private final HttpClient client;
    private final String apiKey;

    public RiotApiClient(String apiKey) {
        this.client = HttpClient.newHttpClient();
        this.apiKey = apiKey;
    }

    public String getPuuid(String gameName, String tagLine) {
        String encodedName = URLEncoder.encode(gameName, StandardCharsets.UTF_8).replace("+", "%20");

        String url = String.format(
            GET_PUUID_URL,
            encodedName,
            tagLine
        );

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header(this.HEADER_APP, this.apiKey)
            .GET()
            .build();

            try {
                HttpResponse<String> response = client.send(
                    request, 
                    HttpResponse.BodyHandlers.ofString()
                );

                System.out.println("Status Code: " + response.statusCode());
                System.out.println(response.body());
                JSONObject json = new JSONObject(response.body());
                return json.getString("puuid");
                
            } catch(Exception e) {
                e.printStackTrace();
                return "None";
            }
    }

    public JSONObject getMatchs() {
        return new JSONObject();
    }

}