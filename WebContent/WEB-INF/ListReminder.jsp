<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reminder</title>
</head>
<body>

<div class="container">
	<H1>Welcome ${name}</H1>


 	<table class="table table-striped">
		<caption>Your Reminders</caption>
		<thead>
			<th>Description</th>
			<th>Category</th>
			<th>Actions</th>
		</thead>
		<tbody>
			<c:forEach items="${Reminders}" var="Reminder">
				<tr>
 					<td>${Reminder.name}</td>
					<td>${Reminder.category}</td>
					<td>&nbsp;&nbsp;<a class="btn btn-danger"
						href="/Riminder/DeleteReminder?todo=${Reminder.name}&category=${Reminder.category}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 

	<p>
		<font color="red">${errorMessage}</font>
	</p>
	
	<form action="/Riminder/AddReminder">
		<input type="submit" value="Add New Todo">
	</form>
	
	<form action="/Riminder/Logout">
		<input type="submit" value="Logout">
	</form>
	
	<form action="/Riminder/AddImage">
		<input type="submit" value="Profile Update">
	</form>
	
	<form action="/Riminder/GeneratePDF">
		<input type="submit" value="Generate PDF Reminder">
	</form>
</div>
</body>
</html>