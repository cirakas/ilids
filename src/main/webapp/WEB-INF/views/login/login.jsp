<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<h1>Login form</h1>

<security:authorize access="isAnonymous()">
            <center>
                <form action="<c:url value='j_spring_security_check' />"
                      method="post">
                  Name:  <input type="text" placeholder="username" name="j_username"
                           required /> 
                    <br/>
                  Password:  <input type="password" placeholder="password"
                           name="j_password" required />
                  <br/>
                    <button type="submit" class="btn">Sign in</button>
                </form>
            </center>
<!--    <li><a href='<c:url value="#"/>' style="color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</a></li>-->
</security:authorize>
<br/>

<li><a href='<c:url value="/register"/>'>Register</a></li>