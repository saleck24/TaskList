<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Admin</title>
</head>
<body>
    <h1>Gestion des utilisateurs</h1>

    <h2>Liste des utilisateurs :</h2><br>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nom d'utilisateur</th>
            <th>Rôle</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.nom}</td>
                <td>${user.role}</td>
                <td>
                    <form action="Serv_admin" method="post">
                        <input type="hidden" name="action" value="deleteUser">
                        <input type="hidden" name="userId" value="${user.id}">
                        <input type="submit" value="Supprimer">
                    </form><br><br>
                    <form action="Serv_admin" method="post">
                        <input type="hidden" name="action" value="updateRole">
                        <input type="hidden" name="userId" value="${user.id}">
                        
                        <select name="newRole">
                            <option value="user">Utilisateur</option>
                            <option value="admin">Administrateur</option>
                        </select>
                        
                        <input type="submit" value="Modifier rôle">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <h2>Ajouter un nouvel utilisateur :</h2>
    
    <form action="Serv_admin" method="post">
        <input type="hidden" name="action" value="addUser">
        
        <label for="username">Nom d'utilisateur :</label>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="role">Rôle :</label>
        <select id="role" name="role">
            <option value="user">Utilisateur</option>
            <option value="admin">Administrateur</option>
        </select><br><br>
        
        <input type="submit" value="Ajouter">
    </form>
</body>
</html>
