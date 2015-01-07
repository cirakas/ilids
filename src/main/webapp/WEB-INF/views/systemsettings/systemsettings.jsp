<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../header.jsp" %>
<%@ include file="../../footer.jsp" %>




<script type="text/javascript">

    function onClickEditsystemsettings(val) {
        ajaxLink('/ilids/editSystemsettings', {'id': val}, 'viewDiv');
    }
    function onClickAddsystemsettings() {
        document.getElementById('SystemSettingsModel').action = "saveSystemSettings/"
        document.getElementById('myModalLabel_systemSettings').innerHTML = "Add SystemSettings";
        document.getElementById('btn-save').innerHTML = "Save";
        document.getElementById('mdv').value = "";
        document.getElementById('ratesPerUnit').value = "";
        document.getElementById('timeZone').value = "";
        document.getElementById('systemClock').value = "";

    }

    function ajaxLink(url, params, displayComponentId) {
        $.post(url, params, function(data) {

            document.getElementById('SystemSettingsModel').action = "saveSystemSettings/" + data.id;
            document.getElementById('myModalLabel_systemSettings').innerHTML = "Edit SystemSettings";
            document.getElementById('btn-save').innerHTML = "Save changes";
            document.getElementById('mdv').value = data.mdv;
            document.getElementById('ratesPerUnit').value = data.ratesPerUnit;
            document.getElementById('timeZone').value = data.timeZone;
            document.getElementById('systemClock').value = data.systemClock;
        });
    }
    function confirmDelete()
    {
        var x = confirm("Are you sure you want to remove this Settings?");
        if (x)
            return true;
        else
            return false;
    }
//empty field check
    function fieldCheck() {
        var mdv = document.getElementById('mdv');
        var rates = document.getElementById('ratesPerUnit');
        var time = document.getElementById('timeZone');
        var systemClock = document.getElementById('systemClock');
        if (mdv.value && rates.value && time.value && systemClock.value) {
            return true;
        }
        else {
            alert("All the fields are Mandatory");
            return false;
        }
    }
</script>
<div class="row">
    <div class="col-lg-12" style="padding-left: 235px;">
        <h1><spring:message code="label.systemSettingsManagement" /></h1>
    </div>
</div><!-- /.row -->

<!-- Button trigger modal -->
<div class="row" style="padding-left: 235px; padding-top: 30px;">
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="onClickAddsystemsettings()">
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
                <h4 class="modal-title" id="myModalLabel_systemSettings">Add SystemSettings</h4>
            </div>
            <c:url value="/saveSystemSettings" var="url" />
            <form:form action="${url}" method="post" modelAttribute="SystemSettingsModel" onsubmit="return fieldCheck();">
                <div class="modal-body">
                    <div class="form-group"><label>MDV<span class="mandatory" style="color: red"> *</span> :</label><form:input path="mdv" class="form-control required code" data-placement="top" placeholder="mdv" required="required"/></div>
                    <div class="form-group"><label>Rates Per Unit<span class="mandatory" style="color: red"> *</span> :</label> <form:input path="ratesPerUnit" class="form-control required code" data-placement="top" placeholder="ratesPerUnit" required="required"/></div>
                    <div class="form-group"><label>Time Zone<span class="mandatory" style="color: red"> *</span> :</label><form:input path="timeZone" class="form-control required code" data-placement="top" placeholder="timeZone" required="required"/></div>
                    <div class="form-group"><label>System Clock<span class="mandatory" style="color: red"> *</span> :</label><form:input path="systemClock" class="form-control required code" data-placement="top" placeholder="systemClock" required="required"/></div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <form:button class="btn btn-primary" id="btn-save" disabled="false">Save changes</form:button>
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
                    <!--                    <th>ID <i class="fa fa-sort"></i></th>-->
                    <th>MDV <i class="fa fa-sort"></i></th>
                    <th>Rates Per Unit <i class="fa fa-sort"></i></th>
                    <th>Time Zone <i class="fa fa-sort"></i></th>
                    <th>System Clock <i class="fa fa-sort"></i></th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="systemSettings" items="${SystemSettingsList}">
                    <tr>
                       <!--<td>${systemSettings.id}</td>-->
                        <td>${systemSettings.mdv}</td>
                        <td>${systemSettings.ratesPerUnit}</td>
                        <td>${systemSettings.timeZone}</td>
                        <td>${systemSettings.systemClock}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="onClickEditsystemsettings(${systemSettings.id})">
                                edit
                            </button></td>
                        <td><c:url var="deleteUrl" value="/deleteSystemsettings"/>
                            <form method="post" action="${deleteUrl}">
                                <input type="hidden" value="${systemSettings.id}" name="systemSettingsId" /> 
                                <button id="deleteSystemsettings" class="btn btn-primary btn-danger" onclick="return confirmDelete();" >delete</button>
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