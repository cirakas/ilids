<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

function ajaxLink(url, params, displayComponentId) {
            $.post(url, params, function(data) {
               document.getElementById('userModel').action="saveUser/"+data.id;
               document.getElementById('name').value=data.name;
               document.getElementById('email').value=data.email;
               document.getElementById('username').value=data.username;
               document.getElementById('myModalLabel').innerHTML="Edit user";
               document.getElementById('btn-save').innerHTML="Save changes";
               });
        }


        function onClickEdituser(val){
            ajaxLink('/ilids/editUser', {'id': val}, 'viewDiv');
        }
        function onClickAdduser(){
               document.getElementById('userModel').action="saveUser/";
               document.getElementById('myModalLabel').innerHTML="Add user";
               document.getElementById('btn-save').innerHTML="Save";
               document.getElementById('name').value="";
               document.getElementById('email').value="";
               document.getElementById('username').value="";
               document.getElementById('password').value="";
        }
</script>
<div id="viewDiv">
        <div class="row">
          <div class="col-lg-12">
              <h1><spring:message code="label.userManagement" /></h1>
<!--            <ol class="breadcrumb">
              <li class="active"><i class="fa fa-dashboard"></i> Users</li>
            </ol>-->
<!--            <div class="alert alert-success alert-dismissable">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              Welcome to ILIDS
            </div>-->
          </div>
        </div><!-- /.row -->

        <!-- Button trigger modal -->
          <div class="row">
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="onClickAdduser()">
  <span class="glyphicon glyphicon-plus"></span>
</button></div>
        <br/>

<!-- Popup for Add user -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="myModalLabel"><spring:message code="label.addUser" /></h4>
      </div>
        <c:url value="/saveUser" var="url" />
   <form:form action="${url}" method="post" modelAttribute="userModel">
     <div class="modal-body">
           <div class="form-group"><label> <spring:message code="label.name" />: </label><form:input path="name" class="form-control required name" placeholder="name" required="required"/></div>
           <div class="form-group"><spring:message code="label.email" />: <form:input path="email" class="form-control required email" data-placement="top" placeholder="email" required="required"/></div>
           <div class="form-group"><label> <spring:message code="label.userName" />: </label><form:input path="username" class="form-control required userame" placeholder="username" required="required"/></div>
           <div class="form-group" id="passwordfield"> <label><spring:message code="label.password" />:</label> <form:password class="form-control required pass" path="password"  placeholder="password" required="required"/></div>
     </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.close" />;</button>
        <form:button class="btn btn-primary" id="btn-save"><spring:message code="label.save" /></form:button>
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
                    <th><spring:message code="label.name" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.userName" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.email" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.enable" /> <i class="fa fa-sort"></i></th>
                  </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
				<tr>
					<td>${user.name}</td>
                                        <td>${user.username}</td>
					<td>${user.email}</td>
                                        <td>${user.enabled}</td>
                                        <td><button class="btn btn-primary btn-sm" onclick="onClickEdituser(${user.id})"  data-toggle="modal" data-target="#myModal" >
                                                    <spring:message code="label.edit" />
                                                </button>
                                            </td>
                                             <td><c:url var="deleteUrl" value="/deleteUser"/>
                                              <form method="post" action="${deleteUrl}">
                                                <input type="hidden" value="${user.id}" name="userId" />
                                                <button id="deleteUser" class="btn btn-primary btn-danger" ><spring:message code="label.delete" /></button>
						</form>
                                            </td>
                                </tr>
                    </c:forEach>
                </tbody>
              </table>
          </div>
        </div><!-- /.row -->
</div>