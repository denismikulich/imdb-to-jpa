package com.sg.imdb2jpa.parser;

import com.sg.imdb2jpa.domain.TitlePrincipal;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

public class TitlePrincipleRecordParser implements ImdbRecordParser<TitlePrincipal> {

    @Override
    public TitlePrincipal apply(CSVRecord record) {
        TitlePrincipal entity = new TitlePrincipal();
        entity.setTconst(record.get("tconst"));
        Optional.ofNullable(record.get("ordering"))
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::createInteger)
                .ifPresent(entity::setOrdering);
        entity.setNconst(record.get("nconst"));
        Optional.ofNullable(record.get("category"))
                .ifPresent(entity::setCategory);
        Optional.ofNullable(record.get("job"))
                .ifPresent(entity::setJob);
        Optional.ofNullable(record.get("characters"))
                .ifPresent(entity::setCharacters);
        return entity;
    }
}
