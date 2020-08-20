package ru.snx.webapp.model;

public class TextSection extends AbstractSection {
    private String information;

    public TextSection(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
