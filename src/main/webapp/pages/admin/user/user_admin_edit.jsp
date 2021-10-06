<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Users Edit">
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
                        <h1 class="h2">User Edit</h1>
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
                            <a class="btn btn-primary float-right" href="get_user_activity_admin_page.command?userId=${user.getUserId()}">User Activities</a>
                        </div>
                    </div>
                    <form method="post">
                        <table>
                            <tr>
                                <td><label for="user-first-name">First Name:</label></td>
                                <td><input id="user-first-name" type="text" placeholder="First name"
                                           name="user_first_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" value="${user.getUserFirstName()}"
                                           size="30" required/>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="user-last-name">Last Name:</label></td>
                                <td><input id="user-last-name" type="text" placeholder="Last name"
                                           name="user_last_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" value="${user.getUserLastName()}"
                                           size="30" required/></td>
                            </tr>
                            <tr>
                                <td><label for="user-email">Email:</label></td>
                                <td><input id="user-email" type="text" placeholder="email@domain.com" name="user_email"
                                           pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}" value="${user.getUserEmail()}"
                                           size="30" required/></td>
                            </tr>
                            <tr>
                                <td><label for="user-password">Password:</label></td>
                                <td><input id="user-password" name="user_password" type="password" pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : '');
    if(this.checkValidity()) form.user_repeat_password.pattern = this.value;" placeholder="Password min 6 symbols"
                                           value="${user.getUserPassword()}" size="30" required></td>
                            </tr>
                            <tr>
                                <td><label for="user-password-two">Confirm Password:</label></td>
                                <td><input id="user-password-two" name="user_repeat_password" type="password" pattern="^\S{6,}$"
                                           onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
                                           placeholder="Verify Password" value="${user.getUserPassword()}" size="30" required></td>
                            </tr>
                            <tr>
                                <td><label for="user-role">Choose user role:</label></td>
                                <td><select name="user_role" id="user-role" required>
                                    <c:forEach items="${userRoles}" var="role">
                                        <c:choose>
                                            <c:when test="${role eq user.getUserRole()}">
                                                <option value="${role}" selected>
                                                    <c:out value="${role}"/>
                                                </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${role}">
                                                    <c:out value="${role}"/>
                                                </option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select></td>
                            </tr>
                            <tr>
                                <td><label for="user-status">Choose user status:</label></td>
                                <td><select name="user_status" id="user-status" required>
                                    <c:forEach items="${userStatuses}" var="status">
                                        <c:choose>
                                            <c:when test="${status eq user.getUserStatus()}">
                                                <option value="${status}" selected>
                                                    <c:out value="${status}"/>
                                                </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${status}">
                                                    <c:out value="${status}"/>
                                                </option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select></td>
                            </tr>
                            <tr>
                                <td><label for="user-about">About:</label></td>
                                <td><input id="user-about" type="text" placeholder="Info about user" name="user_about"
                                           value="${user.getUserAbout()}" size="30"/></td>
                            </tr>
                        </table>
                        <input type="hidden" name="userId" value="${user.getUserId()}">
                        <button type="submit" class="btn btn-primary" formaction="update_user_admin.command">Update</button>
                        <button type="submit" class="btn btn-primary" formaction="delete_user_admin.command" formnovalidate>Delete</button>
                        <button formaction="get_user_admin_main.command" class="btn btn-primary" formnovalidate>Cancel</button>
                    </form>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>
