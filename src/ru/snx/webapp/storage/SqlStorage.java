package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.*;
import ru.snx.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.doQuery("DELETE FROM resume", (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.doTransactionQuery(connection -> {
            String uuid = r.getUuid();
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume SET full_name=? WHERE uuid = ? ")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NoExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doTransactionQuery(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContacts(r, connection);
            insertSections(r, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doQuery(
                "SELECT full_name, resume_uuid, type, value " +
                        "FROM resume " +
                        "LEFT JOIN contact " +
                        "ON resume.uuid = contact.resume_uuid " +
                        "WHERE resume.uuid=?", (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NoExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        getContacts(r, rs);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.doQuery("DELETE FROM resume WHERE uuid=?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NoExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resMap = new LinkedHashMap<>();
        return sqlHelper.doTransactionQuery(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT uuid, full_name " +
                            "FROM resume " +
                            "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String full_name = rs.getString("full_name");
                    resMap.put(uuid, new Resume(uuid, full_name));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT resume_uuid, type, value FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    getContacts(resMap.get(uuid), rs);
                }
            }
            return new ArrayList<>(resMap.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.doQuery("SELECT count(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void getContacts(Resume r, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            r.addContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void insertSections(Resume r, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                switch (e.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        ps.setString(1, r.getUuid());
                        ps.setString(2, e.getKey().name());
                        ps.setString(3, ((TextSection) e.getValue()).getInformation());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        ps.setString(1, r.getUuid());
                        ps.setString(2, e.getKey().name());
                        StringBuilder val = new StringBuilder();
                        ((ListSection) e.getValue()).getInformation().forEach(s -> val.append(s).append("\n"));
                        ps.setString(3, val.toString());
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
