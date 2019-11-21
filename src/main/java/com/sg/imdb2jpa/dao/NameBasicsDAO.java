package com.sg.imdb2jpa.dao;

import com.sg.imdb2jpa.domain.NameBasics;
import org.springframework.stereotype.Repository;

@Repository
public class NameBasicsDAO extends AbstractJpaDAO<NameBasics> {

    public NameBasicsDAO() {
        super();
        setClazz(NameBasics.class);
    }
}
