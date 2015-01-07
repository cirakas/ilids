<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../header.jsp" %>
<%@ include file="../../footer.jsp" %>
<h1>Register form</h1>
<c:if test="${not empty success}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">×</button>
		${success}
	</div>
</c:if>
<c:if test="${not empty error}">
	<div class="alert alert-error">
		<button type="button" class="close" data-dismiss="alert">×</button>
		${error}
	</div>
</c:if>
<c:url value="/register" var="url" />
<form:form action="${url}" method="post" modelAttribute="user">
	<form:errors path="username">
		<label class="alert"> <strong>Warning!</strong> <form:errors
				path="username" />
		</label>
	</form:errors>
	<form:errors path="password">
		<label class="alert"> <strong>Warning!</strong> <form:errors
				path="password" />
		</label>
	</form:errors>
	<form:errors path="email">
		<label class="alert"> <strong>Warning!</strong> <form:errors
				path="email" />
		</label>
	</form:errors>
    Name: <form:input path="username" class="input-small"
		placeholder="username" required="required"/>
	<br />
    Password: <form:password path="password" class="input-small"
		placeholder="password" required="required"/>
	<br />
    Email: <form:input path="email" class="input-small"
		placeholder="email" required="required"/>
	<hr />
	<form:button class="btn"> Register</form:button>
</form:form>