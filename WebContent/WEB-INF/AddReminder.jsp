<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<script type="text/javascript">
			function callAjax(url, callback){
			    var xmlhttp;
			    xmlhttp = new XMLHttpRequest();
			    xmlhttp.responseType = 'json';
			    xmlhttp.onreadystatechange = function(){
			        if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			            callback(xmlhttp.response);
			        }
			    }
			    xmlhttp.open("GET", url, true);
			    xmlhttp.send();
			}
			
			function myCallback(resp){
				var form = document.forms['addReminderForm'];
				   var element = document.createElement("INPUT");
				   element.type = "hidden";
				   element.name = "hiddenTokenField";
				   element.value = resp["syncToken"];
				   form.appendChild(element);
			}
			callAjax("../Riminder/SyncTokenProvider", myCallback);
</script>

<%
			response.setHeader("Cache-Control","no-cache, must-revalidate"); //Forces caches to obtain a new copy of the page from the origin server
			response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
			response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
			response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
			
			if(session.getAttribute("UserEmail") == null)
				response.sendRedirect("/Riminder/Login");
%>

<div class="container">
	Your New Action Item:
	<form method="POST" name="addReminderForm" action="/Riminder/AddReminder">
		<fieldset class="form-group">
			<label>Description</label> <input name="reminder" type="text"
				class="form-control" /> <BR />
		</fieldset>
		<fieldset class="form-group">
			<label>Category</label> <input name="category" type="text"
				class="form-control" /> <BR />
		</fieldset>
		<input name="add" type="submit" class="btn btn-success" value="Submit" />
	</form>
</div>



</body>
</html>