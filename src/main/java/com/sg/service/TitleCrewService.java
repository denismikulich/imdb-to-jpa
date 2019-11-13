package com.sg.service;

import com.sg.dao.TitleCrewDAO;
import com.sg.domain.TitleCrew;
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

}
