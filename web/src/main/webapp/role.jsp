<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang"
       value="${not empty sessionScope.lang ? sessionScope.lang : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="com.sam.app.i18n.Messages"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Employees</title>
    <link href="main.css" rel="stylesheet" type="text/css"/>
    <script>
        var updateButtonName = '<fmt:message key="UPDATE.BUTTON" />';
        var addButtonName = '<fmt:message key="ADD.BUTTON" />';
    </script>
</head>
<body>

<%@include file="header.jsp" %>

<table id="main_table">
    <tbody>
    <tr>
        <td>

            <form id="form" action="/webapp/RoleServlet" method="post">
                <table border=1>
                    <tr>
                        <td align="left"><fmt:message key="ID.LABEL"/></td>
                        <td align="right"><input type="text" id="userId"
                                                 name="id"/></td>
                    </tr>
                    <tr>
                        <td align="left"><fmt:message key="NAME.LABEL"/></td>
                        <td align="right"><input type="text" id="userName"
                                                 name="name"/></td>
                    </tr>
                    <tr>
                        <td colspan=3 align="center"><input type="submit"
                                                            id="updateButton"
                                                            value="<fmt:message key="ADD.BUTTON" />"/>
                        </td>
                    </tr>
                </table>
            </form>
            <p>

            <p>
            <table id="table" border=1>
                <thead>
                <tr>
                    <th><fmt:message key="UPDATE.TABLE_HEADER"/></th>
                    <th>Id</th>
                    <th><fmt:message key="NAME.LABEL"/></th>
                    <th><fmt:message key="DELETE.TABLE_HEADER"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${roles}" var="role">
                    <tr>
                        <td align="center"><input type="checkbox"
                                                  name="checkRadio"
                                                  onclick="OnChangeCheckbox(this)"
                                                  id="chk1"
                                                  class="userCheckboxes"/></td>
                        <td align="center"><c:out value="${role.id}"/></td>
                        <td align="center"><c:out value="${role.name}"/></td>
                        <td align="center"><a
                                href="/webapp/RoleServlet?action=delete&id=<c:out value="${role.id}"/>"><fmt:message
                                key="DELETE.TABLE_HEADER"/></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </td>
    </tr>
    </tbody>
</table>

<%@include file="footer.jsp" %>

<script type="text/javascript" src="role.js"></script>
</body>
</html>