package com.example.play_app_backend.services;

import com.example.play_app_backend.interfaces.FeedbackRepository;
import com.example.play_app_backend.models.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository f){
        feedbackRepository = f;
    }

    //method to save a feedback to database!

    public void saveFeedback(Feedback f){
        String songId = f.getSongId();
        //this means that we have to delete old feedback document already in database!
        if(feedbackRepository.existsBySongId(songId)){
            System.out.println("document exists in database...");
            feedbackRepository.deleteBySongId(songId);
        }
        feedbackRepository.save(f);
    }

    //method to get all feedbacks stored in database!
    public List<Feedback> getFeedbacks(){
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackBySongId(String songId){
        return this.feedbackRepository.findBySongId(songId);
    }
}
