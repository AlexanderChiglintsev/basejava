<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="expList" type="java.util.List<ru.snx.webapp.model.Organization.Experience>" scope="request"/>
    <title>Редактирование резюме</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="<%=request.getParameter("uuid")%>">
        <fieldset>
            <legend><%=request.getParameter("str")%></legend>
            <c:forEach var="exp" items="${expList}">
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
            <dl>
                <dt>Дата начала</dt>
                <dd><input type="text" name="dateBegin" size=30" value="" required placeholder="yyyy-MM"></dd>
            </dl>
            <dl>
                <dt>Дата окончания</dt>
                <dd><input type="text" name="dateEnd" size=30" value="" placeholder="yyyy-MM"></dd>
            </dl>
            <dl>
                <dt>Должность</dt>
                <dd><input type="text" name="position" size=30" value="" required></dd>
            </dl>
            <dl>
                <dt>Описание</dt>
                <dd><input type="text" name="description" size=30" value=""></dd>
            </dl>
            <input type="hidden" name="edit" value="addExpItem">
            <input type="hidden" name="str" value="<%=request.getParameter("str")%>">
            <button type="submit">Сохранить</button>
            <button onclick="window.history.back()">Отменить</button>
        </fieldset>
    </form>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
