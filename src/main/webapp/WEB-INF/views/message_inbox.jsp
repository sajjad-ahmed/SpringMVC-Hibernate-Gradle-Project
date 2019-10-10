<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>My Inbox</title>
</head>

<body>
<div>
    <h3> Sent Messages</h3>
    <c:if test="${!empty sentMessages}">
        <table>
            <tr>
                <th>Sent on</th>
                <th>To</th>
                <th>Message</th>
                <th>Receiver role</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${sentMessages}" var="message">
                <tr>
                    <td><c:out value="${message.createdAt}"/></td>
                    <td><c:out value="${message.receiver.firstName}"/></td>
                    <td><c:out value="${message.body}"/></td>
                    <td><c:out value="${message.receiver.role}"/></td>
                    <td><a class="button danger"
                           href="<c:url value="/message/delete/${message.id}"/>">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty sentMessages}">
        <h3> You haven't sent any message</h3>
    </c:if>

    <br>
    <br>
    <br>

    <h3> Received Messages</h3>
    <c:if test="${!empty receivedMessages}">
        <table>
            <tr>
                <th>Received on</th>
                <th>Sent by</th>
                <th>Message</th>
                <th>User type</th>
                <th>Delete</th>
            </tr>
            <c:forEach items="${receivedMessages}" var="message">
                <tr>
                    <td><c:out value="${message.createdAt}"/></td>
                    <td><c:out value="${message.sender.firstName}"/></td>
                    <td><c:out value="${message.body}"/></td>
                    <td><c:out value="${message.sender.role}"/></td>
                    <td><a class="button danger"
                           href="<c:url value="/message/delete/${message.id}"/>">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty receivedMessages}">
        <h3> No message received</h3>
    </c:if>

</div>
</body>
</html>


