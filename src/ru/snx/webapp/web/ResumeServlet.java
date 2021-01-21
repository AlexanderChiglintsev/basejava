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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        String create = request.getParameter("create");
        Resume r;
        String value;
        String dateEnd;
        switch (editType) {
            case "contacts":
                if (create != null) {
                  r = new Resume(uuid, "");
                  storage.save(r);
                } else {
                    r = storage.get(uuid);
                }
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
                forward(r, "/WEB-INF/jsp/edit.jsp", request, response);
                break;
            case "personal":
            case "objective":
                editTS(storage.get(uuid), editType.equals("personal") ? SectionType.PERSONAL : SectionType.OBJECTIVE, editType, request, response);
                break;
            case "achievement":
            case "qualification":
                addStrInLS(storage.get(uuid), editType.equals("achievement") ? SectionType.ACHIEVEMENT : SectionType.QUALIFICATION, editType, request, response);
                break;
            case "experience":
            case "education":
                addOrg(storage.get(uuid), editType.equals("experience") ? SectionType.EXPERIENCE : SectionType.EDUCATION, request, response);
                break;
            case "addEduItem":
                r = storage.get(uuid);
                List<Organization> edus = ((OrganizationSection) r.getSection(SectionType.EDUCATION)).getInformation();
                String searchKey = request.getParameter("str");
                dateEnd = request.getParameter("dateEnd");
                if (!dateEnd.equals("")) {
                    dateEnd = dateEnd.trim();
                } else {
                    dateEnd = "3000-01";
                }
                for (Organization org : edus) {
                    if (org.getName().equals(searchKey)) {
                        org.getExperienceList().add(new Organization.Experience(
                                YearMonth.parse(request.getParameter("dateBegin").trim()),
                                YearMonth.parse(dateEnd),
                                request.getParameter("position").trim())
                        );
                        break;
                    }
                }
                storage.update(r);
                forward(r, "/WEB-INF/jsp/edit.jsp", request, response);
                break;
            case "addExpItem":
                r = storage.get(uuid);
                List<Organization> exps = ((OrganizationSection) r.getSection(SectionType.EXPERIENCE)).getInformation();
                searchKey = request.getParameter("str");
                dateEnd = request.getParameter("dateEnd");
                if (!dateEnd.equals("")) {
                    dateEnd = dateEnd.trim();
                } else {
                    dateEnd = "3000-01";
                }
                for (Organization org : exps) {
                    if (org.getName().equals(searchKey)) {
                        org.getExperienceList().add(new Organization.Experience(
                                YearMonth.parse(request.getParameter("dateBegin").trim()),
                                YearMonth.parse(dateEnd),
                                request.getParameter("position").trim(),
                                request.getParameter("description"))
                        );
                        break;
                    }
                }
                storage.update(r);
                forward(r, "/WEB-INF/jsp/edit.jsp", request, response);
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
        String searchKey;
        switch (action) {
            case "create":
                r = new Resume(UUID.randomUUID().toString(), "");
                forward(r, "/WEB-INF/jsp/add.jsp", request, response);
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
                forward(r,
                        "view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp",
                        request,
                        response
                );
                break;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "deleteAchString":
            case "deleteQualString":
                deleteStrFromList(storage.get(uuid), action.equals("deleteAchString") ? SectionType.ACHIEVEMENT : SectionType.QUALIFICATION, request, response);
                return;
            case "deleteExpOrg":
            case "deleteEduOrg":
                deleteOrg(storage.get(uuid), action.equals("deleteExpOrg") ? SectionType.EXPERIENCE : SectionType.EDUCATION, request, response);
                return;
            case "addEduItem":
                r = storage.get(uuid);
                List<Organization> edus = ((OrganizationSection) r.getSection(SectionType.EDUCATION)).getInformation();
                searchKey = request.getParameter("str");
                List<Organization.Experience> eduList = Collections.emptyList();
                for (Organization org : edus) {
                    if (org.getName().equals(searchKey)) {
                        eduList = org.getExperienceList();
                        break;
                    }
                }
                request.setAttribute("eduList", eduList);
                request.getRequestDispatcher("/WEB-INF/jsp/addeduitem.jsp").forward(request, response);
                return;
            case "addExpItem":
                r = storage.get(uuid);
                List<Organization> orgs = ((OrganizationSection) r.getSection(SectionType.EXPERIENCE)).getInformation();
                searchKey = request.getParameter("str");
                List<Organization.Experience> expList = Collections.emptyList();
                for (Organization org : orgs) {
                    if (org.getName().equals(searchKey)) {
                        expList = org.getExperienceList();
                        break;
                    }
                }
                request.setAttribute("expList", expList);
                request.getRequestDispatcher("/WEB-INF/jsp/addexpitem.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal !");
        }
    }

    private void editTS(Resume r, SectionType st, String param, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String val = req.getParameter(param).trim();
        if (r.getSection(st) == null) {
            r.addSection(st, new TextSection(val));
        } else {((TextSection) r.getSection(st)).setInformation(val);}
        storage.update(r);
        forward(r, "/WEB-INF/jsp/view.jsp", req, resp);
    }

    private void addStrInLS(Resume r, SectionType st, String param, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String val = req.getParameter(param).trim();
        if (r.getSection(st) == null) {
            r.addSection(st, new ListSection(val));
        } else {((ListSection) r.getSection(st)).addInformation(val);}
        storage.update(r);
        forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
    }

    private void addOrg(Resume r, SectionType st, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (r.getSection(st) == null) {
            r.addSection(st, new OrganizationSection(getOrganization(req)));
        } else {((OrganizationSection) r.getSection(st)).addOrganization(getOrganization(req));}
        storage.update(r);
        forward(r, "/WEB-INF/jsp/edit.jsp", req, resp);
    }

    private void deleteStrFromList(Resume r, SectionType st, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchKey = req.getParameter("str").trim();
        ((ListSection) r.getSection(st)).getInformation().remove(searchKey);
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
