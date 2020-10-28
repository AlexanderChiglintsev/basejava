package ru.snx.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null!");
        Objects.requireNonNull(fullName, "fullName must not be null!");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
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

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void addSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        System.out.println(Objects.equals(sections.get(SectionType.ACHIEVEMENT), resume.sections.get(SectionType.ACHIEVEMENT)));
        System.out.println(Objects.equals(sections.get(SectionType.EDUCATION), resume.sections.get(SectionType.EDUCATION)));
        System.out.println(Objects.equals(sections.get(SectionType.EXPERIENCE), resume.sections.get(SectionType.EXPERIENCE)));
        System.out.println(Objects.equals(sections.get(SectionType.OBJECTIVE), resume.sections.get(SectionType.OBJECTIVE)));
        System.out.println(Objects.equals(sections.get(SectionType.PERSONAL), resume.sections.get(SectionType.PERSONAL)));
        System.out.println(Objects.equals(sections.get(SectionType.QUALIFICATION), resume.sections.get(SectionType.QUALIFICATION)));
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        System.out.println("ID: " + uuid);
        System.out.println("Полное имя: " + fullName);
        System.out.println("\nСписок контактов:\n");
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }

        for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        return "\n";
    }
}
