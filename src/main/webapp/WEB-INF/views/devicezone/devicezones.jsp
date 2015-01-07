<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../header.jsp" %>
<%@ include file="../../footer.jsp" %>



<script type="text/javascript">
//Add device zone
    function onClickAddDeviceZone() {
        document.getElementById('deviceZoneModel').action = "saveDeviceZone/";
        document.getElementById('myModalLabel_device_zone').innerHTML = "Add Device Zone";
        document.getElementById('btn-save').innerHTML = "Save";
        document.getElementById('name').value = "";
        document.getElementById('description').value = "";
    }

//Edit device zone
    function onClickEditDeviceZone(val) {
        ajaxLink('/ilids/editDeviceZone', {'id': val}, 'viewDiv');
    }
//ajax function    
    function ajaxLink(url, params) {
        $.post(url, params, function(data) {
            document.getElementById('deviceZoneModel').action = "saveDeviceZone/" + data.id;
            document.getElementById('myModalLabel_device_zone').innerHTML = "Edit Device Zone";
            document.getElementById('btn-save').innerHTML = "Save Changes";
            document.getElementById('name').value = data.name;
            document.getElementById('description').value = data.description;
        });
    }

//Function to confirm deletion
    function ConfirmDelete()
    {
        var x = confirm("Are you sure you want to remove this device?");
        if (x)
            return true;
        else
            return false;
    }

//function to check empty field
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
    <div class="col-lg-12" style="padding-left: 235px;">
        <h1><spring:message code="label.zoneManagement" /></h1>
    </div>
</div><!-- /.row -->

<!-- Button trigger modal -->
<div class="row" style="padding-left: 235px; padding-top: 30px;">
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="onClickAddDeviceZone()">
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
                <h4 class="modal-title" id="myModalLabel_device_zone"><spring:message code="label.addTitle" /></h4>
            </div>
            <c:url value="/saveDeviceZone" var="url" />
            <form:form action="${url}" method="post" modelAttribute="deviceZoneModel" onsubmit="return fieldCheck();">
                <div class="modal-body">
                    <div class="form-group"><label> <spring:message code="label.zoneName" /><span class="mandatory" style="color: red"> *</span> : </label><form:input path="name" class="form-control required name" placeholder="Device Zone name" required="required"/></div>
                    <div class="form-group"><label> <spring:message code="label.zoneDescription" /> : </label><form:input path="description" class="form-control required name" placeholder="Description" /></div>  
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.close" /></button>
                    <form:button class="btn btn-primary" id="btn-save"><spring:message code="label.saveChanges" /></form:button>
                    </div>
            </form:form>
        </div>
    </div>
</div>

<div class="row" style="padding-left: 235px; padding-right: 20px;">
    <div class="table-responsive">
        <table class="table table-bordered table-hover table-striped tablesorter">
            <thead>
                <tr>
                    <th><spring:message code="label.zoneName" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.zoneDescription" /><i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.edit" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.delete" /> <i class="fa fa-sort"></i></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="deviceZone" items="${deviceZoneList}">
                    <tr>
                        <td>${deviceZone.name}</td>
                        <td>${deviceZone.description}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="onClickEditDeviceZone(${deviceZone.id})">
                                <spring:message code="label.edit" />
                            </button></td>
                        <td>
                            <form method="post" action='<c:url value="/deleteDeviceZone"/>'>
                                <input type="hidden" value="${deviceZone.id}" name="deviceZoneId" /> 
                                <button id="deleteDevice" class="btn btn-primary btn-danger" onclick="return ConfirmDelete();"><spring:message code="label.delete" /></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>       
            </tbody>
        </table>
    </div>
</div><!-- /.row -->
<div class="collapse  navbar-collapse navbar-ex1-collapse bg_res">
    <c:url value="/systemsettings" var="sysurl"/>
    <security:authorize access="isAuthenticated()">
        <ul class="nav navbar-nav side-nav color-menu"  style="background: #272727;background: #4f5b6f;">             
            <c:forEach var="menuIdList" items="${menuIdList}" >
                <c:if test="${menuIdList=='1'}">
                    <li><a href="<c:url value="/energy"/>"><i class=""><img src="/ilids/resources/images/mbl_1.png"></i>&nbsp; Dashboard<div class="active_arrow"></div></a></li>
                            </c:if>
                            <c:if test="${menuIdList=='3'}">
                    <li><a href="<c:url value="/devices"/>"><i class=""><img src="/ilids/resources/images/mbl_1.png"></i>&nbsp; Devices<div class="active_arrow"></div></a></li>
                            </c:if>
                            <c:if test="${menuIdList=='9'}">
                    <li><a href="<c:url value="/devicezones"/>"><i class=""><img src="/ilids/resources/images/mbl_1.png"></i>&nbsp; Device Zone<div class="active_arrow"></div></a></li>
                            </c:if>
                            <c:if test="${menuIdList=='7'}">
                    <li><a href="<c:url value="/add"/>"><i class=""><img src="/ilids/resources/images/notes_1.png"></i>&nbsp; Notes<div class="active_arrow"></div></a></li>
                            </c:if>   


                <c:if test="${menuIdList=='10'}">
                    <li><a href="<c:url value="/charts"/>"><i class=""><img src="/ilids/resources/images/chart_1.png"></i>&nbsp; Charts<div class="active_arrow"></div></a></li>
                            </c:if>

            </c:forEach>
        </ul>
    </security:authorize>
</div>