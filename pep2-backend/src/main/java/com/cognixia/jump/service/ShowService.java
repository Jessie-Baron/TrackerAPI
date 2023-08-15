package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.InsufficientPermissionsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Show;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.ShowRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class ShowService {
    
    @Autowired
    private ShowRepository repo;
    
    @Autowired 
    private UserRepository userRepo;
    
    public void checkPermissions(String userId) throws Exception {
    	
    	Optional<User> found = userRepo.findById(userId);
    	
    	if(found.isEmpty()) {
    		throw new ResourceNotFoundException("User");
    	}
    	
    	if(found.get().getRole() != Role.ROLE_ADMIN) {
    		throw new InsufficientPermissionsException();
    	}
    	
    }

    public List<Show> getShows() {
        return repo.findAll();
    }

    public Optional<Show> getShowById(String id) {
        return repo.findById(id);
    }

    public Show createShow(Show show, String userId) throws Exception{
    	
    	// Validation check to see if show already exists
    	
    	// Validate user role
    	checkPermissions(userId);
    	
    	
        show.setId(null);
        return repo.save(show);

    }
    
    public Show updateShow(Show show, String userId) throws Exception {
    	
    	checkPermissions(userId);
    	
        Optional<Show> foundShow = repo.findById(show.getId());
        if (foundShow.isEmpty()) return null;
        
        return repo.save(show);
    }

    public Show deleteShowById(String id, String userId) throws Exception {
    	
    	checkPermissions(userId);
    	
        Optional<Show> foundShow = repo.findById(id);
        if (foundShow.isEmpty()) return null;
        repo.deleteById(id);
        return foundShow.get();
    }
}
