package com.cognixia.jump.model;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("User")
public class User {

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }
    
    @Id
    private String id;
    
    private String username;
    private String password;
    private String email;
    private Role role;
    private List<Show> showsWatched;


    public User() {
    }


	public User(String id, String username, String password, String email, Role role, List<Show> showsWatched) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.showsWatched = showsWatched;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public List<Show> getShowsWatched() {
		return showsWatched;
	}


	public void setShowsWatched(List<Show> showsWatched) {
		this.showsWatched = showsWatched;
	}

	

   

}
