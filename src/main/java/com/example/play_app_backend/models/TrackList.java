package com.example.play_app_backend.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List; 

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackList {
    @JsonProperty("items")
    private List<TrackItem> items; 


    public List<TrackItem> getItems(){
        return items; 
    }

}
