<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title><c:out value="${post.title}"/></title>
</head>
<body>
<c:if test="${!empty post}">
    <h5 style="padding-top:20px; "> Author: <c:out value="${post.getAuthorName()}"/>; Date: <c:out
            value="${post.createdAt}"/></h5>

    <c:forEach items="${post.categories}" var="category">
        <div class="chip"><b class="primary">T</b><c:out value="${category.name}"/></div>
    </c:forEach>
    <div class="chipContainer">
    </div>

    <img src="data:image/*;base64,${post.getImageBase64()}" style="padding-top:20px; padding-bottom:40px;">

    <p><c:out value="${post.body}"/></p>

    <div style="padding: 30px"/>

    <h3><strong>Comments: </strong></h3>

    <div style="padding: 10px"/>

    <c:if test="${!empty post.comments}">
        <c:forEach items="${post.comments}" var="eachComment">
            <form class="hform">
                <fieldset>
                    <legend>Comment by <strong> <c:out value="${eachComment.userId.fullName}"/> </strong></legend>

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
                            <a class="button danger"
                               href="<c:url value="/comment/delete/${eachComment.id}"/>">Delete</a>
                            <c:if test="${sessionScope.userID == eachComment.userId.id}">
                                <a class="button primary" href="<c:url value="/comment/update/${eachComment.id}"/>">Update</a>
                            </c:if>
                        </c:if>
                        <c:if test="${sessionScope.userID == eachComment.userId.id && sessionScope.userRole != 'ADMIN'}">
                            <a class="button danger"
                               href="<c:url value="/comment/delete/${eachComment.id}"/>">Delete</a>
                            <a class="button primary"
                               href="<c:url value="/comment/update/${eachComment.id}"/>">Update</a>
                        </c:if>
                    </dd>
                </fieldset>
            </form>
        </c:forEach>
    </c:if>
    <c:if test="${empty post.comments}">
        <h3><i>No comments to show. </i></h3>
    </c:if>

    <div style="padding: 30px"/>

    <c:if test="${ !empty sessionScope.userID}">
        <form:form action="/comment/add" method="post" modelAttribute="comment" class="hform">
            <form:input path="id" type="hidden"/>
            <form:input path="postId" type="hidden" value="${post.id}"/>
            <form:input path="userId" value="${sessionScope.userID}" type="hidden"/>
            <fieldset>
                <legend>Write your comment here</legend>

                <dt><label aria-atomic="true" aria-live="polite">Your Comment:</label></dt>
                <dd>
                    <form:textarea path="body" cssClass="tht" aria-labelledby="code-label" aria-required="true"/>
                </dd>
                <dd><form:errors path="body" cssStyle="color: red"/></dd>

                <c:if test="${comment.id ==0}">
                    <dd><input type="submit" value="Comment" class="primary"></dd>
                </c:if>
                <c:if test="${comment.id !=0}">
                    <dd><input type="submit" value="Update Comment" class="primary"></dd>
                </c:if>
            </fieldset>
        </form:form>
    </c:if>

    <c:if test="${empty sessionScope.userID}">
        <h4> You need to <a href="/auth/login"> log in</a> first to comment.</h4>
    </c:if>
</c:if>
</body>
</html>
