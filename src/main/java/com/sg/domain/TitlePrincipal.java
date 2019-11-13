package com.sg.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Contains the principal cast/crew for titles
 */
@Entity
@Table(name = "title_principals")
public class TitlePrincipal implements Serializable {

    /**
     * alphanumeric unique identifier of the title
     */
    @EmbeddedId
    private TID tid;

    /**
     * a number to uniquely identify rows for a given titleId
     */
    @Column
    private Integer ordering;

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
        return tid.tconst;
    }

    public void setTconst(String tconst) {
        if (tid == null) {
            tid = new TID();
        }
        this.tid.tconst = tconst;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getNconst() {
        return tid.nconst;
    }

    public void setNconst(String nconst) {
        if (tid == null) {
            tid = new TID();
        }
        this.tid.nconst = nconst;
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

    @Embeddable
    private static class TID implements Serializable {
        private String tconst;
        private String nconst;

        public TID() {}

        public TID(String tconst, String nconst) {
            this.tconst = tconst;
            this.nconst = nconst;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TID tID = (TID) o;
            return tconst.equals(tID.tconst) &&
                    nconst.equals(tID.nconst);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tconst, nconst);
        }
    }
}
