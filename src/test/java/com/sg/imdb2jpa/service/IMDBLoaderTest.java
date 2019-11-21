package com.sg.imdb2jpa.service;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
public class IMDBLoaderTest {

    @Autowired
    private IMDBLoader imdbLoader;
    @Autowired
    private TitleRatingService titleRatingService;
    @Autowired
    private TitleBasicsService titleBasicsService;
    @Autowired
    private TitleCrewService titleCrewService;
    @Autowired
    private TitlePrincipleService titlePrincipleService;
    @Autowired
    private NameBasicsService nameBasicsService;

    @Test(expected = NullPointerException.class)
    public void validateSourceDir_NPE() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDir(null);
    }

    @Test(expected = MalformedURLException.class)
    public void validateSourceDir_emptyURL() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDirOnFilesystem(new URL(""));
    }

    @Test(expected = IOException.class)
    public void validateSourceDir_noneDirectory() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDirOnFilesystem(getClass().getClassLoader().getResource("data/name.basics.tsv.gz"));
    }

    @Test
    public void validateSourceDir_onFilesystem() throws IOException, URISyntaxException {
       imdbLoader.validateSourceDirOnFilesystem(getClass().getClassLoader().getResource("data"));
    }

    @Test(expected = NotImplementedException.class)
    public void validateSourceDir_onSite() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDir(new URL("https://datasets.imdbws.com/"));
    }

    @Test
    public void load() throws IOException, URISyntaxException {
        imdbLoader.loadFromDir(getClass().getClassLoader().getResource("data"), 9.0);
        Assert.assertEquals("expecting 4 ratings of movies", 4, titleRatingService.findAll().size());
        Assert.assertEquals("expecting 4 movies", 4, titleBasicsService.findAll().size());
        Assert.assertEquals("expecting 4 records of crew", 4, titleCrewService.findAll().size());
        Assert.assertEquals("expecting 40 records of principals", 40, titlePrincipleService.findAll().size());
        Assert.assertEquals("expecting 11 records of names", 11, nameBasicsService.findAll().size());
    }

}
