<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
    <div class="col-lg-12">
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
