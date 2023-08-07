package com.example.play_app_backend.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List; 

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackItem {
    @JsonProperty("artists")
    private List<Artist> artists; 
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;

    public List<Artist> getArtists(){
        return artists; 
    }

    public String getName(){
        return name; 
    }

    public String getId(){
        return id;
    }
}
