<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
        <div class="row">
          <div class="col-lg-12">
            <h1>Dashboard <small>Statistics Overview</small></h1>
            <ol class="breadcrumb">
              <li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
            </ol>
            <div class="alert alert-success alert-dismissable">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              Welcome to ILIDS
            </div>
          </div>
        </div><!-- /.row -->


<!--welcome <security:authentication property="principal.username" />

-->

<!--<div style="width: 200px;height: 100px; float: right;">
    Learn crud operations
     <security:authorize access="hasRole('ROLE_ADMIN')">
         <li><a href='<c:url value="/admin"/>'>Admin</a></li>
    </security:authorize>
        <security:authorize access="isAuthenticated()">
           <li><a href='<c:url value="/book/add"/>'>Books</a></li>
     </security:authorize>

</div>-->

<!--<br/>
Logged-in Users</h1><table>
<tr>
<td width="100">Username</td>
<td width="150">Authorities</td>
<td width="170">IsAccountNonExpired</td>
<td width="190">IsCredentialsNonExpired</td>
<td width="150">IsAccountNonLocked</td>
</tr>
<c:forEach items="${users}" var="user">
<tr>
<td><a href='<c:url value="/admin/invalidate/${user.username}"/>'><c:out value="${user.username}"/></a></td>
<td><c:out value="${user.authorities}" /></td>
<td><c:out value="${user.accountNonExpired}" /></td>
<td><c:out value="${user.credentialsNonExpired}" /></td>
<td><c:out value="${user.accountNonLocked}" /></td>
</tr>
</c:forEach>
</table>-->