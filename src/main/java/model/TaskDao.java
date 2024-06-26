package model;

import beans.Task;
import java.util.List;

public interface TaskDao {
    void saveTask(Task task);
    void updateTask(Task task);
    void deleteTask(int id, int userId); // Ajout de userId pour vérifier la suppression par l'utilisateur propriétaire
    List<Task> getAllTasksByUser(int userId); // Nouvelle méthode pour obtenir les tâches par utilisateur
    Task findTaskByIdAndUser(int id, int userId); // Nouvelle méthode pour trouver une tâche par id et utilisateur
    List<Task> getFilteredAndSortedTasks(String filter, String sortBy, int userId); // Nouvelle méthode pour les filtres et tris par utilisateur
}
