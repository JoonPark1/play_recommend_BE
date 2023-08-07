package com.example.play_app_backend.controllers;


import com.example.play_app_backend.models.Feedback;
import com.example.play_app_backend.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    //constructor
    @Autowired
    public FeedbackController(FeedbackService f){
        this.feedbackService = f;
    }

    @PostMapping
    public String addFeedback(@RequestBody Feedback f){
        System.out.println("adding feedback for song with title: " + f.getSongName());
        this.feedbackService.saveFeedback(f);
        return "successfully saved feedback";
    }

}
