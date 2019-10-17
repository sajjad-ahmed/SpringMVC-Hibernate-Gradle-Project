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
    <a href="/category/add" class="button primary"> <spring:message code="label.category.add.new.title"/></a>
    <br/>
    <br/>
    <c:if test="${confirmation == 'ADDED'}">
        <div class="alert success">
            <strong><spring:message code="label.success.message.prefix"/></strong>
            <spring:message code="label.category"/> <spring:message code="label.success.add.message.suffix"/>
        </div>
    </c:if>
    <c:if test="${confirmation == 'UPDATED'}">
        <div class="alert success">
            <strong><spring:message code="label.success.message.prefix"/></strong>
            <spring:message code="label.category"/> <spring:message code="label.success.update.message.suffix"/>
        </div>
    </c:if>
    <br/>
    <br/>

    <div class="hform" align="center">
        <fieldset>
            <legend><spring:message code="label.category.list.title"/></legend>
            <c:if test="${!empty categories}">
                <table class="bordered">
                    <tr>
                        <th><spring:message code="label.category.header"/></th>
                        <th><spring:message code="label.update.header"/></th>
                        <th><spring:message code="label.delete.header"/></th>
                    </tr>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:out value="${category.name}"/></td>
                            <td>
                                <form:form action="/category/update/" method="get"
                                           modelAttribute="category" class="hform">
                                    <form:input path="id" type="hidden" value="${category.id}"/>
                                    <input type="submit" value="<spring:message code="label.update.header"/>"
                                           class="primary"/>
                                </form:form>
                            </td>
                            <td>
                                <form:form action="/category/delete" method="post"
                                           modelAttribute="category" class="hform" id="form-id-${category.id}">
                                    <form:input path="id" type="hidden" value="${category.id}"/>
                                    <input type="button" onclick="getConfirmation('<spring:message
                                            code="label.confirmation.prompt.delete"/>', 'form-id-${category.id}')"
                                           value="<spring:message code="label.delete.header"/>"
                                           class="danger"/>
                                </form:form>
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
