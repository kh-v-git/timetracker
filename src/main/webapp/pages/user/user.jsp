<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>User DashBoard</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<h1></h1>
<ul>
    <li><a href="">Dashboard</a></li>
    <li><a href="">MyActivities</a></li>
</ul>

<table>
    <caption>Categories table</caption>
    <tr>
        <th scope="col">Activity Name</th>
        <th scope="col">Category</th>
        <th scope="col">Time logged</th>
    </tr>
</table>
<ul>
    <a href="">Log Time</a>
    <a href="logout.command">Logout</a>
</ul>
</body>
</html>
