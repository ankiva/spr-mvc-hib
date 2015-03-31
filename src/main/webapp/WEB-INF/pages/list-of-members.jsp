<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<?xml version="1.0" encoding="UTF-8" ?>

<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1" />
	<!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<title><spring:message code="list-member.title"/></title>
</head>
<body>
<%@ include file="menu.jsp" %>
<h1><spring:message code="list-member.title"/></h1>
<p><spring:message code="list-member.text"/></p>
<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="10%"><spring:message code="id.label"/></th>
<th width="15%"><spring:message code="firstname.label"/></th>
<th width="10%"><spring:message code="lastname.label"/></th>
<th width="10%"><spring:message code="teams.label"/></th>
<th width="10%"><spring:message code="actions.label"/></th>
</tr>
</thead>
<tbody id="member-list-table">
<c:forEach var="member" items="${members}">
<tr>
	<td>${member.id}</td>
	<td name="memberfirstnamecell">${member.firstName}</td>
	<td name="memberlastnamecell">${member.lastName}</td>
	<td>${member.teams}</td>
	<td>
	<a name="membereditlink" href="${pageContext.request.contextPath}/member/edit/${member.id}.html"><spring:message code="edit.action"/></a><br/>
	<a name="memberdeletelink" href="${pageContext.request.contextPath}/member/delete/${member.id}.html"><spring:message code="delete.action"/></a><br/>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

<p><a href="${pageContext.request.contextPath}/index.html"><spring:message code="home.page"/></a></p>

</body>
</html>