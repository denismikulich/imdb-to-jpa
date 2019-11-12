package com.sg.service;

import com.sg.dao.TitleRatingDAO;
import com.sg.domain.TitleRating;
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

}
