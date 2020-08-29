package ru.snx.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private YearMonth start;
    private YearMonth end;
    private String position;
    private String description;

    public Experience(YearMonth start, YearMonth end, String position, String description) {
        Objects.requireNonNull(start, "start must not be null !!!");
        Objects.requireNonNull(end, "end must not be null !!!");
        Objects.requireNonNull(description, "position must not be null !!!");
        this.start = start;
        this.end = end;
        this.position = position;
        this.description = description;
    }

    public Experience(YearMonth start, YearMonth end, String description) {
        Objects.requireNonNull(start, "start must not be null !!!");
        Objects.requireNonNull(end, "end must not be null !!!");
        Objects.requireNonNull(description, "position must not be null !!!");
        this.start = start;
        this.end = end;
        this.position = null;
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
