package com.example.play_app_backend.controllers;

import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.play_app_backend.FormData;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.play_app_backend.SpotifyApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.play_app_backend.models.SpotifyResponse; 
import com.example.play_app_backend.models.TrackItem; 
import com.example.play_app_backend.models.Artist; 
import com.example.play_app_backend.models.ParsedResponse;
import com.example.play_app_backend.models.Song; 
import java.util.List;
import java.util.ArrayList; 

@RestController
@RequestMapping("/api")
public class FormController {
    private SpotifyApiClient spotifyClient;

    public FormController() {
        spotifyClient = new SpotifyApiClient();
    }

    // helper to construct the query parameter q's query string!
    public String constructQueryString(FormData d) {
        String query = String.format("artist:%s genre:%s",
                d.getArtist(),
                d.getGenre());
        System.out.printf("Query String: %s", query);
        return query;
    }

    @PostMapping
    public String handleFormSubmission(@RequestBody FormData data) {
        try {
            String accessToken = spotifyClient.getAccessToken(); 
            System.out.println("accessToken: " + accessToken); 
            // now, with accessToken, we should utilize the data gotten from front-end to
            // look for Spotify tracks
            // that best match user criteria!

            // base url of api endpoint for /search!
            String apiUrl = "https://api.spotify.com/v1/search";
            String queryString = constructQueryString(data);
            // full url containing necessary query params!
            String fullUrl = apiUrl + '?' + "q=" + queryString + "&type=track";
            System.out.println("full Url: " + fullUrl); 
            OkHttpClient httpClient = new OkHttpClient();
            Request req = new Request.Builder().url(fullUrl).addHeader("Authorization", "Bearer " + accessToken).get()
                    .build();
            try (Response resp = httpClient.newCall(req).execute()) {
                if (resp.isSuccessful()) {
                    String responseBody = resp.body().string();
                    ObjectMapper mapper = new ObjectMapper();
                    //parse response to conform to my custom SpotifyResponse model!  
                    SpotifyResponse spotifResp = mapper.readValue(responseBody, SpotifyResponse.class); 
                    List<TrackItem> tracks = spotifResp.getTracks().getItems(); 


                    ParsedResponse pr = new ParsedResponse(); 
                    List<Song> songs = new ArrayList<Song>(); 
                    for(TrackItem ti: tracks){
                        Song newSong = new Song(); 
                        newSong.setName(ti.getName()); 
                        List<String> artistNames = new ArrayList<String>(); 
                        List<Artist> curTrackArtists = ti.getArtists(); 
                        System.out.println("Starting to write names of artists for current track: " + ti.getName()); 
                        for(Artist a: curTrackArtists){
                            artistNames.add(a.getName()); 
                            System.out.println("current artist: " + a.getName()); 
                        }
                        newSong.setArtists(artistNames); 
                        System.out.println("Done writing names!"); 
                        songs.add(newSong); 
                    }
                    pr.setSongs(songs); 
                    String jsonResponse = mapper.writeValueAsString(pr);
                    return jsonResponse; 
                } else {
                    return "failed to fetch tracks from spotify /search api endpoint!";
                }
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        } catch (Exception e) {
            String errorMsg = "can't get access token" + e.getMessage(); 
            return errorMsg;
        }
    }
}
