package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Title;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.UserShow;
import com.cognixia.jump.service.UserService;



@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/********************
	 GET OPERATIONS
	 ********************/
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(){
		
		List<User> users = userService.getAllUsers();
		
		return ResponseEntity.status(200).body(users);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable String id) throws Exception{
		
		User user = userService.getUserById(id);
		
		return ResponseEntity.status(200).body(user);
	}

	
	/********************
	 POST OPERATIONS
	 ********************/
	
	@PostMapping("/user/auth")
	public ResponseEntity<?> getUserByCredentials(@Valid @RequestBody User user) throws Exception{
		
		User validUser = userService.getUserByCredentials(user.getUsername(), user.getPassword());
		
		return ResponseEntity.status(200).body(validUser);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) throws Exception{
		
		User created = userService.createUser(user);
		
		return ResponseEntity.status(201).body(created);
	}

	@PostMapping("/users/count")
	public ResponseEntity<?> getCompletedCount(@RequestBody Title title) {

		return ResponseEntity.status(200).body(userService.getGlobalShowCompletedCount(title.getTitle()));
	} 

	@PostMapping("/users/rating")
	public ResponseEntity<AggregationResults<Object>> getAverageRating(@RequestBody Title title) {

		userService.getShowsAverageRating(title.getTitle());

		return ResponseEntity.status(200).build();
	}
	
	// Will remove method once in production
	@PostMapping("/user/admin")
	public ResponseEntity<?> createAdmin(@Valid @RequestBody User user) throws Exception{
		
		User created = userService.createAdmin(user);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@PostMapping("/user/show/{id}")
	public ResponseEntity<?> addShow(@PathVariable String id, @RequestBody UserShow userShow ) throws Exception{
		
		UserShow show = userService.addShow(id, userShow);
		
		return ResponseEntity.status(201).body(show);
	}
	
	
	/********************
	 UPDATE OPERATIONS
	 ********************/
	@PatchMapping("/user/{id}") 
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user ) throws Exception{
		
		User updated = userService.updateUser(id, user);
		
		return ResponseEntity.status(201).body(updated);
	}
	
	@PatchMapping("/user/updateShow/{id}")
	public ResponseEntity<?> updateUserShow(@PathVariable String id, @RequestBody UserShow show) throws Exception {
		
		UserShow updated = userService.updateShow(id, show);
		
		return ResponseEntity.status(200).body(updated);
	}
	
	/********************
	 DELETE OPERATIONS
	 ********************/
	
	// ID of the user you want to delete goes within the URI, while the body request the User object of the admin
	// The request body can just have {id: "<id here>"}, it does not need to have all fields
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String userId, @RequestBody User admin) throws Exception{
		
		User deleted = userService.deleteUser(userId, admin);
		
		return ResponseEntity.status(201).body(deleted);
	}
	
	@DeleteMapping("/user/{id}/{showId}")
	public ResponseEntity<?> removeShow(@PathVariable String id, @PathVariable String showId) throws ResourceNotFoundException{
		
		UserShow userShow = userService.removeShow(id, showId);
		
		return ResponseEntity.status(200).body(userShow);
	}
	
	
}
