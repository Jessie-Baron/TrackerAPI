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
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String profileImg;
    private String email;
    private Role role;
    private List<Show> showsWatched;


    public User() {
    }

    public User(String firstName, String lastName, String username, String password, String profileImg, String email, Role role, List<Show> showsWatched) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.profileImg = profileImg;
        this.email = email;
        this.role = role;
        this.showsWatched = showsWatched;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImg() {
        return this.profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Show> getShowsWatched() {
        return this.showsWatched;
    }

    public void setShowsWatched(List<Show> showsWatched) {
        this.showsWatched = showsWatched;
    }

    public User firstName(String firstName) {
        setFirstName(firstName);
        return this;
    }

    public User lastName(String lastName) {
        setLastName(lastName);
        return this;
    }

    public User username(String username) {
        setUsername(username);
        return this;
    }

    public User password(String password) {
        setPassword(password);
        return this;
    }

    public User profileImg(String profileImg) {
        setProfileImg(profileImg);
        return this;
    }

    public User email(String email) {
        setEmail(email);
        return this;
    }

    public User role(Role role) {
        setRole(role);
        return this;
    }

    public User showsWatched(List<Show> showsWatched) {
        setShowsWatched(showsWatched);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(profileImg, user.profileImg) && Objects.equals(email, user.email) && Objects.equals(role, user.role) && Objects.equals(showsWatched, user.showsWatched);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, profileImg, email, role, showsWatched);
    }

    @Override
    public String toString() {
        return "{" +
            " firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", profileImg='" + getProfileImg() + "'" +
            ", email='" + getEmail() + "'" +
            ", role='" + getRole() + "'" +
            ", showsWatched='" + getShowsWatched() + "'" +
            "}";
    }

}
