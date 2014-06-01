<center>
	<h2>Add/Remove role</h2>
	<form method="post" action='<c:url value="/admin/createorremoverole"/>'>
		<table class="table table-hover">
			<thead>
				<tr>
					<th></th>
					<th></th>
					<th>action</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Add role</td>
					<td><input type="text" name="newrole"
						placeholder="Type the new role's name" /></td>
					<td><button type="submit" class="btn" name="action"
							value="add">Add</button></td>
				</tr>
				<tr>
					<td>Remove role</td>
					<td><select name="rolename" title="Warning"
						data-content="Deleting a role can cause users to exist without any role."
						id="roleRemove">
							<c:forEach var="role" items="${rolesWithoutRestrictedOnes}">
								<option value="${role.name}">${role.name}</option>
							</c:forEach>
					</select></td>
					<td><button type="submit" class="btn" name="action"
							value="remove">Remove</button></td>
				</tr>
			</tbody>
		</table>
	</form>

	<h2>Users</h2>
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th>username</th>
				<th>email</th>
				<th>roles</th>
				<th>enabled</th>
				<th>add role</th>
				<th>remove role</th>
				<th>action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td><c:forEach var="role" items="${user.roles}">
							<c:out value="${role.name}" />
							<br />
						</c:forEach></td>
					<td>
						<form method="post" action='<c:url value="/admin/enabled"/>'>
							<input type="hidden" value="${user.id}" name="userid" />
							<div class="btn-group" data-toggle="buttons-radio">
								<button type="submit"
									class="btn btn-success ${user.enabled eq 'true' ? 'active' : '' }"
									name="enabled" value="true">Enabled</button>
								<button type="submit"
									class="btn btn-danger ${user.enabled eq 'false' ? 'active' : '' }"
									name="enabled" value="false">Disabled</button>
							</div>
						</form>
					</td>
					<td>
						<form method="post" action='<c:url value="/admin/addrole"/>'>
							<input type="hidden" value="${user.id}" name="userid" /> <select
								name="roleid">
								<c:forEach var="role" items="${roles}">
									<option value="${role.id}">${role.name}</option>
								</c:forEach>
							</select>
							<button class="btn btn-success" type="submit">Add</button>
						</form>
					</td>
					<td>
						<form method="post" action='<c:url value="/admin/removerole"/>'>
							<input type="hidden" value="${user.id}" name="userid" /> <select
								name="roleid">
								<c:forEach var="role" items="${user.roles}">
									<option value="${role.id}">${role.name}</option>
								</c:forEach>
							</select>
							<button class="btn btn-danger" type="submit">Remove</button>
						</form>
					</td>
					<td>
						<form method="post" action='<c:url value="/admin/removeuser"/>'>
							<input type="hidden" value="${user.id}" name="userid" />
							<button class="btn btn-danger" type="submit">Remove</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</center>