<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.loginPage.header"/></title>
</head>
<body>
<div class="row">
    <c:if test="${sessionScope.userID != null}">
        <h3> You are already logged in</h3>
    </c:if>
    <c:if test="${sessionScope.userID == null}">

        <form:form method="post" modelAttribute="user" action="/auth/login" class="hform">
            <form:input path="id" type="hidden"/>
            <form:input path="firstName" type="hidden"/>
            <form:input path="lastName" type="hidden"/>
            <form:input path="role" type="hidden"/>
            <form:input path="profilePicture" type="hidden"/>
            <fieldset>
                <legend><spring:message code="label.loginPage.legend"/></legend>
                <dt><label aria-atomic="true" aria-live="polite"><spring:message
                        code="label.loginPage.email.prompt"/> </label></dt>
                <dd>
                    <form:input path="email" placeholder="Enter your email" aria-labelledby="code-label"
                                aria-required="true"/>
                    <form:errors path="email" cssStyle="color: red"/>
                </dd>
                <dt><label aria-atomic="true" aria-live="polite"><spring:message
                        code="label.loginPage.password.prompt"/> </label></dt>
                <dd>
                    <form:input path="password" type="password" placeholder="Enter your password"
                                aria-labelledby="code-label"
                                aria-required="true"/>
                    <form:errors path="password" cssStyle="color: red"/>
                </dd>
                <c:if test="${!empty authFailed}">
                    <dd>
                        <div class="alert error">
                            <strong>Error</strong>
                            <spring:message code="label.loginPage.auth.error"/>
                        </div>
                    </dd>
                </c:if>
                <dd><input type="submit" value="Log in" class="primary"></dd>
                <dd><spring:message code="label.loginPage.newHere.prompt"/> <a href="/user/signup"> <spring:message
                        code="label.signUp.header"/> </a></dd>
            </fieldset>
        </form:form>
    </c:if>
</div>
</body>
</html>


