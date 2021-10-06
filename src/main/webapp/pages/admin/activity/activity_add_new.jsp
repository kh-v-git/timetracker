<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Activities Edit">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <c:set value="${searchCategories}" var="category"> </c:set>
        <c:set value="${editActivity}" var="activity"> </c:set>
        <c:set value="${editCategory}" var="categoryDefault"> </c:set>
        <div class="container-fluid">
            <div class="row">
                <t:admin-nav currentPage="activities" />

                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                        <h1 class="h2">Add New Activity</h1>
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
                    <form method="POST">
                        <label for="activityName">New activity name:</label>
                        <input type="text" id="activityName" name="actNewName" required> <br><br>
                        <label for="activityDescription">New activity description:</label>
                        <input type="text" id="activityDescription" name="actNewDescription" required> <br><br>
                        <label for="activityDescription">Choose category: </label>
                        <select name="categoryId" required>
                            <option value="" selected disabled hidden>Choose here</option>
                            <c:forEach items="${searchCategories}" var="category">
                                <option value="${category.getCategoryId()}"><c:out value="${category.getCategoryName()}"/></option>
                            </c:forEach>
                        </select><br><br>
                        <button class="btn btn-primary" formaction="add_new_activity.command">Add</button>
                        <button class="btn btn-primary" formaction="get_activities_main.command" formnovalidate>Cancel</button>
                    </form>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>
