package ru.snx.webapp.model;

public class AbstractSection<T> {
    private T information;

    public AbstractSection(T information) {
        this.information = information;
    }

    public T getInformation() {
        return information;
    }

    public void setInformation(T information) {
        this.information = information;
    }
}
