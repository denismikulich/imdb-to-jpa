package com.sg.parser;

import com.sg.dao.NameBasicsDAO;
import com.sg.service.NameBasicsService;
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
public class TestNameBasicsRecordParser {

    @Autowired
    private NameBasicsService nameBasicsService;
    @Autowired
    private NameBasicsDAO nameBasicsDAO;

    @Test
    public void testLoadGZipFile() throws IOException {
        URL url = getClass().getClassLoader().getResource("data/name.basics.tsv.gz");
        String fileName = url.getFile();
        FileInputStream fs = new FileInputStream(fileName);
        GZIPInputStream gzs = new GZIPInputStream(fs);
        Reader decoder = new InputStreamReader(gzs, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);

        ImdbDatasetParser.readAndSave(buffered, new NameBasicsRecordParser(), nameBasicsDAO);

        Assert.assertEquals("expecting 40 names records", 40, nameBasicsService.findAll().size());
    }
}