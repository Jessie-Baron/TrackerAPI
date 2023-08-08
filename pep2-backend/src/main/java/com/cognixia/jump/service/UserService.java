package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UserExistsException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	/********************
		GET OPERATIONS
	 ********************/
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User getUserByCredentials(String username, String password) throws Exception {
		
		Optional<User> user = userRepo.getByCredentials(username, password);
		if(user.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		return user.get();
	}
	
	
	/********************
		POST OPERATIONS
	 ********************/
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

	/********************
		UPDATE OPERATIONS
	 ********************/
	public User updateUser(String id, User user) throws Exception {

		Optional<User> found = userRepo.findById(id);
		
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		User exists = found.get();
		
		if(user.getFirstName()  != null) exists.setFirstName(user.getFirstName());
		if(user.getLastName()   != null) exists.setLastName(user.getLastName());
		if(user.getUsername()   != null) exists.setUsername(user.getUsername());
		if(user.getPassword()   != null) exists.setPassword(user.getPassword());
		if(user.getProfileImg() != null) exists.setProfileImg(user.getProfileImg());
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