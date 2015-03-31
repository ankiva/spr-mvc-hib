<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    
    <div class="clearfix">
    <div class="btn-group dropdown pull-right">
  <button id="langbutton" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
    ${currentLanguage.languageName}
    
    <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" role="menu">
    <c:forEach var="langItem" items="${supportedLanguages}">
      <c:if test="${!currentLanguage.code.equals(langItem.code)}">
        <li><a id="lang_link_${langItem.code}" href="?locale=${langItem.code}">${langItem.languageName}</a></li>
      </c:if>
    </c:forEach>
  </ul>
</div>
</div>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a id="homelink" class="navbar-brand" href="${pageContext.request.contextPath}"><spring:message code="home"/></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a id="organizations-link" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="organizations.label"/> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a id="organization-add-link" href="${pageContext.request.contextPath}/organization/add"><spring:message code="add.label"/></a></li>
            <li><a id="organization-list-link" href="${pageContext.request.contextPath}/organization/list"><spring:message code="list.label"/></a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a id="teams-link" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="teams.label"/> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a id="team-add-link" href="${pageContext.request.contextPath}/team/add"><spring:message code="add.label"/></a></li>
            <li><a id="team-list-link" href="${pageContext.request.contextPath}/team/list"><spring:message code="list.label"/></a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a id="members-link" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="team-members.label"/> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a id="member-add-link" href="${pageContext.request.contextPath}/member/add"><spring:message code="add.label"/></a></li>
            <li><a id="member-list-link" href="${pageContext.request.contextPath}/member/list"><spring:message code="list.label"/></a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
