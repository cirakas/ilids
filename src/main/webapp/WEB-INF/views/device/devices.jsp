<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<center>
	<h1>Device Entry</h1>

	<c:if test="${not empty success}">
            <div class="a">
		
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

	<c:url var="url" value="/device/add" />
	<form:form action="${url}" method="post" modelAttribute="device">
             Note: <form:input path="name" class="input-large"
		placeholder="device" required="required"/>
	<hr />
	<form:button class="btn"> add</form:button>
        </form:form>
        <hr />
	<h4>Previously added devices</h4>        
	<hr />
        <table cellpadding="0" cellspacing="0" border="0"
		class="table table-striped table-bordered" id="example">
		<thead>
			<tr>
				<th>Device ID</th>
                                <th>Device Name</th>
				<th>Owner</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="device" items="${devices}">
				<tr>
                                        <td>${device.id}</td>
					<td>${device.name}</td>
					<td>${device.user.username}</td>
					<td><c:url var="deleteUrl" value="/device/delete" />
						<form action="${deleteUrl}" method="post">
							<input type="hidden" value="${device.id}" name="deviceid"/>
							<button type="submit" class="btn btn-danger">Remove</button>
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</center>