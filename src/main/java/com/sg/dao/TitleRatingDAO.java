package com.sg.dao;

import com.sg.domain.TitleRating;
import org.springframework.stereotype.Repository;

@Repository
public class TitleRatingDAO extends AbstractJpaDAO<TitleRating> {

    public TitleRatingDAO() {
        super();
        setClazz(TitleRating.class);
    }

    public TitleRating findOne(final String tconst) {
        return entityManager.find(TitleRating.class, tconst);
    }
}
