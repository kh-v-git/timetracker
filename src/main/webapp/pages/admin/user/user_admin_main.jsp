<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | Users Main</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<ul>
    <li>
        <a href="get_user_admin_page.command">Add User</a>
    </li>
    <li>
        <form method="get" action="get_user_admin_main.command">
            <label for="site-search">Search in Users:</label>
            <input type="search" id="site-search" name="searchText"
                   placeholder="Search text (Email, Firs Name, Status etc.. "
                   value="${param.searchText}"/>
            <input type="submit" value="Search"/>
            <a href="get_user_admin_main.command">Clear</a>
        </form>
    </li>
    <li>
        <a href="secured.command">Go Home</a>
    </li>
</ul>
<table>
    <caption>Users table</caption>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Email</th>
        <th scope="col">First Name</th>
        <th scope="col">Last Name</th>
        <th scope="col">Role</th>
        <th scope="col">Status</th>
    </tr>
    <c:forEach items="${searchUsers}" var="user">
        <tr>
            <td>${user.getUserId()}</td>
            <td>
                <a href="edit_user_admin_page.command?categoryId=${user.getUserId()}">${user.getUserEmail()}</a>
            </td>
            <td>${user.getUserFirstName()}</td>
            <td>${user.getUserLastName()}</td>
            <td>${user.getUserRole()}</td>
            <td>${user.getUserStatus()}</td>
        </tr>
    </c:forEach>

</table>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>

</body>
</html>
