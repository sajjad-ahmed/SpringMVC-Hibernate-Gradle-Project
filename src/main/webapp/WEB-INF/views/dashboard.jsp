<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<c:if test="${sessionScope.userRole == 'ADMIN' }">
    <a href="/post/manage"> Manage Post</a>
    <br>
    <br>
    <a href="/category/manage"> Manage Category</a>
    <br>
    <br>
    <a href="/user/manage"> Manage user</a>
    <br>
    <br>
    <a href="/message/send"> Send A message</a>
    <br>
    <br>
    <a href="/inbox"> My Inbox</a>
</c:if>

<c:if test="${sessionScope.userRole == 'AUTHOR' }">
    <a href="/post/manage"> Manage Post</a>
    <br>
    <br>
    <a href="/message/send"> Send A message</a>
    <br>
    <br>
    <a href="/inbox"> My Inbox</a>
</c:if>

<c:if test="${sessionScope.userRole == 'SUBSCRIBER' }">
    <a href="/message/send"> Send A message</a>
    <br>
    <br>
    <a href="/inbox"> My Inbox</a>
</c:if>

</body>
</html>


