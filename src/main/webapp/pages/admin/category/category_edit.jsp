<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Categories Edit">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <t:admin-nav currentPage="activity-categories"/>
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                        <h1 class="h2">Edit Category</h1>
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
                    </div>

                    <c:set value="${editCategory}" var="category"> </c:set>
                    <form method="POST">
                        <label for="categoryName">Category name:</label>
                        <input type="text" id="categoryName" name="catNewName" value="${category.getCategoryName()}"> <br><br>
                        <label for="categoryDescription">Category description:</label>
                        <input type="text" id="categoryDescription" name="catNewDescription" value="${category.getCategoryDescription()}">
                        <br><br>
                        <button class="btn btn-primary" formaction="update_category.command?categoryId=${category.getCategoryId()}">Update</button>
                        <button class="btn btn-primary" formaction="delete_category.command?categoryId=${category.getCategoryId()}" formnovalidate>Delete</button>
                        <button class="btn btn-primary" formaction="get_categories_main.command" formnovalidate>Cancel</button>
                    </form>
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
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>