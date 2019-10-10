<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Send a Message</title>
</head>
<body style="margin:0px; background: white;">

<div>

    <form:form action="/message/send" method="post" modelAttribute="message" class="hform">
        <table align="center">

            <fieldset id="freship-group">
                <form:input path="id" type="hidden"/>
                <form:input path="sender" value="${sessionScope.userID}" type="hidden"/>
                <legend>Enter a new message</legend>
                <dt><label aria-atomic="true" aria-live="polite">Select an user</label></dt>

                <dd>
                    <form:select path="receiver" multiple="false" items="${users}"
                                 itemLabel="nameAndRole" itemValue="id"/>
                    <form:errors path="receiver"/>
                </dd>
                <dt><label aria-atomic="true" aria-live="polite">Your message</label></dt>
                <dd>
                    <form:textarea path="body" type="text" placeholder="Type your message here"
                                   cssClass="tht"/>
                    <form:errors path="body"/>
                </dd>
                <dd><input type="submit" value="Send" class="primary"></dd>
            </fieldset>

        </table>
    </form:form>
</div>
</body>
</html>


