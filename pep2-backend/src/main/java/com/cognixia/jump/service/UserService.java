package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.InsufficientPermissionsException;
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
	
	public User createAdmin(User user) throws Exception {
		
		Optional<User> exists = userRepo.findByUsername(user.getUsername());
		
		if(exists.isPresent()) {
			throw new UserExistsException("User");
		}
		
		user.setRole(Role.ROLE_ADMIN);
		
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
		
		// Checks if the user has an existing list	
		List<UserShow> shows = user.getShowsWatched() == null ? new ArrayList<>() : user.getShowsWatched();
		
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
	public User deleteUser(String userId, User admin) throws Exception {
		
		if(admin.getId() == null) {
			throw new ResourceNotFoundException("ID");
		}
		
		Optional<User> foundUser = userRepo.findById(userId);
		Optional<User> foundAdmin = userRepo.findById(admin.getId());
		
		if(foundUser.isEmpty() || foundAdmin.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		if(foundAdmin.get().getRole() == Role.ROLE_USER) {
			throw new InsufficientPermissionsException();
		}
		
		User deleted = foundUser.get();
		userRepo.delete(foundUser.get());
		return deleted;
	}

	public UserShow removeShow(String id, String showId) throws ResourceNotFoundException {
		
		Optional<User> found = userRepo.findById(id);
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		User user = found.get();
		List<UserShow> list = user.getShowsWatched();
		Optional<UserShow> removed = Optional.empty();
		
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().equals(showId)) {
				removed = Optional.of(list.get(i));
				list.remove(i);
			}
		}
		
		if(removed.isEmpty()) {
			throw new ResourceNotFoundException("Show");
		}
		
		user.setShowsWatched(list);
		userRepo.save(user);
		
		return removed.get() ;
	}



	
	
	
	
}
