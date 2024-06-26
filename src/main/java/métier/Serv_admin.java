package métier;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import bd.DBconnection;
import beans.User;
import model.UserDao;


public class Serv_admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
       
    
    public Serv_admin() {
        super();
        this.userDao = new DBconnection();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<User> users = userDao.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");

	    if ("addUser".equals(action)) {
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        String role = request.getParameter("role");
	        
	        // Créer un nouvel utilisateur
	        User user = new User();
	        user.setNom(username);
	        user.setPassword(password);
	        user.setRole(role);
	        
	        // Enregistrer l'utilisateur dans la base de données
	        userDao.saveUser(user);
	    } else if ("deleteUser".equals(action)) {
	        int userId = Integer.parseInt(request.getParameter("userId"));
	        
	        // Supprimer l'utilisateur de la base de données
	        userDao.deleteUser(userId);
	    } else if ("updateRole".equals(action)) {
	        int userId = Integer.parseInt(request.getParameter("userId"));
	        String newRole = request.getParameter("newRole");
	        
	        // Mettre à jour le rôle de l'utilisateur dans la base de données
	        userDao.updateUserRole(userId, newRole);
	    }

	    // Rediriger vers la page d'administration des utilisateurs après l'opération
	    request.getRequestDispatcher("/WEB-INF/dashboardadmin.jsp").forward(request, response);
	}

}
