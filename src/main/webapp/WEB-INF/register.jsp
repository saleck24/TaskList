<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <form action="Serv_user" method="post">
    
        <input type="hidden" name="action" value="register"/>
        
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"/><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"/><br><br>
        
        <button type="submit">Register</button>
    </form>
</body>
</html>
