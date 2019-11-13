package com.sg.service;

import com.sg.domain.TitleBasics;
import com.sg.domain.TitleCrew;
import com.sg.domain.TitleRating;
import com.sg.parser.ImdbRecordParser;
import com.sg.parser.TitleBasicsRecordParser;
import com.sg.parser.TitleCrewRecordParser;
import com.sg.parser.TitleRatingRecordParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

@Service
public class IMDBLoader {

    @Autowired
    private TitleRatingService titleRatingService;
    @Autowired
    private TitleBasicsService titleBasicsService;
    @Autowired
    private TitleCrewService titleCrewService;

    public void loadFromDir(final URL sourceDir, final Double minRate) throws IOException, URISyntaxException {
        validateSourceDir(sourceDir);
        loadRatings(sourceDir, minRate);
        loadBasics(sourceDir);
        loadCrews(sourceDir);
    }

    private void loadRatings(final URL sourceDir, final Double minRate) throws IOException {
        final double minTitleRating = Optional.ofNullable(minRate).orElse(8.0);
        Iterable<CSVRecord> records = readIMDBFile(sourceDir, "/title.ratings.tsv.gz");
        ImdbRecordParser<TitleRating> parser = new TitleRatingRecordParser();
        for (CSVRecord record : records) {
            TitleRating entity = parser.apply(record);
            if (entity.getAverageRating() >= minTitleRating) {
                titleRatingService.create(entity);
            }
        }
    }

    private void loadBasics(final URL sourceDir) throws IOException {
        Iterable<CSVRecord> records = readIMDBFile(sourceDir, "/title.basics.tsv.gz");
        ImdbRecordParser<TitleBasics> parser = new TitleBasicsRecordParser();
        for (CSVRecord record : records) {
            TitleBasics entity = parser.apply(record);
            if (titleRatingService.findOne(entity.getTconst()) != null) {
                titleBasicsService.create(entity);
            }
        }
    }

    private void loadCrews(final URL sourceDir) throws IOException {
        Iterable<CSVRecord> records = readIMDBFile(sourceDir, "/title.crew.tsv.gz");
        ImdbRecordParser<TitleCrew> parser = new TitleCrewRecordParser();
        for (CSVRecord record : records) {
            TitleCrew entity = parser.apply(record);
            if (titleBasicsService.findOne(entity.getTconst()) != null) {
                titleCrewService.create(entity);
            }
        }
    }

    void validateSourceDir(final URL sourceDir) throws URISyntaxException, IOException {
        if ("file".equalsIgnoreCase(Objects.requireNonNull(sourceDir, "sourceDir could not be NULL").getProtocol())) {
            validateSourceDirOnFilesystem(sourceDir);
        } else {
            validateSourceDirOnSite(sourceDir);
        }
    }

    void validateSourceDirOnFilesystem(final URL sourceDir) throws URISyntaxException, IOException {
        if (!Files.isDirectory(Paths.get(sourceDir.toURI()))) {
            throw new IOException("wrong path to imdb data directory");
        }

        validateExistChildFile("title.ratings.tsv.gz", sourceDir);
        validateExistChildFile("title.principals.tsv.gz", sourceDir);
        validateExistChildFile("title.crew.tsv.gz", sourceDir);
        validateExistChildFile("title.basics.tsv.gz", sourceDir);
        validateExistChildFile("name.basics.tsv.gz", sourceDir);
    }

    private void validateExistChildFile(final String childFile, final URL sourceDir) throws IOException {
        if (!new File(sourceDir.getPath() + "/" + childFile).exists()) {
            throw new IOException("'" + childFile + "' file not found");
        }
    }

    void validateSourceDirOnSite(final URL sourceDir) throws URISyntaxException, IOException {
        throw new NotImplementedException("loading from imdb site not implemented yet");
    }

    private Iterable<CSVRecord> readIMDBFile(final URL sourceDir, String imdbDataFile) throws IOException {
        File titleRatingsFile = new File(sourceDir.getPath() + imdbDataFile);
        FileInputStream fs = new FileInputStream(titleRatingsFile);
        GZIPInputStream gzs = new GZIPInputStream(fs);
        Reader decoder = new InputStreamReader(gzs, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);
        return CSVFormat
                .TDF
                .withFirstRecordAsHeader()
                .parse(buffered);
    }
}
