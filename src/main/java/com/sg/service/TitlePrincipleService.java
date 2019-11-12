package com.sg.service;

import com.sg.dao.TitlePrincipalDAO;
import com.sg.domain.TitlePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TitlePrincipleService {

    @Autowired
    private TitlePrincipalDAO titlePrincipalDAO;

    @Transactional
    public void create(final TitlePrincipal titlePrincipal) {
        titlePrincipalDAO.create(titlePrincipal);
    }

    @Transactional
    public List<TitlePrincipal> findAll() {
        return titlePrincipalDAO.findAll();
    }
}
