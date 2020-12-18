<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Registration</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<script>  
function validateform(){    
var password=document.registerForm.password.value;  
  
if(password.length<6){  
  alert("Password must be at least 6 characters long.");  
  return false;  
  }  
}  
</script> 

<body>
	<h1>Welcome to Reminder Web App</h1>
	<p>Please login to use the application!!</p>
	<div class="container">
		<form name="registerForm" action="/Riminder/Registration" method="post" onsubmit="return validateform()">
			<p>
				<font color="red">${errorMessage}</font>
			</p>
			<br>Name: <input type="text" name="name" required /> 
			<br>Email:<input type="email" name="email" required/>
			<br>Password:<input type="password" name="password" required/> 
			
				<input type="submit" value="Submit" />
		</form> 
		<p>Please click <a href="/Riminder/Login">here</a> to login!!</p>
	</div>
</body>

</html>

