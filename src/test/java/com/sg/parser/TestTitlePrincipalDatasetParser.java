package com.sg.parser;

import com.sg.dao.TitlePrincipalDAO;
import com.sg.service.TitlePrincipleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContextTest.xml"})
@Transactional
public class TestTitlePrincipalDatasetParser {

    @Autowired
    private TitlePrincipleService titlePrincipleService;
    @Autowired
    private TitlePrincipalDAO titlePrincipalDAO;

    @Test
    public void testLoadGZipFile() throws IOException {
        URL url = getClass().getClassLoader().getResource("data/title.principals.tsv.gz");
        String fileName = url.getFile();
        FileInputStream fs = new FileInputStream(fileName);
        GZIPInputStream gzs = new GZIPInputStream(fs);
        Reader decoder = new InputStreamReader(gzs, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);

        ImdbDatasetParser.readAndSave(buffered, new TitlePrincipleRecordParser(), titlePrincipalDAO);

        Assert.assertEquals("expecting 59 principals of movies", 59, titlePrincipleService.findAll().size());
    }
}
