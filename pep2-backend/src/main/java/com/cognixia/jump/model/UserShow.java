package com.cognixia.jump.model;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// @Document(collection = "showsWatched")
public class UserShow {

    public enum Status {
        PLANNED, CURRENTLY_WATCHING, COMPLETED
    }
    
    @Id
    private String id;
    
    private String title;
    private Status status;
    private Integer rating;
    private Integer episodesWatched;


    public UserShow() {
    }

    public UserShow(String id, String title, Status status, Integer rating, Integer episodesWatched) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.rating = rating;
        this.episodesWatched = episodesWatched;
    }
    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getEpisodesWatched() {
        return this.episodesWatched;
    }

    public void setEpisodesWatched(Integer episodesWatched) {
        this.episodesWatched = episodesWatched;
    }

    public UserShow id(String id) {
        setId(id);
        return this;
    }

    public UserShow title(String title) {
        setTitle(title);
        return this;
    }

    public UserShow status(Status status) {
        setStatus(status);
        return this;
    }

    public UserShow rating(Integer rating) {
        setRating(rating);
        return this;
    }

    public UserShow episodesWatched(Integer episodesWatched) {
        setEpisodesWatched(episodesWatched);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserShow)) {
            return false;
        }
        UserShow userShow = (UserShow) o;
        return Objects.equals(id, userShow.id) && Objects.equals(title, userShow.title) && Objects.equals(status, userShow.status) && Objects.equals(rating, userShow.rating) && Objects.equals(episodesWatched, userShow.episodesWatched);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status, rating, episodesWatched);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", rating='" + getRating() + "'" +
            ", episodesWatched='" + getEpisodesWatched() + "'" +
            "}";
    }


}
