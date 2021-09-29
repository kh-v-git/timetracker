<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User`s Page | Request activity</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<ul style="list-style-type:none">
    <li><a href="index.jsp">Go Home</a></li>
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
<form method="post" action="request_new_activity_user.command">
    <select id="categoryIdSelect" name="categoryId" required>
        <option value="" selected disabled hidden>Choose Category</option>
    </select>
    <br>
    <select id="activityIdSelect" name="activityId" required>
        <option value="" selected disabled hidden>Choose Activity</option>
    </select>
    <br>
    <input type="submit" value="Request"/>
    <button formaction="index.command" method="get" formnovalidate>Cancel</button>
</form>
<br>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
