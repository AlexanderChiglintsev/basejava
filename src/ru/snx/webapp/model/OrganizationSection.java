package ru.snx.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Experience> information;

    public OrganizationSection(List<Experience> information) {
        Objects.requireNonNull(information, "information must not be null !!!");
        this.information = information;
    }

    public List<Experience> getInformation() {
        return information;
    }

    public void setInformation(List<Experience> information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return information.equals(that.information);
    }

    @Override
    public int hashCode() {
        return information.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Experience ex : information) {
            str.append(ex).append("\n");
        }
        return str.toString();
    }
}
