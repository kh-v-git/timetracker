<%@tag pageEncoding="UTF-8" %>
<%@attribute name="currentPage" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="col-md-2 d-none d-md-block bg-light sidebar">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'dashboard'}">active</c:if>" href="index.command">
                    <span data-feather="home"></span>
                    Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'user-info'}">active</c:if>" href="edit_user_page.command?userId=${userId}">
                    <span data-feather="layers"></span>
                    User Info
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'user-activities'}">active</c:if>" href="request_activity_user_page.command">
                    <span data-feather="file"></span>
                    Request Activity
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'log-activity'}">active</c:if>" href="log_activity_user_page.command">
                    <span data-feather="users"></span>
                    Log Activity
                </a>
            </li>
        </ul>
    </div>
</nav>
