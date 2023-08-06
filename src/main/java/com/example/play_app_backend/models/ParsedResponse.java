package com.example.play_app_backend.models;
import java.util.List; 

public class ParsedResponse {

    private List<Song> songs; 

    public List<Song> getSongs(){
        return songs; 
    }

    public void setSongs(List<Song> s){
        songs = s; 
    }
}
