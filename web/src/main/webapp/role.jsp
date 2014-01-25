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
</head>
<body>

<%@include file="header.jsp" %>

<table id="main_table">
    <tbody>
    <tr>
        <td>

            <form id="form"
                  action="${pageContext.request.contextPath}/RoleServlet"
                  method="post">
                <input type="hidden" id="roleId" name="id"/>
                <table border=1>
                    <tr>
                        <td align="left"><fmt:message key="NAME.LABEL"/></td>
                        <td align="right"><input type="text"
                                                 onkeyup="disableControlsIfNeed(this)"
                                                 onblur="disableControlsIfNeed(this)"
                                                 onchange="disableControlsIfNeed(this)"
                                                 id="roleName"
                                                 name="name"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div style="text-align: center">
                                <button type="submit" name="action"
                                        value="update" id="updateButton">
                                    <fmt:message key="UPDATE.BUTTON"/></button>
                                <button type="submit" name="action" value="add"
                                        id="addButton"><fmt:message
                                        key="ADD.BUTTON"/></button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
            <p>
                <c:if test="${error != null}">
                    <div id="errorMessage">
                        <fmt:message key="${error}"/>
                    </div>
                </c:if>
            <p>
            <table id="table" border=1>
                <thead>
                <tr>
                    <th align="center"><fmt:message
                            key="UPDATE.TABLE_HEADER"/></th>
                    <th align="center">Id</th>
                    <th align="center"><fmt:message key="NAME.LABEL"/></th>
                    <th align="center"><fmt:message
                            key="DELETE.TABLE_HEADER"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${roles}" var="role">
                    <tr>
                        <td align="center"><input type="radio"
                                                  name="radios"
                                                  onchange="OnRadioSelected(this)"/>
                        </td>
                        <td align="center"><c:out value="${role.id}"/></td>
                        <td align="center"><c:out value="${role.name}"/></td>
                        <td align="center">
                            <c:if test="${role.getEmployees().isEmpty()}">
                                <a
                                        href="${pageContext.request.contextPath}/RoleServlet?action=delete&id=<c:out value="${role.id}"/>"><fmt:message
                                        key="DELETE.TABLE_HEADER"/></a>
                            </c:if>
                            <c:if test="${!role.getEmployees().isEmpty()}">
                                <a title="<fmt:message key="ROLE.REMOVE.HINT"/> "
                                   href="javascript:void(0)"><fmt:message
                                        key="DELETE.TABLE_HEADER"/></a>
                            </c:if>
                        </td>
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