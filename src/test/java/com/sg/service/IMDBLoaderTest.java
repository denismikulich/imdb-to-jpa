package com.sg.service;

import org.apache.commons.lang3.NotImplementedException;
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

    @Test(expected = NullPointerException.class)
    public void validateSourceDir_NPE() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDir(null);
    }

    @Test(expected = MalformedURLException.class)
    public void validateSourceDir_emptyURL() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDir(new URL(""));
    }

    @Test(expected = IOException.class)
    public void validateSourceDir_noneDirectory() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDir(getClass().getClassLoader().getResource("data/name.basics.tsv.gz"));
    }

    @Test
    public void validateSourceDir_onFilesystem() throws IOException, URISyntaxException {
       imdbLoader.validateSourceDir(getClass().getClassLoader().getResource("data"));
    }

    @Test(expected = NotImplementedException.class)
    public void validateSourceDir_onSite() throws IOException, URISyntaxException {
        imdbLoader.validateSourceDir(new URL("https://datasets.imdbws.com/"));
    }
}
