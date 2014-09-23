<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript">
    function onClickAddRoles() {
        var frm_elements = roleModel.elements;
        var field_type = "";
        for (var i = 0; i < frm_elements.length; i++) {
            field_type = frm_elements[i].type.toLowerCase();
            if (field_type === 'checkbox') {
                frm_elements[i].checked = false;
            }
        }
        document.getElementById('myModalLabel').innerHTML = "Add Role";
        document.getElementById('btn-save').innerHTML = "Save";
        document.getElementById('name').value = "";
        document.getElementById('description').value = "";
        document.getElementById('roleModel').action = "saveRole/";
        document.getElementById("btn-save").disabled = true;
        var div = document.getElementById("duplicateRole");
        div.style.display = "none";
    }
    function onClickEditRoles(val) {
        var frm_elements = roleModel.elements;
        var field_type = "";
        for (var i = 0; i < frm_elements.length; i++) {
            field_type = frm_elements[i].type.toLowerCase();
            if (field_type === 'checkbox') {
                frm_elements[i].checked = false;
            }
        }
        ajaxLink('/ilids/editRoles', {'id': val}, 'viewDiv');
    }
    function ajaxLink(url, params, displayComponentId) {
        $.post(url, params, function(data) {
            var menuObjectList = data.menuObjectList;
            for (var i = 0; i < menuObjectList.length; i++) {
                document.getElementById("menuvalues" + data.menuObjectList[i]).checked = true;
            }
            document.getElementById('roleModel').action = "saveRole/" + data.id;
            document.getElementById('name').value = data.name;
            document.getElementById('description').value = data.description;
            document.getElementById('myModalLabel').innerHTML = "Edit Role";
            document.getElementById('btn-save').innerHTML = "Save Changes";
        });
    }
    
//check box validate
    function vaidateCheckbox() {
        var frm_elements = roleModel.elements;
        var field_type = "";
        var flag = false;
        for (var i = 0; i < frm_elements.length; i++) {
            field_type = frm_elements[i].type.toLowerCase();
            if (field_type === 'checkbox' && frm_elements[i].checked === true) {
                flag = true;
            }

        }

        if (!flag) {
            alert('Please select atleast one option');
            return false;
        }
    }
    
 //role duplicate check
    function roleValidate() {
        var div = document.getElementById('duplicateRole');
        var name = document.getElementById('name');
        $.post('/ilids/duplicateRole', {'name': name.value}, function(data) {
            if (data) {
                div.style.display = "block";
                document.getElementById('btn-save').disabled = true;
            }
            if (!data) {
                div.style.display = "none";

            }
        });
    }
//empty field check
    function emptyCheck() {
        var desc = document.getElementById('description');
        var name = document.getElementById('name');
        if (name.value && desc.value) {
            document.getElementById('btn-save').disabled = false;
        }
        else {
            document.getElementById('btn-save').disabled = true;
        }

    }
    function confirmDelete()
    {
        var x = confirm("Are you sure you want to remove this Role?");
        if (x)
            return true;
        else
            return false;
    }

</script>
<div class="row">
    <div class="col-lg-12">
        <h1>Role Management</h1>
    </div>
</div>
<div class="row">
    <div class="col-lg-6">
        <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="onClickAddRoles()">
            <span class="glyphicon glyphicon-plus"></span>
        </button>
    </div>
    <div class="col-lg-6"><div style="color: tomato;">${deleteMessage}</div><div style="color: green;">${successMessage}</div></div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Add Role</h4>
            </div>     <c:url value="/saveRole" var="url" />
            <form:form action="${url}" method="post" modelAttribute="roleModel" onsubmit="return vaidateCheckbox()">
                <div class="modal-body">
                    <div class="form-group"><label> Role Name: </label><form:input path="name" class="form-control required name" placeholder="Role name" required="required" onblur="roleValidate();"/></div>
                    <div class="form-group"><label> Description: </label><form:input path="description" class="form-control required name" placeholder="Description" required="required" onblur="emptyCheck();"/></div>
                    <label>Menu Items</label>
                    <div class="form-group">

                        <form:checkboxes path="menuvalues"   items="${menuList}" itemLabel="name" itemValue="id"  />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <form:button class="btn btn-primary" id="btn-save" disabled="true">Save</form:button>
                    </div>
                    <div id="duplicateRole" style="display:none;">
                        <p>Role already exist</p>
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
                    <th>Role Name <i class="fa fa-sort"></i></th>
                    <th>Description <i class="fa fa-sort"></i></th>
                    <th>Edit <i class="fa fa-sort"></i></th>
                    <th>Delete <i class="fa fa-sort"></i></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="role" items="${roleList}">
                    <tr>
                        <td>${role.name}</td>
                        <td>${role.description}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="onClickEditRoles(${role.id})">
                                edit
                            </button></td>
                        <td>
                            <form method="post" action='<c:url value="/deleteRole"/>'>
                                <input type="hidden" value="${role.id}" name="roleId" />
                                <button id="deleteRole" class="btn btn-primary btn-danger" onclick="confirmDelete();" >delete</button>
                            </form>
                        </td>
                    </tr>
                    </div>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
