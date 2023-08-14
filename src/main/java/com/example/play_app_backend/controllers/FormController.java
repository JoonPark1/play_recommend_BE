package com.example.play_app_backend.controllers;
import java.util.HashSet;
import com.example.play_app_backend.models.*;
import com.example.play_app_backend.services.FeedbackService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.play_app_backend.FormData;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.play_app_backend.SpotifyApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList; 

@RestController
@RequestMapping("/api")
public class FormController {
    private SpotifyApiClient spotifyClient;

    private FeedbackService feedbackService;

    @Autowired
    public FormController(FeedbackService f) {
        spotifyClient = new SpotifyApiClient();
        feedbackService = f;
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
            // now, with accessToken, we should utilize the data gotten from front-end to
            // look for Spotify tracks
            // that best match user criteria!

            // base url of api endpoint for /search!
            String apiUrl = "https://api.spotify.com/v1/search";
            String queryString = constructQueryString(data);
            // full url containing necessary query params!
            String fullUrl = apiUrl + '?' + "q=" + queryString + "&type=track";
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
                    List<Feedback> userFeedback = feedbackService.getFeedbacks();

                    ParsedResponse pr = new ParsedResponse(); 
                    List<Song> songs = new ArrayList<Song>();
                    HashSet<String> h = new HashSet<String>();
                    for(TrackItem ti: tracks){
                        Song newSong = new Song();
                        String songName = ti.getName();
                        System.out.println("song name: " + songName);
                        System.out.println("song id: " + ti.getId());
                        if(h.contains(songName)){
                            continue;
                        }
                        else{
                            h.add(songName);
                        }
                        newSong.setName(songName);
                        newSong.setId(ti.getId());
                        List<String> artistNames = new ArrayList<String>(); 
                        List<Artist> curTrackArtists = ti.getArtists();
                        for(Artist a: curTrackArtists){
                            artistNames.add(a.getName());
                        }
                        newSong.setArtists(artistNames);
                        //see if the song has a feedback: if so, set the custom feedback! Otherwise, default to liked cause we can't assume what user likes/dislikes for specific song!
                        for(Feedback f: userFeedback){
                            System.out.println("feedback id: " + f.getSongId());
                            if(f.getSongId().equals(ti.getId())){
                                boolean isLiked = f.getFeedbackType().equalsIgnoreCase("Thumbs Up");
                                newSong.setLiked(isLiked);
                                break;
                            }
                        }
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
