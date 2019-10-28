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
                <dt>
                    <label aria-atomic="true" aria-live="polite">
                        <spring:message code="label.category.body.name"/>
                    </label>
                </dt>
                <dd>
                    <form:input path="name"/>
                    <form:errors cssStyle="color: red" path="name"/>
                </dd>
                <c:if test="${category.id == 0}">
                    <dd>
                        <input type="submit" value="<spring:message code="label.add.header"/>" class="primary">
                    </dd>
                </c:if>
                <c:if test="${category.id != 0}">
                    <dd>
                        <input type="submit" value="<spring:message code="label.update.header"/>" class="primary">
                    </dd>
                </c:if>
            </fieldset>
        </table>
    </form:form>
</div>
</body>
</html>
