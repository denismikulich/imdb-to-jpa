package com.sg.dao;

import com.sg.domain.NameBasics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NameBasicsDAO extends AbstractJpaDAO<NameBasics> {

    public NameBasicsDAO() {
        super();
        setClazz(NameBasics.class);
    }
}
