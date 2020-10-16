package ru.snx.webapp.model;

import ru.snx.webapp.utils.DateUtil;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link link;
    private List<Experience> experienceList = new ArrayList<>();

    public Organization(String link, String url, Experience... experiences) {
        this.link = new Link(link, url);
        if (experiences.length != 0) {
            experienceList.addAll(Arrays.asList(experiences));
        } else throw new IllegalArgumentException("At least one work period is required !!!");
    }

    public Organization(Link link, List<Experience> experienceList) {
        this.link = link;
        this.experienceList = experienceList;
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

    public static class Experience implements Serializable {
        private static final long serialVersionUID = 1L;

        private final YearMonth startDate;
        private final YearMonth endDate;
        private final String position;
        private final String description;

        public Experience(YearMonth startDate, YearMonth endDate, String position, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null !!");
            Objects.requireNonNull(endDate, "endDate must not be null !!");
            Objects.requireNonNull(description, "position must not be null !!");
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.description = description;
        }

        public Experience(YearMonth startDate, YearMonth endDate, String description) {
            this(startDate, endDate, null, description);
        }

        public Experience(YearMonth startDate, String position, String description) {
            this(startDate, DateUtil.NOW, position, description);
        }

        public Experience(YearMonth startDate, String description) {
            this(startDate, DateUtil.NOW, null, description);
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public YearMonth getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Experience that = (Experience) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!Objects.equals(position, that.position)) return false;
            return description.equals(that.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + (position != null ? position.hashCode() : 0);
            result = 31 * result + description.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return " Начало: " + startDate + "\n" +
                    " Окончание: " + endDate + "\n" +
                    " Позиция: " + position + "\n" +
                    " Описание: " + description;
        }
    }
}
