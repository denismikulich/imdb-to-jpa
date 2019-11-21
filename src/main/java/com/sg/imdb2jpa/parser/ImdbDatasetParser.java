package com.sg.imdb2jpa.parser;

import com.sg.imdb2jpa.dao.AbstractJpaDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

public class ImdbDatasetParser {

    public static <T extends Serializable> void readAndSave(final Reader in, ImdbRecordParser<T> recordParser, AbstractJpaDAO<T> dao) throws IOException {
        Iterable<CSVRecord> records = read(in);
        for (CSVRecord record : records) {
            dao.create(recordParser.apply(record));
        }
    }

    public static Iterable<CSVRecord> read(final Reader in) throws IOException {
        return CSVFormat
                .TDF
                .withFirstRecordAsHeader()
                .withQuote(null)
                .parse(in);
    }
}
