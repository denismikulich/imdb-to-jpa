package com.sg.measure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Optional;

public class ImdbLoadingWatcher {
    private static Log log = LogFactory.getLog(ImdbLoadingWatcher.class);

    private boolean isEnabled;
    private TimeMarker validateSourceDir;
    private TimeMarker loadRatings;
    private TimeMarker loadBasics;
    private TimeMarker loadCrews;
    private TimeMarker loadPricipals;
    private TimeMarker loadNames;

    public ImdbLoadingWatcher() {
        this(log.isTraceEnabled());
    }

    public ImdbLoadingWatcher(boolean isEnabled) {
        this.isEnabled = isEnabled;
        this.validateSourceDir = new TimeMarker(isEnabled);
        this.loadRatings = new TimeMarker(isEnabled);
        this.loadBasics = new TimeMarker(isEnabled);
        this.loadCrews = new TimeMarker(isEnabled);
        this.loadPricipals = new TimeMarker(isEnabled);
        this.loadNames = new TimeMarker(isEnabled);
    }

    public void startValidateSourceDir() {
        this.validateSourceDir.start();
    }

    public void finishValidateSourceDir() {
        this.validateSourceDir.finish();
    }

    public Optional<TimeMarker> getMarkerByFilename(final String imdbDataFile) {
        if (imdbDataFile.contains("title.ratings")) {
            return Optional.of(this.loadRatings);
        }
        if (imdbDataFile.contains("title.basics")) {
            return Optional.of(this.loadBasics);
        }
        if (imdbDataFile.contains("title.crew")) {
            return Optional.of(this.loadCrews);
        }
        if (imdbDataFile.contains("title.principals")) {
            return Optional.of(this.loadPricipals);
        }
        if (imdbDataFile.contains("name.basics")) {
            return Optional.of(this.loadNames);
        }
        return Optional.empty();
    }

    public void statistics() {
        if (!isEnabled)
            return;
        StringBuilder sb = new StringBuilder("\n").append("IMDB data loading time statistic:").append("\n");
        sb.append("validate source dir ").append(validateSourceDir.simpleStatistic()).append("\n");
        sb.append("loading title ratings ").append(loadRatings.simpleStatistic()).append("\n");
        sb.append("loading title basics ").append(loadBasics.simpleStatistic()).append("\n");
        sb.append("loading title crews ").append(loadCrews.simpleStatistic()).append("\n");
        sb.append("loading principals ").append(loadCrews.simpleStatistic()).append("\n");
        sb.append("loading names ").append(loadCrews.simpleStatistic()).append("\n");

        log.trace(sb.toString());
    }

}
