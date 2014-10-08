<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
//Edit devices    
    function onClickEditDevices(val) {
        ajaxLink('/ilids/editDevices', {'id': val}, 'viewDiv');
    }
    function ajaxLink(url, params, displayComponentId) {
        $.post(url, params, function(data) {
            document.getElementById('deviceModel').action = "saveDevice/" + data.id;
            document.getElementById('myModalLabel').innerHTML = "Edit Device";
            document.getElementById('btn-save').innerHTML = "Save Changes";
            document.getElementById('name').value = data.name;
            document.getElementById('slaveId').value = data.slaveId;
            document.getElementById('deviceZoneId').value = data.deviceZoneId;
        });
    }
//Add devices
    function onClickAddDevices() {
        document.getElementById('deviceModel').action = "saveDevice/";
        document.getElementById('myModalLabel').innerHTML = "Add Device";
        document.getElementById('btn-save').innerHTML = "Save";
        document.getElementById('name').value = "";
        document.getElementById('slaveId').value = "";
        document.getElementById('deviceZoneId').value = "";
    }

//Confirm delete
    function ConfirmDelete()
    {
        var x = confirm("Are you sure you want to remove this device?");
        if (x)
            return true;
        else
            return false;
    }
//empty field check
    function fieldCheck() {
        var deviceName = document.getElementById('name');
        var deviceId = document.getElementById('slaveId');
        if (deviceName.value && deviceId.value) {
            return true;
        }
        else {
            alert("Both the fields are Mandatory");
            return false;
        }
    }

</script>
<div class="row">
    <div class="col-lg-12">
        <h1><spring:message code="label.deviceManagement" /></h1>
    </div>
</div><!-- /.row -->

<!-- Button trigger modal -->
<div class="row">
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="onClickAddDevices()">
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
                <h4 class="modal-title" id="myModalLabel"><spring:message code="label.addDevice" /></h4>
            </div>
            <c:url value="/saveDevice" var="url" />
            <form:form action="${url}" method="post" modelAttribute="deviceModel" onsubmit="return fieldCheck();">
                <div class="modal-body">
                    <div class="form-group"><label><spring:message code="label.deviceName" /><span class="mandatory" style="color: red"> *</span> :</label><form:input path="name" class="form-control required name" placeholder="Device name" required="required"/></div>
                    <div class="form-group"><label><spring:message code="label.deviceId" /><span class="mandatory" style="color: red"> *</span> :</label><form:input path="slaveId" class="form-control required name" placeholder="Slave Id" required="required"/></div>
                    <div class="form-group"><label><spring:message code="label.zone" /></label><form:select class="form-control" path="deviceZoneId" items="${deviceZones}" itemLabel="name" itemValue="id" multiple="false" /></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.close" /></button>
                    <form:button class="btn btn-primary" id="btn-save"><spring:message code="label.saveChanges" /></form:button>
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
                    <th><spring:message code="label.deviceId" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.zoneName" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.edit" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.delete" /> <i class="fa fa-sort"></i></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="device" items="${deviceList}">
                    <tr>
                        <td>${device.name}</td>
                        <td>${device.slaveId}</td>
                        <td>${device.deviceZone.name}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="onClickEditDevices(${device.id})">
                                <spring:message code="label.edit" />
                            </button></td>
                        <td>
                            <form method="post" action='<c:url value="/deleteDevice"/>'>
                                <input type="hidden" value="${device.id}" name="deviceId" /> 
                                <button id="deleteDevice" class="btn btn-primary btn-danger" onclick="return ConfirmDelete();"><spring:message code="label.delete" /></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>       
            </tbody>
        </table>
    </div>
</div><!-- /.row -->

