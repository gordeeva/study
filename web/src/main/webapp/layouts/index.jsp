<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/main.css" />
</head>
<body>
    <tiles:insertAttribute name="header" />
    <tiles:insertAttribute name="footer" />
</body>
</html>