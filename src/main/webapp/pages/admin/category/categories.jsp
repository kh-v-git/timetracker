<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Categories Main">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <t:admin-nav currentPage="activity-categories" />
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                        <h1 class="h2">Categories table</h1>
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
                            <form method="get" action="get_categories_main.command" class="w-100">
                                <div class="btn-group mr-2">
                                    <input type="search" id="site-search" name="searchText"
                                           class="btn btn-sm btn-outline-secondary"
                                           placeholder="Search categories"
                                           aria-label="Search"
                                           value="${searchText}">

                                    <input class="btn btn-sm btn-outline-secondary" type="submit" value="Search"/>
                                </div>

                                <a href="get_categories_main.command" class="btn btn-sm btn-outline-secondary">Clear</a>

                            </form>
                        </div>
                    </div>
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3">
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <a class="btn btn-primary float-right" href="get_category_page.command">Add Category</a>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Activities Count</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${searchCategories}" var="category">
                                <tr>
                                    <td>${category.getCategoryId()}</td>
                                    <td><a href="edit_category_page.command?categoryId=${category.getCategoryId()}">${category.getCategoryName()}</a></td>
                                    <td>&nbsp;</td>
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


