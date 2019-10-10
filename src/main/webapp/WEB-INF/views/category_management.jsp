<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Category Management</title>
</head>
<body>

<div class="row">

    <form:form action="/category/add" method="post" modelAttribute="category" class="hform">
        <table align="center">
            <fieldset>
                <form:input path="id" type="hidden"/>
                <legend>Add a new Category</legend>
                <dt><label aria-atomic="true" aria-live="polite">Category Name</label></dt>
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
            <legend>Category List</legend>
            <c:if test="${!empty categories}">
                <table class="bordered">
                    <tr>
                        <th>ID</th>
                        <th>Category Name</th>
                        <th>Update</th>
                        <th>Delete</th>
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
                <h4> No categories added yet</h4>
            </c:if>
        </fieldset>
    </div>
</div>

</body>
</html>
