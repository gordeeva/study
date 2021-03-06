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
</head>
<body>

<%@include file="header.jsp" %>
<table id="main_table">
    <tbody>
    <tr>
        <td>

            <form id="form"
                  action="${pageContext.request.contextPath}/EmployeeServlet"
                  method="post">
                <input type="hidden" id="userId" name="id"/>
                <table border=1>
                    <tr>
                        <td align="left"><fmt:message key="NAME.LABEL"/></td>
                        <td align="right"><input type="text" id="userName"
                                                 name="name"/></td>
                    </tr>
                    <tr>
                        <td align="left"><fmt:message
                                key="DEPARTMENT.LABEL"/></td>
                        <td align="right">
                            <select id="departmentName" name="department" style="width:100%;">
                                <c:forEach items="${departments}"
                                           var="department">
                                    <option value="${department.id}"><c:out
                                            value="${department.name}"/></option>
                                </c:forEach>
                            </select></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div style="text-align: center">
                                <input type="submit" name="action" value="update" id="updateButton" value="<fmt:message key="UPDATE.BUTTON" />"/>
                                <div class="divider"/>
                                <input type="submit" name="action" value="add" id="addButton" value="<fmt:message key="ADD.BUTTON" />"/>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
            <p>

            <p>
            <table id="all_employees_table" border=1>
                <thead>
                <tr>
                    <th><fmt:message key="UPDATE.TABLE_HEADER"/></th>
                    <th>Id</th>
                    <th><fmt:message key="NAME.LABEL"/></th>
                    <th><fmt:message key="DEPARTMENT.LABEL"/></th>
                    <th><fmt:message key="ROLES.LABEL"/></th>
                    <th><fmt:message key="UPDATE_ROLES.TABLE_HEADER"/></th>
                    <th><fmt:message key="DELETE.TABLE_HEADER"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employees}" var="employee">
                    <c:set var="emp" value="${employee}"/>
                    <tr>
                        <td align="center"><input type="radio" name="radios"
                              onchange="OnRadioSelected(this, ${employee.department.id})"/>
                        </td>
                        <td name="id" align="center"><c:out
                                value="${employee.id}"/></td>
                        <td name="emp_name" align="center"><c:out
                                value="${employee.name}"/></td>
                        <td name="dep_name" align="center"><c:out
                                value="${employee.department.name}"/></td>
                        <td name="existing_roles_management" align="center">
                            <form name="delete_role"
                                  action="/webapp/EmployeeServlet"
                                  method="post">
                                <input type="hidden" name="action"
                                       value="deleteRole"/>
                                <input type="hidden" name="id"
                                       value="<c:out value="${employee.id}"/>"/>
                                <select name="existing_roles"
                                        style="width:100%;">
                                    <c:forEach items="${employee.roles}"
                                               var="role">
                                        <option value="${role.id}"><c:out
                                                value="${role.name}"/></option>
                                        <br>
                                    </c:forEach>
                                </select><br>
                                <input type="submit"
                                       value="<fmt:message key="DELETE.BUTTON"/>"/>
                            </form>
                        </td>
                        <td name="new_roles_management" align="center">
                            <form action="/webapp/EmployeeServlet"
                                  method="post">
                                <input type="hidden" name="action"
                                       value="addRole"/> <input
                                    type="hidden" name="id"
                                    value="<c:out value="${employee.id}"/>"/>
                                <select name="new_roles" style="width:100%;">
                                    <c:forEach items="${employee.rolesToAdd}"
                                               var="role">
                                        <option value="${role.id}"><c:out
                                                value="${role.name}"/></option>
                                    </c:forEach>
                                </select> <br> <input type="submit"
                                                      value="<fmt:message key="ADD.BUTTON" />">
                            </form>
                        </td>
                        <td align="center"><a
                                href="/webapp/EmployeeServlet?action=delete&id=<c:out value="${employee.id}"/>"><fmt:message
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

<script type="text/javascript" src="employee.js"></script>

</body>
</html>