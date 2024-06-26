<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tasks</title>
</head>
<body>
<h1>Tasks</h1>
<a href="Serv_task?action=addTask">Ajouter une tâche</a><br><br>


<form action="Serv_task" method="get">
    <label for="filter">Filter:</label>
    <select id="filter" name="filter">
        <option value="toutes" ${param.filter == 'toutes' ? 'selected' : ''}>Toutes</option>
        <option value="en_cours" ${param.filter == 'en_cours' ? 'selected' : ''}>En_cours</option>
        <option value="en_attente" ${param.filter == 'en_attente' ? 'selected' : ''}>En_attente</option>
        <option value="terminees" ${param.filter == 'terminees' ? 'selected' : ''}>Terminer</option>
    </select>
    
    <label for="sortBy">Trier par:</label>
    <select id="sortBy" name="sortBy">
        <option value="date_asc" ${param.sortBy == 'date_asc' ? 'selected' : ''}>Date (Ancienne  en premier )</option>
        <option value="date_desc" ${param.sortBy == 'date_desc' ? 'selected' : ''}>Date (Nouvelle en premier)</option>
        <option value="status_asc" ${param.sortBy == 'status_asc' ? 'selected' : ''}>Status (A-Z)</option>
        <option value="status_desc" ${param.sortBy == 'status_desc' ? 'selected' : ''}>Status (Z-A)</option>
    </select>
    
    <input type="submit" value="Appliquer">
</form>
    
<table border="1">
    <tr>
        <th>Titre</th>
        <th>Description</th>
        <th>Date Echeance</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td>${task.titre}</td>
            <td>${task.description}</td>
            <td>${task.date_echeance}</td>
            <td>${task.statut}</td>
            <td>
                <form action="Serv_task" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="id" value="${task.id}">
                    <input type="submit" value="Edit">
                </form>
                <form action="Serv_task" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="${task.id}">
                    <input type="submit" value="Delete" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette tâche ?');">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
