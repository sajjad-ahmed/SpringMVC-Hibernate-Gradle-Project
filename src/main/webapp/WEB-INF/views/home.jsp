<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title> Home </title>
</head>
<body>
<c:if test="${!empty posts}">
    <c:forEach items="${posts}" var="post">
        <section class="card z-depth2">
            <img src="data:image/*;base64,${post.getImageBase64()}"/>

            <div class="content">
                <h3><c:out value="${post.title}"/></h3>

                <p>Author: <c:out value="${post.getAuthorName()}"/>; Date: <c:out value="${post.createdAt}"/></p>
            </div>
            <div class="footer">
                <a href="/post/show/${post.id}" class="button outline primary">Read More</a>
            </div>
        </section>
    </c:forEach>
</c:if>

<c:if test="${empty posts}">
    <h4>No post yet</h4>
</c:if>

</body>
</html>
