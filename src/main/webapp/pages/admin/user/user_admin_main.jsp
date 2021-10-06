<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Users Main">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <t:admin-nav currentPage="users" />
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                        <h1 class="h2">Users table</h1>
                        <c:if test="${not empty error}">
                            <div class="">
                                <p class="h2">
                                    <span class="badge bg-danger">${error}</span>
                                </p>
                            </div>
                        </c:if>
                        <c:if test="${not empty actionStatus}">
                            <div class="">
                                <p class="h2">
                                    <span class="badge bg-danger">${actionStatus}</span>
                                </p>
                            </div>
                        </c:if>
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <form method="get" action="get_user_admin_main.command" class="w-100">
                                <div class="btn-group mr-2">
                                    <input type="search" id="site-search" name="searchText"
                                           class="btn btn-sm btn-outline-secondary"
                                           placeholder="Search users"
                                           aria-label="Search"
                                           value="${param.searchText}">
                                    <input class="btn btn-sm btn-outline-secondary" type="submit" value="Search"/>
                                </div>
                                <a href="get_user_admin_main.command" class="btn btn-sm btn-outline-secondary">Clear</a>
                            </form>
                        </div>
                    </div>
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3">
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <a class="btn btn-primary float-right" href="get_user_admin_page.command">Add User</a>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Email</th>
                                <th scope="col">First Name</th>
                                <th scope="col">Last Name</th>
                                <th scope="col">Role</th>
                                <th scope="col">Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${searchUsers}" var="user">
                                <tr>
                                    <td>${user.getUserId()}</td>
                                    <td>
                                        <a href="edit_user_admin_page.command?userId=${user.getUserId()}">${user.getUserEmail()}</a>
                                    </td>
                                    <td>${user.getUserFirstName()}</td>
                                    <td>${user.getUserLastName()}</td>
                                    <td>${user.getUserRole()}</td>
                                    <td>${user.getUserStatus()}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </main>

            </div>
        </div>
    </jsp:body>
</t:page>
