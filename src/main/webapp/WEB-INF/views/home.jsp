<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page session="false" %>

<tags:template>

	<jsp:attribute name="breadcrumb">Home</jsp:attribute>

<jsp:body>

<h2>Chatter To Do Items</h2>
<p>Chatter items that you have liked or that you have been mentioned in will show up here as to do items. You can check each off as you complete it or decide that there is no need to re-visit it.</p>
<table>
<thead>
	<tr><th>done</th><th>author</th><th>body</th><th>reason</th><th>link</th><th>set as complete</th></tr>
</thead>
<c:forEach items="${posts}" var="post">
	<tr>
		<td>${post.done}</td><td>${post.author}</td><td>${post.body}</td><td>${post.reason}</td><td><a href="${post.link}" target="new">link</a></td><td><a href="/completeItem/${post.localId}">complete</a></td>
	</tr>
</c:forEach>
</table>

</jsp:body>
</tags:template>
