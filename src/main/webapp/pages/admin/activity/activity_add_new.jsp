<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | New Activity</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<form method="POST">
    <label for="activityName">New activity name:</label>
    <input type="text" id="activityName" name="actNewName" required> <br><br>
    <label for="activityDescription">New activity description:</label>
    <input type="text" id="activityDescription" name="actNewDescription" required> <br><br>
    <label for="activityDescription">Choose category: </label>
    <select name="categoryId" required>
        <option value="" selected disabled hidden>Choose here</option>
        <c:forEach items="${searchCategories}" var="category">
            <option value="${category.getCategoryId()}"><c:out value="${category.getCategoryName()}"/></option>
        </c:forEach>
    </select><br><br>
    <button formaction="add_new_activity.command">Add</button>
    <button formaction="get_activities_main.command" formnovalidate>Cancel</button>
</form>

</body>
</html>
