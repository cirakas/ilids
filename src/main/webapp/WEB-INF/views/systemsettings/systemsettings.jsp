<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <div class="row">
          <div class="col-lg-12">
            <h1>SystemSettings Management</h1>
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
</button></div>
        <br/>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Add SystemSettings</h4>
      </div>
        <c:url value="/saveSystemSettings" var="url" />
           <form:form action="${url}" method="post" modelAttribute="SystemSettingsModel">
      <div class="modal-body">
           <div class="form-group"><label> ID </label><form:input path="id" class="form-control required name" placeholder="id" required="required"/></div>
           <div class="form-group">MDV <form:input path="mdv" class="form-control required code" data-placement="top" placeholder="mdv" required="required"/></div>
           <div class="form-group">Rates Per Unit <form:input path="ratesPerUnit" class="form-control required code" data-placement="top" placeholder="ratesPerUnit" required="required"/></div>
           <div class="form-group">Time Zone <form:input path="timeZone" class="form-control required code" data-placement="top" placeholder="timeZone" required="required"/></div>
           <div class="form-group">System Clock <form:input path="systemClock" class="form-control required code" data-placement="top" placeholder="systemClock" required="required"/></div>

           
           
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
                    <th>ID <i class="fa fa-sort"></i></th>
                    <th>MDV <i class="fa fa-sort"></i></th>
                    <th>Rates Per Unit <i class="fa fa-sort"></i></th>
                     <th>Time Zone <i class="fa fa-sort"></i></th>
                    <th>System Clock <i class="fa fa-sort"></i></th>
                    
                    
                  </tr>
                </thead>
                <tbody>
                    <c:forEach var="systemSettings" items="${SystemSettingsList}">
				<tr>
					<td>${systemSettings.id}</td>
                                        <td>${systemSettings.mdv}</td>
					<td>${systemSettings.ratesPerUnit}</td>
                                        <td>${systemSettings.timeZone}</td>
                                        <td>${systemSettings.systemClock}</td>
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
        
        