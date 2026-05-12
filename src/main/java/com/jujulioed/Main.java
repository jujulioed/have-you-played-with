package com.jujulioed;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String apiKey = dotenv.get("RIOT_API_KEY");

        String dbUrl = "jdbc:sqlite:sample.db";

        String gameName = "NSF DukeLowDX";
        String tagLine = "BR2";

        String encodedGameName = URLEncoder.encode(gameName, StandardCharsets.UTF_8)
                        .replace("+", "%20");

        String url = String.format(
                "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/%s/%s",
                encodedGameName,
                tagLine
        );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Riot-Token", apiKey)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Status Code: " + response.statusCode());
            System.out.println(response.body());
            HistoryDB db = new HistoryDB(dbUrl);
            db.registerData(response.body());


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }



    }
}