package ru.snx.webapp.model;

public enum ContactType {
    ADDRESS("Адрес"),
    PHONE("Телефон"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("E-mail") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("LinkedIn"){
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    },
    STACKOVERFLOW("Stackoverflow"){
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    },
    GITHUB("GitHub"){
        @Override
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value){
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
