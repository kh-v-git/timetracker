<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | Reports</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<ul>
        <a href="secured.command">Back Home</a>
    </li>
</ul>
<table>
    <caption>Report table</caption>
    <tr>
        <th scope="col">Activity</th>
        <th scope="col">Users count</th>
        <th scope="col">Total logged</th>
    </tr>
    <c:forEach items="${activityCountList}" var="activityCount">
        <tr>
            <td>${activityCount.activityName}</td>
            <td>${activityCount.usersActivityCount}</td>
            <td>${activityCount.activityTimeTotalLogged}</td>
        </tr>
    </c:forEach>

</table>


<br>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
