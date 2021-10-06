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
                        <h1 class="h2">Activity Edit</h1>
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
                        <label for="activityName">Activity name:</label>
                        <input type="text" id="activityName" name="actNewName" value="${activity.getActivityName()}" required> <br><br>
                        <label for="activityDescription">Activity description:</label>
                        <input type="text" id="activityDescription" name="actNewDescription"
                               value="${activity.getActivityDescription()}"
                               required size="30"> <br><br>
                        <label for="activityDescription">Activity category:</label>
                        <select name="categoryId">
                            <c:forEach items="${searchCategories}" var="category">
                                <c:choose>
                                    <c:when test="${category.getCategoryId() eq categoryDefault.getCategoryId() }">
                                        <option value="${category.getCategoryId()}" selected>
                                            <c:out value="${category.getCategoryName()}"/>
                                        </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${category.getCategoryId()}">
                                            <c:out value="${category.getCategoryName()}"/>
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="activityId" value="${activity.getActivityId()}">
                        <br><br>
                        <button class="btn btn-primary" type="submit" formaction="update_activity.command" >Update</button>
                        <button class="btn btn-primary" type="submit" formaction="delete_activity.command" formnovalidate>Delete</button>
                        <button class="btn btn-primary" type="submit" formaction="get_activities_main.command" formnovalidate>Cancel</button>
                    </form>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>
