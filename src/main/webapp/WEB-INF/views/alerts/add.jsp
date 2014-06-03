<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h1>Alerts</h1>

<c:url value="/alerts" var="url" />
<form:form action="${url}" method="post" modelAttribute="alerts">
    Id: <form:input path="id" class="input-small"
		placeholder="id" required="required"/>
	<br />
    Power: <form:input path="power" class="input-small"
		placeholder="power" required="required"/>
	<form:button class="btn"> Register</form:button>
</form:form>
        
        <c:url value="/deviceUrl" var="dUrl" />
        <form:form action="${dUrl}" method="post" modelAttribute="devices">
    Code: <form:input path="name" class="input-small"
		placeholder="name" required="required"/>
	<br />
    Name: <form:input path="code" class="input-small"
		placeholder="code" required="required"/>
	<form:button class="btn"> Register</form:button>
</form:form>