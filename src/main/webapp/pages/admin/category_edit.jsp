<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | Edit Category</title>
</head>
<body>
<span>${error}</span>

<c:set value="${edit_category}" var="category"> </c:set>

<form action="update_category.command?category_id=${category.id}" method="post">
    <form method="POST">
        <input type="submit" formaction="update_category.command" style="visibility: hidden; display: none;">
        <label for="categoryName">Category name:</label>
        <input type="text" id="categoryName" name="catNewName" value="${category.getName()}"> <br><br>
        <label for="categoryDescription">Category description:</label>
        <input type="text" id="categoryDescription" name="catNewDescription" value="${category.getDescription()}">
        <br><br>
        <input type="submit" value="Update">
        <button formaction="delete_category.command?category_id=${category.id}" formnovalidate>Delete</button>
        <button formaction="get_categories_list.command" formnovalidate>Cancel</button>
    </form>
</form>
</body>
</html>