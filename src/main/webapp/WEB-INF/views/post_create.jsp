<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Create Post</title>
</head>

<body>
<div>
    <form:form action="/post/create" method="post" modelAttribute="post" enctype="multipart/form-data" class="hform">
        <form:input path="id" type="hidden"/>
        <form:input path="creator" value="${sessionScope.userID}" type="hidden"/>
        <fieldset>
            <legend>Post description</legend>

            <dt><label aria-atomic="true" aria-live="polite">Post Title:</label></dt>
            <dd>
                <form:input path="title" aria-labelledby="code-label" aria-required="true"/>
            </dd>
            <dd><form:errors path="title" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite">Post body :</label></dt>
            <dd>
                <form:textarea path="body" cssClass="tht" aria-labelledby="code-label" aria-required="true"/>
            </dd>
            <dd><form:errors path="body" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite">Post URI :</label></dt>
            <dd>
                <form:input path="uri" aria-labelledby="code-label" aria-required="true"/>
            </dd>
            <dd><form:errors path="uri" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite">Post Access :</label></dt>
            <dd>
                <form:input path="access" type="number" aria-labelledby="code-label" aria-required="true"/>
                <div class="alert info">
                    <strong>Note</strong>
                    The access should be a 3 digit number
                    <br>
                    First digit refers the Author level access
                    <br>
                    First digit refers the Subscriber level access
                    <br>
                    First digit refers the Guest/Everyone level access
                    <br>
                    1 refers no access. 7 refers full access
                </div>
            </dd>
            <dd><form:errors path="access" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite">Post Cover :</label></dt>
            <form:input type="hidden" path="picture"/>
            <dd>
                <input type="file" name="file">
            </dd>
            <dd><form:errors path="picture" name="picture" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite">Select category:</label></dt>
            <dd>
                <form:select path="categories" multiple="true" name="select-categories">
                    <form:options items="${categories}" itemLabel="name" itemValue="id"/>
                </form:select>
            </dd>
            <dd><form:errors path="categories" cssStyle="color: red"/></dd>

            <dd><input type="submit" value="Create a new post" class="primary"></dd>
        </fieldset>

    </form:form>
</div>
</body>
</html>


