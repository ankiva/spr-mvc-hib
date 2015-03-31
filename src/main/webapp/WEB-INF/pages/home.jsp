<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="ISO-8859-1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<title><spring:message code="home.page"/></title>
</head>
<body>

<%@ include file="menu.jsp" %>

<h1><spring:message code="home.page"/></h1>

<p>
${message}<br/>
<a href="${pageContext.request.contextPath}/team/add.html"><spring:message code="add-new-team"/></a><br/>
<a href="${pageContext.request.contextPath}/team/list.html"><spring:message code="team-list"/></a><br/>
</p>
<p>
${organizationmessage}<br/>
<a href="${pageContext.request.contextPath}/organization/add.html"><spring:message code="add-new-organization"/></a><br/>
<a href="${pageContext.request.contextPath}/organization/list.html"><spring:message code="organization-list"/></a><br/>
</p>
<p>
${membermessage}<br/>
<a href="${pageContext.request.contextPath}/member/add.html"><spring:message code="add-new-member"/></a><br/>
<a href="${pageContext.request.contextPath}/member/list.html"><spring:message code="member-list"/></a><br/>
</p>
</body>
</html>