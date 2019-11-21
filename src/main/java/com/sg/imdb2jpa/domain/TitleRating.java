package com.sg.imdb2jpa.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * the IMDb rating and votes information for titles
 */
@Entity
@Table(name = "title_rating")
public class TitleRating implements Serializable {

    /**
     * alphanumeric unique identifier of the title
     */
    @Id
    @Column(name = "tconst", nullable = false, unique = true)
    private String tconst;

    /**
     * weighted average of all the individual user ratings
     */
    @Column
    private Double averageRating;

    /**
     * number of votes the title has received
     */
    @Column
    private Integer numVotes;

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }
}
