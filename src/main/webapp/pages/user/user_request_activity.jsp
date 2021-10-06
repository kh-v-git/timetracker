<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="User Page | Acitivity Request">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <c:set value="${editUser}" var="user"> </c:set>
        <c:set value="${userRole}" var="userRoles"> </c:set>
        <c:set value="${userStatus}" var="userStatus"> </c:set>
        <div class="container-fluid">
            <div class="row">
                <t:user-nav currentPage="user-activities" />
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                        <h1 class="h2">User Activities</h1>
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
                    <script>
                        ;(function () {
                            const categoryActivities = ${categoryActivities};
                            window.onload = function () {
                                const categories = document.getElementById("categoryIdSelect");
                                const activities = document.getElementById("activityIdSelect");
                                categoryActivities.forEach(function (category) {
                                    categories.options[categories.options.length] = new Option(category.categoryName, category.categoryId);
                                });
                                categories.onchange = function () {
                                    activities.length = 1;
                                    activities.value = "";
                                    categoryActivities.find(category => category.categoryId == this.value).categoryActivitiesList.forEach(function (activity) {
                                        activities.options[activities.options.length] = new Option(activity.activityName, activity.activityId);
                                    });
                                }
                            }
                        })();
                    </script>
                    <form method="post" action="request_new_activity_user.command">
                        <select id="categoryIdSelect" name="categoryId" required>
                            <option value="" selected disabled hidden>Choose Category</option>
                        </select>
                        <br>
                        <select id="activityIdSelect" name="activityId" required>
                            <option value="" selected disabled hidden>Choose Activity</option>
                        </select>
                        <br>
                        <input type="submit" class="btn btn-primary" value="Request"/>
                        <button formaction="index.command" class="btn btn-primary" method="get" formnovalidate>Cancel</button>
                    </form>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>
