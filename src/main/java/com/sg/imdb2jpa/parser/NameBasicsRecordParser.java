package com.sg.imdb2jpa.parser;

import com.sg.imdb2jpa.domain.NameBasics;
import org.apache.commons.csv.CSVRecord;

public class NameBasicsRecordParser implements ImdbRecordParser<NameBasics> {

    @Override
    public NameBasics apply(CSVRecord record) {
        NameBasics entity = new NameBasics();
        entity.setNconst(record.get("nconst"));
        entity.setPrimaryName(record.get("primaryName"));
        ImdbRecordParser.convertYear(record.get("birthYear"))
                .ifPresent(entity::setBirthYear);
        ImdbRecordParser.convertYear(record.get("deathYear"))
                .ifPresent(entity::setDeathYear);
        entity.setPrimaryProfession(record.get("primaryProfession"));
        entity.setKnownForTitles(record.get("knownForTitles"));
        return entity;
    }
}
