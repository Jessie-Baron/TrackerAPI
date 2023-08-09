package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UserExistsException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.model.UserShow;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
//	@Autowired
//	private MongoTemplate mongoTemplate;
	
	/********************
		GET OPERATIONS
	 ********************/
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public User getUserById(String id) throws ResourceNotFoundException {
		
		Optional<User> found = userRepo.findById(id);
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
	
		return found.get();
	}

	/********************
		POST OPERATIONS
	 ********************/
	public User getUserByCredentials(String username, String password) throws Exception {
		
		Optional<User> user = userRepo.getByCredentials(username, password);
		if(user.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		return user.get();
	}
	
	public User createUser(User user) throws Exception {
		
		Optional<User> exists = userRepo.findByUsername(user.getUsername());
		
		if(exists.isPresent()) {
			throw new UserExistsException("User");
		}
		
		user.setRole(Role.ROLE_USER);
		
		User created = user;
		userRepo.save(user);
		return created;
	}

	public UserShow addShow(String id, UserShow userShow) throws Exception {
		
		Optional<User> found = userRepo.findById(id);
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		User user = found.get();
		List<UserShow> shows;
		
		// Checks if the user has an existing list
		if(user.getShowsWatched() == null) {
			shows = new ArrayList<>();
		} else {
			shows = user.getShowsWatched();
		}
		
		// Generate unique id
		int unique_id= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
		String showId = String.valueOf(unique_id) + user.getId() + userShow.getTitle().replace(" ", "");
		
		userShow.setId(showId);
		shows.add(userShow);
		user.setShowsWatched(shows);
		
		userRepo.save(user);
		
//		Query query = new Query(Criteria.where("_id").is(id));
//		Update update = new Update().push("showsWatched", userShow);
//		
//		mongoTemplate.updateFirst(query, update, User.class);
		
		return userShow;
	}
	
	/********************
		UPDATE OPERATIONS
	 ********************/
	public User updateUser(String id, User user) throws Exception {

		Optional<User> found = userRepo.findById(id);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		User exists = found.get();
		
		if(user.getUsername()   != null) exists.setUsername(user.getUsername());
		if(user.getPassword()   != null) exists.setPassword(user.getPassword());
		if(user.getEmail()      != null) exists.setEmail(user.getEmail());
		if(user.getRole()       != null) exists.setRole(user.getRole());
		
		User updated = exists;
		userRepo.save(exists);
		return updated;
		
	}

	/********************
		DELETE OPERATIONS
	 ********************/
	public User deleteUser(String id) throws Exception {
		
		Optional<User> found = userRepo.findById(id);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		User deleted = found.get();
		userRepo.delete(found.get());
		return deleted;
	}



	
	
	
	
}
