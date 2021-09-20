<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<h1>Admin`s page </h1>
<ul>
    <li><a href="">Reports</a></li>
    <li><a href="get_user_admin_main.command">Users</a></li>
    <li><a href="get_activities_main.command">Activities</a></li>
    <li><a href="get_categories_main.command">Activity Categories</a></li>
</ul>

<form action="logout.command" method="post">
    <input type="Submit" value="Logout"/>
</form>
</body>
</html>
