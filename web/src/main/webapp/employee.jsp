<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Employees</title>
<link href="main.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<%@include file="header.jsp"%>
    <table id="main_table">
        <tbody>
        <tr><td>

	<form id="form" action="${pageContext.request.contextPath}/EmployeeServlet" method="post">

		<table border=1>
			<tr>
				<td align="left">id</td>
				<td align="right"><input type="text" id="userId" name="id" /></td>
			</tr>
			<tr>
				<td align="left">name</td>
				<td align="right"><input type="text" id="userName" name="name" /></td>
			</tr>
            <tr>
				<td align="left">department</td>
                <td align="right">
                    <select id="departmentName" name="department">
                        <c:forEach items="${departments}" var="department">
                            <option><c:out value="${department.name}"/></option>
                        </c:forEach>
                    </select></td>
			</tr>
			<tr>
				<td colspan=3 align="center"><input type="submit"
					id="updateButton" value="Add" /></td>
			</tr>
		</table>
	</form>
	<p>
	<p>
	<table id="table" border=1>
		<thead>
			<tr>
				<th>Update</th>
				<th>Id</th>
				<th>Name</th>
				<th>Department</th>
				<th>Roles</th>
				<th>Update roles</th>
				<th>Delete record</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${employees}" var="employee">
				<tr>
					<td align="center"><input type="checkbox" name="checkRadio"
						onclick="OnChangeCheckbox(this)" id="chk1" class="userCheckboxes" />
					</td>
					<td align="center"><c:out value="${employee.id}" /></td>
					<td align="center"><c:out value="${employee.name}" /></td>
					<td align="center"><c:out value="${employee.department.name}" /></td>
					<td align="center"><c:forEach items="${employee.roles}"
							var="role">
							<c:out value="${role.name}" />
							<br>
						</c:forEach></td>
					<td>
						<form action="/webapp/EmployeeServlet" method="post">
							<input type="hidden" name="action" value="addRole" /> <input
								type="hidden" name="id" value="<c:out value="${employee.id}"/>" />
							<select name="roleName">
								<c:forEach items="${roles}"
									var="role">
						<<option><c:out value="${role.name}" /></option>
								</c:forEach>
							</select> <input type="submit" value="add">
						</form>
						<form action="/webapp/EmployeeServlet" method="post">
							<input type="hidden" name="action" value="deleteRole" /> <input
								type="hidden" name="id" value="<c:out value="${employee.id}"/>" />
							<select name="roleName" id="deleteRoleComboId">
								<c:forEach
									items="${employee.roles}"
									var="role">
						<<option><c:out value="${role.name}" /></option>
								</c:forEach>
							</select> <input type="submit" value="delete">
						</form>

					</td>
					<td align="center"><a
						href="/webapp/EmployeeServlet?action=delete&id=<c:out value="${employee.id}"/>">Delete
							record</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

        </td></tr>
        </tbody>
    </table>

	<%@include file="footer.jsp"%>

	<script type="text/javascript" src="employee.js"></script>
</body>
</html>