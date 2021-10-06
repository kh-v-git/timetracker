<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:page title="Admin Page | Users Add New">
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
                        <h1 class="h2">Add New User</h1>
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
                    <form method="post">
                        <table>
                            <tr>
                                <td><label for="user-first-name">First Name:</label></td>
                                <td><input id="user-first-name" type="text" placeholder="First name"
                                           name="user_first_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" size="30" required/>
                                </td>
                            </tr>
                            <tr>
                                <td><label for="user-last-name">Last Name:</label></td>
                                <td><input id="user-last-name" type="text" placeholder="Last name"
                                           name="user_last_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" size="30" required/></td>
                            </tr>
                            <tr>
                                <td><label for="user-email">Email:</label></td>
                                <td><input id="user-email" type="text" placeholder="email@domain.com" name="user_email"
                                           pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}" size="30" required/></td>
                            </tr>
                            <tr>
                                <td><label for="user-password">Password:</label></td>
                                <td><input id="user-password" name="user_password" type="password" pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : '');
    if(this.checkValidity()) form.user_repeat_password.pattern = this.value;" placeholder="Password min 6 symbols" size="30" required></td>
                            </tr>
                            <tr>
                                <td><label for="user-password-two">Confirm Password:</label></td>
                                <td><input id="user-password-two" name="user_repeat_password" type="password" pattern="^\S{6,}$"
                                           onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
                                           placeholder="Verify Password" size="30" required></td>
                            </tr>
                            <tr>
                                <td> <label for="user-role">Choose user role:</label></td>
                                <td> <select name="user_role" id="user-role" required>
                                    <option value="" selected disabled hidden>Choose here</option>
                                    <c:forEach items="${userRoles}" var="role">
                                        <option value="${role}"><c:out value="${role}"/></option>
                                    </c:forEach>
                                </select></td>
                            </tr>
                            <tr>
                                <td><label for="user-status">Choose user status:</label></td>
                                <td><select name="user_status" id="user-status" required>
                                    <option value="" selected disabled hidden>Choose here</option>
                                    <c:forEach items="${userStatuses}" var="status">
                                        <option value="${status}"><c:out value="${status}"/></option>
                                    </c:forEach>
                                </select></td>
                            </tr>
                            <tr>
                                <td><label for="user-about">About:</label></td>
                                <td><input id="user-about" type="text" placeholder="Info about user" name="user_about" size="30"/></td>
                            </tr>
                        </table>
                        <button type="submit" class="btn btn-primary" formaction="set_new_user_admin.command">Save</button>
                        <button formaction="get_user_admin_main.command" class="btn btn-primary" formnovalidate>Cancel</button>
                    </form>
                </main>
            </div>
        </div>
    </jsp:body>
</t:page>
