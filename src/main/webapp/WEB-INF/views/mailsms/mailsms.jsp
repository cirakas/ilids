<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

    function ajaxLink(url, params, displayComponentId) {
        $.post(url, params, function(data) {
            document.getElementById('mailSmsModel').action = "saveMailSms/" + data.id;
            document.getElementById('mail').value = data.mail;
            document.getElementById('sms').value = data.sms;
            document.getElementById('myModalLabel_mail_sms').innerHTML='Edit Mail and Sms';
        });
    }


    function onClickEditMailSms(val) {
        ajaxLink('/ilids/editMailSms', {'id': val}, 'viewDiv');
        var div = document.getElementById("errorPost");
        div.style.display = "none";
        var div1 = document.getElementById("duplicateMailPost");
        div1.style.display = "none";
    }
    function onClickAddMailSms() {
        document.getElementById('mailSmsModel').action = "saveMailSms/";
        document.getElementById('mail').value = "";
        document.getElementById('sms').value = "";
        document.getElementById('myModalLabel_mail_sms').innerHTML='Add Mail and Sms';
        var div = document.getElementById("errorPost");
        div.style.display = "none";
        var div1 = document.getElementById("duplicateMailPost");
        div1.style.display = "none";

    }

     function checkEmail() {
        var email = document.getElementById('mail');
        var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        var div = document.getElementById("errorPost");
        var div1 = document.getElementById("duplicateMailPost");
        var val = true;
        
        if (!filter.test(email.value)) {
            div.style.display = "block";
            div1.style.display = "none";
            val = false;
        }
        if (val) {
            $.post('/ilids/duplicateMailSms', {'mail': email.value}, function(data) {
                if (!data) {
                   div1.style.display = "block";
                   val = false;
                }
                if (data) {
                    div1.style.display = "none";
                    document.getElementById("mailSmsModel").submit();
                }
            });
            div.style.display = "none";
        }
        email.focus;
        return false;
    }

    function confirmDelete()
    {
        var x = confirm("Are you sure you want to remove this E-mail Address?");
        if (x)
            return true;
        else
            return false;
    }


</script>

<div class="row">
    <div class="col-lg-12">
        <h1><spring:message code="label.mainAndSmsManagement" /></h1>
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
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" onclick="onClickAddMailSms()">
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
                <h4 class="modal-title" id="myModalLabel_mail_sms">Add Mail and Sms</h4>
            </div>
            <c:url value="/saveMailSms" var="url" />
            <form:form action="${url}" method="post" modelAttribute="mailSmsModel" onsubmit="return checkEmail();">
                <div id="errorPost" style="display:none;">
                    <div style="color: tomato;"> <p style="text-indent: 20px;margin-top:10px;">Please provide a valid email address</p>
                    </div></div>
                <div id="duplicateMailPost" style="display:none;">
                    <div style="color: tomato;">   <p style="text-indent: 20px;margin-top:10px;">  E-mail id already exist</p>
                    </div>   </div> 
                <div class="modal-body">
                    <div class="form-group"><label> E-mail <span class="mandatory" style="color: red"> *</span> : </label><form:input path="mail" class="form-control required name" placeholder="E-mail id" required="required"/></div>
                    <div class="form-group"><label> Mobile No : </label><form:input path="sms" class="form-control required name" placeholder="Mobile no" /></div>  
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <form:button class="btn btn-primary"  id="btn-save" >Save changes</form:button>
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
                    <th>E-mail <i class="fa fa-sort"></i></th>
                    <th>Mobile <i class="fa fa-sort"></i></th>
                    <th>Edit <i class="fa fa-sort"></i></th>
                    <th>Delete <i class="fa fa-sort"></i></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="mailSms" items="${mailSmsList}">
                    <tr>
                        <td>${mailSms.mail}</td>
                        <td>${mailSms.sms}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="onClickEditMailSms(${mailSms.id})">
                                edit
                            </button></td>
                        <td>
                            <form method="post" action='<c:url value="/deleteMailSms"/>'>
                                <input type="hidden" value="${mailSms.id}" name="mailsmsId" /> 
                                <button id="deleteDevice" class="btn btn-primary btn-danger" onclick="return confirmDelete();">delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>       
            </tbody>
        </table>
    </div>
</div><!-- /.row -->
