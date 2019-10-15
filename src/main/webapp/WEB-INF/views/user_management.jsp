<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.user.management.title"/></title>
</head>

<body>
<div>
    <a href="/user/add" class="button primary"> <spring:message code="label.user.management.form.title"/></a>
    <h4><spring:message code="label.user.management.list.title"/></h4>
    <c:if test="${!empty users}">
        <table class="bordered">
            <tr>
                <th><spring:message code="label.user.management.id.header"/></th>
                <th><spring:message code="label.user.management.firstName.header"/></th>
                <th><spring:message code="label.user.management.lastName.header"/></th>
                <th><spring:message code="label.user.management.email.header"/></th>
                <th><spring:message code="label.user.management.passowrd.header"/></th>
                <th><spring:message code="label.user.management.role.header"/>Role</th>
                <th><spring:message code="label.user.management.picture.header"/>Profile Picture</th>
                <th width="60"><spring:message code="label.update.header"/></th>
                <th width="60"><spring:message code="label.delete.header"/></th>
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
                    <td><a class="button primary" href="<c:url value="/user/update/${post.id}"/>"><spring:message
                            code="label.update.header"/></a></td>
                    <td><a class="button danger" href="<c:url value="/user/delete/${post.id}"/>"><spring:message
                            code="label.delete.header"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>


