<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html>

<html>
<head>
    <title><c:out value="${post.title}"/></title>
</head>
<body>
<c:if test="${!empty post}">
    <h5 style="padding-top:20px; "> by <strong><c:out value="${post.getAuthorName()}"/></strong> - <c:out
            value="${post.formattedDate}"/></h5>

    <c:forEach items="${post.categories}" var="category">
        <a href="<c:url value="/category/post/show/${category.id}"/>" style="text-decoration: none">
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

    <c:if test="${!empty post.comments}">
        <c:forEach items="${post.comments}" var="eachComment">
            <form class="hform">
                <fieldset>
                    <legend><spring:message code="label.post.comment.legend"/> <strong> <c:out
                            value="${eachComment.userId.fullName}"/> </strong></legend>

                    <dt><label aria-atomic="true" aria-live="polite" style="color: dodgerblue;font-style: italic">
                        <c:out value="${eachComment.commentedOn}"/> </label></dt>
                    <dd>
                        <p style="font-size: 16px;">"
                            <c:out value="${eachComment.body}"/>
                            "
                        </p>
                    </dd>

                    <dd>
                        <c:if test="${sessionScope.userRole == 'ADMIN' }">
                            <form:form action="/comment/delete" method="post"
                                       modelAttribute="comment" class="hform">
                                <form:input path="id" type="hidden" value="${eachComment.id}"/>
                                <input type="submit" value="<spring:message code="label.delete.header"/>"
                                       class="primary danger"/>
                            </form:form>
                            <c:if test="${sessionScope.userID == eachComment.userId.id}">
                                <form:form action="/comment/update" method="post"
                                           modelAttribute="comment" class="hform">
                                    <form:input path="id" type="hidden" value="${eachComment.id}"/>
                                    <input type="submit" value="<spring:message code="label.update.header"/>"
                                           class="primary"/>
                                </form:form>
                            </c:if>
                        </c:if>
                        <c:if test="${sessionScope.userID == eachComment.userId.id && sessionScope.userRole != 'ADMIN'}">
                            <form:form action="/comment/delete" method="post"
                                       modelAttribute="comment" class="hform">
                                <form:input path="id" type="hidden" value="${eachComment.id}"/>
                                <input type="submit" value="<spring:message code="label.delete.header"/>"
                                       class="primary danger"/>
                            </form:form>
                            <form:form action="/comment/update" method="post"
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

    <c:if test="${ !empty sessionScope.userID}">
        <form:form action="/comment/add" method="post" modelAttribute="comment" class="hform">
            <form:input path="id" type="hidden"/>
            <form:input path="postId" type="hidden" value="${post.id}"/>
            <form:input path="userId" value="${sessionScope.userID}" type="hidden"/>
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
                    <dd><input type="submit" value="<spring:message code="label.post.comment.button.update"/>"
                               class="primary"></dd>
                </c:if>
            </fieldset>
        </form:form>
    </c:if>

    <c:if test="${empty sessionScope.userID}">
        <h4><spring:message code="label.post.comment.prompt.pref"/> <a href="/auth/login"> <spring:message
                code="label.home.login.text"/></a> <spring:message code="label.post.comment.login.to.comment"/></h4>
    </c:if>
</c:if>
</body>
</html>
