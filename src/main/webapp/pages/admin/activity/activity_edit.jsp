<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | Activity Edit</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>

<c:set value="${searchCategories}" var="category"> </c:set>
<c:set value="${editActivity}" var="activity"> </c:set>
<c:set value="${editCategory}" var="categoryDefault"> </c:set>

<form method="POST">
    <label for="activityName">Activity name:</label>
    <input type="text" id="activityName" name="actNewName" value="${activity.getActivityName()}" required> <br><br>
    <label for="activityDescription">Activity description:</label>
    <input type="text" id="activityDescription" name="actNewDescription"
           value="${activity.getActivityDescription()}"
           required size="30"> <br><br>
    <label for="activityDescription">Activity category:</label>
    <select name="categoryId">
        <c:forEach items="${searchCategories}" var="category">
            <c:choose>
                <c:when test="${category.getCategoryId() eq categoryDefault.getCategoryId() }">
                    <option value="${category.getCategoryId()}" selected>
                        <c:out value="${category.getCategoryName()}"/>
                    </option>
                </c:when>
                <c:otherwise>
                    <option value="${category.getCategoryId()}">
                        <c:out value="${category.getCategoryName()}"/>
                    </option>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </select>
    <input type="hidden" name="activityId" value="${activity.getActivityId()}">
    <br><br>
    <button type="submit" formaction="update_activity.command" >Update</button>
    <button type="submit" formaction="delete_activity.command" formnovalidate>Delete</button>
    <button type="submit" formaction="get_activities_main.command" formnovalidate>Cancel</button>
</form>
</body>
</html>
