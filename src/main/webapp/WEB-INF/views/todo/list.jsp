<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
<style type="text/css">
.strike {
    text-decoration: line-through;
}
</style>
</head>
<body>
    <h1>Todo List</h1>
    <div id="todoForm">
        <!-- (1) -->
        <form:form
           action="${pageContext.request.contextPath}/todo/create"
            method="post" modelAttribute="todoForm">
            <!-- (2) -->
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
                        <c:when test="${todo.finished}"><!-- (4) -->
                            <span class="strike">
                            <!-- (5) -->
                            ${f:h(todo.todoTitle)}
                            </span>
                        </c:when>
                        <c:otherwise>
                            ${f:h(todo.todoTitle)}
                         </c:otherwise>
                    </c:choose></li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
