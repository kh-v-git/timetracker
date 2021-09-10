<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error</title>
</head>
<body>
        <h1>404</h1>
        <br/>
        <h3 class="text-center">Ooops. Go to Login.</h3>
        <br/>
        <form action="login.command" method="post">
            <input type="submit" value="Login"/>
        </form>
</body>
</html>
