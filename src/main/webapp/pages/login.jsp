<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
    <title>Login Form</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<form method="post" action="authenticate.command">
    <div class="container">
        <label for="user-email">Email </label>
        <input id="user-email" type="text" placeholder="enter email" name="user_email" required/>

        <label for="user-password">Password</label>
        <input id="user-password" type="password" placeholder="enter password" name="user_password" required/>

        <button type="submit">Login</button>
    </div>
    <a href="register_user_page.command">Register</a>
</form>

</body>
</html>
