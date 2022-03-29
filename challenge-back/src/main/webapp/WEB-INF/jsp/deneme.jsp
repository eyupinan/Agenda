<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Spring Boot Session Example</title>
</head>
<body>
	<div>
		<form action="persistMessage" method="post">
			<textarea name="msg" cols="40" rows="2"></textarea>
			<br> <input type="submit" value="Save Message" />
		</form>
	</div>
	<div>
		<h2>Messages</h2>
		<c:forEach items="${sessionMessages}" var="str">
        ${str}<br/>
        </c:forEach>
	</div>
	<div>
		<h2>Session ID</h2>
		Current Session ID is ${sessionId}
                <h2>JSession ID</h2>
		Current JSession ID is ${JsessionId}
                <h2>Username</h2>
		Current username is ${username}
	</div>
	<div>
		<form action="destroy" method="post">
			<input type="submit" value="Destroy Session" />
		</form>
                <form action="sa" method="post">
                    <textarea name="sa" cols="40" rows="2"></textarea>
			<input type="submit" value="sa" />
		</form>
	</div>
</body>
</html>