<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

function ajaxLink(url, params, displayComponentId) {
            $.post(url, params, function(data) {
               document.getElementById('userModel').action="saveUser/"+data.id;
               document.getElementById('roleId').value=data.roleId;
               document.getElementById('name').value=data.name;
               document.getElementById('email').value=data.email;
               document.getElementById('username').value=data.username;
               document.getElementById('myModalLabel').innerHTML="Edit user";
               document.getElementById('btn-save').innerHTML="Save changes";
               });
        }


        function onClickEdituser(val){
            ajaxLink('/ilids/editUser', {'id': val}, 'viewDiv');
            document.getElementById("btn-save").disabled = true;
        }
        function onClickAdduser(){
               document.getElementById('userModel').action="saveUser/";
               document.getElementById('myModalLabel').innerHTML="Add user";
               document.getElementById('btn-save').innerHTML="Save";
               document.getElementById('name').value="";
               document.getElementById('email').value="";
               document.getElementById('username').value="";
               document.getElementById('password').value="";
               document.getElementById("btn-save").disabled = true;
               var div1=document.getElementById("duplicateUser");
               div1.style.display = "none";
               var div2=document.getElementById('errorPost');
               div2.style.display = "none";
        }
        
        function checkEmail() {
    var email = document.getElementById('email');
    var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var div = document.getElementById("errorPost");
   // var but = document.getElementById("btn-save");
   var div1 = document.getElementById("duplicateUser");
    if (!filter.test(email.value)) {
     div.style.display = "block";
     document.getElementById("btn-save").disabled = true;
 }
     if (filter.test(email.value)) {
         $.post('/ilids/duplicateUser', {'email': email.value}, function(data) {
                      if(data){
                      div1.style.display = "block";
                      document.getElementById("btn-save").disabled = true;
                    }
                    if(!data){
                      div1.style.display = "none";  
                    }
                  });
                  
        div.style.display = "none";
        document.getElementById("btn-save").disabled = false;
    }
 
    email.focus;
    return false;
 
}

function confirmDelete()
    {
      var x = confirm("Are you sure you want to remove this User?");
      if (x)
          return true;
      else
        return false;
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
          
            <div id="duplicateUser" style="left: 80px; display:none; color: tomato;">
                <p style="text-indent: 20px;margin-top:10px;">User already exist</p>
            </div>
             <div id="errorPost" style="display:none; color: tomato;">
                <p style="text-indent: 20px;margin-top:10px;">Please provide a valid email address</p>
             </div>
          
        <c:url value="/saveUser" var="url" />
   <form:form action="${url}" method="post" modelAttribute="userModel">
     
       
     <div class="modal-body">
         <div class="form-group"><label> <spring:message code="label.name" /><span class="mandatory" style="color: red"> *</span> : </label><form:input path="name" class="form-control required name" placeholder="name" required="required"/></div>
           <div class="form-group"> <label><spring:message code="label.email" /><span class="mandatory" style="color: red"> *</span> : </label><form:input path="email" class="form-control required email" data-placement="top" placeholder="email" required="required" onblur="checkEmail();"/></div>
           <div class="form-group"><label>Role</label><form:select class="form-control" path="roleId" items="${roles}"  itemLabel="name" itemValue="id" multiple="false" /></div>
           <div class="form-group"><label> <spring:message code="label.userName" /><span class="mandatory" style="color: red"> *</span> : </label><form:input path="username" class="form-control required userame" placeholder="username" required="required"/></div>
           <div class="form-group" id="passwordfield"> <label><spring:message code="label.password" /><span class="mandatory" style="color: red"> *</span> :</label> <form:password class="form-control required pass" path="password"  placeholder="password" required="required"/></div>
           
     </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="label.close" /></button>
        <form:button class="btn btn-primary" id="btn-save" style="disabled:true"><spring:message code="label.save" /></form:button>
      </div>
 
           
    </form:form>
    </div>
  </div>
</div>

        <div class="row">
            <div class="table-responsive">
              <table class="table table-bordered table-hover table-striped tablesorter" >
                <thead>
                  <tr>
                    <th ><spring:message code="label.name" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.userName" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.email" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.enable" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.edit" /> <i class="fa fa-sort"></i></th>
                    <th><spring:message code="label.delete" /> <i class="fa fa-sort"></i></th>
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
                                               <form method="post" action='<c:url value="/deleteUser"/>'>
                                <input type="hidden" value="${user.id}" name="userId" /> 
                                <button id="deleteUser" class="btn btn-primary btn-danger" onclick="return confirmDelete();">delete</button>
                            </form>
                                            </td>
                                </tr>
                    </c:forEach>
                </tbody>
              </table>
          </div>
        </div><!-- /.row -->
</div>