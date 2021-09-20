<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Page | User Add</title>
</head>
<body>
<span>${error}</span>
<span>${actionStatus}</span>
<form method="post">
    <table>
        <tr>
            <td><label for="user-first-name">First Name:</label></td>
            <td><input id="user-first-name" type="text" placeholder="First name"
                       name="user_first_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" required/>
            </td>
        </tr>
        <tr>
            <td><label for="user-last-name">Last Name:</label></td>
            <td><input id="user-last-name" type="text" placeholder="Last name"
                       name="user_last_name" pattern="[A-Za-z\u0400-\u04ff]{1,32}" required/></td>
        </tr>
        <tr>
            <td><label for="user-email">Email:</label></td>
            <td><input id="user-email" type="text" placeholder="email@domain.com" name="user_email"
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}" required/></td>
        </tr>
        <tr>
            <td><label for="user-password">Password:</label></td>
            <td><input id="user-password" name="user_password" type="password" pattern="^\S{6,}$" onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : '');
    if(this.checkValidity()) form.user_repeat_password.pattern = this.value;" placeholder="Password min 6 symbols" required></td>
        </tr>
        <tr>
            <td><label for="user-password-two">Confirm Password:</label></td>
            <td><input id="user-password-two" name="user_repeat_password" type="password" pattern="^\S{6,}$"
                       onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
                       placeholder="Verify Password" required></td>
        </tr>
        <tr>
            <td> <label for="user-role">Choose user role:</label></td>
            <td> <select name="user_role" id="user-role" required>
                <option value="" selected disabled hidden>Choose here</option>
                <option value="user">User</option>
                <option value="admin">Administrator</option>
            </select></td>
        </tr>
        <tr>
            <td><label for="user-status">Choose user status:</label></td>
            <td><select name="user_status" id="user-status" required>
                <option value="" selected disabled hidden>Choose here</option>
                <option value="new">New</option>
                <option value="active">Active</option>
                <option value="deactivated">Deactivated</option>
            </select></td>
        </tr>
        <tr>
            <td><label for="user-about">About:</label></td>
            <td><input id="user-about" type="text" placeholder="Info about user" name="user_about"/></td>
        </tr>
    </table>
    <button type="submit" formaction="set_new_user_admin.command">Save</button>
    <button formaction="get_user_admin_main.command" formnovalidate>Cancel</button>
</form>

</body>
</html>