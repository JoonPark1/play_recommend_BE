package com.example.play_app_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaylistSong {
    @JsonProperty("artists")
    private String[] artists;

    @JsonProperty("songId")
    private String songId;

    @JsonProperty("title")
    private String title;
}
