package ru.snx.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Organization {
    private Link link;
    private List<Experience> experienceList = new ArrayList<>();

    public Organization(String link, String url, Experience... experiences) {
        this.link = new Link(link, url);
        if (experiences.length != 0) {
            experienceList.addAll(Arrays.asList(experiences));
        } else throw new IllegalArgumentException("At least one work period is required !!!");
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        return experienceList.equals(that.experienceList);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + experienceList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(link);
        for (Experience w : experienceList) {
            str.append(w).append("\n");
        }
        return str.toString();
    }
}
