package ru.snx.webapp.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<ContactType, String> contacts;
    private Map<SectionType, AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null!");
        Objects.requireNonNull(uuid, "fullName must not be null!");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setSections(Map<SectionType, AbstractSection> sections) {
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        System.out.println("ID: " + uuid);
        System.out.println("Full Name: " + fullName);
        System.out.println("Phone: " + getContact(ContactType.PHONE));
        System.out.println("Skype: " + getContact(ContactType.SKYPE));
        System.out.println("E-mail: " + getContact(ContactType.EMAIL));
        System.out.println("LinkedIn: " + getContact(ContactType.LINKEDIN));
        System.out.println("GitHub: " + getContact(ContactType.GITHUB));
        System.out.println("StackOverflow: " + getContact(ContactType.STACKOVERFLOW));
        System.out.println("\nПозиция: " + ((TextSection) getSection(SectionType.OBJECTIVE)).getInformation());
        System.out.println("\nЛичные качества: " + ((TextSection) getSection(SectionType.PERSONAL)).getInformation());
        System.out.println("\nДостижения\n");
        List<String> achievements = ((BulletedListSection) getSection(SectionType.ACHIEVEMENT)).getInformation();
        for (String str : achievements) {
            System.out.println(str);
        }
        System.out.println("\nКвалификация\n");
        List<String> qualify = ((BulletedListSection) getSection(SectionType.QUALIFICATION)).getInformation();
        for (String str : qualify) {
            System.out.println(str);
        }
        System.out.println("\nОпыт работы\n");
        List<Experience> exper = ((OrganizationSection) getSection(SectionType.EXPERIENCE)).getInformation();
        for (Experience obj : exper) {
            System.out.println(obj.getOrgName());
            System.out.println(obj.getUrl() != null ? obj.getUrl() : "Not presented !!!");
            for (WorkInterval w : obj.getWorks()) {
                System.out.println(w.getStart());
                System.out.println(w.getEnd());
                System.out.println(w.getPosition());
                System.out.println(w.getDescription());
            }

        }
        System.out.println("\nОбразование\n");
        List<Experience> educations = ((OrganizationSection) getSection(SectionType.EDUCATION)).getInformation();
        for (Experience edu : educations) {
            System.out.println(edu.getOrgName());
            System.out.println(edu.getUrl() != null ? edu.getUrl() : "Not presented !!");
            for (WorkInterval w : edu.getWorks()) {
                System.out.println(w.getStart());
                System.out.println(w.getEnd());
                System.out.println(w.getDescription());
            }
        }
        return "\n";
    }
}
