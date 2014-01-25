<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang"
       value="${not empty sessionScope.lang ? sessionScope.lang : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="com.sam.app.i18n.Messages"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8"/>
    <title><fmt:message key="DEPARTMENTS.LABEL"/> </title>
</head>
<body>


<jsp:include page="header.jsp"/>
<table id="main_table">
    <tbody>
    <tr>
        <td>
            <form id="form"
                  action="${pageContext.request.contextPath}/DepartmentServlet"
                  method="post">
                <input type="hidden" id="departmentId" name="id"/>
                <table border=1>
                    <tr>
                        <td align="left"><fmt:message key="NAME.LABEL"/></td>
                        <td align="right"><input type="text" id="departmentName"
                                                 name="name"
                                                 onkeyup="disableControlsIfNeed(this)"
                                                 onblur="disableControlsIfNeed(this)"
                                                 onchange="disableControlsIfNeed(this)"/>
                        </td>
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
                    <th align="center">ID</th>
                    <th align="center"><fmt:message key="NAME.LABEL"/></th>
                    <th align="center"><fmt:message key="EMPLOYEES.LABEL"/></th>
                    <th align="center"><fmt:message
                            key="DELETE.TABLE_HEADER"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${departments}" var="department">
                    <tr>
                        <td align="center"><input type="radio"
                                                  name="radios"
                                                  onchange="OnRadioSelected(this)"
                                /></td>
                        <td align="center"><c:out
                                value="${department.id}"/></td>
                        <td align="center"><c:out
                                value="${department.name}"/></td>
                        <td align="center">
                            <select name="employees" style="width:100%;">
                                <c:forEach items="${department.employees}"
                                           var="employee">
                                    <option value="${employee.id}"><c:out
                                            value="${employee.name}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                        <td align="center"><a name="deleteDepartmentLinks"
                                              title="<fmt:message key="DEPARTMENT.REMOVE.HINT"/> "
                                              href="${pageContext.request.contextPath}/DepartmentServlet?action=delete&id=<c:out value="${department.id}"/>"><fmt:message
                                key="DELETE.TABLE_HEADER"/></a>
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

<script type="text/javascript" src="department.js"></script>
</body>
</html>