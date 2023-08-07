package com.example.play_app_backend.interfaces;

import com.example.play_app_backend.models.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String>{
    //custom query methods here for this interface for Feedback collection!
}
