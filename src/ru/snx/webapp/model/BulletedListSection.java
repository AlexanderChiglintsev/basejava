package ru.snx.webapp.model;

import java.util.List;

public class BulletedListSection extends AbstractSection {
    private List<String> information;

    public BulletedListSection(List<String> information) {
        this.information = information;
    }

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }
}
