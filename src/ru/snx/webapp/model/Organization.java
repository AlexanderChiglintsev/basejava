package ru.snx.webapp.model;

import ru.snx.webapp.utils.DateUtil;
import ru.snx.webapp.utils.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link link;
    private List<Experience> experienceList = new ArrayList<>();

    //Used for XML serialization
    public Organization() {
    }

    public Organization(String link, String url, Experience... experiences) {
        this.link = new Link(link, url);
        if (experiences.length != 0) {
            experienceList.addAll(Arrays.asList(experiences));
        } else throw new IllegalArgumentException("At least one work period is required !!!");
    }

    public Organization(String link, String url, List<Experience> experienceList) {
        this.link = new Link(link, url);
        this.experienceList = experienceList;
    }

    public String getName() {
        return link.getName();
    }

    public String getUrl() {
        return link.getUrl();
    }

    public List<Experience> getExperienceList() {
        return experienceList;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth startDate;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth endDate;
        private String position;
        private String description;

        public Experience() {
        }

        public Experience(YearMonth startDate, YearMonth endDate, String position, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null !!");
            Objects.requireNonNull(endDate, "endDate must not be null !!");
            Objects.requireNonNull(position, "position must not be null !!");
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.description = description;
        }

        public Experience(YearMonth startDate, YearMonth endDate, String position) {
            this(startDate, endDate, position, null);
        }

        public Experience(YearMonth startDate, String position, String description) {
            this(startDate, DateUtil.NOW, position, description);
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
            return startDate.equals(that.startDate) &&
                    endDate.equals(that.endDate) &&
                    position.equals(that.position) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, position, description);
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
