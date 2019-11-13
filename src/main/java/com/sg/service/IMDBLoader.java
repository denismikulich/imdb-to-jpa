package com.sg.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class IMDBLoader {

    public void loadFromDir(final String sourceDir, final Double minRate) {
        final double minTitleRating = Optional.ofNullable(minRate).orElse(8.0);
        /*Iterable<CSVRecord> titleRatings = CSVFormat
                .TDF
                .withFirstRecordAsHeader()
                .parse(in);*/
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
}
