
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Admin Page</title>
</head>

<body>
    <h1>This page is secured</h1>
    <a href="getCategoriesList.command?cat_id=10">Categories</a>
    <form action="getCategoriesList.command" method="GET">
        <input type="submit" value="Categories"/>
        <input type="hidden" name="searchCategories" value="">
    </form>

    <form action="" method="post">
        <input type="submit" value="Reports"/>
    </form>

    <form action="">
        <input type="submit" value="Users"/>
    </form>

    <form action="">
        <input type="submit" value="Activities"/>
    </form>

    <form action="logout.command" method="post">
        <input type="submit" value="Logout"/>
    </form>
</body>
</html>
