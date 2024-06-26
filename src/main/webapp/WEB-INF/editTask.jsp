<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Task</title>
</head>
<body>
<h1>Edit Task</h1>
<form action="Serv_task" method="post">
    <input type="hidden" name="action" value="edit">
    <input type="hidden" name="id" value="${task.id}">
    
    <label for="titre">Titre:</label>
    <input type="text" id="titre" name="titre" value="${task.titre}"><br><br>
    
    <label for="description">Description:</label>
    <input type="text" id="description" name="description" value="${task.description}"><br><br>
    
    <label for="date_echeance">Date Echeance:</label>
    <input type="date" id="date_echeance" name="date_echeance" value="${task.date_echeance}"><br><br>
    
    <label for="statut">Statut:</label>
    <select id="statut" name="statut">
        <option value="EN_COURS" ${task.statut == 'EN_COURS' ? 'selected' : ''}>En cours</option>
        <option value="EN_ATTENTE" ${task.statut == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
        <option value="TERMINE" ${task.statut == 'TERMINE' ? 'selected' : ''}>Terminé</option>
    </select><br><br>
    
    <input type="submit" value="Update Task">
</form>
</body>
</html>
