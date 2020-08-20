package ru.snx.webapp.model;

import java.util.List;

public class OrganizationSection extends AbstractSection {
    private List<Experience> information;

    public OrganizationSection(List<Experience> information) {
        this.information = information;
    }

    public List<Experience> getInformation() {
        return information;
    }

    public void setInformation(List<Experience> information) {
        this.information = information;
    }
}
