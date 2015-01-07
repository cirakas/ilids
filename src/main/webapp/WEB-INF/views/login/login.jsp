<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../header.jsp" %>
<%@ include file="../../footer.jsp" %>
<style>

    body{background: #27a9e3;}
    .container {
        margin: 110px auto;
        width: 45%;
        float: left;
        margin-left: 15%;
    }

    .login {
        position: relative;
        margin: 0 auto;
        padding: 20px 20px 20px;
        width: 100%;
        background: white;
        float: left;
        border-radius: 20px;
        -webkit-box-shadow: 0 0 250px rgba(255, 255, 255, 1), 0 4px 4px rgba(0, 0, 0, 0.5);
        box-shadow: 0 0 250px rgba(255, 255, 255, 1), 0 4px 4px rgba(0, 0, 0, 0.5);
    }
    .login:before {
        content: '';
        position: absolute;
        top: -8px;
        right: -8px;
        bottom: -8px;
        left: -8px;
        z-index: -1;
        background: rgba(0, 0, 0, 0.08);
        border-radius: 20px;
    }
    .login h1 {
        margin: -20px -20px 21px;
        line-height: 40px;
        font-size: 16px;
        font-family: 'Lucida Grande', Tahoma, Verdana, sans-serif;
        color: #fff;
        padding: 10px 0;
        text-align: center;
        letter-spacing: 1px;
        background: #e19114;
        border-bottom: 1px solid #de8a07;
        border-radius: 20px 20px 0 0;
        /*  background-image: -webkit-linear-gradient(top, whiteffd, #eef2f5);
          background-image: -moz-linear-gradient(top, whiteffd, #eef2f5);
          background-image: -o-linear-gradient(top, whiteffd, #eef2f5);
          background-image: linear-gradient(to bottom, whiteffd, #eef2f5);*/
        -webkit-box-shadow: 0 1px whitesmoke;
        box-shadow: 0 1px whitesmoke;
    }
    .login p {
        margin: 20px 0 0;
    }
    .login p:first-child {
        margin-top: 0;
    }
    .login input[type=text], .login input[type=password] {
        width: 278px;
    }
    .login p.remember_me {
        float: left;
        line-height: 31px;
    }
    .login p.remember_me label {
        font-size: 12px;
        color: #777;
        cursor: pointer;
    }
    .login p.remember_me input {
        position: relative;
        bottom: 1px;
        margin-right: 4px;
        vertical-align: middle;
    }
    .login p.submit {
        text-align: right;
    }

    .login-help {
        margin: 20px 0;
        font-size: 11px;
        color: white;
        text-align: center;
        text-shadow: 0 1px #2a85a1;
    }
    .login-help a {
        color: #cce7fa;
        text-decoration: none;
    }
    .login-help a:hover {
        text-decoration: underline;
    }

    :-moz-placeholder {
        color: #c9c9c9 !important;
        font-size: 13px;
    }

    ::-webkit-input-placeholder {
        color: #ccc;
        font-size: 13px;
    }

    input {
        font-family: 'Lucida Grande', Tahoma, Verdana, sans-serif;
        font-size: 14px;
    }

    input[type=text], input[type=password] {
        margin: 5px;
        padding: 0 10px;
        width: 100%;
        height: 40px;
        color: #404040;
        background: white;
        border: 1px solid;
        border-color: #cfb68e;
        border-radius: 2px;
        outline: 5px solid #fdf9f2;
        -moz-outline-radius: 3px;
        -webkit-box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.12);
        box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.12);
    }
    input[type=text]:focus, input[type=password]:focus {
        border-color: #7dc9e2;
        outline-color: #dceefc;
        outline-offset: 0;
    }

    input[type=submit] {
        padding: 0 18px;
        height: 29px;
        font-size: 12px;
        font-weight: bold;
        color: #527881;
        text-shadow: 0 1px #e3f1f1;
        background: #cde5ef;
        border: 1px solid;
        border-color: #b4ccce #b3c0c8 #9eb9c2;
        border-radius: 16px;
        outline: 0;
        -webkit-box-sizing: content-box;
        -moz-box-sizing: content-box;
        box-sizing: content-box;
        background-image: -webkit-linear-gradient(top, #edf5f8, #cde5ef);
        background-image: -moz-linear-gradient(top, #edf5f8, #cde5ef);
        background-image: -o-linear-gradient(top, #edf5f8, #cde5ef);
        background-image: linear-gradient(to bottom, #edf5f8, #cde5ef);
        -webkit-box-shadow: inset 0 1px white, 0 1px 2px rgba(0, 0, 0, 0.15);
        box-shadow: inset 0 1px white, 0 1px 2px rgba(0, 0, 0, 0.15);
    }
    input[type=submit]:active {
        background: #cde5ef;
        border-color: #9eb9c2 #b3c0c8 #b4ccce;
        -webkit-box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.2);
        box-shadow: inset 0 0 3px rgba(0, 0, 0, 0.2);
    }

    .lt-ie9 input[type=text], .lt-ie9 input[type=password] {
        line-height: 34px;
    }
    .btn_1{
        background: #464747;color: #c1c3c3; 
        margin-top: 6px;
        margin-right: 5px;
    }
    .btn_1:hover,.btn_1:active{
        background: #3a3b3b;color: #d6d7d8; 
    }
    @media all and (max-width: 900px) and (min-width: 551px){
        .container{width:80%;margin:15% auto 0;float: none;}
    }
    @media all and (max-width: 550px) and (min-width: 150px){
        .container{width:100%;margin:15% auto 0;}
    }

</style>

<script type = "text/javascript">
    /*var devices = '${firstSlaveId}';
     //var dd = devices[1];
     //alert(devices);   
     var date = new Date();
     var dformat = date.getMonth() + 1 + "/" + date.getDate() + "/" + date.getFullYear();
     document.cookie = "phase=" + "null" + " " + "start=" + dformat + " " + "end=" + dformat + " " + "frHours=" + "00" + " " + "frMinutes=" + "00" + " " + "tHours=" + "23" + " " + "tMinutes=" + "59" + " " + "deviceId=" + devices + " ";
     document.cookie = "cname=; expires=Thu, 01 Jan 1970 00:00:00 GMT";*/

</script>

<security:authorize access="isAnonymous()">   
    <section class="container">
        <div class="login">
            <h1>Login to iLids</h1>

            <form action="<c:url value='j_spring_security_check' />" method="post" class="" role="form">
                <div class="col-lg-8"><div style="color: tomato;">${ErrorMessage}</div></div>    
                <p><input type="text"  id="userName" placeholder="User name" name="j_username" required style="width:98%;margin-top: 10px;"></p>               
                <p><input type="password"  id="password" placeholder="Password"  name="j_password" required style="width:98%;"></p>
                <button type="submit" class="btn btn_1" style="float: right;">Sign in</button>
            </form>

            <%--<c:forEach items="${slaveIdList}" var="slaveIdList">--%>     
            <%--</c:forEach>--%>
            <c:url value="/slaveId" var="url" />
            <%--<form:form action="${url}" method="post" modelAttribute="modelAdd">--%>
            <%--</form:form>--%>
        </div>
    </section>

<!--<li><a href='<c:url value="/register"/>'>Register</a></li>-->
</security:authorize>                      

<!--    <li><a href='<c:url value="#"/>' style="color: red">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</a></li>-->

