package com.cognixia.jump.model;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Show")
public class Show {

    @Id
    private String id;
    private String title;
    private String creators;
    private Integer episodes;


    public Show() {
    }

    public Show(String title, String creators, Integer episodes) {
        this.title = title;
        this.creators = creators;
        this.episodes = episodes;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreators() {
        return this.creators;
    }

    public void setCreators(String creators) {
        this.creators = creators;
    }

    public Integer getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Show title(String title) {
        setTitle(title);
        return this;
    }

    public Show creators(String creators) {
        setCreators(creators);
        return this;
    }

    public Show episodes(Integer episodes) {
        setEpisodes(episodes);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Show)) {
            return false;
        }
        Show show = (Show) o;
        return Objects.equals(title, show.title) && Objects.equals(creators, show.creators) && Objects.equals(episodes, show.episodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, creators, episodes);
    }

    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            ", creators='" + getCreators() + "'" +
            ", episodes='" + getEpisodes() + "'" +
            "}";
    }

}
