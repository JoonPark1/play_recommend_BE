package com.example.play_app_backend.models;
import java.util.List; 

public class Song {
    private String name; 

    private List<String> artists; 

    public void setName(String newName){
        name = newName; 
    }

    public void setArtists(List<String> a){
        artists = a;
    }

    public String getSongName(){
        return name; 
    }

    public List<String> getArtistNames(){
        return artists; 
    }
}
