<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Users Activity Edit">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <c:set value="${editUser}" var="user"> </c:set>
        <c:set value="${userRoles}" var="userRoles"> </c:set>
        <c:set value="${userStatuses}" var="userStatus"> </c:set>
        <div class="container-fluid">
            <div class="row">
                <t:admin-nav currentPage="users" />
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                        <h1 class="h2">User Activity Edit</h1>
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
                            const selectedCategory = ${currentActivity.activityCatId};
                            const selectedActivity = ${currentActivity.activityId};
                            const categoryActivities = ${categoryActivities};

                            function buildActivityList(activities, categoryId, selectedActivityId) {
                                activities.length = 1;
                                categoryActivities.find(category => category.categoryId == categoryId).categoryActivitiesList.forEach(function (activity) {
                                    activities.options[activities.options.length] = new Option(activity.activityName, activity.activityId);
                                });
                                activities.value = selectedActivityId || '';
                            }

                            window.onload = function () {
                                const categories = document.getElementById("categoryIdSelect");
                                const activities = document.getElementById("activityIdSelect");
                                categoryActivities.forEach(function (category) {
                                    categories.options[categories.options.length] = new Option(category.categoryName, category.categoryId);
                                });
                                categories.value = selectedCategory || '';
                                if (selectedActivity) {
                                    buildActivityList(activities, selectedCategory, selectedActivity);
                                }
                                categories.onchange = function () {
                                    buildActivityList(activities, this.value, '');
                                }
                            }
                        })();
                    </script>
                    <form method="post" action="update_user_activity.command">
                        <select id="categoryIdSelect" name="categoryId">
                            <option value="" selected disabled hidden>Choose Category</option>
                        </select>
                        <br>
                        <select id="activityIdSelect" name="activityId">
                            <option value="" selected disabled hidden>Choose Activity</option>
                        </select>
                        <br>
                        <select name="activityStatus" id="activity-status" required>
                            <option value="" selected disabled hidden>Choose Status</option>
                            <c:forEach items="${activityStatusList}" var="userActivityStatus">
                                <c:choose>
                                    <c:when test="${not empty userActivity && userActivity.activityStatus eq userActivityStatus}">
                                        <option value="${userActivityStatus}" selected><c:out value="${userActivityStatus}"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${userActivityStatus}"><c:out value="${userActivityStatus}"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <br>
                        <input type="hidden" value="${userActivity.userId}" name="userId">
                        <input type="hidden" value="${userActivity.userActivityId}" name="userActivityId">
                        <input type="submit" class="btn btn-primary" value="Update"/>
                        <button type="submit" class="btn btn-primary" formaction="delete_user_activity.command" formnovalidate>Delete</button>
                        <button type="submit" class="btn btn-primary" formaction="get_user_activity_admin_page.command?userId=${userId}" formnovalidate>Cancel
                        </button>
                    </form>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>