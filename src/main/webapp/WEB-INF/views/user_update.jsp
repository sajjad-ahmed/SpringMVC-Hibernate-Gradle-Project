<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.user.update.title"/></title>
</head>

<body>
<div>
    <form:form action="/user/update/self" method="post" modelAttribute="user" enctype="multipart/form-data">
        <table align="center" class="valigntop">
            <form:input path="id" type="hidden"/>
            <tr>
                <td><spring:message code="label.user.field.firsName"/></td>
                <td><form:input path="firstName"/></td>
                <td><form:errors path="firstName" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td><spring:message code="label.user.field.lastName"/></td>
                <td><form:input path="lastName"/></td>
                <td><form:errors path="lastName" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td><spring:message code="label.user.field.email"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><form:input path="email" type="hidden" value="${user.email}"/></td>
                <td><form:errors path="email" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td><spring:message code="label.user.field.new.password"/></td>
                <td><form:password path="password"/></td>
                <td><form:errors path="password" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td><spring:message code="label.user.field.picture"/></td>
                <td><form:input type="hidden" path="profilePicture"/></td>
                <td><input type="file" name="file"></td>
                <td><form:errors path="profilePicture" name="profilePicture" cssStyle="color: red"/></td>
            </tr>
            <form:input type="hidden" path="role" value="${user.role}"/>
            <tr>
                <td><input type="submit" value="<spring:message code="label.update.header"/> " class="primary"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>


