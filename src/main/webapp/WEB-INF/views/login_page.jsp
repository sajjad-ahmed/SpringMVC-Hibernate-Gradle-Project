<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Login</title>
</head>
<body>
<div class="row">
    <form:form method="post" modelAttribute="user" action="/auth/login" class="hform">
        <form:input path="id" type="hidden"/>
        <form:input path="firstName" type="hidden"/>
        <form:input path="lastName" type="hidden"/>
        <form:input path="role" type="hidden" />
        <form:input path="profilePicture" type="hidden" />
        <fieldset>
            <legend>Enter your credential to log in</legend>
            <dt><label aria-atomic="true" aria-live="polite">Email Address</label></dt>
            <dd>
                <form:input path="email" type="email" placeholder="Enter your email" aria-labelledby="code-label"
                       aria-required="true"/>
            </dd>
            <dt><label aria-atomic="true" aria-live="polite">Password </label></dt>
            <dd>
                <form:input path="password" type="password" placeholder="Enter your password" aria-labelledby="code-label"
                       aria-required="true"/>
            </dd>
            <c:if test="${!empty authFailed}">
                <dd>
                    <div class="alert error">
                        <strong>Error</strong>
                        Login Failed. Re check your email and password.
                    </div>
                </dd>
            </c:if>
            <dd><input type="submit" value="Log in" class="primary"></dd>
            <dd>New here? <a href="/user/signup"> Sign up</a></dd>
        </fieldset>
    </form:form>
</div>
</body>
</html>


