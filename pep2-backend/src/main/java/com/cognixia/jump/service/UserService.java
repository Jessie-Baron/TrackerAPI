package com.cognixia.jump.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;

	public User getUserByCredentials(String username, String password) throws Exception {
		
		Optional<User> user = userRepo.getByCredentials(username, password);
		if(user.isEmpty()) {
			throw new Exception("User does not exists");
		}
		
		return user.get();
	}
	
	public User createUser(User user) throws Exception {
		
		Optional<User> exists = userRepo.findByUsername(user.getUsername());
		
		if(exists.isPresent()) {
			throw new Exception("User already exists");
		}
		
		user.setRole(Role.ROLE_USER);
		
		User created = user;
		userRepo.save(user);
		return created;
	}

	public User updateUser(String id, User user) throws Exception {
		
		Optional<User> found = userRepo.findById(id);
		
		if(found.isEmpty()) {
			throw new Exception("User does not exists");
		}
		
		User exists = found.get();
		
		if(user.getFirstName()  == null) user.setFirstName(exists.getFirstName());
		if(user.getLastName()   == null) user.setLastName(exists.getLastName());
		if(user.getUsername()   == null) user.setUsername(exists.getUsername());
		if(user.getPassword()   == null) user.setPassword(exists.getPassword());
		if(user.getProfileImg() == null) user.setProfileImg(exists.getProfileImg());
		if(user.getEmail()      == null) user.setEmail(exists.getEmail());
		if(user.getRole()       == null) user.setRole(exists.getRole());
		
		user.setId(id);
		User updated = user;
		userRepo.save(user);
		return updated;
		
	}

	public User deleteUser(String id) throws Exception {
		
		Optional<User> found = userRepo.findById(id);
		
		if(found.isEmpty()) {
			throw new Exception("User does not exists");
		}
		
		User deleted = found.get();
		userRepo.delete(found.get());
		return deleted;
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	
	
	
}
