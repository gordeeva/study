<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty sessionScope.lang ? sessionScope.lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="com.sam.app.i18n.Messages" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8" />
<title>Employees</title>
<link href="main.css" rel="stylesheet" type="text/css" />
<script>
    var updateButtonName = '<fmt:message key="UPDATE.BUTTON" />';
    var addButtonName = '<fmt:message key="ADD.BUTTON" />';
</script>
</head>
<body>


	<jsp:include page="header.jsp" />
	<table id="main_table">
	<tbody>
	<tr><td>
			<form id="form" action="/webapp/DepartmentServlet" method="post">
				<table border=1>
					<tr>
						<td align="left">ID</td>
						<td align="right"><input type="text" id="userId" name="id" /></td>
					</tr>
					<tr>
						<td align="left"><fmt:message key="NAME.LABEL" /></td>
						<td align="right"><input type="text" id="userName"
							name="name" /></td>
					</tr>
					<tr>
						<td colspan=3 align="center"><input type="submit"
							id="updateButton" value="<fmt:message key="UPDATE.BUTTON" />" /></td>
					</tr>
				</table>
			</form>
			<p></p>
			<p></p>
			<table id="table" border=1>
				<thead>
					<tr>
						<th><fmt:message key="UPDATE.TABLE_HEADER" /></th>
						<th>ID</th>
						<th><fmt:message key="NAME.LABEL" /></th>
						<th><fmt:message key="EMPLOYEES.LABEL" /></th>
						<th><fmt:message key="DELETE.TABLE_HEADER" /></th>
					</tr>
				</thead>
                <tbody>
                <c:forEach items="${departments}" var="department">
                    <tr>
                        <td align="center"><input type="checkbox" name="checkRadio"
                                                  onclick="OnChangeCheckbox(this)" id="chk1"
                                                  class="userCheckboxes"/></td>
                        <td align="center"><c:out value="${department.id}"/></td>
                        <td align="center"><c:out value="${department.name}"/></td>
                        <td align="center">
                            <c:forEach items="${department.employees}" var="employee">
                                <c:out value="${employee.name}"/> <br>
                            </c:forEach>
                        </td>
                        <td align="center"><a
                                href="/webapp/DepartmentServlet?action=delete&id=<c:out value="${department.id}"/>">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
			</table>


	</td></tr>
	</tbody>
	</table>
	<%@include file="footer.jsp" %>

	<script type="text/javascript" src="department.js"></script>
</body>
</html>