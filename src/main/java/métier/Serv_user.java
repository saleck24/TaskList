package métier;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
//import java.sql.Connection;

import bd.DBconnection;
import beans.User;
import model.UserDao;


public class Serv_user extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new DBconnection();
   
    public Serv_user() {
        super();
        
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
        if ("register".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } else if ("login".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            // Afficher une page d'accueil ou de connexion par défaut
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }        
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		String action = request.getParameter("action");
		
		 if ("register".equals(action)) {
	            String username = request.getParameter("username");
	            String password = request.getParameter("password");
	            User user = new User();
	            user.setNom(username);
	            user.setPassword(password);
	            user.setRole("user"); // Ajout du rôle par défaut
	            userDao.saveUser(user);
	            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); 
	       }

	       if ("login".equals(action)) {
	           String username = request.getParameter("username");
	           String password = request.getParameter("password");
	           User user = userDao.findByUsernameAndPassword(username, password);
	           if (user != null) {
	               HttpSession session = request.getSession();
	               session.setAttribute("user", user);
	               request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
	            } else {
	            	request.setAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect");
	                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	         
	            }
	        } 
       
    }	

}
