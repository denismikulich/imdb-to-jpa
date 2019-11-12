package com.sg.parser;

import com.sg.domain.TitleRating;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

public class TitleRatingRecordParser implements ImdbRecordParser<TitleRating> {

    @Override
    public TitleRating apply(CSVRecord record) {
        TitleRating entity = new TitleRating();
        entity.setTconst(record.get("tconst"));
        Optional.ofNullable(record.get("averageRating"))
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::createDouble)
                .ifPresent(entity::setAverageRating);
        Optional.ofNullable(record.get("numVotes"))
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::createInteger)
                .ifPresent(entity::setNumVotes);
        return entity;
    }
}
