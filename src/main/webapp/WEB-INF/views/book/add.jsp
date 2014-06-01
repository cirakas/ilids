<center>
	<h1>Add book</h1>

	<c:if test="${not empty success}">
		<div class="alert alert-success">
			<button type="button" class="close" data-dismiss="alert">×</button>
			${success}
		</div>
	</c:if>

	<c:url var="url" value="/book/add" />
	<form method="post" action="${url}">
		Book's title: <input type="text" name="name" placeholder="Title" /> <br />
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
				<th>Title</th>
				<th>Owner</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="book" items="${books}">
				<tr>
					<td>${book.name}</td>
					<td>${book.user.username}</td>
					<td><c:url var="deleteUrl" value="/book/delete" />
						<form action="${deleteUrl}" method="post">
							<input type="hidden" value="${book.id}" name="bookid"/>
							<button type="submit" class="btn btn-danger">Remove</button>
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</center>