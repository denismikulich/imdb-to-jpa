package com.sg.imdb2jpa.dao;

import com.sg.imdb2jpa.domain.TitleBasics;
import org.springframework.stereotype.Repository;

@Repository
public class TitleBasicsDAO extends AbstractJpaDAO<TitleBasics> {

    public TitleBasicsDAO() {
        super();
        setClazz(TitleBasics.class);
    }

    public TitleBasics findOne(String tconst) {
        return entityManager.find(TitleBasics.class, tconst);
    }
}
