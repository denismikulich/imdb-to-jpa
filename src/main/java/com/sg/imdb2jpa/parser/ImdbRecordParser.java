package com.sg.imdb2jpa.parser;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

public interface ImdbRecordParser<T> extends Function<CSVRecord, T> {

    static Optional<LocalDate> convertYear(final String year) {
        return Optional.ofNullable(year)
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::createInteger)
                .map(y -> LocalDate.ofYearDay(y, 1));
    }
}
