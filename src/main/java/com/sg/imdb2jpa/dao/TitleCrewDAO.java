package com.sg.imdb2jpa.dao;

import com.sg.imdb2jpa.domain.TitleCrew;
import org.springframework.stereotype.Repository;

@Repository
public class TitleCrewDAO extends AbstractJpaDAO<TitleCrew> {

    public TitleCrewDAO() {
        super();
        setClazz(TitleCrew.class);
    }

    public TitleCrew findOne(String tconst) {
        return entityManager.find(TitleCrew.class, tconst);
    }
}
