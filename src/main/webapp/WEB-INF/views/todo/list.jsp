<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css" type="text/css">
</head>
<body>
	<h1>Todo List</h1>
	<div id="todoForm">
		<t:messagesPanel />
		<form:form action="${pageContext.request.contextPath}/todo/create"
			method="post" modelAttribute="todoForm">
			<form:errors path="todoTitle" cssClass="text-error" />
			<form:input path="todoTitle" />
			<input type="submit" value="Create Todo" />
		</form:form>
	</div>
	<hr />
	<div id="todoList">
		<ul>
			<!-- (3) -->
			<c:forEach items="${todos}" var="todo">
				<li><c:choose>
						<c:when test="${todo.finished}">
							<span class="strike"> <!-- (5) --> ${f:h(todo.todoTitle)}
							</span>
						</c:when>
						<c:otherwise>
                            ${f:h(todo.todoTitle)}
                            <form:form
								action="${pageContext.request.contextPath}/todo/finish"
								method="post" modelAttribute="todoForm"
								cssStyle="display: inline-block;">
								<form:hidden path="todoId" value="${f:h(todo.todoId)}" />
								<input type="submit" name="finish" value="Finish" />
							</form:form>
						</c:otherwise>
					</c:choose> <form:form action="${pageContext.request.contextPath}/todo/delete"
						method="post" modelAttribute="todoForm"
						cssStyle="display: inline-block;">
						<form:hidden path="todoId" value="${f:h(todo.todoId)}" />
						<input type="submit" value="Delete" />
					</form:form></li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>
