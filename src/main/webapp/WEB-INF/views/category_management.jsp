<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.category.title"/></title>
</head>
<body>

<div class="row">

    <form:form action="/category/add" method="post" modelAttribute="category" class="hform">
        <table align="center">
            <fieldset>
                <form:input path="id" type="hidden"/>
                <legend><spring:message code="label.category.body.title"/></legend>
                <dt><label aria-atomic="true" aria-live="polite"><spring:message
                        code="label.category.body.name"/> </label></dt>
                <dd>
                    <form:input path="name"/>
                    <form:errors cssStyle="color: red" path="name"/>
                </dd>
                <dd><input type="submit" value="ADD" class="primary"></dd>
            </fieldset>
        </table>
    </form:form>

    <br>
    <br>
    <br>

    <div class="hform" align="center">
        <fieldset>
            <legend><spring:message code="label.category.list.title"/></legend>
            <c:if test="${!empty categories}">
                <table class="bordered">
                    <tr>
                        <th><spring:message code="label.id.header"/></th>
                        <th><spring:message code="label.category.header"/></th>
                        <th><spring:message code="label.update.header"/></th>
                        <th><spring:message code="label.delete.header"/></th>
                    </tr>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category.id}"/></td>
                            <td><c:out value="${category.name}"/></td>
                            <td><a class="button primary"
                                   href="<c:url value="/category/update/${category.id}"/>">Update</a>
                            </td>
                            <td><a class="button danger"
                                   href="<c:url value='/category/delete/${category.id}'/>">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${empty categories}">
                <h4><spring:message code="label.category.list.notFound"/></h4>
            </c:if>
        </fieldset>
    </div>
</div>

</body>
</html>
