<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Welcome to Reminder Web App</h1>
	<p>Upload you profile image here!!</p>
	<div class="container">
		<p>
			<font color="red">${errorMessage}</font>
		</p>
		<br><img src="data:image/jpg;base64,${pic}" width="240" height="300"/>
		
		<form action="/Riminder/AddImage" method="post" enctype="multipart/form-data">
		Select Image to Upload:<input type="file" name="Image">
		<br><input type="submit" value="Upload">
		</form>

	<form action="/Riminder/ListReminder">
		<input type="submit" value="Cancel">
	</form>
	</div>
</body>
</html>