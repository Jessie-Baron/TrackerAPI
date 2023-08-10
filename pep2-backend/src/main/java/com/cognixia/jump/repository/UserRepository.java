package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cognixia.jump.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    
	@Query("{'username': ?0, 'password': ?1}")
	public Optional<User> getByCredentials(String username, String password);
	
	public Optional<User> findByUsername(String username);
	
    @Query(value = "{$and: [{showsWatched.title: 0?}, {showWatched.status: 'COMPLETED'}]}", count = true)
    public int countHowManyUsersCompletedAShow(String title);
	
    @Aggregation(pipeline = {"[ {$match: {$and: [{showsWatched.title: 0?}, {showsWatched.rating: {$gte: 1}}, {showsWatched.rating: {$lte: 5}}]}}, {$group: {_id: null, avgRating: {$avg: '$showsWatched.rating'}}}]"})
    public List<Object> getAverageRatingOfAShow(String title);
}
