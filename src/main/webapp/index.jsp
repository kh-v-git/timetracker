
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>User DashBoard</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<h1> </h1>
<ul style="list-style-type:none">
    <li><a href="">Dashboard</a></li>
    <li><a href="">MyActivities</a></li>
    <li><button type="button" formaction="">Log Activity</button></li>
    <li><a href="logout.command">Logout</a></li>
</ul>

<table>
    <caption>Logged Activities</caption>
    <tr>
        <th scope="col">Activity Name</th>
        <th scope="col">Category</th>
        <th scope="col">Time logged</th>
    </tr>
</table>
</body>
</html>
