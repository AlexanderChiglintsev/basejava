package ru.snx.webapp.model;

public class TextSection extends AbstractSection<String> {
    private String information;

    public TextSection(String information) {
        this.information = information;
    }


    @Override
    public String getInformation() {
        return information;
    }

    @Override
    public void setInformation(String information) {
        this.information = information;
    }
}
