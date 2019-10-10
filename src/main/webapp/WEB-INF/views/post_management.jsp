<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>User Management</title>
</head>

<body>
<div>
    <a href="/post/create" class="button primary"> Create A new Post</a>
    <h4>Available Posts:</h4>
    <c:if test="${!empty posts}">
        <table class="bordered">
            <tr>
                <th>Post ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Access</th>
                <th>URI</th>
                <th>Created</th>
                <th>Updated</th>
                <th width="60">Update</th>
                <th width="60">Delete</th>
            </tr>
            <c:forEach items="${posts}" var="post">
                <tr>
                    <td><c:out value="${post.id}"/></td>
                    <td><c:out value="${post.title}"/></td>
                    <td><c:out value="${post.getAuthorName()}"/></td>
                    <td><c:out value="${post.access}"/></td>
                    <td><c:out value="${post.uri}"/></td>
                    <td><c:out value="${post.createdAt}"/></td>
                    <td><c:out value="${post.updatedAt}"/></td>
                    <td><a class="button primary" href="<c:url value="/post/update/${post.id}"/>">Update</a></td>
                    <td><a class="button danger" href="<c:url value="/post/delete/${post.id}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>


