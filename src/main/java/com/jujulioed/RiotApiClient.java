package com.jujulioed;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;

public class RiotApiClient {

    private final String GET_PUUID_URL = "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s";
    private final String GET_MATCHES_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?start=0&count=100";
    private final String GET_MATCH_DETAILS_URL = "https://americas.api.riotgames.com/lol/match/v5/matches/%s";
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

    public ArrayList<String> getMatchs(String puuid) {
        String matchsUrl = String.format(
            GET_MATCHES_URL,
            puuid
        );

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(matchsUrl))
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
            String onlyMatches = response.body()
                .replace("[", "")
                .replace("]", "");

            ArrayList<String> matchesList = new ArrayList<>(Arrays.asList(onlyMatches.split(",")));
            return matchesList;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String getMatchDetails(String matchId) {
        String matchUrl = String.format(
            GET_MATCH_DETAILS_URL,
            matchId
        );

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(matchUrl))
            .header(this.HEADER_APP, this.apiKey)
            .GET()
            .build();

        try {
            Thread.sleep(1000);
            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );
            
            System.out.println("Status Code: " + response.statusCode());
            System.out.println(response.body());
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

}