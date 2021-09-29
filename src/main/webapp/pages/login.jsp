<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Login page" skipHeader="true">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/signin.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div class="text-center w-100">
            <form class="form-signin" method="post" action="authenticate.command">
                <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
                <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
                <label for="inputEmail" class="sr-only">Email address</label>
                <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus name="user_email">
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" class="form-control" placeholder="Password" required name="user_password">
                <c:if test="${not empty error}">
                    <div class="">
                        <p class="h2">
                            <span class="badge bg-danger">${error}</span>
                        </p>
                    </div>
                </c:if>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                <a href="register_user_page.command">Register</a>

                <p class="mt-5 mb-3 text-muted">&copy; 2021</p>
            </form>
        </div>
    </jsp:body>
</t:page>
