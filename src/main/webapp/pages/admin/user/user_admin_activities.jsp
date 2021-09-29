<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | User Activities</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<ul>
    <li>
        <a href="edit_user_admin_page.command?userId=${userId}">Back to User Edit</a>
    </li>
    <li>
        <a href="get_user_admin_main.command">Back to Users List</a>
    </li>
    <li>
        <a href="secured.command">Back Home</a>
    </li>
    <li>
        <a href="set_new_user_activity_admin_page.command?userId=${userId}">Add Activity to User</a>
    </li>
</ul>
<table>
    <caption>Users Activities</caption>
    <tr>
        <th scope="col">Activity Name</th>
        <th scope="col">Time Tracked</th>
        <th scope="col">Status</th>
        <th scope="col">Start Date</th>
    </tr>
    <c:forEach items="${userActivitiesList}" var="userActivities">
        <tr>
            <td>
                <a href="edit_user_activity_admin_page.command?userActivityId=${userActivities.userActivityId}">${userActivities.activityName}</a>
            </td>
            <td>
                <c:choose>
                    <c:when test="${empty userActivities.activityTimeLog}">
                    </c:when>
                    <c:otherwise>
                        ${userActivities.activityTimeLog}
                    </c:otherwise>
                </c:choose>
                    </td>
            <td>${userActivities.activityStatus}</td>
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
