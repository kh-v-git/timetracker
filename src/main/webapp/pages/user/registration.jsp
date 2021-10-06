<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Register page" skipHeader="true">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/signin.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div class="text-center w-100">
            <form class="form-signin" method="post" action="register.command">
                <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
                <h1 class="h3 mb-3 font-weight-normal">Registration</h1>
                <label for="user-first-name" class="sr-only">First Name:</label>
                <input type="text" id="user-first-name" class="form-control" name="user_first_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" placeholder="First Name" required autofocus>
                <label for="user-last-name" class="sr-only">Last Name:</label>
                <input type="text" id="user-last-name" class="form-control" name="user_last_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" placeholder="Last Name" required autofocus>
                <label for="input-mail" class="sr-only">Email address</label>
                <input type="email" id="input-mail" class="form-control" placeholder="email@domain.com" name="user_email"
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}"  required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" required name="user_password"
                       pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : '');
                        if(this.checkValidity()) form.user_repeat_password.pattern = this.value;" placeholder="Password min 6 symbols">

                <label for="inputPassword" class="sr-only">Password Confirm</label>
                <input type="password" id="inputPassword" class="form-control" placeholder="Repeat pass" required name="user_repeat_password"
                       pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above':'');"
                       placeholder="Verify Password">

                <label for="userAbout" class="sr-only">User About</label>
                <input type="text" id="userAbout" class="form-control" name="user_about" placeholder="About user" autofocus>
                <c:if test="${not empty error}">
                    <div class="">
                        <p class="h2">
                            <span class="badge bg-danger">${error}</span>
                        </p>
                    </div>
                </c:if>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
                <a href="login.command">Sign-in</a>
                <p class="mt-5 mb-3 text-muted">&copy; 2021</p>
            </form>
        </div>
    </jsp:body>
</t:page>
