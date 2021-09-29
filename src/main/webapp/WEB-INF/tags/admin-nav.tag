<%@tag pageEncoding="UTF-8" %>
<%@attribute name="currentPage" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="col-md-2 d-none d-md-block bg-light sidebar">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'dashboard'}">active</c:if>" href="secured.command">
                    <span data-feather="home"></span>
                    Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'activity-categories'}">active</c:if>" href="get_categories_main.command">
                    <span data-feather="layers"></span>
                    Activity Categories
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'activities'}">active</c:if>" href="get_activities_main.command">
                    <span data-feather="file"></span>
                    Activities
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'users'}">active</c:if>" href="get_user_admin_main.command">
                    <span data-feather="users"></span>
                    Users
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:if test="${currentPage == 'reports'}">active</c:if>" href="get_admin_report_page.command">
                    <span data-feather="bar-chart-2"></span>
                    Reports
                </a>
            </li>
        </ul>
    </div>
</nav>
