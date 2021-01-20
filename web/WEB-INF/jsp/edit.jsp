<%@ page import="ru.snx.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.snx.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <fieldset>
            <legend>Контактная информация</legend>
            <dl>
                <dt>Имя:</dt>
                <dd><input type="text" name="fullName" value="${resume.fullName}" required></dd>
            </dl>
            <h3>Контакты:</h3>
            <p>
                <c:forEach var="type" items="<%=ContactType.values()%>">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                    </dl>
                </c:forEach>
            </p>
            <input type="hidden" name="edit" value="contacts">
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Отменить</button>
        </fieldset>
    </form>

    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <fieldset>
            <legend>Личные качества</legend>
            <p>
                <textarea name="personal" required><%=
                resume.getSection(SectionType.PERSONAL) == null ?
                "" : ((TextSection) resume.getSection(SectionType.PERSONAL)).getInformation()
                %></textarea>
            </p>
            <input type="hidden" name="edit" value="personal">
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Отменить</button>
        </fieldset>
    </form>

    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <fieldset>
            <legend>Позиция</legend>
            <p>
                <textarea name="objective" required><%=
                resume.getSection(SectionType.OBJECTIVE) == null ?
                "" : ((TextSection) resume.getSection(SectionType.OBJECTIVE)).getInformation()
                %></textarea>
            </p>
            <input type="hidden" name="edit" value="objective">
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Отменить</button>
        </fieldset>
    </form>

    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <fieldset>
            <legend>Достижения</legend>
            <p>
                <c:set var="list" value="<%=resume.getSection(SectionType.ACHIEVEMENT) == null ?
                                            null : ((ListSection) resume.getSection(SectionType.ACHIEVEMENT)).getInformation()
                                            %>" scope="request"/>
                <ul>
                    <c:forEach var="text" items="${list}">
                        <li>
                                ${text}&nbsp;<a href="resume?uuid=${resume.uuid}&action=deleteAchString&str=${fn:escapeXml(text)}"><img src="img/delete.png"
                                                                                                                                        alt="Delete item">
                                </a>
                        </li>
                    </c:forEach>
                </ul>
                <textarea name="achievement" required></textarea>
            </p>
            <input type="hidden" name="edit" value="achievement">
            <button type="submit">Добавить запись</button>
        </fieldset>
    </form>

    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <fieldset>
            <legend>Квалификация</legend>
            <p>
                <c:set var="list" value="<%=resume.getSection(SectionType.QUALIFICATION) == null ?
                                            null : ((ListSection) resume.getSection(SectionType.QUALIFICATION)).getInformation()
                                            %>" scope="request"/>
            <ul>
                <c:forEach var="text" items="${list}">
                    <li>
                            ${text}&nbsp;<a href="resume?uuid=${resume.uuid}&action=deleteQualString&str=${fn:escapeXml(text)}"><img src="img/delete.png"
                                                                                                                                    alt="Delete item">
                    </a>
                    </li>
                </c:forEach>
            </ul>
            <textarea name="qualification" required></textarea>
            </p>
            <input type="hidden" name="edit" value="qualification">
            <button type="submit">Добавить запись</button>
        </fieldset>
    </form>

    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <fieldset>
            <legend>Опыт работы</legend>
            <p>
                <c:set var="list" value="<%=resume.getSection(SectionType.EXPERIENCE) == null ?
                                            null : ((OrganizationSection) resume.getSection(SectionType.EXPERIENCE)).getInformation()
                                            %>" scope="request"/>
                    <ul>
                        <c:forEach var="org" items="${list}">
                            <jsp:useBean id="org" type="ru.snx.webapp.model.Organization"/>
                            <li><%=org.getUrl() == null ? org.getName() : "<a href="+org.getUrl()+">"+org.getName()+"</a>"%>
                                <a href="resume?uuid=<%=resume.getUuid()%>&action=deleteExpOrg&str=<%=org.getName()%>"><img src="img/delete.png" alt="Delete item">
                                </a>
                            </li>
                            <c:forEach var="exp" items="${org.experienceList}">
                                ${exp.startDate} -
                                <c:if test="${exp.endDate != '3000-01'}">
                                    ${exp.endDate}
                                </c:if>
                                <c:if test="${exp.endDate == '3000-01'}">
                                    <c:out value="по н.в."/>
                                </c:if>
                                &nbsp
                                <i><b>${exp.position}</b></i><br/>
                                <p>${exp.description}</p>
                            </c:forEach>
                        </c:forEach>
                    </ul>

                    <dl>
                        <dt>Название организации</dt>
                        <dd><input type="text" name="orgName" size=30" value="" required></dd>
                    </dl>
                    <dl>
                        <dt>URL</dt>
                        <dd><input type="url" name="url" size=30" value=""></dd>
                    </dl>
                    <dl>
                        <dt>Дата начала</dt>
                        <dd><input type="text" name="dateBegin" size=30" value="" required placeholder="yyyy-MM"></dd>
                    </dl>
                    <dl>
                        <dt>Дата окончания</dt>
                        <dd><input type="text" name="dateEnd" size=30" value=""></dd>
                    </dl>
                    <dl>
                        <dt>Должность</dt>
                        <dd><input type="text" name="position" size=30" value=""></dd>
                    </dl>
                    <dl>
                        <dt>Описание</dt>
                        <dd><input type="text" name="description" size=30" value="" required></dd>
                    </dl>
            </p>
            <input type="hidden" name="edit" value="experience">
            <button type="submit">Добавить запись</button>
        </fieldset>
    </form>

    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <fieldset>
            <legend>Образование</legend>
            <p>
                <c:set var="list" value="<%=resume.getSection(SectionType.EDUCATION) == null ?
                                            null : ((OrganizationSection) resume.getSection(SectionType.EDUCATION)).getInformation()
                                            %>" scope="request"/>
                <ul>
                    <c:forEach var="edu" items="${list}">
                        <jsp:useBean id="edu" type="ru.snx.webapp.model.Organization"/>
                        <li><%=edu.getUrl() == null ? edu.getName() : "<a href="+edu.getUrl()+">"+edu.getName()+"</a>"%>
                            <a href="resume?uuid=<%=resume.getUuid()%>&action=deleteEduOrg&str=<%=edu.getName()%>"><img src="img/delete.png" alt="Delete item">
                            </a>
                        </li>
                         <c:forEach var="exp" items="${edu.experienceList}">
                            ${exp.startDate} -
                            <c:if test="${exp.endDate != '3000-01'}">
                                ${exp.endDate}
                            </c:if>
                            <c:if test="${exp.endDate == '3000-01'}">
                                <c:out value="по н.в."/>
                            </c:if>
                            &nbsp
                            <i><b>${exp.position}</b></i><br/>
                        </c:forEach>
                    </c:forEach>
                </ul>

                <dl>
                    <dt>Название организации</dt>
                    <dd><input type="text" name="orgName" size=30" value="" required></dd>
                </dl>
                <dl>
                    <dt>URL</dt>
                    <dd><input type="url" name="url" size=30" value=""></dd>
                </dl>
                <dl>
                    <dt>Дата начала</dt>
                    <dd><input type="text" name="dateBegin" size=30" value="" required placeholder="yyyy-MM"></dd>
                </dl>
                <dl>
                    <dt>Дата окончания</dt>
                    <dd><input type="text" name="dateEnd" size=30" value=""></dd>
                </dl>
                <dl>
                    <dt>Описание</dt>
                    <dd><input type="text" name="position" size=30" value="" required></dd>
                </dl>
            </p>
            <input type="hidden" name="edit" value="education">
            <button type="submit">Добавить запись</button>
        </fieldset>
    </form>

</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
