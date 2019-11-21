package com.sg.imdb2jpa.service;

import com.sg.imdb2jpa.dao.TitleRatingDAO;
import com.sg.imdb2jpa.domain.TitleRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TitleRatingService {

    @Autowired
    private TitleRatingDAO titleRatingDAO;

    @Transactional
    public void create(final TitleRating titleRating) {
        titleRatingDAO.create(titleRating);
    }

    @Transactional
    public List<TitleRating> findAll() {
        return titleRatingDAO.findAll();
    }

    @Transactional
    public TitleRating findOne(final String tconst) {
        return titleRatingDAO.findOne(tconst);
    }

}
