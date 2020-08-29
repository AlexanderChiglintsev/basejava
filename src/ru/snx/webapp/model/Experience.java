package ru.snx.webapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Experience {
    private Link organization;
    private List<WorkInterval> works = new ArrayList<>();

    public Experience(String organization, String url, WorkInterval... w) {
        this.organization = new Link(organization, url);
        if (w.length != 0) {
            Collections.addAll(works, w);
        } else throw new IllegalArgumentException("At least one work period is required !!!");
    }

    public String getOrgName() {
        return organization.getOrganization();
    }

    public String getUrl() {
        return organization.getUrl();
    }

    public List<WorkInterval> getWorks() {
        return works;
    }

    public void setWorks(List<WorkInterval> works) {
        this.works = works;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!organization.equals(that.organization)) return false;
        return works.equals(that.works);
    }

    @Override
    public int hashCode() {
        int result = organization.hashCode();
        result = 31 * result + works.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(organization);
        for (WorkInterval w : works) {
            str.append(w).append("\n");
        }
        return str.toString();
    }
}
