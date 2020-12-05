package ru.snx.webapp.storage;

import ru.snx.webapp.exceptions.NoExistStorageException;
import ru.snx.webapp.model.ContactType;
import ru.snx.webapp.model.Resume;
import ru.snx.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume SET full_name=? WHERE uuid = ? ")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NoExistStorageException(r.getUuid());
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            insertContacts(r, connection);
            return null;
        });

        sqlHelper.doQuery("UPDATE resume SET uuid=?, full_name=? WHERE uuid = ? ", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.setString(3, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NoExistStorageException(r.getUuid());
            }
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
                ps.executeUpdate();
            }
            insertContacts(r, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doQuery(
                "SELECT uuid, full_name, type, value " +
                        "FROM resume " +
                        "LEFT JOIN contact " +
                        "ON resume.uuid = contact.resume_uuid " +
                        "WHERE uuid=?", (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NoExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    extractContacts(r, rs);
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
        return sqlHelper.doTransactionQuery(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT uuid, full_name " +
                            "FROM resume " +
                            "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                List<Resume> resList = new ArrayList<>();
                while (rs.next()) {
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    try (PreparedStatement ps1 = conn.prepareStatement(
                            "SELECT type, value " +
                                    "FROM contact " +
                                    "WHERE resume_uuid = ?")) {
                        ps1.setString(1, resume.getUuid());
                        ResultSet rs1 = ps1.executeQuery();
                        rs1.next();
                        extractContacts(resume, rs1);
                    }
                    resList.add(resume);
                }
                return resList;
            }
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

    private void extractContacts(Resume r, ResultSet rs) throws SQLException {
        do {
            ContactType contactType = ContactType.valueOf(rs.getString("type"));
            String value = rs.getString("value");
            r.addContact(contactType, value);
        } while (rs.next());
    }
}
