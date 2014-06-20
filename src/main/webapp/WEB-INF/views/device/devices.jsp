<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <div class="row">
          <div class="col-lg-12">
            <h1>Device Management</h1>
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
        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
          <span class="glyphicon glyphicon-plus"></span>
        </button>
     </div>
        <br/>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Add Device</h4>
      </div>
        <c:url value="/saveDevice" var="url" />
           <form:form action="${url}" method="post" modelAttribute="deviceModel">
      <div class="modal-body">
           <div class="form-group"><label> Device Name: </label><form:input path="name" class="form-control required name" placeholder="name" required="required"/></div>
           <div class="form-group">Device Code <form:input path="code" class="form-control required code" data-placement="top" placeholder="code" required="required"/></div>
           
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
                    <th>Code <i class="fa fa-sort"></i></th>
                    <th>Action <i class="fa fa-sort"></i></th>
                  </tr>
                </thead>
                <tbody>
                    <c:forEach var="device" items="${deviceList}">
				<tr>
					<td>${device.name}</td>
                                         <td>${device.code}</td>
                                         <td>
                                           <form method="post" action='<c:url value="/deleteDevice"/>'>
                                                <input type="hidden" value="${device.id}" name="deviceId" /> 
                                                <button id="deleteUser" class="btn btn-primary btn-danger" >delete</button>
						</form>
                                             
                                         </td>
                                            
                                </tr>
                    </c:forEach>       
                </tbody>
              </table>
          </div>
        </div><!-- /.row -->
        
        