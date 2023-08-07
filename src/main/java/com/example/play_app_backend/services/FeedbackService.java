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
        feedbackRepository.save(f);
    }
    //method to get all feedbacks stored in database!
    public List<Feedback> getFeedbacks(){
        return feedbackRepository.findAll();
    }


}
