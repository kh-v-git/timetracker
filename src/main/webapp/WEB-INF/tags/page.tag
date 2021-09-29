<%@tag pageEncoding="UTF-8"%>
<%@attribute name="css" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@attribute name="skipHeader" type="java.lang.Boolean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <jsp:invoke fragment="css"/>
    <title>${title}</title>
</head>

<body>
    <c:if test="${!skipHeader}">
    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="${pageContext.request.contextPath}">Time tracker</a>
        <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
                <a class="nav-link" href="logout.command">Sign out</a>
            </li>
        </ul>
    </nav>
    <c:if test="${not empty error}">
        <div class="">
            <span class="badge rounded-pill bg-danger align-content-center">${error}</span>
        </div>
    </c:if>
    <span class="badge rounded-pill bg-success">${actionStatus}</span>

    <jsp:invoke fragment="header"/>
</c:if>
<jsp:doBody/>
<jsp:invoke fragment="footer"/>
<!-- Bootstrap core JavaScript
        ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>

<%--<script>window.jQuery || document.write('<script src="js/vendor/jquery-slim.min.js"><\/script>')</script>--%>
<%--<script src="js/vendor/popper.min.js"></script>--%>
<script src="js/bootstrap.js"></script>

<!-- Icons -->
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>
</body>
</html>
