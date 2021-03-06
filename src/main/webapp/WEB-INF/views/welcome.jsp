<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page
	import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

welcome <security:authentication property="principal.username" /><br/>
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
</table>