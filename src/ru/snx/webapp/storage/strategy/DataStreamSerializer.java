package ru.snx.webapp.storage.strategy;

import ru.snx.webapp.exceptions.StorageException;
import ru.snx.webapp.model.*;
import ru.snx.webapp.utils.DataReader;
import ru.snx.webapp.utils.DataWriter;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class DataStreamSerializer implements Serializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) {
        try (DataOutputStream dos = new DataOutputStream(os)) {

            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithException(dos, new ArrayList<>(resume.getContacts().entrySet()), (cont) -> {
                dos.writeUTF(cont.getKey().name());
                dos.writeUTF(cont.getValue());
            });

            writeWithException(dos, new ArrayList<>(resume.getSections().entrySet()), (sect) -> {
                dos.writeUTF(sect.getKey().name());
                switch (sect.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) sect.getValue()).getInformation());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> listStr = ((ListSection) sect.getValue()).getInformation();
                        writeWithException(dos, listStr, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> listOrg = ((OrganizationSection) sect.getValue()).getInformation();
                        writeWithException(dos, listOrg, (org) -> {
                            dos.writeUTF(org.getName());
                            String url = org.getUrl();
                            dos.writeUTF(url != null ? url : "null");
                            List<Organization.Experience> listExp = org.getExperienceList();
                            writeWithException(dos, listExp, (exp) -> {
                                dos.writeUTF(exp.getStartDate().toString());
                                dos.writeUTF(exp.getEndDate().toString());
                                String position = exp.getPosition();
                                dos.writeUTF(position != null ? position : "null");
                                dos.writeUTF(exp.getDescription());
                            });
                        });
                        break;
                }
            });

        } catch (IOException e) {
            throw new StorageException("doWrite() error !!!", null, e);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            readWithException(dis.readInt(), () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis.readInt(), () -> {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        readWithException(1, () -> resume.addSection(st, new TextSection(dis.readUTF())));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> lstStr = new ArrayList<>();
                        readWithException(dis.readInt(), () -> lstStr.add(dis.readUTF()));
                        resume.addSection(st, new ListSection(lstStr));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> lstOrg = new ArrayList<>();
                        readWithException(dis.readInt(), () -> {
                            List<Organization.Experience> lstExp = new ArrayList<>();
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            readWithException(dis.readInt(), () -> {
                                YearMonth begin = YearMonth.parse(dis.readUTF());
                                YearMonth end = YearMonth.parse(dis.readUTF());
                                String position = dis.readUTF();
                                String description = dis.readUTF();
                                lstExp.add(new Organization.Experience(
                                                begin,
                                                end,
                                                (position.equals("null") ? null : position),
                                                description
                                ));
                            });
                            lstOrg.add(new Organization(
                                            name,
                                            url.equals("null") ? null : url,
                                            lstExp
                                    ));
                        });
                        resume.addSection(st, new OrganizationSection(lstOrg));
                        break;
                }
            });

            return resume;
        }
    }

    private <T> void writeWithException(DataOutputStream dos, List<T> list, DataWriter<T> writer) throws IOException {
        dos.writeInt(list.size());
        for (T s : list) {
            writer.writeIt(s);
        }
    }

    private void readWithException(int size, DataReader reader) throws IOException {
        for (int i = 0; i < size; i++) {
            reader.readIt();
        }
    }

}
