<%@ page import="com.helloworld.admin.category.Category" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Khomenko Vadym
  Date: 10.09.2021
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>

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
<form action="" method="post">
    <input type="submit" value="Add Category"/>
</form>

<form action="" method="post">
    <input type="submit" value="Search"/>
</form>
<%List<Category> categoryList = (List) request.getAttribute("searchCategories"); %>
<%= categoryList%>
<table>
    <tr>
        <th>ID</th>
        <th>NAME</th>

    </tr>
    <c:forEach items="${searchCategories}" var="category">
        <tr>
            <td>${category.id}</td>
            <td>${category.name}</td>
        </tr>
    </c:forEach>

</table>

<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>



</body>
</html>
