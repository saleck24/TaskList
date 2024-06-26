<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form action="Serv_user" method="post">
        <input type="hidden" name="action" value="login"/>
        
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"/><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/><br><br>
        
        <button type="submit">Login</button>
    </form>
    <c:if test="${not empty errorMessage}">
        <p style="color:red;">${errorMessage}</p>
    </c:if>
</body>
</html>
