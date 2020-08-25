package ru.snx.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private YearMonth start;
    private YearMonth end;
    private Link organization;
    private String position;
    private String description;

    public Experience(YearMonth start, YearMonth end, String organization, String url, String position, String description) {
        Objects.requireNonNull(start, "start must not be null !!!");
        Objects.requireNonNull(end, "end must not be null !!!");
        Objects.requireNonNull(position, "position must not be null !!!");
        this.start = start;
        this.end = end;
        this.organization = new Link(organization, url);
        this.position = position;
        this.description = description;
    }

    public String getOrgName() {
        return organization.getOrganization();
    }

    public String getUrl() {
        return organization.getUrl();
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

    public Link getOrganization() {
        return organization;
    }

    public void setOrganization(Link organization) {
        this.organization = organization;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!start.equals(that.start)) return false;
        if (!end.equals(that.end)) return false;
        if (!Objects.equals(organization, that.organization)) return false;
        if (!position.equals(that.position)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + (organization != null ? organization.hashCode() : 0);
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
