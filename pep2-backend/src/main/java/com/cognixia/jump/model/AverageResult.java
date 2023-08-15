package com.cognixia.jump.model;
import java.util.Objects;

public class AverageResult {
    
    private Double avgRating;


    public AverageResult() {
    }

    public AverageResult(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getAvgRating() {
        return this.avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public AverageResult avgRating(Double avgRating) {
        setAvgRating(avgRating);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AverageResult)) {
            return false;
        }
        AverageResult averageResult = (AverageResult) o;
        return Objects.equals(avgRating, averageResult.avgRating);
    }

    // @Override
    // public int hashCode() {
    //     return super.hashCode()(avgRating);
    // }

    @Override
    public String toString() {
        return "{" +
            " avgRating='" + getAvgRating() + "'" +
            "}";
    }
    
}
