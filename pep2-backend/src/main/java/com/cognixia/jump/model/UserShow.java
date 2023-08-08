package com.cognixia.jump.model;
import java.util.Objects;

import org.springframework.data.annotation.Id;

public class UserShow {

    public enum Status {
        PLANNED, CURRENTLY_WATCHING, COMPLETED
    }
    
    @Id
    private String id;
    private String title;
    private Status status;
    private Integer episodesWatched;


    public UserShow() {
    }

    public UserShow(String title, Status status, Integer episodesWatched) {
        this.title = title;
        this.status = status;
        this.episodesWatched = episodesWatched;
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

    public Integer getEpisodesWatched() {
        return this.episodesWatched;
    }

    public void setEpisodesWatched(Integer episodesWatched) {
        this.episodesWatched = episodesWatched;
    }

    public UserShow title(String title) {
        setTitle(title);
        return this;
    }

    public UserShow status(Status status) {
        setStatus(status);
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
        return Objects.equals(title, userShow.title) && Objects.equals(status, userShow.status) && Objects.equals(episodesWatched, userShow.episodesWatched);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, status, episodesWatched);
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", episodesWatched='" + getEpisodesWatched() + "'" +
            "}";
    }

}
