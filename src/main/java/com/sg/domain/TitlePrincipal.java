package com.sg.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Contains the principal cast/crew for titles
 */
@Entity
@Table(name = "title_principals")
public class TitlePrincipal implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    /**
     * alphanumeric unique identifier of the title
     */
    @Column(name = "tconst", nullable = false)
    private String tconst;

    /**
     * a number to uniquely identify rows for a given titleId
     */
    @Column
    private Integer ordering;

    /**
     * alphanumeric unique identifier of the name/person
     */
    @Column(name = "nconst")
    private String nconst;

    /**
     * the category of job that person was in
     */
    @Column
    private String category;

    /**
     * the specific job title if applicable, else '\N'
     */
    @Column
    private String job;

    /**
     * the name of the character played if applicable, else '\N'
     */
    @Column
    private String characters;

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getNconst() {
        return nconst;
    }

    public void setNconst(String nconst) {
        this.nconst = nconst;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }
}
