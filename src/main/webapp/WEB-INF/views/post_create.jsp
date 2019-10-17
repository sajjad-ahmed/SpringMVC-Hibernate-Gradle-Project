<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><spring:message code="label.post.createPage.title"/></title>
</head>

<body>
<div>
    <form:form action="/post/create" method="post" modelAttribute="post" enctype="multipart/form-data" class="hform">
        <form:input path="id" type="hidden"/>
        <form:input path="creator" value="${sessionScope.userID}" type="hidden"/>
        <fieldset>
            <legend><spring:message code="label.post.createPage.legend"/></legend>

            <dt><label aria-atomic="true" aria-live="polite"><spring:message
                    code="label.post.createPage.title.prompt"/> </label></dt>
            <dd>
                <form:input path="title" aria-labelledby="code-label" aria-required="true"/>
            </dd>
            <dd><form:errors path="title" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite"><spring:message
                    code="label.post.createPage.body.prompt"/> </label></dt>
            <dd>
                <form:textarea path="body" cssClass="tht" aria-labelledby="code-label" aria-required="true"/>
            </dd>
            <dd><form:errors path="body" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite"> <spring:message
                    code="label.post.createPage.url.prompt"/> </label></dt>
            <dd>
                <form:input path="uri" aria-labelledby="code-label" aria-required="true"/>
            </dd>
            <dd><form:errors path="uri" cssStyle="color: red"/></dd>

            <dt><label aria-atomic="true" aria-live="polite"> <spring:message
                    code="label.post.createPage.access.prompt"/> </label></dt>
            <dd>
                <form:input path="access" type="number" aria-labelledby="code-label" aria-required="true"/>
                <div class="alert info">
                    <spring:message code="label.post.createPage.access.note"/>
                </div>
            </dd>
            <dd><form:errors path="access" cssStyle="color: red"/></dd>
            <c:if test="${post.picture != null}">
                <dt><label aria-atomic="true" aria-live="polite"><spring:message
                        code="label.post.createPage.old.cover.prompt"/></label></dt>
                <dd><img src="data:image/*;base64,${post.imageBase64}"/></dd>

                <dt><spring:message code="label.post.createPage.new.cover.prompt"/></dt>
                <dd><form:input type="hidden" path="picture"/></dd>
                <dd><input type="file" name="file"></dd>
                <dd><form:errors path="picture" name="picture" cssStyle="color: red"/></dd>
            </c:if>
            <c:if test="${post.picture == null}">
                <dt><label aria-atomic="true" aria-live="polite"><spring:message
                        code="label.post.createPage.cover.prompt"/></label></dt>
                <dd><form:input type="hidden" path="picture"/></dd>
                <dd><input type="file" name="file"></dd>
                <dd><form:errors path="picture" name="picture" cssStyle="color: red"/></dd>
            </c:if>

            <dt><label aria-atomic="true" aria-live="polite"> <spring:message
                    code="label.post.createPage.category.prompt"/> </label></dt>
            <dd>
                <form:select path="categories" multiple="true" name="select-categories">
                    <form:options items="${categories}" itemLabel="name" itemValue="id"/>
                </form:select>
            </dd>
            <dd><form:errors path="categories" cssStyle="color: red"/></dd>

            <c:if test="${post.id != 0}">
                <dd><input type="submit" value="<spring:message code="label.post.createPage.update.button"/>"
                           class="primary"></dd>
            </c:if>
            <c:if test="${post.id == 0}">
                <dd><input type="submit" value="<spring:message code="label.post.createPage.submit.button"/>"
                           class="primary"></dd>
            </c:if>
        </fieldset>

    </form:form>
</div>
</body>
</html>


