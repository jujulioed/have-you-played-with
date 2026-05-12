package com.jujulioed;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String apiKey = dotenv.get("RIOT_API_KEY");

        String dbUrl = "jdbc:sqlite:sample.db";

        String gameName = "NSF DukeLowDX";
        String tagLine = "BR2";

        RiotApiClient rpc = new RiotApiClient(apiKey);

        String puuid = rpc.getPuuid(gameName, tagLine);
        System.out.println(puuid);
    }

    
}