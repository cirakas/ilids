<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../header.jsp" %>
<%@ include file="../../footer.jsp" %>



<script>
    function confirmDelete()
    {
        var x = confirm("Are you sure you want to remove this Note?");
        if (x)
            return true;
        else
            return false;
    }
//empty field check
    function fieldCheck() {
        var notes = document.getElementById('note');
        if (notes.value) {
            return true;
        }
        else {
            alert("Please add a note");
            return false;
        }
    }
</script>
<div class="row">
    <div class="col-lg-12" style="padding-left: 235px; padding-right: 20px;">
        <h1><spring:message code="label.addNote" /></h1><br>
        <c:if test="${not empty success}">
            <div class="text-success" style="background: #DFF0D8;color: #3C763D;padding: 4px;border-color: #D6E9C6;width: 24%;border-radius:2px;margin:0 0 8px 60px;text-align: center; ">
                <button type="" class="close" data-dismiss="alert" style="float: none;">×</button>
                ${success}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <button type="button" class="close" data-dismiss="alert">×</button>
                ${error}
            </div>
        </c:if>
        <c:url var="url" value="/note/create" />
        <form:form action="${url}" method="post" modelAttribute="noteAdd" style="float:left;width:100%" onsubmit="return fieldCheck();">
            <div style="float:left;width:60px;margin:8px 0 0 0;">Note:</div> <form:input path="note" class="input-large form-control"
                        placeholder="note" required="required" style="width:500px;float:left;"/>  
            <form:button class="btn btn-primary" style="float:left;margin:0 0 0 8px;"> Add</form:button>
        </form:form>
        <hr /><br>
        <h4>Previously added notes</h4>  
        <hr /> 
        </br>
        <div class="table-responsive">
            <table cellpadding="0" cellspacing="0" border="0"
                   class="table table-bordered table-hover table-striped tablesorter" id="example" style="table-layout: fixed; word-wrap: break-word;" >
                <thead>
                    <tr>
                        <th>Note <i class="fa fa-sort"></i></th>
                        <th>User <i class="fa fa-sort"></i></th>
                        <th>Action <i class="fa fa-sort"></i></th>
                    </tr>
                </thead>
                <c:forEach var="note" items="${notesList}" >
                    <tr>
                        <td>${note.note}</td>
                        <td>${note.user.username}</td>
                        <td><c:url var="deleteUrl" value="/note/delete" />
                            <form action="${deleteUrl}" method="post">
                                <input type="hidden" value="${note.id}" name="noteid"/>
                                <button type="submit" class="btn btn-danger" onclick="return confirmDelete();">Remove</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<div class="collapse  navbar-collapse navbar-ex1-collapse bg_res">
    <c:url value="/systemsettings" var="sysurl"/>
    <security:authorize access="isAuthenticated()">
        <ul class="nav navbar-nav side-nav color-menu"  style="background: #272727;background: #4f5b6f;">             
            <c:forEach var="menuIdList" items="${menuIdList}" >
                <c:if test = "${selection == 'EMeter'}">
                    <c:if test="${menuIdList=='3'}">
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
                            </c:if>

                <c:if test = "${selection == 'TMeter'}">
                    <c:if test="${menuIdList=='2'}">
                        <li><a href="<c:url value="/thermal"/>"><i class=""><img src="/ilids/resources/images/notes_1.png"></i>&nbsp; Dashboard<div class="active_arrow"></div></a></li>
                                </c:if>  
                                <c:if test="${menuIdList=='7'}">
                        <li><a href="<c:url value="/add"/>"><i class=""><img src="/ilids/resources/images/notes_1.png"></i>&nbsp; Notes<div class="active_arrow"></div></a></li>
                                </c:if>   


                            </c:if>
                        </c:forEach>
        </ul>
<!--        <ul class="nav navbar-nav navbar-right navbar-user margin_">
            <li class="dropdown alerts-dropdown">
                <a href="#" class="dropdown-toggle msg_bg" data-toggle="dropdown"><i class="fa fa-bell"></i> <span id="alertCountId" style="color: red;">${alertCount}</span> Alerts <span id="alertId" class="badge" style="background:#FF0404;"></span> <b class="caret"></b></a>
                <ul class="dropdown-menu" style="padding-bottom: 0;">
                    <li><a href="#">Default <span class="label label-default">Default</span></a></li>
                    <li><a href="#">Primary <span class="label label-primary">Primary</span></a></li>
                    <li><a href="#">Success <span class="label label-success">Success</span></a></li>
                    <li><a href="#">Info <span class="label label-info">Info</span></a></li>
                    <li><a href="#">Warning <span class="label label-warning" style="margin-left: 4px;">Warning</span></a></li>
                    <li><a href="#">Danger <span class="label label-danger" style="margin-left: 10px;padding: 3px 9px;">Danger</span></a></li>
                    <li class="divider" style="margin-bottom: 0;"></li>
                    <li style=""><a class="view_all_bg" href="#" onclick="latestAlertRequest(1, 10);" data-toggle="modal" data-target="#myAlertModal">View All</a></li>
                </ul>
            </li>
        </ul>-->
    </security:authorize>
</div>