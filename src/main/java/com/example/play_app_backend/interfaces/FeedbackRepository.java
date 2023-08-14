package com.example.play_app_backend.interfaces;

import com.example.play_app_backend.models.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepository extends MongoRepository<Feedback, String>{
    //define custom query methods to find and check for existence of MongoDB document by songId value.
    Feedback findBySongId(String songId);
    boolean existsBySongId(String songId);

    void deleteBySongId(String songId);
}
