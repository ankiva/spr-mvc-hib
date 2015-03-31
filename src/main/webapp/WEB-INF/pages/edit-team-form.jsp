<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1" />
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<title><spring:message code="edit-team.title"/></title>
</head>
<body>
<%@ include file="menu.jsp" %>
<h1><spring:message code="edit-team.title"/></h1>
<p><spring:message code="edit-team.text"/></p>
<p>${message}</p>
<form:form method="POST" commandName="team" action="${pageContext.request.contextPath}/team/edit/${team.id}.html">
<table>
<tbody>
	<tr>
		<td><spring:message code="name.label"/>:</td>
		<td><form:input id="team-edit-name" path="name" /></td>
	</tr>
	<tr>
		<td><spring:message code="rating.label"/>:</td>
		<td><form:input path="rating" /></td>
	</tr>
	<tr>
		<td><spring:message code="organization.label"/>:</td>
		<spring:message code="empty.option.label" var="optionText"/>
		<td><form:select path="organization" >
		<form:option value="" label="${optionText}"/>
		<form:options items="${organizations}" itemValue="id" itemLabel="name"/>
		</form:select></td>
	</tr>
	<tr>
		<spring:message code="edit.action" var="submitText"/>
		<td><input type="submit" value="${submitText}" /></td>
		<td></td>
	</tr>
</tbody>
</table>
</form:form>

<p><a href="${pageContext.request.contextPath}/index.html"><spring:message code="home.page"/></a></p>
</body>
</html>