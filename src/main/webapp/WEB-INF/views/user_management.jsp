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
    <c:url value="/user/add" var="addUserUrl"/>
    <a href="${addUserUrl}" class="button primary"> <spring:message code="label.user.management.form.title"/></a>
    <c:if test="${confirmation == 'ADDED'}">
        <br/>
        <br/>

        <div class="alert success">
            <strong><spring:message code="label.success.message.prefix"/></strong>
            <spring:message code="label.user"/> <spring:message code="label.success.add.message.suffix"/>
        </div>
    </c:if>
    <c:if test="${confirmation == 'UPDATED'}">
        <br/>
        <br/>

        <div class="alert success">
            <strong><spring:message code="label.success.message.prefix"/></strong>
            <spring:message code="label.user"/> <spring:message code="label.success.update.message.suffix"/>
        </div>
    </c:if>
    <br/>
    <br/>
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
                    <c:if test="${user.imageBase64.length() != 0}">
                        <td><img src="data:image/*;base64,${user.imageBase64}" width="70" height="50"/></td>
                    </c:if>
                    <c:if test="${user.imageBase64.length() == 0}">
                        <td><spring:message code="label.image.not.found"/></td>
                    </c:if>
                    <td><form:form action="/user/add" method="get"
                                   modelAttribute="user" class="hform">
                        <form:input path="id" type="hidden" value="${user.id}"/>
                        <input type="submit" value="<spring:message code="label.update.header"/>"
                               class="primary"/>
                    </form:form></td>
                    <td>
                        <c:if test="${sessionScope.sessionUser.id != user.id}">
                            <form:form action="/user/delete" method="post" id="form-id-${user.id}"
                                       modelAttribute="user" class="hform">
                                <form:input path="id" type="hidden" value="${user.id}"/>
                                <input type="button" onclick="getConfirmation('<spring:message
                                        code="label.confirmation.prompt.delete"/>', 'form-id-${user.id}')"
                                       value="<spring:message code="label.delete.header"/>"
                                       class="danger"/>
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


