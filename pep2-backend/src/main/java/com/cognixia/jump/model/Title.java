package com.cognixia.jump.model;
import java.util.Objects;

public class Title {
    
    private String title;


    public Title() {
    }

    public Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Title title(String title) {
        setTitle(title);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Title)) {
            return false;
        }
        Title title = (Title) o;
        return Objects.equals(title, title.title);
    }


    @Override
    public String toString() {
        return "{" +
            " title='" + getTitle() + "'" +
            "}";
    }
    
}
