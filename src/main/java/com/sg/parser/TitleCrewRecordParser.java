package com.sg.parser;

import com.sg.domain.TitleCrew;
import org.apache.commons.csv.CSVRecord;

import java.util.Optional;

public class TitleCrewRecordParser implements ImdbRecordParser<TitleCrew> {

    @Override
    public TitleCrew apply(CSVRecord record) {
        TitleCrew entity = new TitleCrew();
        entity.setTconst(record.get("tconst"));
        Optional.ofNullable(record.get("directors"))
                .ifPresent(entity::setDirectors);
        Optional.ofNullable(record.get("writers"))
                .ifPresent(entity::setWriters);
        return entity;
    }
}
