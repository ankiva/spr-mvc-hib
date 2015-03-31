<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1" />
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<title><spring:message code="team-list.title"/></title>
</head>
<body>
<%@ include file="menu.jsp" %>
<h1><spring:message code="team-list.title"/></h1>
<p><spring:message code="team-list.text" /></p>
<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="10%"><spring:message code="id.label"/></th>
<th width="15%"><spring:message code="name.label"/></th>
<th width="10%"><spring:message code="rating.label"/></th>
<th width="10%"><spring:message code="organization.label"/></th>
<th width="10%"><spring:message code="actions.label"/></th>
</tr>
</thead>
<tbody id="team-list-table">
<c:forEach var="team" items="${teams}">
<tr>
	<td>${team.id}</td>
	<td name="teamnamecell">${team.name}</td>
	<td>${team.rating}</td>
	<td>${team.organization.name}</td>
	<td>
	<a name="teameditlink" href="${pageContext.request.contextPath}/team/edit/${team.id}.html"><spring:message code="edit.action"/></a><br/>
	<a name="teamdeletelink" href="${pageContext.request.contextPath}/team/delete/${team.id}.html"><spring:message code="delete.action"/></a><br/>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

<p><a href="${pageContext.request.contextPath}/index.html"><spring:message code="home.page"/></a></p>

</body>
</html>