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
    <a href="/user/add" class="button primary"> Add a new user</a>
    <h4>User list:</h4>
    <c:if test="${!empty users}">
        <table class="bordered">
            <tr>
                <th>User ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email Name</th>
                <th>Password</th>
                <th>Role</th>
                <th>Profile Picture</th>
                <th width="60">Update</th>
                <th width="60">Delete</th>
            </tr>
            <c:forEach items="${users}" var="post">
                <tr>
                    <td><c:out value="${post.id}"/></td>
                    <td><c:out value="${post.firstName}"/></td>
                    <td><c:out value="${post.lastName}"/></td>
                    <td><c:out value="${post.email}"/></td>
                    <td><c:out value="${post.password}"/></td>
                    <td><c:out value="${post.role}"/></td>
                    <td><img src="data:image/*;base64,${post.getImageBase64()}" width="70" height="50"/></td>
                    <td><a class="button primary" href="<c:url value="/user/update/${post.id}"/>">Update</a></td>
                    <td><a class="button danger" href="<c:url value="/user/delete/${post.id}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>


