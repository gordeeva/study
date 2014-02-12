<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<header>
    <link href="/styles/main.css" rel="stylesheet" type="text/css"/>
</header>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="com.sam.app.i18n.Messages"/>
</div>
<div></div>
<div id="substrate-footer"></div>
</div>
<!-- конец блока PAGE -->
<div id="footer">
    <h4>
        <fmt:message key="ADMIN_CONTACTS.LABEL"/>
        <a href="mailto:admin@webapp.com">admin@webapp.com</a>
    </h4>
</div>
