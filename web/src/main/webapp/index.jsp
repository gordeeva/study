
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty sessionScope.lang ? sessionScope.lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="com.sam.app.i18n.Messages" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="main.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<jsp:include page="indexHeader.jsp"/>
<table id="main_table">
    <tbody>
    <tr>
        <td>
            <h3>
                <a href="/webapp/EmployeeServlet"><fmt:message key="EMPLOYEES.LABEL"/></a> <br>
                <a href="/webapp/DepartmentServlet"><fmt:message key="DEPARTMENTS.LABEL"/></a> <br>
                <a href="/webapp/RoleServlet"><fmt:message key="ROLES.LABEL"/></a> <br>
            </h3>
        </td>
    </tr>
    </tbody>
</table>
<%@include file="footer.jsp" %>

</body>
</html>