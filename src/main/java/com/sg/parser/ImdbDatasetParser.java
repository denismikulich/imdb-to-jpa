package com.sg.parser;

import com.sg.dao.AbstractJpaDAO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

public class ImdbDatasetParser {

    public static <T extends Serializable> void readAndSave(final Reader in, ImdbRecordParser<T> recordParser, AbstractJpaDAO<T> dao) throws IOException {
        Iterable<CSVRecord> records = CSVFormat
                .TDF
                .withFirstRecordAsHeader()
                .parse(in);
        for (CSVRecord record : records) {
            dao.create(recordParser.apply(record));
        }
    }
}
