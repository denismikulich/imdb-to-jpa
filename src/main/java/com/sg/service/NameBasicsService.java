package com.sg.service;

import com.sg.dao.NameBasicsDAO;
import com.sg.domain.NameBasics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NameBasicsService {

    @Autowired
    private NameBasicsDAO nameBasicsDAO;

    @Transactional
    public void create(final NameBasics nameBasics) {
        nameBasicsDAO.create(nameBasics);
    }

    @Transactional
    public List<NameBasics> findAll() {
        return nameBasicsDAO.findAll();
    }
}
