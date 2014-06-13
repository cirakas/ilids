<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <div class="row">
          <div class="col-lg-12">
            <h1>User Management</h1>
<!--            <ol class="breadcrumb">
              <li class="active"><i class="fa fa-dashboard"></i> Users</li>
            </ol>-->
            <div class="alert alert-success alert-dismissable">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              Welcome to ILIDS
            </div>
          </div>
        </div><!-- /.row -->
        
        <!-- Button trigger modal -->
          <div class="row">
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  <span class="glyphicon glyphicon-plus"></span>
</button></div>
        <br/>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Add User</h4>
      </div>
        <c:url value="/saveUser" var="url" />
           <form:form action="${url}" method="post" modelAttribute="userModel">
      <div class="modal-body">
           <div class="form-group"><label> Name: </label><form:input path="name" class="form-control required name" placeholder="name" required="required"/></div>
           <div class="form-group">Email: <form:input path="email" class="form-control required email" data-placement="top" placeholder="email" required="required"/></div>
           <div class="form-group"><label> User name </label><form:input path="username" class="form-control required userame" placeholder="username" required="required"/></div>
         <div class="form-group"> <label>Password</label> <form:password class="form-control required pass" path="password"  placeholder="password" required="required"/></div>
     </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <form:button class="btn btn-primary">Save changes</form:button>
      </div>
    </form:form>
    </div>
  </div>
</div>


        <div class="row">
            <div class="table-responsive">
              <table class="table table-bordered table-hover table-striped tablesorter">
                <thead>
                  <tr>
                    <th>Name <i class="fa fa-sort"></i></th>
                    <th>User name <i class="fa fa-sort"></i></th>
                    <th>E-mail <i class="fa fa-sort"></i></th>
                    <th>Enable <i class="fa fa-sort"></i></th>
                     <th>Action <i class="fa fa-sort"></i></th>
                  </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
				<tr>
					<td>${user.name}</td>
                                         <td>${user.username}</td>
					<td>${user.email}</td>
                                        <td>${user.enabled}</td>
                                          <td><button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
                                                    edit
                                                </button>
                                              <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal">
                                                delete
                                                </button>
                                            </td>
                                </tr>
                    </c:forEach>       
                </tbody>
              </table>
          </div>
        </div><!-- /.row -->
        
        