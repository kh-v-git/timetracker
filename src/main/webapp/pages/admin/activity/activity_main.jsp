<%@ page import="com.tracker.admin.category.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | Activity Main</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<ul>
    <li>
        <a href="get_new_activity_page.command">Add Activity</a>
    </li>
    <li>
        <form method="get" action="get_activities_main.command">
            <label for="site-search">Search for Activity:</label>
            <input type="search" id="site-search" name="searchText"
                   placeholder="Search activities"
                   value="${searchText}">
            <select name="searchCategoryId">
                <option value="" selected disabled hidden>Choose Category</option>
                <option value="">All Categories</option>
                <c:forEach items="${searchCategories}" var="category">
                    <c:choose>
                        <c:when test="${not empty searchCategoryId && category.getCategoryId() eq searchCategoryId }">
                            <option value="${category.getCategoryId()}" selected><c:out
                                    value="${category.getCategoryName()}"/></option>
                        </c:when>
                        <c:otherwise>
                            <option value="${category.getCategoryId()}"><c:out
                                    value="${category.getCategoryName()}"/></option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <input type="submit" value="Search"/>
            <a href="get_activities_main.command">Clear</a>
        </form>
    </li>
    <li>
        <a href="secured.command">Go Home</a>
    </li>
</ul>

<table>
    <caption>Activities table (TO DO)</caption>
    <tr>
        <th scope="col">ActivityID</th>
        <th scope="col">ActivityName</th>
        <th scope="col">Users</th>
    </tr>
    <c:forEach items="${searchActivities}" var="activity">
        <tr>
            <td>${activity.getActivityId()}</td>
            <td>
                <a href="edit_activity_page.command?activityId=${activity.getActivityId()}&categoryId=${activity.getActivityCatId()}">${activity.getActivityName()}</a>
            </td>
        </tr>
    </c:forEach>

</table>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>


</body>
</html>

