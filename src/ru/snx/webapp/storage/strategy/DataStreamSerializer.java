package ru.snx.webapp.storage.strategy;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.*;
import ru.snx.webapp.utils.Writer;

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

            Writer<String> writerStr = dos::writeUTF;

            Writer<Organization.Experience> writerExp = (exp) -> {
                dos.writeUTF(exp.getStartDate().toString());
                dos.writeUTF(exp.getEndDate().toString());
                dos.writeUTF(exp.getPosition() != null ? exp.getPosition() : "null");
                dos.writeUTF(exp.getDescription());
            };

            Writer<Organization> writerOrg = (org) -> {
                dos.writeUTF(org.getName());
                dos.writeUTF(org.getUrl() != null ? org.getUrl() : "null");
                List<Organization.Experience> listExp = org.getExperienceList();
                dos.writeInt(listExp.size());
                writeWithException(listExp, writerExp);
            };

            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(resume.getSections().size());
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) entry.getValue()).getInformation());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> listStr = ((ListSection) entry.getValue()).getInformation();
                        dos.writeInt(listStr.size());
                        writeWithException(listStr, writerStr);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> listOrg = ((OrganizationSection) entry.getValue()).getInformation();
                        dos.writeInt(listOrg.size());
                        writeWithException(listOrg, writerOrg);
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
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        resume.addSection(st, readListSection(dis));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(st, readOrgSection(dis));
                        break;
                }
            }
            return resume;
        }
    }

    private <T> void writeWithException(List<T> list, Writer<T> writer) throws IOException {
        for (T s : list) {
            writer.writeIt(s);
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
                YearMonth begin = YearMonth.parse(dis.readUTF());
                YearMonth end = YearMonth.parse(dis.readUTF());
                String position = dis.readUTF();
                String description = dis.readUTF();
                exp.add(new Organization.Experience(begin, end, (position.equals("null") ? null : position), description));
            }
            info.add(new Organization(name, (url.equals("null") ? null : url), exp));
        }
        return new OrganizationSection(info);
    }

}
