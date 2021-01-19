<%@ page import="ru.snx.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.snx.webapp.model.Resume" scope="request"/>
    <title>Добавить резюме</title>
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
            <input type="hidden" name="create" value="true">
            <input type="hidden" name="edit" value="contacts">
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Отменить</button>
        </fieldset>
    </form>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
