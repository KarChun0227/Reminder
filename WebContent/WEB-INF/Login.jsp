<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>
	<h1>Welcome to Reminder Web App</h1>
	<p>Please login to use the application!!</p>
	<div class="container">
		<form action="/Riminder/Login" method="post">
			<p>
				<font color="red">${errorMessage}</font>
			</p>
			<br>Email: <input type="text" name="name" /> 
			<br>Password:<input type="password" name="password" /> 
				<input type="submit" value="Login" />
		</form>
		<p>if you not a user yet, please register <a href="/Riminder/Registration">here</a>!!</p>
	</div>
</body>

</html>