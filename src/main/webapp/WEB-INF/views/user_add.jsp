<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Add User</title>

</head>

<body>
<div>
    <form:form action="/user/add" method="post" modelAttribute="user" enctype="multipart/form-data">
        <table align="center" class="valigntop">
            <form:input path="id" type="hidden"/>
            <tr>
                <td>User First Name :</td>
                <td><form:input path="firstName"/></td>
                <td><form:errors path="firstName" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>User Last Name :</td>
                <td><form:input path="lastName"/></td>
                <td><form:errors path="lastName" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>User email :</td>
                <td><form:input path="email"/></td>
                <td><form:errors path="email" cssStyle="color: red"/></td>
            </tr>

            <tr>
                <td>password :</td>
                <td><form:password path="password"/></td>
                <td><form:errors path="password" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>Profile picture :</td>
                <td><form:input type="hidden" path="profilePicture"/></td>
                <td><input type="file" name="file"></td>
                <td><form:errors path="profilePicture" name="profilePicture" cssStyle="color: red"/></td>
            </tr>
            <tr>
                <td>Select role:</td>
                <td><form:select path="role" name="select-role">
                    <form:options items="${roles}"/>
                    <form:errors path="role" cssStyle="color: red"/>
                </form:select></td>
            </tr>
            <tr>
                <td><input type="submit" value="Add" class="primary"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>


