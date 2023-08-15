package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cognixia.jump.model.AverageResult;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.UserShow;


public interface UserRepository extends MongoRepository<User, String> {
    
	@Query("{'username': ?0, 'password': ?1}")
	public Optional<User> getByCredentials(String username, String password);
	
	public Optional<User> findByUsername(String username);
	
//	@Query("{_id: ?0,showsWatched : {id : ?1}}")
//	public Optional<UserShow> getUserShow(String userId,String showId);
	
	/*
	 * db.User.deleteOne({_id: ObjectId("64d3df241217646265e70d4c"), "showsWatched._id": "169160682664d3df241217646265e70d4cHunter"});
	 */

    @Query(value = "{$and: [{showsWatched.title: 0?}, {showWatched.status: 'COMPLETED'}]}", count = true)
    public List<Object> getCountOfHowManyUsersCompletedAShow(String title);
	
    @Aggregation(pipeline = { 
        "$project: {_id: 1, showsWatched: 1}}",
        "{$match: {$and: [{'showsWatched.title': 0?}, {'showsWatched.rating': {$gte: 1}}, {'showsWatched.rating': {$lte: 5}}]}}", 
        "{$unwind: '$showsWatched'}",
        "{$group: {_id: null, avgRating: {$avg: '$showsWatched.rating'}}}", 
        "{$project: {_id: 0, avgRating: 1}}"
    })
    public AggregationResults<AverageResult> getAverageRatingOfAShow(String title);

    // @Aggregation(pipeline = "[ {$match: {$and: [{'showsWatched.title': 'Breaking Bad'}, {'showsWatched.rating': {$gte: 1}}, {'showsWatched.rating': {$lte: 5}}]}}, {$unwind: '$showsWatched'}, {$group: {_id: null, avgRating: {$avg: '$showsWatched.rating'}}}]")
    // public Double getAverageRatingOfAShow();
}
