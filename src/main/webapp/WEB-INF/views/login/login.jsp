<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<security:authorize access="isAnonymous()">
            <center>
                <form action="<c:url value='j_spring_security_check' />" method="post">
                    <table style="margin-top:100px;">
                        <tr><td> Name:</td><td><input type="text" placeholder="username" name="j_username"
                           required /> </td></tr>
                        <tr><td>Password:</td><td><input type="password" placeholder="password"
                                               name="j_password" required /></td></tr>
                        
                    </table>
                    
                    <button type="submit" class="btn">Sign in</button>
                </form>
            </center>
<!--    <li><a href='<c:url value="#"/>' style="color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</a></li>-->
</security:authorize>
<br/>

<li><a href='<c:url value="/register"/>'>Register</a></li>