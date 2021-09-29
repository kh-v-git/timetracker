<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <title>User`s Page | User DashBoard</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<h1> </h1>
<ul style="list-style-type:none">
    <li><a href="edit_user_page.command?userId=${userId}">User Info</a></li>
    <li><a href="request_activity_user_page.command">Request Activity</a></li>
    <li><a href="log_activity_user_page.command">Log Activity</a></li>
</ul>

<table>
    <caption>User Activities</caption>
    <tr>
        <th scope="col">Activity</th>
        <th scope="col">Status</th>
        <th scope="col">Time Log</th>
        <th scope="col">Start Date</th>
    </tr>
    <c:forEach items="${userActivitiesList}" var="userActivities">
        <tr>
            <td>${userActivities.activityName}</td>
            <td>
                <c:choose>
                    <c:when test="${empty userActivities.activityTimeLog}">
                    </c:when>
                    <c:otherwise>
                        ${userActivities.activityTimeLog}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    ${userActivities.activityStatus}</td>
            <td>
                <c:choose>
                    <c:when test="${empty userActivities.activityStartDate}">
                    </c:when>
                    <c:otherwise>
                        ${userActivities.activityStartDate}
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
