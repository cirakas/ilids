<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


 <security:authorize access="isAnonymous()">   
     <div class="row" style="margin-top: 200px;">
 <form action="<c:url value='j_spring_security_check' />" method="post" class="form-horizontal" role="form">
  <div class="form-group">
    <label for="userName" class="col-sm-2 control-label">User name</label>
    <div class="col-sm-4">
      <input type="text" class="form-control" id="userName" placeholder="User name" name="j_username" required>
    </div>
  </div>
  <div class="form-group">
    <label for="password" class="col-sm-2 control-label">Password</label>
    <div class="col-sm-4">
      <input type="password" class="form-control" id="password" placeholder="Password"  name="j_password" required>
    </div>
  </div>
 
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">Sign in</button>
    </div>
  </div>
</form>
  </div>

<li><a href='<c:url value="/register"/>'>Register</a></li>
    </security:authorize>                      
                    
<!--    <li><a href='<c:url value="#"/>' style="color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</a></li>-->

