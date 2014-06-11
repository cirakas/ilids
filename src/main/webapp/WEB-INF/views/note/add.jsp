<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
     <div class="col-lg-12">
	<h1>Add note</h1>
     
        <c:if test="${not empty success}">
	<div class="text-success">
            <button type="" class="close" data-dismiss="alert" style="float: none;">×</button>
		${success}
	</div>
        </c:if>
        <c:if test="${not empty error}">
                <div class="alert alert-error">
                        <button type="button" class="close" data-dismiss="alert">×</button>
                        ${error}
                </div>
        </c:if>
	<c:url var="url" value="/note/create" />
        
        <form:form action="${url}" method="post" modelAttribute="noteAdd">
             Note: <form:input path="note" class="input-large"
		placeholder="note" required="required"/>  
             
	<form:button class="btn btn-default"> Add</form:button>
        </form:form>
         
        <hr />
	<h4>Previously added notes</h4>  
        <hr /> 
        </br>
          <div class="table-responsive">
        <table cellpadding="0" cellspacing="0" border="0"
		class="table table-bordered table-hover table-striped tablesorter" id="example">
		<thead>
			<tr>
				<th>Note</th>
				<th>User</th>
                                <th>Created Date</th>
				<th>Action</th>
			</tr>
		</thead>
                <c:forEach var="note" items="${notesList}">
                    <tr>
                        <td>${note.note}</td>
                        <td>${note.user.username}</td>
                        <td>${note.createdDate}</td>
                        <td><c:url var="deleteUrl" value="/note/delete" />
						<form action="${deleteUrl}" method="post">
							<input type="hidden" value="${note.id}" name="noteid"/>
							<button type="submit" class="btn btn-danger">Remove</button>
						</form>
                        </td>
                    </tr>
        </c:forEach>
              </table>
          </div>
    </div>
</div>
