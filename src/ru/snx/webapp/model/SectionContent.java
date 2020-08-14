package ru.snx.webapp.model;

public class SectionContent {
    private String timeinterval;
    private String company;
    private String position;
    private String description;

    public SectionContent(String timeinterval, String company, String position, String description) {
        this.timeinterval = timeinterval;
        this.company = company;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        return "SectionContent{" +
                "timeinterval='" + timeinterval + '\'' +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getTimeinterval() {
        return timeinterval;
    }

    public void setTimeinterval(String timeinterval) {
        this.timeinterval = timeinterval;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
}
