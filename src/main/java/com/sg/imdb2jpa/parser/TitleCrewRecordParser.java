package com.sg.imdb2jpa.parser;

import com.sg.imdb2jpa.domain.TitleCrew;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Optional;

public class TitleCrewRecordParser implements ImdbRecordParser<TitleCrew> {
    private static Log log = LogFactory.getLog(TitleBasicsRecordParser.class);

    @Override
    public TitleCrew apply(CSVRecord record) {
        TitleCrew entity = new TitleCrew();
        entity.setTconst(record.get("tconst"));
        Optional.ofNullable(record.get("directors"))
                .map(val -> StringUtils.left(val, 99))
                .ifPresent(entity::setDirectors);
        Optional.ofNullable(record.get("writers"))
                .map(val -> StringUtils.left(val, 99))
                .ifPresent(entity::setWriters);

        log.trace("Parsed: " + entity.toString());
        return entity;
    }

}
