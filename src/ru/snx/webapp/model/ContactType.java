package ru.snx.webapp.model;

public enum ContactType {
    ADDRESS("Адрес"),
    PHONE("Телефон"),
    SKYPE("Skype"),
    EMAIL("E-mail"),
    LINKEDIN("LinkedIn"),
    STACKOVERFLOW("Stackoverflow"),
    GITHUB("GitHub");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title + ": ";
    }
}
