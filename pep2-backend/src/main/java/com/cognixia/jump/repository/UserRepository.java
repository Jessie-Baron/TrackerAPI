package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.cognixia.jump.model.AverageResult;
import com.cognixia.jump.model.User;


public interface UserRepository extends MongoRepository<User, String> {
    
	@Query("{'username': ?0, 'password': ?1}")
	public Optional<User> getByCredentials(String username, String password);
	
	public Optional<User> findByUsername(String username);
	

	@Aggregation( pipeline = {
			"{$match: {'showsWatched.title': ?0,'showsWatched.status': 'COMPLETED'}}",
			"{$group: {_id: null, count:{$sum: 1}}}",
			"{$project: {_id:0}}"
	})
    public Optional<Integer> getCountOfHowManyUsersCompletedAShow(String title);
	
    @Aggregation(pipeline = { 
        "{$project: {_id: 1, showsWatched: 1}}",
        "{$match: {$and: [{'showsWatched.title': ?0}, {'showsWatched.rating': {$gte: 1}}, {'showsWatched.rating': {$lte: 5}}]}}", 
        "{$unwind: '$showsWatched'}",
        "{$group: {_id: null, avgRating: {$avg: '$showsWatched.rating'}}}", 
        "{$project: {_id: 0, avgRating: 1}}"
    })
    public Optional<Double> getAverageRatingOfAShow(String title);
}
