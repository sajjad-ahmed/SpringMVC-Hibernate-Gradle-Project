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
                <th><spring:message code="label.user.management.firstName.header"/></th>
                <th><spring:message code="label.user.management.lastName.header"/></th>
                <th><spring:message code="label.user.management.email.header"/></th>
                <th><spring:message code="label.user.management.role.header"/>Role</th>
                <th><spring:message code="label.user.management.picture.header"/></th>
                <th width="60"><spring:message code="label.update.header"/></th>
                <th width="60"><spring:message code="label.delete.header"/></th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.role}"/></td>
                    <td><img src="data:image/*;base64,${user.imageBase64}" width="70" height="50"/></td>
                    <td><form:form action="/user/update" method="post"
                                   modelAttribute="user" class="hform">
                        <form:input path="id" type="hidden" value="${user.id}"/>
                        <input type="submit" value="<spring:message code="label.update.header"/>"
                               class="primary"/>
                    </form:form></td>
                    <td>
                        <c:if test="${sessionScope.userID != user.id}">
                            <form:form action="/user/delete" method="post"
                                       modelAttribute="user" class="hform">
                                <form:input path="id" type="hidden" value="${user.id}"/>
                                <input type="submit" value="<spring:message code="label.delete.header"/>"
                                       class="primary danger"/>
                            </form:form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>


