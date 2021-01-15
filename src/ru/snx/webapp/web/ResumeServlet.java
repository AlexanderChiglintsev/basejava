package ru.snx.webapp.web;

import ru.snx.webapp.model.*;
import ru.snx.webapp.storage.Storage;
import ru.snx.webapp.utils.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.List;

public class ResumeServlet extends javax.servlet.http.HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        String editType = request.getParameter("edit");
        Resume r;
        String value;
        switch (editType) {
            case "contacts":
                r = storage.get(uuid);
                r.setFullName(fullName);
                for (ContactType type : ContactType.values()) {
                    value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.addContact(type, value.trim());
                    } else {
                        r.getContacts().remove(type);
                    }
                }
                storage.update(r);
                response.sendRedirect("resume");
                break;
            case "personal":
                editTS(storage.get(uuid), SectionType.PERSONAL, "personal", request, response);
                break;
            case "objective":
                editTS(storage.get(uuid), SectionType.OBJECTIVE, "objective", request, response);
                break;
            case "achievement":
                addStrInLS(storage.get(uuid), SectionType.ACHIEVEMENT, "achievement", request, response);
                break;
            case "qualification":
                addStrInLS(storage.get(uuid), SectionType.QUALIFICATION, "qualification", request, response);
                break;
            case "experience":
                addOrg(storage.get(uuid), SectionType.EXPERIENCE, request, response);
                break;
            case "education":
                addOrg(storage.get(uuid), SectionType.EDUCATION, request, response);
                break;
            default:
                throw new IllegalArgumentException("Action " + editType + " is illegal !");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "create":
                //storage.delete(uuid);
                r = new Resume();
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "deleteAchString":
                deleteStrFromList(storage.get(uuid), SectionType.ACHIEVEMENT, request, response);
                return;
            case "deleteQualString":
                deleteStrFromList(storage.get(uuid), SectionType.QUALIFICATION, request, response);
                return;
            case "deleteExpOrg":
                deleteOrg(storage.get(uuid), SectionType.EXPERIENCE, request, response);
                return;
            case "deleteEduOrg":
                deleteOrg(storage.get(uuid), SectionType.EDUCATION, request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal !");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }

    private void editTS(Resume r, SectionType st, String param, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String val = req.getParameter(param).trim();
        if (r.getSection(st) == null) {
            r.addSection(st, new TextSection(val));
            storage.update(r);
            forward(r, "/WEB-INF/jsp/view.jsp", req, resp);
            return;
        }
        ((TextSection) r.getSection(st)).setInformation(val);
        storage.update(r);
        forward(r, "/WEB-INF/jsp/view.jsp", req, resp);
    }

    private void addStrInLS(Resume r, SectionType st, String param, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String val = req.getParameter(param).trim();
        if (r.getSection(st) == null) {
            r.addSection(st, new ListSection(val));
            storage.update(r);
            forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
            return;
        }
        ((ListSection) r.getSection(st)).addInformation(val);
        storage.update(r);
        forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
    }

    private void addOrg(Resume r, SectionType st, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (r.getSection(st) == null) {
            r.addSection(st, new OrganizationSection(getOrganization(req)));
            storage.update(r);
            forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
            return;
        }
        ((OrganizationSection) r.getSection(st)).addOrganization(getOrganization(req));
        forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
    }

    private void deleteStrFromList(Resume r, SectionType st, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchKey = req.getParameter("str").trim();
        ((ListSection) r.getSection(SectionType.QUALIFICATION)).getInformation().remove(searchKey);
        storage.update(r);
        forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
    }

    private void deleteOrg(Resume r, SectionType st, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchKey = req.getParameter("str").trim();
        List<Organization> orgs = ((OrganizationSection) r.getSection(st)).getInformation();
        for (int i = 0; i < orgs.size(); i++) {
            if (orgs.get(i).getName().equals(searchKey)) {
                orgs.remove(i);
                break;
            }
        }
        storage.update(r);
        forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
    }

    private Organization getOrganization(HttpServletRequest request) {
        String description = request.getParameter("description");
        String dateEnd = request.getParameter("dateEnd");
        if (description != null) {
            description = description.trim();
        }
        if (!dateEnd.equals("")) {
            dateEnd = dateEnd.trim();
        } else {
            dateEnd = "3000-01";
        }
        return new Organization(
                request.getParameter("orgName").trim(),
                request.getParameter("url").trim(),
                new Organization.Experience(
                        YearMonth.parse(request.getParameter("dateBegin").trim()),
                        YearMonth.parse(dateEnd),
                        request.getParameter("position").trim(),
                        description
                )
        );
    }

    private void forward(Resume r, String path, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("resume", r);
        req.getRequestDispatcher(path).forward(req, resp);
    }

}
