<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page session="false" %>

<tags:template>

	<jsp:attribute name="breadcrumb">Home</jsp:attribute>

<jsp:body>
<h1>A Basic Java Web App</h1>
This template project consists of:
<ul>
<li>A standard Spring MVC project structure</li>
<li>A simple <code>HomeController</code> class that shows this page</li> 
<li>A few JSP pages that renders the views with <code>template.tag</code> template file and <code>layout.css</code>
</ul>

<h2>Chatter Posts</h2>
<ul>
<c:forEach items="${posts}" var="post">
	<li>id: ${post.id}, title: ${post.title}</li>
</c:forEach>
</ul>
</jsp:body>
</tags:template>
