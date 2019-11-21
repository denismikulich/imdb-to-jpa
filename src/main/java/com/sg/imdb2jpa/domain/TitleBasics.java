package com.sg.imdb2jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "title_basics")
public class TitleBasics implements Serializable {

    /**
     * alphanumeric unique identifier of the title
     */
    @Id
    @Column(name = "tconst", nullable = false, unique = true)
    private String tconst;


    /**
     * the type/format of the title (e.g. movie, short, tvseries, tvepisode, video, etc)
     */
    @Column
    private String titleType;

    /**
     * primaryTitle (string) – the more popular title / the title used by the filmmakers on promotional materials at the point of release
     */
    @Column
    private String primaryTitle;

    /**
     * originalTitle (string) - original title, in the original language
     */
    @Column
    private String originalTitle;

    /**
     * isAdult (boolean) - 0: non-adult title; 1: adult title
     */
    @Column
    private boolean isAdult;

    /**
     * startYear (YYYY) – represents the release year of a title. In the case of TV Series, it is the series start year
     */
    @Column
    private LocalDate startYear;

    /**
     * endYear (YYYY) – TV Series end year. ‘\N’ for all other title types
     */
    @Column
    private LocalDate endYear;

    /**
     * runtimeMinutes – primary runtime of the title, in minutes
     */
    @Column
    private Integer runtimeMinutes;

    /**
     * genres (string array) – includes up to three genres associated with the title
     */
    @Column
    private String genres;

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public LocalDate getStartYear() {
        return startYear;
    }

    public void setStartYear(LocalDate startYear) {
        this.startYear = startYear;
    }

    public LocalDate getEndYear() {
        return endYear;
    }

    public void setEndYear(LocalDate endYear) {
        this.endYear = endYear;
    }

    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "TitleBasics{" +
                "tconst='" + tconst + '\'' +
                ", titleType='" + titleType + '\'' +
                ", primaryTitle='" + primaryTitle + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", isAdult=" + isAdult +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", runtimeMinutes=" + runtimeMinutes +
                ", genres='" + genres + '\'' +
                '}';
    }
}
