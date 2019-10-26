<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="src/main/webapp/css/style.css"/>
    <title><c:out value="${post.title}"/></title>
</head>
<body>
<c:if test="${!empty post}">
    <h5 style="padding-top:20px; "> by <strong><c:out value="${post.getAuthorName()}"/></strong> - <c:out
            value="${post.formattedDate}"/></h5>

    <c:forEach items="${post.categories}" var="category">
        <c:url value="/category/post/show/${category.id}" var="categoryUrl"/>
        <a href="${categoryUrl}" style="text-decoration: none">
            <div class="chip"><b class="primary">C</b> <c:out value="${category.name}"/></div>
        </a>
    </c:forEach>
    <div class="chipContainer">
    </div>

    <img src="data:image/*;base64,${post.getImageBase64()}" style="padding-top:20px; padding-bottom:40px;">

    <p><c:out value="${post.body}"/></p>

    <div style="padding: 30px"/>

    <h3><strong><spring:message code="label.post.comment.title"/> </strong></h3>

    <div style="padding: 10px"/>

    <c:if test="${confirmation == 'ADDED'}">
        <br/>
        <br/>

        <div class="alert success">
            <strong><spring:message code="label.success.message.prefix"/></strong>
            <spring:message code="label.comment"/> <spring:message code="label.success.add.message.suffix"/>
        </div>
    </c:if>
    <c:if test="${confirmation == 'UPDATED'}">
        <br/>
        <br/>

        <div class="alert success">
            <strong><spring:message code="label.success.message.prefix"/></strong>
            <spring:message code="label.comment"/> <spring:message code="label.success.update.message.suffix"/>
        </div>
    </c:if>
    <c:if test="${!empty post.comments}">
        <c:forEach items="${post.comments}" var="eachComment">
            <form class="hform">
                <fieldset>
                    <legend><spring:message code="label.post.comment.legend"/> <strong> <c:out
                            value="${eachComment.user.fullName}"/> </strong></legend>

                    <dt><label aria-atomic="true" aria-live="polite" class="comment-date">
                        <c:out value="${eachComment.commentedOn}"/> </label></dt>
                    <dd>
                        <p class="comment-bdy">
                            <c:out value="${eachComment.body}"/>

                        </p>
                    </dd>

                    <dd>
                        <c:if test="${sessionScope.sessionUser.role == 'ADMIN' }">
                            <form:form action="/comment/delete" method="post" id="id-form-cc-${eachComment.id}"
                                       modelAttribute="comment" class="hform">
                                <form:input path="id" type="hidden" value="${eachComment.id}"/>
                                <input type="button" onclick="getConfirmation('<spring:message
                                        code="label.confirmation.prompt.delete"/>', 'id-form-cc-${eachComment.id}')"
                                       value="<spring:message code="label.delete.header"/>"
                                       class="danger"/>
                            </form:form>
                            <c:if test="${sessionScope.sessionUser.id == eachComment.user.id}">
                                <form:form action="/comment/update" method="get"
                                           modelAttribute="comment" class="hform">
                                    <form:input path="id" type="hidden" value="${eachComment.id}"/>
                                    <input type="submit" value="<spring:message code="label.update.header"/>"
                                           class="primary"/>
                                </form:form>
                            </c:if>
                        </c:if>
                        <c:if test="${sessionScope.sessionUser.id == eachComment.user.id
                        && sessionScope.sessionUser.role != 'ADMIN'}">
                            <form:form action="/comment/delete" method="post" id="id-form-ct-${eachComment.id}"
                                       modelAttribute="comment" class="hform">
                                <form:input path="id" type="hidden" value="${eachComment.id}"/>
                                <input type="button" onclick="getConfirmation('<spring:message
                                        code="label.confirmation.prompt.delete"/>', 'id-form-ct-${eachComment.id}')"
                                       value="<spring:message code="label.delete.header"/>"
                                       class="danger"/>
                            </form:form>
                            <form:form action="/comment/update" method="get"
                                       modelAttribute="comment" class="hform">
                                <form:input path="id" type="hidden" value="${eachComment.id}"/>
                                <input type="submit" value="<spring:message code="label.update.header"/>"
                                       class="primary"/>
                            </form:form>
                        </c:if>
                    </dd>
                </fieldset>
            </form>
        </c:forEach>
    </c:if>
    <c:if test="${empty post.comments}">
        <h3><i> <spring:message code="label.post.comment.not.found"/> </i></h3>
    </c:if>

    <div style="padding: 30px"/>

    <c:if test="${ !empty sessionScope.sessionUser.id}">
        <form:form action="/comment/add" method="post" modelAttribute="comment" class="hform">
            <form:input path="id" type="hidden"/>
            <form:input path="post" type="hidden" value="${post.id}"/>
            <form:input path="user" value="${sessionScope.sessionUser.id}" type="hidden"/>
            <fieldset>
                <legend><spring:message code="label.post.comment.legend.new"/></legend>

                <dt><label aria-atomic="true" aria-live="polite"><spring:message
                        code="label.post.comment.prompt"/> </label></dt>
                <dd>
                    <form:textarea path="body" cssClass="tht" aria-labelledby="code-label" aria-required="true"/>
                </dd>
                <dd><form:errors path="body" cssStyle="color: red"/></dd>

                <c:if test="${comment.id ==0}">
                    <dd><input type="submit" value="<spring:message code="label.post.comment.button.submit"/>"
                               class="primary"></dd>
                </c:if>
                <c:if test="${comment.id !=0}">
                    <dd>
                        <input type="submit" value="<spring:message code="label.post.comment.button.update"/>"
                               class="primary">
                    </dd>
                </c:if>
            </fieldset>
        </form:form>
    </c:if>

    <c:if test="${empty sessionScope.sessionUser.id}">
        <c:url value="/auth/login" var="loginUrl"/>
        <h4><spring:message code="label.post.comment.prompt.pref"/>
            <a href="${loginUrl}">
                <spring:message code="label.home.login.text"/>
            </a>
            <spring:message code="label.post.comment.login.to.comment"/>
        </h4>
    </c:if>
</c:if>
</body>
</html>
