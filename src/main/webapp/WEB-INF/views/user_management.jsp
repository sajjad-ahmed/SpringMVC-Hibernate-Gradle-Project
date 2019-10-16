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
            <c:forEach items="${users}" var="post">
                <tr>
                    <section class="card">
                        <div class="header">
                            <img src="data:image/*;base64,${post.getImageBase64()}" width="100" height="75"/>
                        </div>
                        <div class="content">
                            <table class="bordered">
                                <tr>
                                    <td width="200px"><strong><spring:message code="label.user.field.id"/></strong></td>
                                    <td><c:out value="${post.id}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message
                                            code="label.user.field.firsName"/></strong></td>
                                    <td><c:out value="${post.firstName}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message
                                            code="label.user.field.lastName"/></strong></td>
                                    <td><c:out value="${post.lastName}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message code="label.user.field.email"/></strong>
                                    </td>
                                    <td><c:out value="${post.email}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message
                                            code="label.user.field.password"/></strong></td>
                                    <td><c:out value="${post.password}"/></td>
                                </tr>
                                <tr>
                                    <td width="200px"><strong><spring:message code="label.user.field.role"/></strong>
                                    </td>
                                    <td><c:out value="${post.role}"/></td>
                                </tr>
                            </table>
                        </div>
                        <div class="footer">
                            <form:form action="/user/update" method="post"
                                       modelAttribute="user" class="hform">
                                <form:input path="id" type="hidden" value="${post.id}"/>
                                <input type="submit" value="<spring:message code="label.update.header"/>"
                                       class="primary"/>
                            </form:form>

                            <c:if test="${sessionScope.userID != post.id}">
                                <form:form action="/user/delete" method="post"
                                           modelAttribute="user" class="hform">
                                    <form:input path="id" type="hidden" value="${post.id}"/>
                                    <input type="submit" value="<spring:message code="label.delete.header"/>"
                                           class="primary"/>
                                </form:form>
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


