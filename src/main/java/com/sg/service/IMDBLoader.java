package com.sg.service;

import com.sg.domain.*;
import com.sg.measure.ImdbLoadingWatcher;
import com.sg.parser.*;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

@Service
public class IMDBLoader {

    private static Log log = LogFactory.getLog(IMDBLoader.class);

    @Autowired
    private TitleRatingService titleRatingService;
    @Autowired
    private TitleBasicsService titleBasicsService;
    @Autowired
    private TitleCrewService titleCrewService;
    @Autowired
    private TitlePrincipleService titlePrincipleService;
    @Autowired
    private NameBasicsService nameBasicsService;

    private ImdbLoadingWatcher watcher;

    public void loadFromDir(final URL sourceDir, final Double minRate) throws IOException, URISyntaxException {
        watcher = new ImdbLoadingWatcher(true);
        validateSourceDir(sourceDir);
        loadRatings(sourceDir, minRate);
        loadBasics(sourceDir);
        loadCrews(sourceDir);
        loadPrincipals(sourceDir);
        loadNameBasics(sourceDir);

        watcher.statistics();
    }

    private void loadRatings(final URL sourceDir, final Double minRate) throws IOException {
        final double minTitleRating = Optional.ofNullable(minRate).orElse(8.0);
        log.debug("minimal rating is " + minTitleRating);
        Function<TitleRating, Boolean> handler = (parsedEntity) -> {
            if (parsedEntity.getAverageRating() >= minTitleRating) {
                titleRatingService.create(parsedEntity);
                return true;
            }
            return false;
        };
        loadRecords(sourceDir, "/title.ratings.tsv.gz", new TitleRatingRecordParser(), handler);
    }

    private void loadBasics(final URL sourceDir) throws IOException {
        Function<TitleBasics, Boolean> handler = (parsedEntity) -> {
            if (titleRatingService.findOne(parsedEntity.getTconst()) != null) {
                titleBasicsService.create(parsedEntity);
                return true;
            }
            return false;
        };
        loadRecords(sourceDir, "/title.basics.tsv.gz", new TitleBasicsRecordParser(), handler);
    }

    void loadCrews(final URL sourceDir) throws IOException {
        Function<TitleCrew, Boolean> handler = (parsedEntity) -> {
            if (titleRatingService.findOne(parsedEntity.getTconst()) != null) {
                titleCrewService.create(parsedEntity);
                return true;
            }
            return false;
        };
        loadRecords(sourceDir, "/title.crew.tsv.gz", new TitleCrewRecordParser(), handler);
    }

    void loadPrincipals(final URL sourceDir) throws IOException {
        Function<TitlePrincipal, Boolean> handler = (parsedEntity) -> {
            try {
                if (titleRatingService.findOne(parsedEntity.getTconst()) != null) {
                    titlePrincipleService.create(parsedEntity);
                    return true;
                }
            } catch (Exception exc) {
                log.error("Exception during saving entity: " + parsedEntity.toString(), exc);
            }
            return false;
        };
        loadRecords(sourceDir, "/title.principals.tsv.gz", new TitlePrincipleRecordParser(), handler);
    }

    private void loadNameBasics(URL sourceDir) throws IOException {
        Function<NameBasics, Boolean> handler = (parsedEntity) -> {
            try {
                if (titlePrincipleService.hasName(parsedEntity.getNconst())) {
                    nameBasicsService.create(parsedEntity);
                    return true;
                }
            } catch (Exception exc) {
                log.error("Exception during saving entity: " + parsedEntity.toString(), exc);
            }
            return false;
        };
        loadRecords(sourceDir, "/name.basics.tsv.gz", new NameBasicsRecordParser(), handler);
    }

    private <T> void loadRecords(final URL sourceDir, final String imdbDataFile, ImdbRecordParser<T> parser, Function<T, Boolean> handler) throws IOException {
        log.debug("Started loading of '" + imdbDataFile + "'");
        watcher.startLoadCrews();
        int recordsCount = 0;
        int entityCount = 0;
        Optional<T> lastSuccess = Optional.empty();
        Iterable<CSVRecord> records = readIMDBFile(sourceDir, imdbDataFile);
        try {
            for (CSVRecord record : records) {
                recordsCount++;
                T entity = parser.apply(record);
                lastSuccess = Optional.ofNullable(entity);
                if (handler.apply(entity)) {
                    entityCount++;
                }
                if (recordsCount % 100000 == 0) {
                    log.debug("still loading of '" + imdbDataFile + "': already processed " + recordsCount + ", loaded " + entityCount);
                }
            }
        } catch (Exception exc) {
            log.error(exc);
            lastSuccess.ifPresent(last -> log.error("last success: " + last.toString()));
            throw exc;
        }
        log.debug("'" + imdbDataFile + "' fully processed: total records " + recordsCount + ", loaded " + entityCount);
        watcher.finishLoadCrews();
    }

    void validateSourceDir(final URL sourceDir) throws URISyntaxException, IOException {
        watcher.startValidateSourceDir();
        if ("file".equalsIgnoreCase(Objects.requireNonNull(sourceDir, "sourceDir could not be NULL").getProtocol())) {
            validateSourceDirOnFilesystem(sourceDir);
        } else {
            validateSourceDirOnSite(sourceDir);
        }
        watcher.finishValidateSourceDir();
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
        File file = new File(sourceDir.getPath() + "/" + childFile);
        if (!file.exists()) {
            throw new IOException("'" + childFile + "' file not found");
        } else {
            log.debug("found file: '" + file.getPath());
        }
    }

    void validateSourceDirOnSite(final URL sourceDir) throws URISyntaxException, IOException {
        throw new NotImplementedException("loading from imdb site not implemented yet");
    }

    public Iterable<CSVRecord> readIMDBFile(final URL sourceDir, String imdbDataFile) throws IOException {
        File titleRatingsFile = new File(sourceDir.getPath() + imdbDataFile);
        FileInputStream fs = new FileInputStream(titleRatingsFile);
        GZIPInputStream gzs = new GZIPInputStream(fs);
        Reader decoder = new InputStreamReader(gzs, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);
        return ImdbDatasetParser.read(buffered);
    }
}
