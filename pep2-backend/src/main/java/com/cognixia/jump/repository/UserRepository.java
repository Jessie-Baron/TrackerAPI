package com.cognixia.jump.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cognixia.jump.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    
}
