<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.inbox.title"/></title>
</head>

<body>
<div>
    <h3><strong><spring:message code="label.sentMessage.title"/></strong></h3>
    <c:if test="${!empty sentMessages}">
        <table>
            <tr>
                <th><spring:message code="label.sentMessage.sentOn"/></th>
                <th><spring:message code="label.sentMessage.to"/></th>
                <th><spring:message code="label.sentMessage.message"/></th>
                <th><spring:message code="label.delete.header"/></th>
            </tr>
            <c:forEach items="${sentMessages}" var="message">
                <tr>
                    <td><c:out value="${message.createdAt}"/></td>
                    <td><c:out value="${message.receiver.nameAndRole}"/></td>
                    <td><c:out value="${message.body}"/></td>
                    <td>
                        <form:form action="/message/delete" method="post"
                                   modelAttribute="message" class="hform" id="id-form-s-${message.id}">
                            <form:input path="id" type="hidden" value="${message.id}"/>
                            <input type="button" onclick="getConfirmation('<spring:message
                                    code="label.confirmation.prompt.delete"/>', 'id-form-s-${message.id}')"
                                   value="<spring:message code="label.delete.header"/>"
                                   class="danger"/>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty sentMessages}">
        <h3><spring:message code="label.sentMessage.message.notFound"/></h3>
    </c:if>

    <br>
    <br>
    <br>

    <h3><strong><spring:message code="label.receivedMessage.title"/></strong></h3>
    <c:if test="${!empty receivedMessages}">
        <table>
            <tr>
                <th><spring:message code="label.receivedMessage.receivedOn"/></th>
                <th><spring:message code="label.receivedMessage.sentBy"/></th>
                <th><spring:message code="label.receivedMessage.message"/></th>
                <th><spring:message code="label.delete.header"/></th>
            </tr>
            <c:forEach items="${receivedMessages}" var="message">
                <tr>
                    <td><c:out value="${message.createdAt}"/></td>
                    <td><c:out value="${message.sender.nameAndRole}"/></td>
                    <td><c:out value="${message.body}"/></td>
                    <td><form:form action="/message/delete" method="post"
                                   modelAttribute="message" class="hform" id="id-form-r-${message.id}">
                        <form:input path="id" type="hidden" value="${message.id}"/>
                        <input type="button" onclick="getConfirmation('<spring:message
                                code="label.confirmation.prompt.delete"/>', 'id-form-r-${message.id}')"
                               value="<spring:message code="label.delete.header"/>"
                               class="danger"/>
                    </form:form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty receivedMessages}">
        <h3><spring:message code="label.receivedMessage.notFound"/></h3>
    </c:if>

</div>
</body>
</html>


