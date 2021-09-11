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
<form action="add_new_category.command" method="post">

    <form method="POST">
        <input type="submit" formaction="add_new_category.command" style="visibility: hidden; display: none;">
        <label for="categoryName">New category name:</label>
        <input type="text" id="categoryName" name="catNewName" required> <br><br>
        <label for="categoryDescription">New category description:</label>
        <input type="text" id="categoryDescription" name="catNewDescription" required> <br><br>
        <input type="submit" value="Save">
        <button formaction="get_categories_list.command" formnovalidate>Cancel</button>
    </form>

</form>
</body>
</html>
