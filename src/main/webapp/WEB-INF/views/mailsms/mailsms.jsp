<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

function ajaxLink(url, params, displayComponentId) {
            $.post(url, params, function(data) {
               document.getElementById('mailSmsModel').action="saveMailSms/"+data.id;
               document.getElementById('mail').value=data.mail;
               document.getElementById('sms').value=data.sms;
               });
        }


        function onClickEditMailSms(val){
            ajaxLink('/ilids/editMailSms', {'id': val}, 'viewDiv');
        }
        function onClickAddMailSms(){
              document.getElementById('mailSmsModel').action="saveMailSms/";
              document.getElementById('mail').value="";
              document.getElementById('sms').value="";
        }
</script>

<div class="row">
    <div class="col-lg-12">
        <h1>Mail and Sms Management</h1>
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
                <h4 class="modal-title" id="myModalLabel">Add Mail and Sms</h4>
            </div>
            <c:url value="/saveMailSms" var="url" />
            <form:form action="${url}" method="post" modelAttribute="mailSmsModel">
                <div class="modal-body">
                    <div class="form-group"><label> E-mail: </label><form:input path="mail" class="form-control required name" placeholder="E-mail id" required="required"/></div>
                    <div class="form-group"><label> Mobile No: </label><form:input path="sms" class="form-control required name" placeholder="Mobile no" required="required"/></div>  
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <form:button class="btn btn-primary" id="btn-save">Save changes</form:button>
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
                                <button id="deleteDevice" class="btn btn-primary btn-danger" >delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>       
            </tbody>
        </table>
    </div>
</div><!-- /.row -->

