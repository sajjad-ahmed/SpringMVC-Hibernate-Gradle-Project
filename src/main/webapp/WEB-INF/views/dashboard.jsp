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
<c:if test="${confirmation == 'UPDATED'}">
    <br/>
    <div class="alert success">
        <strong><spring:message code="label.success.message.prefix"/></strong>
        <spring:message code="label.user.information"/> <spring:message code="label.success.update.message.suffix"/>
    </div>
    <br/>
</c:if>
<c:forEach items="${uris}" var="uri">
    <br>
    <a href=" ${uri.key}"> ${uri.value}</a>
    <br>
</c:forEach>

</body>
</html>


