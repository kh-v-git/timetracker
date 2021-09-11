<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
</head>
<body>
<h2>Hey, user ${email}!</h2>
<a href="secured.command">User page</a>

<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
