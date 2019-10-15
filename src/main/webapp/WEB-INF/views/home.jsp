<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title> <spring:message code="label.home.home.text"/> </title>
</head>
<body>

<c:if test="${!empty posts}">
    <c:forEach items="${posts}" var="user">
        <section class="card z-depth2">
            <img src="data:image/*;base64,${user.getImageBase64()}"/>

            <div class="content">
                <h3><c:out value="${user.title}"/></h3>
                <p>Author: <c:out value="${user.getAuthorName()}"/>; Date: <c:out value="${user.formattedDate}"/></p>
            </div>
            <div class="footer">
                <a href="/post/show/${user.id}" class="button outline primary"><spring:message code="label.post.read.more"/></a>
            </div>
        </section>
    </c:forEach>
</c:if>

<c:if test="${empty posts}">
    <h4><spring:message code="label.home.postNotFound"/></h4>
</c:if>

</body>
</html>
