package ru.snx.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection<List<String>> {
    private List<String> information;

    public ListSection(List<String> information) {
        this.information = information;
    }

    @Override
    public List<String> getInformation() {
        return information;
    }

    @Override
    public void setInformation(List<String> information) {
        this.information = information;
    }
}
