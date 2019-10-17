<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.home.home.text"/></title>
</head>
<body>

<c:if test="${!empty posts}">
    <c:forEach items="${posts}" var="post">
        <section class="card z-depth2">
            <c:if test="${post.imageBase64.length() != 0}">
                <img src="data:image/*;base64,${post.getImageBase64()}"/>
            </c:if>
            <div class="content">
                <h3><c:out value="${post.title}"/></h3>
                <p>by <strong><c:out value="${post.getAuthorName()}"/></strong> - <c:out
                        value="${post.formattedDate}"/></p>
            </div>
            <div class="footer">
                <a href="/post/show/${post.uri}" class="button outline primary"><spring:message
                        code="label.post.read.more"/></a>
            </div>
        </section>
    </c:forEach>
</c:if>

<c:if test="${empty posts}">
    <h4><spring:message code="label.home.postNotFound"/></h4>
</c:if>

</body>
</html>
