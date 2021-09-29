<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | User Activity Edit</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<ul>
    <li>
        <a href="edit_user_admin_page.command?userId=${userId}">Back to User Edit</a>
    </li>
    <li>
        <a href="get_user_activity_admin_page.command?userId=${userId}">Back to Users Activity List</a>
    </li>
    <li>
        <a href="secured.command">Back Home</a>
    </li>
</ul>
<script>
    ;(function () {
        const selectedCategory = ${currentActivity.activityCatId};
        const selectedActivity = ${currentActivity.activityId};
        const categoryActivities = ${categoryActivities};

        function buildActivityList(activities, categoryId, selectedActivityId) {
            activities.length = 1;
            categoryActivities.find(category => category.categoryId == categoryId).categoryActivitiesList.forEach(function (activity) {
                activities.options[activities.options.length] = new Option(activity.activityName, activity.activityId);
            });
            activities.value = selectedActivityId || '';
        }

        window.onload = function () {
            const categories = document.getElementById("categoryIdSelect");
            const activities = document.getElementById("activityIdSelect");
            categoryActivities.forEach(function (category) {
                categories.options[categories.options.length] = new Option(category.categoryName, category.categoryId);
            });
            categories.value = selectedCategory || '';
            if (selectedActivity) {
                buildActivityList(activities, selectedCategory, selectedActivity);
            }
            categories.onchange = function () {
                buildActivityList(activities, this.value, '');
            }
        }
    })();
</script>
<form method="post" action="update_user_activity.command">
    <select id="categoryIdSelect" name="categoryId">
        <option value="" selected disabled hidden>Choose Category</option>
    </select>
    <br>
    <select id="activityIdSelect" name="activityId">
        <option value="" selected disabled hidden>Choose Activity</option>
    </select>
    <br>
    <select name="activityStatus" id="activity-status" required>
        <option value="" selected disabled hidden>Choose Status</option>
        <c:forEach items="${activityStatusList}" var="userActivityStatus">
            <c:choose>
                <c:when test="${not empty userActivity && userActivity.activityStatus eq userActivityStatus}">
                    <option value="${userActivityStatus}" selected><c:out value="${userActivityStatus}"/></option>
                </c:when>
                <c:otherwise>
                    <option value="${userActivityStatus}"><c:out value="${userActivityStatus}"/></option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
    <br>
    <input type="hidden" value="${userActivity.userId}" name="userId">
    <input type="hidden" value="${userActivity.userActivityId}" name="userActivityId">
    <input type="submit" value="Update"/>
    <button type="submit" formaction="delete_user_activity.command" formnovalidate>Delete</button>
    <button type="submit" formaction="get_user_activity_admin_page.command?userId=${userId}" formnovalidate>Cancel
    </button>
</form>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
