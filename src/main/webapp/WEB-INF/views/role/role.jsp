<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../header.jsp" %>
<%@ include file="../../footer.jsp" %>



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
        //document.getElementById("btn-save").disabled = true;
        var div = document.getElementById("duplicateRole");
        div.style.display = "none";
    }
    function onClickEditRoles(val) {
        var div = document.getElementById("duplicateRole");
        div.style.display = "none";
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

//role and check box validate
    function vaidateRoleCheckbox() {
        var val = true;
        var div = document.getElementById('duplicateRole');
        var name = document.getElementById('name');
        var frm_elements = roleModel.elements;
        var field_type = "";
        var flag = false;
        $.post('/ilids/duplicateRole', {'name': name.value}, function(data) {
            if (!data) {
                div.style.display = "block";
                val = false;
            }
            if (data) {
                div.style.display = "none";
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

                document.getElementById("roleModel").submit();

            }
        });

        return false;

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
    <div class="col-lg-12" style="padding-left: 235px;">
        <h1>Role Management</h1>
    </div>
</div>
<div class="row" style="padding-left: 235px; padding-top: 30px;" >
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
            <form:form action="${url}" method="post" modelAttribute="roleModel" onsubmit="return vaidateRoleCheckbox()">
                <div class="modal-body">
                    <div id="duplicateRole" style="display:none;">
                        <div style="color: tomato;"> 
                            <p style="margin-top:-10px; margin-bottom:20px;">Role already exist</p>
                        </div> 
                    </div>
                    <div class="form-group"><label> Role Name <span class="mandatory" style="color: red"> *</span> :</label><form:input path="name" class="form-control required name" placeholder="Role name" required="required" /></div>
                    <div class="form-group"><label> Description <span class="mandatory" style="color: red"> *</span> : </label><form:input path="description" class="form-control required name" placeholder="Description" required="required" onblur="emptyCheck();"/></div>
                    <label>Menu Items</label>
                    <div class="form-group">

                        <form:checkboxes path="menuvalues"   items="${menuList}" itemLabel="name" itemValue="id"  />
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <form:button class="btn btn-primary" id="btn-save" >Save</form:button>
                    </div>

            </form:form>
        </div>
    </div>
</div>
<br>
<div class="row" style="padding-left: 235px; padding-right: 20px;">
    <div class="table-responsive" style="">
        <table class="table table-bordered table-hover table-striped tablesorter">
            <thead>
                <tr>
                    <th>Role Name  <i class="fa fa-sort"></i></th>
                    <th>Description </th>
                    <th>Edit </th>
                    <th>Delete </th>
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
                                <button id="deleteRole" class="btn btn-primary btn-danger" onclick="return confirmDelete();" >delete</button>
                            </form>
                        </td>
                    </tr>
                    </div>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="collapse  navbar-collapse navbar-ex1-collapse bg_res">
    <c:url value="/systemsettings" var="sysurl"/>
    <security:authorize access="isAuthenticated()">
        <ul class="nav navbar-nav side-nav color-menu"  style="background: #272727;background: #4f5b6f;">             
            <c:forEach var="menuIdList" items="${menuIdList}" >
                <c:if test="${menuIdList=='1'}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class=""><img src="/ilids/resources/images/manage_1.png"></i>&nbsp; User Management<div class="active_arrow"></div><b class="caret"></b></a>
                        <ul class="dropdown-menu" style="padding-bottom: 0;">
                            <li><a href='<c:url value="/user"/>'>&nbsp;&nbsp;&nbsp; Users</a></li>
                            <li style="border-bottom:none;"><a href="<c:url value="/role"/>">&nbsp;&nbsp;&nbsp; Roles</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${menuIdList==2}">
                    <li><a href="<c:url value="/systemsettings"/>"><i class=""><img src="/ilids/resources/images/system_1.png"></i>&nbsp; System Settings<div class="active_arrow"></div></a></li>
                            </c:if>
                            <c:if test="${menuIdList=='6'}">
                    <li><a  href="<c:url value="/mailsms"/>"><i class=""><img src="/ilids/resources/images/sms_1.png"></i>&nbsp; E-mail/SMS Settings<div class="active_arrow"></div></a></li>
                            </c:if>  
                        </c:forEach>
        </ul>
    </li>-->
</ul>
</security:authorize>
</div>
