<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Sign up</title>

</head>

<body>
<div>
    <form:form action="/user/signup" method="post" modelAttribute="user" enctype="multipart/form-data">
        <table align="center" class="valigntop">
            <form:input path="id" type="hidden"/>
            <form:input path="role" type="hidden" value="SUBSCRIBER"/>
            <tr>
                <td>User First Name :</td>
                <td><form:input path="firstName" placeholder="Enter your firest name"/></td>
                <td><form:errors path="firstName" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>User Last Name :</td>
                <td><form:input path="lastName" placeholder="Enter your last name"/></td>
                <td><form:errors path="lastName" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>User email :</td>
                <td><form:input path="email" name="email" type="email" placeholder="Enter your email"
                                aria-labelledby="code-label"
                                aria-required="true"/></td>
                <td><form:errors path="email" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>Password :</td>
                <td><form:password path="password" placeholder="Choose a password"/></td>
                <td><form:errors path="password" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>Profile picture :</td>
                <td><form:input type="hidden" path="profilePicture"/></td>
                <td><input type="file" name="file"></td>
                <td><form:errors path="profilePicture" name="profilePicture" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Sign Up" class="primary"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>


