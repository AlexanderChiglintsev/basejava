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

            Writer<Map.Entry<ContactType, String>> writerContacts = (cont) -> {
                dos.writeUTF(cont.getKey().name());
                dos.writeUTF(cont.getValue());
            };
            writeWithException(dos, new ArrayList<>(resume.getContacts().entrySet()), writerContacts);

            Writer<Map.Entry<SectionType, AbstractSection>> writerSections = (sect) -> {
                dos.writeUTF(sect.getKey().name());
                switch (sect.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) sect.getValue()).getInformation());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        Writer<String> writerStr = dos::writeUTF;
                        List<String> listStr = ((ListSection) sect.getValue()).getInformation();
                        writeWithException(dos, listStr, writerStr);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> listOrg = ((OrganizationSection) sect.getValue()).getInformation();
                        writeWithException(dos, listOrg, getWriterOrg(dos));
                        break;
                }
            };
            writeWithException(dos,new ArrayList<>(resume.getSections().entrySet()), writerSections);

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

    private <T> void writeWithException(DataOutputStream dos, List<T> list, Writer<T> writer) throws IOException {
        dos.writeInt(list.size());
        for (T s : list) {
            writer.writeIt(s);
        }
    }

    private Writer<Organization> getWriterOrg(DataOutputStream dos){
        Writer<Organization.Experience> writerExp = (exp) -> {
            dos.writeUTF(exp.getStartDate().toString());
            dos.writeUTF(exp.getEndDate().toString());
            dos.writeUTF(exp.getPosition() != null ? exp.getPosition() : "null");
            dos.writeUTF(exp.getDescription());
        };
        return (org) -> {
            dos.writeUTF(org.getName());
            dos.writeUTF(org.getUrl() != null ? org.getUrl() : "null");
            List<Organization.Experience> listExp = org.getExperienceList();
            writeWithException(dos, listExp, writerExp);
        };
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
