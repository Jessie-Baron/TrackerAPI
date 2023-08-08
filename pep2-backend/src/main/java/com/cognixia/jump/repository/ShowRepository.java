package com.cognixia.jump.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cognixia.jump.model.Show;

public interface ShowRepository extends MongoRepository<Show, String> {
    
}
