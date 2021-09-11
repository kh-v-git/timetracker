<%@ page import="com.tracker.admin.category.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

    <title>Admin Page | Categories Main</title>
</head>
<body>
<span>${error}</span>
<ul>
    <li>
        <a href="get_category_page.command">Add Category</a>
    </li>
    <li>
        <label for="site-search">Search for Category:</label>
        <input type="search" id="site-search" name="search_category"
               aria-label="Search through categories">
        <button>Search</button>
    </li>
    <li>
        <a href="secured.command">Go Home</a>
    </li>
</ul>

<table>
    <caption>Categories table</caption>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Name</th>
        <th scope="col">Activities Count</th>
    </tr>
    <c:forEach items="${searchCategories}" var="category">
        <tr>
            <td>${category.id}</td>
            <td><a href="edit_category_page.command?category_id=${category.id}">${category.name}</a></td>
        </tr>
    </c:forEach>

</table>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>


</body>
</html>
