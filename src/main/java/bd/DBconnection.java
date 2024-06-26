package bd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import beans.Task;
import beans.User;
import model.TaskDao;
import model.UserDao;

public class DBconnection implements UserDao, TaskDao {
    private Connection connexion;

    public DBconnection() {
        this.connexion = loadDatabase();
    }

    public static Connection loadDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/Mini_projet?characterEncoding=UTF-8", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, user.getNom());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultat = statement.executeQuery()) {
                if (resultat.next()) {
                    user = new User();
                    user.setId(resultat.getInt("id"));
                    user.setNom(resultat.getString("username"));
                    user.setPassword(resultat.getString("password"));
                    user.setRole(resultat.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    @Override
    public void updateUserRole(int userId, String newRole) {
        String query = "UPDATE users SET role = ? WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, newRole);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveTask(Task task) {
        String query = "INSERT INTO task (titre, description, date_echeance, statut, user_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, task.getTitre());
            statement.setString(2, task.getDescription());
            statement.setDate(3, task.getDate_echeance());
            if (task.getStatut() != null) {
                statement.setString(4, task.getStatut().toString());
            } else {
                statement.setNull(4, java.sql.Types.VARCHAR);
            }
            statement.setInt(5, task.getUser().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task findTaskByIdAndUser(int id, int userId) {
        String sql = "SELECT * FROM task WHERE id = ? AND user_id = ?";
        try (Connection connection = loadDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitre(resultSet.getString("titre"));
                task.setDescription(resultSet.getString("description"));
                task.setDate_echeance(resultSet.getDate("date_echeance"));
                task.setStatut(Task.Statut.valueOf(resultSet.getString("statut")));
                task.setUser(new User(resultSet.getInt("user_id"))); // Associer l'utilisateur
                return task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void updateTask(Task task) {
        String query = "UPDATE task SET titre = ?, description = ?, date_echeance = ?, statut = ? WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, task.getTitre());
            statement.setString(2, task.getDescription());
            if (task.getDate_echeance() != null) {
                statement.setDate(3, task.getDate_echeance());
            } else {
                statement.setNull(3, java.sql.Types.DATE);
            }
            if (task.getStatut() != null) {
                statement.setString(4, task.getStatut().toString());
            } else {
                statement.setNull(4, java.sql.Types.VARCHAR);
            }
            statement.setInt(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void deleteTask(int id, int userId) {
        String sql = "DELETE FROM task WHERE id = ? AND user_id = ?";
        try (Connection connection = loadDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getAllTasksByUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE user_id = ?";
        try (Connection connection =loadDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultat = statement.executeQuery();
            while (resultat.next()) {
                Task task = new Task();
                task.setId(resultat.getInt("id"));
                task.setTitre(resultat.getString("titre"));
                task.setDescription(resultat.getString("description"));
                task.setDate_echeance(resultat.getDate("date_echeance"));
                task.setStatut(Task.Statut.valueOf(resultat.getString("statut")));
                task.setUser(new User(resultat.getInt("user_id"))); // Associer l'utilisateur
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    
    @Override
    public List<Task> getFilteredAndSortedTasks(String filter, String sortBy, int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE user_id = ?"; // Début de la requête

        // Ajout des filtres
        if (filter != null && !filter.isEmpty()) {
            switch (filter) {
                case "en_cours":
                    sql += " AND statut = 'EN_COURS'";
                    break;
                case "en_attente":
                    sql += " AND statut = 'EN_ATTENTE'";
                    break;
                case "terminees":
                    sql += " AND statut = 'TERMINE'";
                    break;
            }
        }

        // Ajout des tris
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "date_asc":
                    sql += " ORDER BY date_echeance ASC";
                    break;
                case "date_desc":
                    sql += " ORDER BY date_echeance DESC";
                    break;
                case "status_asc":
                    sql += " ORDER BY statut ASC";
                    break;
                case "status_desc":
                    sql += " ORDER BY statut DESC";
                    break;
            }
        }

        try (Connection connection = loadDatabase();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitre(resultSet.getString("titre"));
                task.setDescription(resultSet.getString("description"));
                task.setDate_echeance(resultSet.getDate("date_echeance"));
                task.setStatut(Task.Statut.valueOf(resultSet.getString("statut")));
                task.setUser(new User(resultSet.getInt("user_id"))); // Associer l'utilisateur
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setNom(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
