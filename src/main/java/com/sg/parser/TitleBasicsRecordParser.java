package com.sg.parser;

import com.sg.domain.TitleBasics;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

public class TitleBasicsRecordParser implements ImdbRecordParser<TitleBasics> {

    @Override
    public TitleBasics apply(CSVRecord record) {
        TitleBasics entity = new TitleBasics();
        entity.setTconst(record.get("tconst"));
        entity.setTitleType(record.get("titleType"));
        entity.setPrimaryTitle(record.get("primaryTitle"));
        entity.setOriginalTitle(record.get("originalTitle"));
        entity.setAdult("1".equals(record.get("isAdult")));
        ImdbRecordParser.convertYear(record.get("startYear"))
                .ifPresent(entity::setStartYear);
        ImdbRecordParser.convertYear(record.get("endYear"))
                .ifPresent(entity::setEndYear);
        Optional.ofNullable(record.get("runtimeMinutes"))
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::createInteger)
                .ifPresent(entity::setRuntimeMinutes);
        entity.setGenres(record.get("genres"));
        return entity;
    }
}
