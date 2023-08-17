package com.example.play_app_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

@Document(collection = "users")
public class User {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("playlists")
    private Playlist[] playlists;

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

    public Playlist[] getPlaylists(){
        return playlists;
    }

    public void setPlaylists(Playlist[] p){
            playlists = p;
    }
}
