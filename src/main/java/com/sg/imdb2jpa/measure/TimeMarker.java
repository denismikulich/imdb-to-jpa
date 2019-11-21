package com.sg.imdb2jpa.measure;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeMarker {
    private final boolean isEnabled;
    private long accumulation = 0;
    private LocalDateTime timeMarker;
    private long counter = 0;

    public TimeMarker(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void start() {
        if (isEnabled) {
            timeMarker = LocalDateTime.now();
        }
    }

    public long finish() {
        if (isEnabled) {
            long duration = Math.abs(ChronoUnit.MILLIS.between(timeMarker, LocalDateTime.now()));
            accumulation += duration;
            counter++;
            return duration;
        }
        return -1;
    }

    public long total() {
        return accumulation;
    }

    public long count() {
        return counter;
    }

    public String simpleStatistic() {
        StringBuilder sb = new StringBuilder();
        if (this.total() > 60*1000) {
            sb.append("time: ").append(this.total()/60).append("sec");
        } else {
            sb.append("time: ").append(this.total()).append("ms");
        }
        return sb.toString();
    }

    public String extendedStatistic() {
        StringBuilder sb = new StringBuilder();
        sb.append("time: ").append(this.total()).append("ms");
        if (this.count() > 0) {
            sb.append("; average: ").append(this.total() / (double) this.count()).append("ms");
            sb.append("; count: ").append(this.count());
        }
        return sb.toString();
    }
}
