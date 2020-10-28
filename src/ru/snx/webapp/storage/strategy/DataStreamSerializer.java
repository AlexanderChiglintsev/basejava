package ru.snx.webapp.storage.strategy;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(resume.getSections().size());
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey().name()) {
                    case "PERSONAL":
                        dos.writeUTF(((TextSection) entry.getValue()).getInformation());
                        break;
                    case "OBJECTIVE":
                        dos.writeUTF(((TextSection) entry.getValue()).getInformation());
                        break;
                    case "ACHIEVEMENT":
                        writeListSection(dos, entry.getValue());
                        break;
                    case "QUALIFICATION":
                        writeListSection(dos, entry.getValue());
                        break;
                    case "EXPERIENCE":
                        writeOrgSection(dos, entry.getValue());
                        break;
                    case "EDUCATION":
                        writeOrgSection(dos, entry.getValue());
                        break;
                }

            }

        } catch (IOException e) {
            throw new StorageException("doWrite() error !!!", null, e);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st.name()) {
                    case "PERSONAL":
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case "OBJECTIVE":
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                        resume.addSection(st, readListSection(dis));
                        break;
                    case "QUALIFICATION":
                        resume.addSection(st, readListSection(dis));
                        break;
                    case "EXPERIENCE":
                        resume.addSection(st, readOrgSection(dis));
                        break;
                    case "EDUCATION":
                        resume.addSection(st, readOrgSection(dis));
                        break;
                }

            }
            return resume;
        }
    }

    private void writeListSection(DataOutputStream dos, AbstractSection section) throws IOException {
        ListSection ls = (ListSection) section;
        List<String> listInfo = ls.getInformation();
        dos.writeInt(listInfo.size());
        for (String s : listInfo) {
            dos.writeUTF(s);
        }
    }

    private void writeOrgSection(DataOutputStream dos, AbstractSection section) throws IOException {
        OrganizationSection os = (OrganizationSection) section;
        List<Organization> listInfo = os.getInformation();
        dos.writeInt(listInfo.size());
        for (Organization org : listInfo) {
            dos.writeUTF(org.getName());
            dos.writeUTF(org.getUrl() != null ? org.getUrl() : "null");
            List<Organization.Experience> listExp = org.getExperienceList();
            dos.writeInt(listExp.size());
            for (Organization.Experience exp : listExp) {
                dos.writeUTF(exp.getStartDate().toString());
                dos.writeUTF(exp.getEndDate().toString());
                dos.writeUTF(exp.getPosition() != null ? exp.getPosition() : "null");
                dos.writeUTF(exp.getDescription());
            }
        }
    }

    private AbstractSection readListSection(DataInputStream dis) throws IOException {
        List<String> info = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            info.add(dis.readUTF());
        }
        return new ListSection(info);
    }

    private AbstractSection readOrgSection(DataInputStream dis) throws IOException {
        List<Organization> info = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            List<Organization.Experience> exp = new ArrayList<>();
            int count = dis.readInt();
            for (int j = 0; j < count; j++) {
                exp.add(new Organization.Experience(YearMonth.parse(dis.readUTF()), YearMonth.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
            }
            info.add(new Organization(name, url, exp));
        }
        return new OrganizationSection(info);
    }

}
