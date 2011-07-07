<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page session="false" %>

<tags:template>

	<jsp:attribute name="breadcrumb">Home</jsp:attribute>

<jsp:body>

<h2>Chatter To Do Items</h2>
<p>Chatter items that you have liked or that you have been mentioned in will show up here as to do items. You can check each off as you complete it or decide that there is no need to re-visit it.</p>
<table class="itemlist">
<thead>
	<tr>
		<th style="width: 80px">Completed</th>
		<th style="width: 100px">Date</th>
		<th style="width: 200px">Author</th>
		<th style="width: 500px">Body</th>
		<th style="width: 50px">Post</th>
	</tr>
</thead>
<c:forEach items="${posts}" var="post">
	<tr>
		<td>
			<c:choose>
				<c:when test="${post.done}" >
					<a href="/unCompleteItem/${post.localId}"><img src="/resources/checkbox-checked-th.png" height="50" width="50"/></a>
				</c:when>
				<c:otherwise>
					<a href="/completeItem/${post.localId}"><img src="/resources/checkbox-unchecked-th.png" height="43" width="43"/></a>
				</c:otherwise>
			</c:choose>
		</td>
		<td>${post.postDateStr}</td>
		<td><a href="${post.authorLink}" target="new">${post.authorName}</a></td>
		<td>${post.body}</td>
		<td><a href="${post.postLink}" target="new">view</a></td>
	</tr>
</c:forEach>
</table>

</jsp:body>
</tags:template>
