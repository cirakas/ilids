<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<center>
	<h1>Add note</h1>
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
	<c:url var="url" value="/note/add" />
        <form:form action="${url}" method="post" modelAttribute="notes">
             Note: <form:input path="note" class="input-large"
		placeholder="note" required="required"/>
	<hr />
	<form:button class="btn"> add</form:button>
        </form:form>
        <hr />
	<h4>Previously added notes</h4>        
	<hr />
        <table cellpadding="0" cellspacing="0" border="0"
		class="table table-striped table-bordered" id="example">
		<thead>
			<tr>
				<th>Note</th>
				<th>User</th>
				<th>Action</th>
			</tr>
		</thead>
                
                
</center>