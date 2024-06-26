<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add Task</title>
</head>
<body>
<h1>Add Task</h1>
<form action="Serv_task" method="post">
    <input type="hidden" name="action" value="add">
    
    <label for="titre">Titre:</label>
    <input type="text" id="titre" name="titre"><br><br>
    
    <label for="description">Description:</label>
    <input type="text" id="description" name="description"><br><br>
    
    <label for="date_echeance">Date Echeance:</label>
    <input type="date" id="date_echeance" name="date_echeance"><br><br>
    
    <label for="statut">Status:</label>
    <select id="statut" name="statut">
        <option value="EN_COURS">En cours</option>
        <option value="EN_ATTENTE">En attente</option>
        <option value="TERMINE">Terminé</option>
    </select><br><br>
    
    <input type="submit" value="Add Task">
</form>
</body>
</html>
