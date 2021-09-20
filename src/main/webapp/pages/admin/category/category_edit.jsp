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
<span>${actionStatus}</span>

<c:set value="${editCategory}" var="category"> </c:set>
<form method="POST">
    <label for="categoryName">Category name:</label>
    <input type="text" id="categoryName" name="catNewName" value="${category.getCategoryName()}"> <br><br>
    <label for="categoryDescription">Category description:</label>
    <input type="text" id="categoryDescription" name="catNewDescription" value="${category.getCategoryDescription()}">
    <br><br>
    <button formaction="update_category.command?categoryId=${category.getCategoryId()}">Update</button>
    <button formaction="delete_category.command?categoryId=${category.getCategoryId()}" formnovalidate>Delete</button>
    <button formaction="get_categories_main.command" formnovalidate>Cancel</button>
</form>
</body>
</html>