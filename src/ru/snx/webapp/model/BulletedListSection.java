package ru.snx.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection {
    private List<String> information;

    public BulletedListSection(String... information){
        this(Arrays.asList(information));
    }

    public BulletedListSection(List<String> information) {
        Objects.requireNonNull(information, "information must not be null !!!");
        this.information = information;
    }

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletedListSection that = (BulletedListSection) o;

        return information.equals(that.information);
    }

    @Override
    public int hashCode() {
        return information.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String s : information) {
            str.append("* ").append(s).append("\n");
        }
        return str.toString();
    }
}
