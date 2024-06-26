<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accueil</title>
</head>
<body>
    <h1>Bienvenue sur notre Gestionnaire de Tâches</h1>
    <form action="Serv_user" method="get">
        <input type="hidden" name="action" value="register"><br><br>
        <input type="submit" value="S'inscrire">
    </form><br>
     <form action="Serv_user" method="get">
        <input type="hidden" name="action" value="login">
        <input type="submit" value="Se connecter">
    </form>
</body>
</html>
