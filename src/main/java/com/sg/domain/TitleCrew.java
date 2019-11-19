package com.sg.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Contains the director and writer information for all the titles in IMDb.
 */
@Entity
@Table(name = "title_crew")
public class TitleCrew implements Serializable {

    /**
     * alphanumeric unique identifier of the title
     */
    @Id
    @Column(name = "tconst", nullable = false)
    private String tconst;

    /**
     * directors (string array) –  director(s) of the given title
     */
    @Column
    private String directors;

    /**
     * writers (string array) –  writer(s) of the given title
     */
    @Column
    private String writers;

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
        this.writers = writers;
    }

    @Override
    public String toString() {
        return "TitleCrew{" +
                "tconst='" + tconst + '\'' +
                ", directors='" + directors + '\'' +
                ", writers='" + writers + '\'' +
                '}';
    }
}
