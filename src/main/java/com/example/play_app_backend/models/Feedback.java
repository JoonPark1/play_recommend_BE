package com.example.play_app_backend.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "feedback")
public class Feedback {

    @JsonProperty("id")
    private String songId;

    @JsonProperty("type")
    private String feedbackType;

    @JsonProperty("name")
    private String name;

    public String getSongId(){
        return songId;
    }

    public String getFeedbackType(){
        return feedbackType;
    }

    public String getSongName(){
        return name;
    }
}
