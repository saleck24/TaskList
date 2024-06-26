package model;

import java.util.List;

import beans.User;

public interface UserDao {
    void saveUser(User user);
    User findByUsernameAndPassword(String username, String password);
    void deleteUser(int id);
	void updateUserRole(int userId, String newRole);
	List<User> getAllUsers();
}
