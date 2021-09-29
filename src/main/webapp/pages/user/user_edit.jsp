<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User`s Page | User Edit</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<c:set value="${editUser}" var="user"> </c:set>
<c:set value="${userRole}" var="userRoles"> </c:set>
<c:set value="${userStatus}" var="userStatus"> </c:set>
<ul style="list-style-type:none">
    <li><a href="index.command">Go Home</a></li>
</ul>
<form method="post">
    <table>
        <tr>
            <td><label for="user-first-name">First Name:</label></td>
            <td><input id="user-first-name" type="text" placeholder="First name"
                       name="user_first_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" value="${user.userFirstName}"
                       size="30" required/>
            </td>
        </tr>
        <tr>
            <td><label for="user-last-name">Last Name:</label></td>
            <td><input id="user-last-name" type="text" placeholder="Last name"
                       name="user_last_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" value="${user.userLastName}"
                       size="30" required/></td>
        </tr>
        <tr>
            <td><label for="user-email">Email:</label></td>
            <td><input id="user-email" type="text" placeholder="email@domain.com" name="user_email"
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}" value="${user.userEmail}"
                       size="30" required/></td>
        </tr>
        <tr>
            <td><label for="user-password">Password:</label></td>
            <td><input id="user-password" name="user_password" type="password" pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : '');
    if(this.checkValidity()) form.user_repeat_password.pattern = this.value;" placeholder="Password min 6 symbols"
                       value="${user.userPassword}" size="30" required></td>
        </tr>
        <tr>
            <td><label for="user-password-two">Confirm Password:</label></td>
            <td><input id="user-password-two" name="user_repeat_password" type="password" pattern="^\S{6,}$"
                       onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
                       placeholder="Verify Password" value="${user.userPassword}" size="30" required></td>
        </tr>
        <tr>
            <td><label for="user-role"></label>User Role</td>
            <td><select name="user_role" id="user-role">
                <option value="" selected><c:out value="${user.userRole}"/></option>
            </select></td>
        </tr>
        <tr>
            <td><label for="user-status">User Status</label></td>
            <td><select name="user_status" id="user-status">
                <option value="" selected><c:out value="${user.userStatus}"/></option>
            </select>
            </td>
        </tr>
        <tr>
            <td><label for="user-about">About:</label></td>
            <td><input id="user-about" type="text" placeholder="Info about user" name="user_about"
                       value="${user.userAbout}" size="30"/></td>
        </tr>
    </table>
    <button type="submit" formaction="update_user.command">Update</button>
    <button type="submit" formaction="delete_user.command" formnovalidate>Delete</button>
    <button formaction="index.command" formnovalidate>Cancel</button>
</form>
<br>
<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
</html>
