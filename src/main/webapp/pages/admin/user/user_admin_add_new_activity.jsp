<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | Activity Add to User</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>

<ul>
    <li>
        <a href="edit_user_admin_page.command?userId=${userId}">Back to User Edit</a>
    </li>
    <li>
        <a href="get_user_admin_main.command">Back to Users Activities</a>
    </li>
    <li>
        <a href="secured.command">Back Home</a>
    </li>
</ul>
<script>
    ;(function () {
        const categoryActivities = ${categoryActivities};
        window.onload = function () {
            const categories = document.getElementById("categoryIdSelect");
            const activities = document.getElementById("activityIdSelect");
            categoryActivities.forEach(function (category) {
                categories.options[categories.options.length] = new Option(category.categoryName, category.categoryId);
            });
            categories.onchange = function () {
                activities.length = 1;
                activities.value = "";
                categoryActivities.find(category => category.categoryId == this.value).categoryActivitiesList.forEach(function (activity) {
                    activities.options[activities.options.length] = new Option(activity.activityName, activity.activityId);
                });
            }
        }
    })();
</script>
<form method="post" action="add_new_activity_user_admin.command">
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
            <option value="${userActivityStatus}"><c:out value="${userActivityStatus}"/></option>
        </c:forEach>
   </select>
    <br>
    <input type="hidden" value="${userId}" name="userId">
    <input type="submit" value="Add"/>
<button formaction="get_user_activity_admin_page.command" method="get" formnovalidate>Cancel</button>
</form>
<br>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
