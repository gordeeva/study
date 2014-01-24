<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<header>
    <link href="main.css" rel="stylesheet" type="text/css"/>
</header>
<c:set var="lang"
       value="${not empty sessionScope.lang ? sessionScope.lang : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="com.sam.app.i18n.Messages"/>

<div id="page">
    <div id="header">
        <div class="left">
            <h4>
                <table>
                    <tr>
                        <td style="padding: 10px;margin: 10px;">
                            <a href="${pageContext.request.contextPath}/EmployeeServlet"><fmt:message
                                    key="EMPLOYEES.LABEL"/></a>
                        </td>
                        <td style="padding: 10px;margin: 10px;">
                            <a href="${pageContext.request.contextPath}/DepartmentServlet"><fmt:message
                                    key="DEPARTMENTS.LABEL"/></a>
                        </td>
                        <td style="padding: 10px;margin: 10px;">
                            <a href="${pageContext.request.contextPath}/RoleServlet"><fmt:message
                                    key="ROLES.LABEL"/></a>
                        </td>
                    </tr>
                </table>
            </h4>
        </div>
        <div class="right">
            <h4>
                <a href="<%=request.getAttribute("javax.servlet.forward.request_uri") %>?action=locale&lang=en">en</a>
                <a href="<%=request.getAttribute("javax.servlet.forward.request_uri") %>?action=locale&lang=ru">ру</a>
            </h4>
        </div>
        <br>


    </div>
    <div id="content">