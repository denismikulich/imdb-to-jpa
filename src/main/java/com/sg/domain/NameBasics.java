package com.sg.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="name_basics")
public class NameBasics implements Serializable {

    /**
     * alphanumeric unique identifier of the name/person
     */
    @Id
    @Column(name = "nconst", nullable = false, unique=true)
    private String nconst;

    /**
     * name by which the person is most often credited
     */
    @Column
    private String primaryName;

    /**
     * in YYYY format
     */
    @Column
    private LocalDate birthYear;

    /**
     * in YYYY format if applicable, else '\N'
     */
    @Column
    private LocalDate deathYear;

    /**
     *  the top-3 professions of the person
     */
    @Column
    private String primaryProfession;

    /**
     *  titles the person is known for
     */
    @Column
    private String knownForTitles;

    public String getNconst() {
        return nconst;
    }

    public void setNconst(String nconst) {
        this.nconst = nconst;
    }

    public String getPrimaryName() {
        return primaryName;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public LocalDate getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(LocalDate birthYear) {
        this.birthYear = birthYear;
    }

    public LocalDate getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(LocalDate deathYear) {
        this.deathYear = deathYear;
    }

    public String getPrimaryProfession() {
        return primaryProfession;
    }

    public void setPrimaryProfession(String primaryProfession) {
        this.primaryProfession = primaryProfession;
    }

    public String getKnownForTitles() {
        return knownForTitles;
    }

    public void setKnownForTitles(String knownForTitles) {
        this.knownForTitles = knownForTitles;
    }

    @Override
    public String toString() {
        return "NameBasics{" +
                "nconst='" + nconst + '\'' +
                ", primaryName='" + primaryName + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                ", primaryProfession='" + primaryProfession + '\'' +
                ", knownForTitles='" + knownForTitles + '\'' +
                '}';
    }
}
