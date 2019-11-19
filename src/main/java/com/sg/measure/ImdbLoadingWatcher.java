package com.sg.measure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImdbLoadingWatcher {
    private static Log log = LogFactory.getLog(ImdbLoadingWatcher.class);

    private boolean isEnabled;
    private TimeMarker validateSourceDir;
    private TimeMarker loadRatings;
    private TimeMarker loadBasics;
    private TimeMarker loadCrews;

    public ImdbLoadingWatcher() {
        this(log.isTraceEnabled());
    }

    public ImdbLoadingWatcher(boolean isEnabled) {
        this.isEnabled = isEnabled;
        this.validateSourceDir = new TimeMarker(isEnabled);
        this.loadRatings = new TimeMarker(isEnabled);
        this.loadBasics = new TimeMarker(isEnabled);
        this.loadCrews = new TimeMarker(isEnabled);
    }

    public void startValidateSourceDir() {
        this.validateSourceDir.start();
    }

    public void finishValidateSourceDir() {
        this.validateSourceDir.finish();
    }

    public void startLoadRatings() {
        this.loadRatings.start();
    }

    public void finishLoadRatings() {
        this.loadRatings.finish();
    }

    public void startLoadBasics() {
        this.loadBasics.start();
    }

    public void finishLoadBasics() {
        this.loadBasics.finish();
    }

    public void startLoadCrews() {
        this.loadCrews.start();
    }

    public void finishLoadCrews() {
        this.loadCrews.finish();
    }

    public void statistics() {
        if (!isEnabled)
            return;
        StringBuilder sb = new StringBuilder("\n").append("IMDB data loading time statistic:").append("\n");
        sb.append("validate source dir ").append(validateSourceDir.simpleStatistic()).append("\n");
        sb.append("loading title ratings ").append(loadRatings.simpleStatistic()).append("\n");
        sb.append("loading title basics ").append(loadBasics.simpleStatistic()).append("\n");
        sb.append("loading title crews ").append(loadCrews.simpleStatistic()).append("\n");

        log.trace(sb.toString());
    }

}
