<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | New Category</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<form method="POST">
    <label for="categoryName">New category name:</label>
    <input type="text" id="categoryName" name="catNewName" required> <br><br>
    <label for="categoryDescription">New category description:</label>
    <input type="text" id="categoryDescription" name="catNewDescription" required> <br><br>
    <button type="submit" formaction="add_new_category.command">Save</button>
    <button formaction="get_categories_main.command" formnovalidate>Cancel</button>
</form>
</body>
</html>
