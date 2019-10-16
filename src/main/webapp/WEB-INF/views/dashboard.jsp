<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.dashboard.title"/></title>
</head>
<body>

<c:forEach items="${uris}" var="uri">
    <br>
    <a href=" ${uri.key}"> ${uri.value}</a>
    <br>
</c:forEach>

</body>
</html>


