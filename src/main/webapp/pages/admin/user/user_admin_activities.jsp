<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Users Activities">
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
                        <h1 class="h2">Users Activities</h1>
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
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3">
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <a class="btn btn-primary float-right" href="set_new_user_activity_admin_page.command?userId=${userId}">Add Activity to User</a>
                        </div>
                    </div>
                    <table>
                        <tr>
                            <th scope="col">Activity Name</th>
                            <th scope="col">Time Tracked</th>
                            <th scope="col">Status</th>
                            <th scope="col">Start Date</th>
                        </tr>
                        <c:forEach items="${userActivitiesList}" var="userActivities">
                            <tr>
                                <td>
                                    <a href="edit_user_activity_admin_page.command?userActivityId=${userActivities.userActivityId}">${userActivities.activityName}</a>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty userActivities.activityTimeLog}">
                                        </c:when>
                                        <c:otherwise>
                                            ${userActivities.activityTimeLog}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${userActivities.activityStatus}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty userActivities.activityStartDate}">
                                        </c:when>
                                        <c:otherwise>
                                            ${userActivities.activityStartDate}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>
