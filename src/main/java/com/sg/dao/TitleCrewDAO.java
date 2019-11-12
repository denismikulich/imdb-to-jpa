package com.sg.dao;

import com.sg.domain.TitleCrew;
import org.springframework.stereotype.Repository;

@Repository
public class TitleCrewDAO extends AbstractJpaDAO<TitleCrew> {

    public TitleCrewDAO() {
        super();
        setClazz(TitleCrew.class);
    }
}
