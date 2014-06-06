<center>
	<h1>Add note</h1>

	<c:if test="${not empty success}">
		<div class="alert alert-success">
			<button type="button" class="close" data-dismiss="alert">×</button>
			${success}
		</div>
	</c:if>

	<c:url var="url" value="/note/add" />
	<form method="post" action="${url}">
		Notes : <input type="text" name="name" placeholder="Notes" /> <br />
		Users: <select name="username">
			<c:forEach var="user" items="${users}">
				<option value="${user.username}">${user.username}</option>
			</c:forEach>
		</select> <br />
		<button type="submit" class="btn">Add</button>
	</form>

	<hr />
	<h2>Previously added books</h2>
	<hr />

	<table cellpadding="0" cellspacing="0" border="0"
		class="table table-striped table-bordered" id="example">
		<thead>
			<tr>
				<th>Notes</th>
				<th>Owner</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="note" items="${notes}">
				<tr>
					<td>${note.name}</td>
					<td>${note.user.username}</td>
					<td><c:url var="deleteUrl" value="/note/delete" />
						<form action="${deleteUrl}" method="post">
							<input type="hidden" value="${note.id}" name="noteid"/>
							<button type="submit" class="btn btn-danger">Remove</button>
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</center>