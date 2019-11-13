package com.sg.service;

import com.sg.dao.TitleBasicsDAO;
import com.sg.domain.TitleBasics;
import com.sg.domain.TitleRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TitleBasicsService {

    @Autowired
    private TitleBasicsDAO titleBasicsDAO;

    @Transactional
    public void create(final TitleBasics titleBasics) {
        titleBasicsDAO.create(titleBasics);
    }

    @Transactional
    public List<TitleBasics> findAll() {
        return titleBasicsDAO.findAll();
    }

    @Transactional
    public TitleBasics findOne(final String tconst) {
        return titleBasicsDAO.findOne(tconst);
    }
}
