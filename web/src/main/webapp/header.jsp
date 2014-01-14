<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="com.sam.app.i18n.Messages" />

<c:out value="${lang}"/> <br>
<c:out value="${sessionScope.lang}"/>
<div id="page">
	<div id="header">
		<div class="left">
			<h4>
				<a href="/webapp/"><fmt:message key="MAIN.LINK" /></a>
			</h4>
		</div>
		<div class="right">
			<h4>
				
				<a href="<%=request.getAttribute("javax.servlet.forward.request_uri") %>?action=locale&lang=en">EN</a> 
				<a href="<%=request.getAttribute("javax.servlet.forward.request_uri") %>?action=locale&lang=ru">RU</a>
			</h4>
		</div>
		<br>


	</div>
	<div id="content">