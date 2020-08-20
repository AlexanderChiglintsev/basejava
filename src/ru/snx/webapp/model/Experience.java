package ru.snx.webapp.model;

import java.time.YearMonth;

public class Experience {
    private YearMonth start;
    private YearMonth end;
    private String organization;
    private String url;
    private String position;
    private String description;

    public Experience(YearMonth start, YearMonth end, String organization, String url, String position, String description) {
        this.start = start;
        this.end = end;
        this.organization = organization;
        this.url = url;
        this.position = position;
        this.description = description;
    }

    public YearMonth getStart() {
        return start;
    }

    public void setStart(YearMonth start) {
        this.start = start;
    }

    public YearMonth getEnd() {
        return end;
    }

    public void setEnd(YearMonth end) {
        this.end = end;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
