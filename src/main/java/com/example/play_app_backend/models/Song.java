package com.example.play_app_backend.models;
import java.util.List; 

public class Song {
    private String name; 

    private List<String> artists;

    private String id;

    private boolean liked = true;

    public void setName(String newName){
        name = newName; 
    }

    public void setArtists(List<String> a){
        artists = a;
    }

    public void setId(String ID){
        id = ID;
    }

    public void setLiked(boolean isLiked){
        liked = isLiked;
    }

    public String getSongName(){
        return name; 
    }

    public List<String> getArtistNames(){
        return artists; 
    }

    public String getId(){
        return id;
    }

    public boolean getLiked(){
        return liked;
    }
}
