package ru.snx.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String information;

    public TextSection() {
    }

    public TextSection(String information) {
        Objects.requireNonNull(information, "information must not be null !!!");
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return information.equals(that.information);
    }

    @Override
    public int hashCode() {
        return information.hashCode();
    }

    @Override
    public String toString() {
        return " " + information;
    }
}
