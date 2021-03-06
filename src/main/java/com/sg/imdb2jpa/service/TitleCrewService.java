package com.sg.imdb2jpa.service;

import com.sg.imdb2jpa.dao.TitleCrewDAO;
import com.sg.imdb2jpa.domain.TitleCrew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TitleCrewService {

    @Autowired
    private TitleCrewDAO titleCrewDAO;

    @Transactional
    public void create(final TitleCrew titleCrew) {
        titleCrewDAO.create(titleCrew);
    }

    @Transactional
    public List<TitleCrew> findAll() {
        return titleCrewDAO.findAll();
    }

    public TitleCrew findOne(String tconst) {
        return titleCrewDAO.findOne(tconst);
    }

}
