package ru.snx.webapp.model;

import ru.snx.webapp.utils.DateUtil;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private Link link;
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

    public static class Experience {
        private YearMonth start;
        private YearMonth end;
        private String position;
        private String description;

        public Experience(YearMonth start, YearMonth end, String position, String description) {
            Objects.requireNonNull(start, "start must not be null !!");
            Objects.requireNonNull(end, "end must not be null !!");
            Objects.requireNonNull(description, "position must not be null !!");
            this.start = start;
            this.end = end;
            this.position = position;
            this.description = description;
        }

        public Experience(YearMonth start, YearMonth end, String description) {
            this(start, end, null, description);
        }

        public Experience(YearMonth start, String position, String description) {
            this(start, DateUtil.NOW, position, description);
        }

        public Experience(YearMonth start, String description) {
            this(start, DateUtil.NOW, null, description);
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
            if (!Objects.equals(position, that.position)) return false;
            return description.equals(that.description);
        }

        @Override
        public int hashCode() {
            int result = start.hashCode();
            result = 31 * result + end.hashCode();
            result = 31 * result + (position != null ? position.hashCode() : 0);
            result = 31 * result + description.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return " Начало: " + start + "\n" +
                    " Окончание: " + end + "\n" +
                    " Позиция: " + position + "\n" +
                    " Описание: " + description;
        }
    }
}
