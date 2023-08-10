package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
	
	
}
