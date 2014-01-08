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
	
	<%@include file="header.jsp" %>
			<form id="form" action="/webapp/RoleServlet" method="post">
				<table border=1>
					<tr>
						<td align="left">id</td>
						<td align="right"><input type="text" id="userId" name="id" /></td>
					</tr>
					<tr>
						<td align="left">name</td>
						<td align="right"><input type="text" id="userName"
							name="name" /></td>
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
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${roles}" var="role">
						<tr>
							<td align="center"><input type="checkbox" name="checkRadio"
								onclick="OnChangeCheckbox(this)" id="chk1"
								class="userCheckboxes" /></td>
							<td align="center"><c:out value="${role.id}" /></td>
							<td align="center"><c:out value="${role.name}" /></td>
							<td align="center"><a
								href="/webapp/RoleServlet?action=delete&id=<c:out value="${role.id}"/>">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		<div></div>
		<!-- очищающий блок -->
		<div id="substrate-footer"></div>
		<!-- блок подложка подвала сайта -->

	<%@include file="footer.jsp" %>

	<script type="text/javascript" src="role.js"></script>
</body>
</html>