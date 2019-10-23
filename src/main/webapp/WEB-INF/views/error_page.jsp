<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.error.page.title"/></title>
</head>
<body>
<h4 style="color: red">Exception: <b>${exceptionType}</b></h4>

<h1 style="color: #454545">Handler method: <b> ${exceptionMessage}</b></h1>
</body>
</html>
