<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html >

<head>
    <title><decorator:title/> | O(n)</title>
    <style>
        <%@include file="/css/style.css" %>
    </style>

    <div class="row top-nav">
        <div class="container" id="navTopRow">
            <nav class="row relative">
                <div class="span-t8 span-s6">
                    <ul class="hnav pull-left hidden-t" style="background-color: transparent">
                        <li><a href="/"><spring:message code="label.home.blogName.text"/></a></li>
                        <c:forEach items="${availableCategories}" var="category">
                            <li><a href="<c:url value="/category/post/show/${category.id}"/>"> <c:out
                                    value="${category.name}"/></a></li>
                        </c:forEach>
                    </ul>
                </div>

                <nav class="span-t4 span-s6">
                    <ul class="hnav pull-right hidden-t" style="background-color: transparent">
                        <c:if test="${sessionScope.userID != null}">
                            <li><a href="/show/dashboard">${sessionScope.userFirstName}'s Dashboard</a></li>
                            <li>
                                <a href="/auth/logout"><spring:message code="label.home.logout.text"/></a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.userID == null}">
                            <li><a href="/auth/login"><spring:message code="label.home.login.text"/></a></li>
                        </c:if>
                    </ul>
                </nav>
            </nav>
        </div>
    </div>
    <decorator:head/>
</head>
<body style="margin:0px; background: white;">
<div class="container">
    <div style="padding-top:50px;background-color: transparent"></div>
    <div style="padding-top:50px;"/>
    <h2 style="color: #880E4F; font-weight: bold;"><decorator:title/></h2>
</div>
<div class="card">
    <div class="content">
        <decorator:body/>
    </div>
</div>

<script type="text/javascript">
    function getConfirmation(message, fieldId) {
        var retVal = confirm(message);
        if (retVal == true) {
            var f = document.getElementById(fieldId);
            f.submit();
            return true;
        } else {
            return false;
        }
    }
</script>

</body>
</html>


