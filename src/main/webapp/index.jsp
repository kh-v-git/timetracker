<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
</head>
<body>
<h2>Hey, user ${email}!</h2>
<a href="secured.command">User page</a>

<form action="logout.command" method="post">
    <input type="submit" value="Logout"/>
</form>
</body>
