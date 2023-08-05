package com.example.play_app_backend;

import okhttp3.*;
import java.util.Base64;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

public class SpotifyApiClient {
    private final String clientId;
    private final String clientSecret;
    private final String tokenUrl = "https://accounts.spotify.com/api/token";

    // constructor
    public SpotifyApiClient() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        // set fields based on environment variable file!
        clientId = dotenv.get("CLIENT_ID");
        System.out.println("clientID: " + clientId); 
        clientSecret = dotenv.get("CLIENT_SECRET");
        System.out.printf("clientSecret: %s", clientSecret); 
    }

    public String getAccessToken() throws Exception {
        OkHttpClient httpClient = new OkHttpClient();
        String base64EncodedCredentials = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());

        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(tokenUrl)
                .addHeader("Authorization", "Basic " + base64EncodedCredentials)
                .post(requestBody)
                .build();

        System.out.println(request.toString()); 

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("got successful response for getting access token..."); 
                String responseBody = response.body().string();
                // Parse the JSON response and extract the access token
                // Example: using Jackson ObjectMapper
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);
                String accessToken = jsonNode.get("access_token").asText();
                return accessToken;
            } else {
                throw new RuntimeException("Failed to get access token: " + response.code());
            }
        }
    }
}
