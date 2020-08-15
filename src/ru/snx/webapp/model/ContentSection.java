package ru.snx.webapp.model;

import java.util.List;

public class ContentSection extends AbstractSection<List<SectionContent>> {
    private List<SectionContent> information;

    public ContentSection(List<SectionContent> information) {
        this.information = information;
    }

    @Override
    public List<SectionContent> getInformation() {
        return information;
    }

    @Override
    public void setInformation(List<SectionContent> information) {
        this.information = information;
    }
}
