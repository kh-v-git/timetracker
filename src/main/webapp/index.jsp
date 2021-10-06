<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="User Page | Main">
    <jsp:attribute name="css">
        <!-- Custom styles for this template -->
        <link href="css/dashboard.css" rel="stylesheet">
    </jsp:attribute>
    <jsp:body>
        <div class="container-fluid">
            <div class="row">
                <t:user-nav currentPage="dashboard" />
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
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                        <caption>User Activities</caption>
                        <tr>
                            <th scope="col">Activity</th>
                            <th scope="col">Status</th>
                            <th scope="col">Time Log</th>
                            <th scope="col">Start Date</th>
                        </tr>
                        <c:forEach items="${userActivitiesList}" var="userActivities">
                            <tr>
                                <td>${userActivities.activityName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty userActivities.activityTimeLog}">
                                        </c:when>
                                        <c:otherwise>
                                            ${userActivities.activityTimeLog}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                        ${userActivities.activityStatus}</td>
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
                    </div>
                </main>

            </div>
        </div>
    </jsp:body>
</t:page>