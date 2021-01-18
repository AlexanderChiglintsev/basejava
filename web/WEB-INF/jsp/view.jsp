<%@ page import="ru.snx.webapp.model.TextSection" %>
<%@ page import="ru.snx.webapp.model.ListSection" %>
<%@ page import="ru.snx.webapp.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"
                                                                                      alt="Edit"></a></h2>
    <h3>Контакты:</h3>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.snx.webapp.model.ContactType, java.lang.String>"/>
            <c:choose>
                <c:when test="${contactEntry.key.name() == 'SKYPE'}">
                    <img src="img/skype.png" alt="skype"/>
                </c:when>
                <c:when test="${contactEntry.key.name() == 'EMAIL'}">
                    <img src="img/email.png" alt="email"/>
                </c:when>
                <c:when test="${contactEntry.key.name() == 'LINKEDIN'}">
                    <img src="img/lin.png" alt="linkedin"/>
                </c:when>
                <c:when test="${contactEntry.key.name() == 'STACKOVERFLOW'}">
                    <img src="img/so.png" alt="stackoverflow"/>
                </c:when>
                <c:when test="${contactEntry.key.name() == 'GITHUB'}">
                    <img src="img/gh.png" alt="github"/>
                </c:when>
            </c:choose>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.snx.webapp.model.SectionType, ru.snx.webapp.model.AbstractSection>"/>
            <c:set var="type" value="<%=sectionEntry.getKey().name()%>" scope="request"/>
            <h3><%=sectionEntry.getKey().getTitle()%></h3>
                <c:choose>
                    <c:when test="${type == 'PERSONAL'}">
                        <p>
                            <%=((TextSection) sectionEntry.getValue()).getInformation()%>
                        </p>
                    </c:when>
                    <c:when test="${type == 'OBJECTIVE'}">
                        <p>
                            <%=((TextSection) sectionEntry.getValue()).getInformation()%>
                        </p>
                    </c:when>
                    <c:when test="${type == 'ACHIEVEMENT'}">
                        <c:set var="list" value="<%=((ListSection) sectionEntry.getValue()).getInformation()%>" scope="request"/>
                        <ul>
                            <c:forEach var="text" items="${list}">
                                <li>${text}</li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:when test="${type == 'QUALIFICATION'}">
                        <c:set var="list" value="<%=((ListSection) sectionEntry.getValue()).getInformation()%>" scope="request"/>
                        <ul>
                            <c:forEach var="text" items="${list}">
                                <li>${text}</li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:when test="${type == 'EXPERIENCE'}">
                        <c:set var="list" value="<%=((OrganizationSection) sectionEntry.getValue()).getInformation()%>" scope="request"/>
                        <ul>
                            <c:forEach var="org" items="${list}">
                                <jsp:useBean id="org" type="ru.snx.webapp.model.Organization"/>
                                <li><%=org.getUrl() == null ? org.getName() : "<a href="+org.getUrl()+">"+org.getName()+"</a>"%></li>
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
                    </c:when>
                    <c:when test="${type == 'EDUCATION'}">
                        <c:set var="list" value="<%=((OrganizationSection) sectionEntry.getValue()).getInformation()%>" scope="request"/>
                        <ul>
                            <c:forEach var="edu" items="${list}">
                                <jsp:useBean id="edu" type="ru.snx.webapp.model.Organization"/>
                                <li><%=edu.getUrl() == null ? edu.getName() : "<a href="+edu.getUrl()+">"+edu.getName()+"</a>"%></li>
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
                                <br>
                            </c:forEach>
                        </ul>
                    </c:when>
                </c:choose>
            </c:forEach>
    </p>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
