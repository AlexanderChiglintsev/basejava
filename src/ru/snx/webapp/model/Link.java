package ru.snx.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String organization;
    private String url;

    Link(String organization, String url) {
        Objects.requireNonNull(organization, "organization must not be null !!!");
        this.organization = organization;
        this.url = url;
    }

    String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!organization.equals(link.organization)) return false;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        int result = organization.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Организация: " + organization + '\n' +
                " URL: " + url + "\n";
    }
}
