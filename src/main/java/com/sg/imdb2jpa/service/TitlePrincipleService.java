package com.sg.imdb2jpa.service;

import com.sg.imdb2jpa.dao.TitlePrincipalDAO;
import com.sg.imdb2jpa.domain.TitlePrincipal;
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

    @Transactional
    public boolean hasTitle(String title) {
        return titlePrincipalDAO.hasTitle(title);
    }

    @Transactional
    public boolean hasName(String name) {
        return titlePrincipalDAO.hasName(name);
    }
}
