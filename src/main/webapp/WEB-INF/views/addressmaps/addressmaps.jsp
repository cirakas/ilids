<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript">
    function EditAddressMap(val){
       ajaxLink('/ilids/EditAddressMap', {'id': val}, 'viewDiv');
    }
    function ajaxLink(url, params, displayComponentId){
       $.post(url, params, function(data){
           document.getElementById('addressmapsModel').action = "saveAddressMaps/"+data.id;
           document.getElementById('btn-save').innerHTML="Save Changes";
           document.getElementById('myModalLabel_addressMap').innerHTML="Edit Address Map details";
           document.getElementById('name').value=data.name;
           document.getElementById('minValue').value=data.minValue;
           document.getElementById('maxValue').value=data.maxValue;
           document.getElementById('offSet').value=data.offSet;
           document.getElementById('paramName').value=data.paramName;
           document.getElementById('wordLength').value=data.wordLength;
           document.getElementById('multiFactor').value=data.multiFactor;
       }) 
    }
    
    function AddAddressMap(){
           document.getElementById('addressmapsModel').action = "saveAddressMaps/";
           document.getElementById('btn-save').innerHTML="Save";
           document.getElementById('myModalLabel_addressMap').innerHTML="Add Address Map details";
           document.getElementById('name').value="";
           document.getElementById('minValue').value="";
           document.getElementById('maxValue').value="";
           document.getElementById('offSet').value="";
           document.getElementById('paramName').value="";
           document.getElementById('wordLength').value="";
           document.getElementById('multiFactor').value="";
    }
    
    function Validate(){
       var wordLength = document.getElementById('wordLength').value;
       var offSet = document.getElementById('offSet').value;
       var minValue = document.getElementById('minValue').value;
       var maxValue = document.getElementById('maxValue').value;
        var multiFact = document.getElementById('multiFactor').value;
        if(isNaN(wordLength)) {
            alert('Word Length should be a number');
        return false;
    }
        else if(isNaN(offSet)){
            alert('offSet should be a number');
        return false;
    } else if(isNaN(multiFact)){
            alert('Multi Factor should be a number');
        return false;
    }
        else if(isNaN(minValue)){
            alert('Minimum value should be a number');
        return false;
    }
        else if(isNaN(maxValue)){
            alert('Maximum value should be a number');  
            return false;
        }
            else {
                if(minValue >= maxValue){
                    alert('Minimum value should be less than Maximum value')
                     return false;
                }
               
                else {
                     return true;
                }
            }
    }
</script>
<div class="row">
    <div class="col-lg-12">
        <h1><spring:message code="label.addressMapManagement" /></h1>
    </div>
</div><!-- /.row -->

<!-- Button trigger modal -->
<div class="row">
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="AddAddressMap()">
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
                <h4 class="modal-title" id="myModalLabel_addressMap"><spring:message code="label.addAddressMapTitle" /></h4>
            </div>
                <c:url value="/saveAddressMaps" var="url"/>
                <form:form action="${url}" method="post" modelAttribute="addressmapsModel" onsubmit="return Validate();">
                    <div class="modal-body">
                        <div class="form-group"><label><spring:message code="label.name"/><span class="mandatory" style="color: red"> *</span>: </label><form:input path="name" class="form-control required name" placeholder="name" required="required"/></div>
                        <div class="form-group"><label><spring:message code="label.range"/><span class="mandatory" style="color: red"> *</span>:</label><br><form:input  path="minValue" class="form-control required name" placeholder="min" required="required" cssStyle="width:50px;"/><form:input path="maxValue" class="form-control required name" placeholder="max" required="required" cssStyle="width:50px;position:fixed;top:170px;left:80px;"/></div>
                        <div class="form-group"><label><spring:message code="label.offset"/><span class="mandatory" style="color: red"> *</span>: </label><form:input  path="offSet" class="form-control required name" placeholder="offSet" required="required"/></div>
                        <div class="form-group"><label><spring:message code="label.paramName"/><span class="mandatory" style="color: red"> *</span>: </label><form:input path="paramName" class="form-control required name" placeholder="paramName" required="required"/></div>
                        <div class="form-group"><label><spring:message code="label.wordLength"/><span class="mandatory" style="color: red"> *</span>: </label><form:input  path="wordLength" class="form-control required name" placeholder="wordLength" required="required"/></div>
                        <div class="form-group"><label><spring:message code="label.multiFactor"/><span class="mandatory" style="color: red"> *</span>: </label><form:input path="multiFactor" class="form-control required name" placeholder="multiFactor" required="required"/></div>
                        
                    </div>
                    <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.close" /></button>
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
                    <th rowspan="2">Name </th>
                    <th colspan="2">Range </th>
                    <th rowspan="2">Prameter Name </th>
                    <th rowspan="2">Offset</th>
                    <th rowspan="2">Word Length</th>
                    <th rowspan="2">Multi Factor</th>
                    <th rowspan="2">Edit </th>
                    <th rowspan="2">Delete </th>
                    
                <tr>
                    <th>Minimum </th>
                    <th> Maximum </th>
                </tr>
            </thead>
            
            <tbody>
                <c:forEach var="addressmaps" items="${addressMapsList}">
                    <tr>
                <td>${addressmaps.name}</td>
                <td>${addressmaps.minValue}</td>
                <td>${addressmaps.maxValue}</td>
                <td>${fn:substring(addressmaps.paramName,0,40)}</td>
                <td>${addressmaps.offSet}</td>
                <td>${addressmaps.wordLength}</td>
                <td>${addressmaps.multiFactor}</td>
                <td><button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="EditAddressMap(${addressmaps.id})">
                                edit
                    </button>
                </td>
                <td><form method="post" action='<c:url value="/deleteAddresssMaps"/>'>
                        <input type="hidden" value="${addressmaps.id}" name="addressMapsId"/>
                         <button id="deleteAddressMaps" class="btn btn-primary btn-danger">delete</button>
                    </form>
                </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
                