package com.example.play_app_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Playlist {
    @JsonProperty("id")
    private Object id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("songs")
    private PlaylistSong[] songs;

    public Object getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }

    public PlaylistSong[] getSongs(){
        return songs;
    }

    public void setSongs(PlaylistSong[] ps){
        songs = ps;
    }
}
