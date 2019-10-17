<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.postManagement.title"/></title>
</head>

<body>
<div>
    <a href="/post/create" class="button primary"> <spring:message code="label.postManagement.new.post.button"/> </a>
    <h4><spring:message code="label.postManagement.available.posts.header"/></h4>
    <c:if test="${!empty posts}">
        <table class="bordered">
            <tr>
                <th><spring:message code="label.postManagement.id.header"/></th>
                <th><spring:message code="label.postManagement.title.header"/></th>
                <th><spring:message code="label.postManagement.author.header"/></th>
                <th><spring:message code="label.postManagement.access.header"/></th>
                <th><spring:message code="label.postManagement.uri.header"/></th>
                <th><spring:message code="label.postManagement.created.header"/></th>
                <th><spring:message code="label.postManagement.updated.header"/></th>
                <th width="60"><spring:message code="label.update.header"/></th>
                <th width="60"><spring:message code="label.delete.header"/></th>
            </tr>
            <c:forEach items="${posts}" var="user">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.title}"/></td>
                    <td><c:out value="${user.getAuthorName()}"/></td>
                    <td><c:out value="${user.access}"/></td>
                    <td><c:out value="${user.uri}"/></td>
                    <td><c:out value="${user.createdAt}"/></td>
                    <td><c:out value="${user.updatedAt}"/></td>
                    <td>
                        <form:form action="/post/update" method="post"
                                   modelAttribute="post" class="hform">
                            <form:input path="id" type="hidden" value="${user.id}"/>
                            <input type="submit" value="<spring:message code="label.update.header"/>"
                                   class="primary"/>
                        </form:form>
                    </td>
                    <td>
                        <form:form action="/post/delete" method="post"
                                   modelAttribute="post" class="hform">
                            <form:input path="id" type="hidden" value="${user.id}"/>
                            <input type="submit" value="<spring:message code="label.delete.header"/>"
                                   class="primary danger"/>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>


