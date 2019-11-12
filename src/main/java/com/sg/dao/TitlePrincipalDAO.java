package com.sg.dao;

import com.sg.domain.TitlePrincipal;
import org.springframework.stereotype.Repository;

@Repository
public class TitlePrincipalDAO extends AbstractJpaDAO<TitlePrincipal> {

    public TitlePrincipalDAO() {
        setClazz(TitlePrincipal.class);
    }
}
