<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript">
    function onClickAddRoles(){
          document.getElementById('myModalLabel').innerHTML = "Add Role";
          document.getElementById('btn-save').innerHTML = "Save";
          document.getElementById('name').value = "";
          document.getElementById('description').value = "";
    }
    function onClickEditRoles(val){
       ajaxLink('/ilids/editRoles', {'id': val}, 'viewDiv'); 
    }
    function ajaxLink(url, params, displayComponentId) {
          $.post(url, params, function(data) {
          document.getElementById('myModalLabel').innerHTML = "Edit Role";
          document.getElementById('btn-save').innerHTML = "Save Changes";
          });
      }
</script>
<div class="row">
    <div class="col-lg-12">
        <h1>Role Management</h1>
    </div>
</div>
<div class="row">
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="onClickAddRoles()">
        <span class="glyphicon glyphicon-plus"></span>
    </button>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Role</h4>
            </div>     <c:url value="/saveRole" var="url" />
            <form:form action="${url}" method="post" modelAttribute="roleModel">
                <div class="modal-body">
                    <div class="form-group"><label> Role Name: </label><form:input path="name" class="form-control required name" placeholder="Role name" required="required"/></div>
                     <div class="form-group"><label> Description: </label><form:input path="description" class="form-control required name" placeholder="Description" required="required"/></div>
                    <label>Menu Items</label>
                     <div class="form-group">
                         
                         <form:checkboxes path="menuvalues"   items="${menuList}" itemLabel="name" itemValue="id"  /> 
                     </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                     <form:button class="btn btn-primary" id="btn-save">Save</form:button>
                    </div>
            </form:form>
        </div>
    </div>
</div>
<br>
<div class="row">
    <div class="table-responsive">
        <table class="table table-bordered table-hover table-striped tablesorter">
            <thead>
                <tr>
                    <th>ID <i class="fa fa-sort"></i></th>
                    <th>Role Name <i class="fa fa-sort"></i></th>
                    <th>Description <i class="fa fa-sort"></i></th>
                    <th>Edit <i class="fa fa-sort"></i></th>
                    <th>Delete <i class="fa fa-sort"></i></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="role" items="${roleList}">
                    <tr>
                        <td>${role.id}</td>
                        <td>${role.name}</td>
                        <td>${role.description}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="onClickEditRoles(${role.id})">
                                edit
                            </button></td>
                        <td>
                            <form method="post" action='<c:url value="/deleteRole"/>'>
                                <input type="hidden" value="${role.id}" name="roleId" />
                                <button id="deleteRole" class="btn btn-primary btn-danger" >delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>       
            </tbody>
        </table>
    </div>
</div>
