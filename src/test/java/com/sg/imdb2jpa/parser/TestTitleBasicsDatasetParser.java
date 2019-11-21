package com.sg.imdb2jpa.parser;

import com.sg.imdb2jpa.dao.TitleBasicsDAO;
import com.sg.imdb2jpa.service.TitleBasicsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
public class TestTitleBasicsDatasetParser {

    @Autowired
    private TitleBasicsService titleBasicsService;
    @Autowired
    private TitleBasicsDAO titleBasicsDAO;

    @Test
    public void testMain() throws IOException {
        String fileName = getClass().getClassLoader().getResource("testdata.tsv").getFile();
        ImdbDatasetParser.readAndSave(new FileReader(fileName), new TitleBasicsRecordParser(), titleBasicsDAO);

        Assert.assertEquals("expecting 49 movies", 49, titleBasicsService.findAll().size());
    }

    @Test
    public void testGZipFile() throws IOException {
        URL url = getClass().getClassLoader().getResource("data/title.basics.tsv.gz");
        String fileName = url.getFile();
        FileInputStream fs = new FileInputStream(fileName);
        GZIPInputStream gzs = new GZIPInputStream(fs);
        Reader decoder = new InputStreamReader(gzs, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);

        ImdbDatasetParser.readAndSave(buffered, new TitleBasicsRecordParser(), titleBasicsDAO);

        Assert.assertEquals("expecting 6 movies", 6, titleBasicsService.findAll().size());
    }

    @Test
    public void testResourceSubfolderList() throws URISyntaxException, IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Path configFilePath = Paths.get(classLoader.getResource("data").toURI());
        Assert.assertEquals(5, Files.list(configFilePath).count());
        URL titleBasicsURL = classLoader.getResource("data/title.basics.tsv.gz");
        Assert.assertNotNull(titleBasicsURL);
    }

}
