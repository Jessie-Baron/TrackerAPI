package com.cognixia.jump.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.InsufficientPermissionsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UserExistsException;
import com.cognixia.jump.model.AverageResult;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.model.UserShow;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
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
		created.setShowsWatched(new ArrayList<>());
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
		created.setShowsWatched(new ArrayList<>());
		userRepo.save(user);
		return created;
	}

	public UserShow addShow(String id, UserShow userShow) throws Exception {
		
		Optional<User> found = userRepo.findById(id);
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		User user = found.get();
		
		
		// Generate unique id
		int unique_id= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
		String showId = String.valueOf(unique_id) + user.getId() + userShow.getTitle().replace(" ", "");
		
		userShow.setId(showId);
		
		Query query = new Query(Criteria.where("_id").is(id));
		Update update = new Update().push("showsWatched", userShow);
		
		mongoTemplate.updateFirst(query, update, User.class);
		
		return userShow;
	}
	
	public int getGlobalShowCompletedCount(String title) throws ResourceNotFoundException {
		
		Optional<Integer> results = userRepo.getCountOfHowManyUsersCompletedAShow(title);
		
		if(results.isEmpty()) {
			throw new ResourceNotFoundException("Show");
		}
		
		return results.get().intValue();
	}

	public AggregationResults<AverageResult> getShowsAverageRating(String title) {
		
		AggregationResults<AverageResult> avg = userRepo.getAverageRatingOfAShow(title);
		System.out.println(avg.toString());
		if (avg == null) return null;
		else return avg;
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
	
	public UserShow updateShow(String id, UserShow show) throws Exception {
		
		Optional<User> found = userRepo.findById(id);
		if(found.isEmpty()) {
			throw new ResourceNotFoundException("User");
		}
		
		Query select = Query.query(Criteria.where("showsWatched._id").is(show.getId()));
		Update update = new Update();
		
		if(show.getEpisodesWatched() != null) update.set("showsWatched.$.episodesWatched", show.getEpisodesWatched());
		if(show.getStatus() != null) update.set("showsWatched.$.status", show.getStatus());
		if(show.getRating() != null) update.set("showsWatched.$.rating", show.getRating());
		User updated = mongoTemplate.findAndModify(select, update, User.class);
		
		if(updated == null) {
			throw new ResourceNotFoundException("Show");
		}
		
		UserShow updatedShow = show;

		return updatedShow;
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
