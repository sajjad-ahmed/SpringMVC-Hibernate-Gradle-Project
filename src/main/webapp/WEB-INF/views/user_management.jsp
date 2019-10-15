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
            <c:forEach items="${users}" var="user">
                <tr>
                    <section class="card">
                        <div class="header">
                            <img src="data:image/*;base64,${user.getImageBase64()}" width="100" height="75"/>
                        </div>
                        <div class="content">
                            <table class="bordered">
                                <tr>
                                    <td width="200px"><strong><spring:message code="label.user.field.id"/></strong></td>
                                    <td><c:out value="${user.id}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message
                                            code="label.user.field.firsName"/></strong></td>
                                    <td><c:out value="${user.firstName}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message
                                            code="label.user.field.lastName"/></strong></td>
                                    <td><c:out value="${user.lastName}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message code="label.user.field.email"/></strong>
                                    </td>
                                    <td><c:out value="${user.email}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message
                                            code="label.user.field.password"/></strong></td>
                                    <td><c:out value="${user.password}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message code="label.user.field.role"/></strong>
                                    </td>
                                    <td><c:out value="${user.role}"/></td>
                                </tr>
                            </table>
                        </div>
                        <div class="footer">
                            <a class="button primary" href="<c:url value="/user/update/${user.id}"/>"><spring:message
                                    code="label.update.header"/></a>
                            <c:if test="${sessionScope.userID != user.id}">
                                <a class="button danger" href="<c:url value="/user/delete/${user.id}"/>"><spring:message
                                        code="label.delete.header"/></a>
                            </c:if>
                        </div>
                    </section>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>


