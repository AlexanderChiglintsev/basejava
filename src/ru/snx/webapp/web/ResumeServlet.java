package ru.snx.webapp.web;

import ru.snx.webapp.model.Resume;
import ru.snx.webapp.storage.Storage;
import ru.snx.webapp.utils.Config;
import ru.snx.webapp.utils.ResumeTestData;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class ResumeServlet extends javax.servlet.http.HttpServlet {

    private final String uuid1 = UUID.randomUUID().toString();
    private final String uuid2 = UUID.randomUUID().toString();
    private final String uuid3 = UUID.randomUUID().toString();
    private final String uuid4 = UUID.randomUUID().toString();
    private final String uuid5 = UUID.randomUUID().toString();
    private final Resume RES1 = new Resume(uuid1, "John");
    private final Resume RES2 = ResumeTestData.getFilledResume(uuid2, "Mike");
    private final Resume RES3 = ResumeTestData.getFilledResume(uuid3, "Alex");
    private final Resume RES4 = ResumeTestData.getFilledResume(uuid4, "Mike");
    private final Resume RES5 = ResumeTestData.getFilledResume(uuid5, "Mike");

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("resumes", storage.getAllSorted());
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);

        /*response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter w = response.getWriter();
        w.write("Hello Resumes !");

        //storage.save(RES1);
        //storage.save(RES2);
        //storage.save(RES3);
        //storage.save(RES4);
        //storage.save(RES5);

        //Writer w = response.getWriter();


        w.println("<H3>All Resumes:</H3>");
        w.println("<table>");

        w.println("<tr>");

        w.println("<th>" + "UUID" + "</th>");
        w.println("<th>" + "Full Name" + "</th>");

        w.println("</tr>");

        for (Resume r : storage.getAllSorted()) {
            w.println("<tr>");

            w.println("<td>" + r.getUuid() + "</td>");
            w.println("<td>" + r.getFullName() + "</td>");

            w.println("</tr>");
        }

        w.println("</table>");*/

    }
}
