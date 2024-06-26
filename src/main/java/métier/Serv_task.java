package métier;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import bd.DBconnection;
import beans.Task;
import beans.User;
import model.TaskDao;

public class Serv_task extends HttpServlet {
    private static final long serialVersionUID = 1L; 
    private TaskDao taskDao = new DBconnection();

    public Serv_task() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); 
        if (user == null) {
            // Redirection vers la page de connexion si l'utilisateur n'est pas connecté
        	request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
    	
    	String action = request.getParameter("action");
    	 if ("addTask".equals(action)) {
    		 // Rediriger vers la page addTask.jsp
    	     request.getRequestDispatcher("/WEB-INF/addTask.jsp").forward(request, response);
    	 }
    	 else if ("edit".equals(action)) {
    		 String idParam = request.getParameter("id");
             if (idParam != null && !idParam.isEmpty()) {
                 int id = Integer.parseInt(idParam);
                 Task task = taskDao.findTaskByIdAndUser(id, user.getId());

                 if (task != null) {
                     request.setAttribute("task", task);
                     request.getRequestDispatcher("/WEB-INF/editTask.jsp").forward(request, response);
                 } else {
                	 request.getRequestDispatcher("/WEB-INF/tasks.jsp").forward(request, response); // Rediriger vers la liste des tâches si la tâche n'existe pas
                 	}
               }
    	 }
    	 else {
             // Récupérer les paramètres de filtrage et de tri
             String filter = request.getParameter("filter");
             String sortBy = request.getParameter("sortBy");

             // Récupérer la liste des tâches selon les filtres et le tri
             List<Task> tasks = taskDao.getFilteredAndSortedTasks(filter, sortBy, user.getId());

             // Passage des tâches filtrées et triées à la JSP
             request.setAttribute("tasks", tasks);
             request.getRequestDispatcher("/WEB-INF/tasks.jsp").forward(request, response);
         }
	    	//récupération de la liste des tâches
	        List<Task> tasks = taskDao.getAllTasksByUser(user.getId());
	        request.setAttribute("tasks", tasks);
	        request.getRequestDispatcher("/WEB-INF/tasks.jsp").forward(request, response);
	     
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 
    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // Redirection vers la page de connexion si l'utilisateur n'est pas connecté
        	request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }

    	String action = request.getParameter("action");
        
        //Le cas d'ajout d'une tâche
        if ("add".equals(action)) {
            String titre = request.getParameter("titre");
            String description = request.getParameter("description");
            String date = request.getParameter("date_echeance");
            String statut1 = request.getParameter("statut");
            
            Task task = new Task();
            task.setTitre(titre);
            task.setDescription(description);
            
            
            if (date != null && !date.isEmpty()) {
                task.setDate_echeance(Date.valueOf(date)); // Convertir String en Date
            }
            if (statut1 != null && !statut1.isEmpty()) {
                task.setStatut(Task.Statut.valueOf(statut1));
            }
            
            task.setUser(user); // Assigner l'utilisateur à la tâche
            taskDao.saveTask(task);
            System.out.println("je suis là ");
            request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
             
        }
     // Cas de modification d'une tâche
        else if ("edit".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                // Gérer l'erreur de paramètre manquant
                System.out.println("Id est null");
                return;
            }

            int id=0;
            try {
                id = Integer.parseInt(idParam);
            } catch (NumberFormatException e) {
                // Gérer l'erreur de format du paramètre ID
                System.out.println("Erreur de conversion id -> Serv_task(edit)" +e);
                return;
               
            }
            // Récupérer la tâche existante depuis la base de données
            Task existingTask = taskDao.findTaskByIdAndUser(id, user.getId());
            if (existingTask == null) {
                // Gérer l'erreur si la tâche n'existe pas
                System.out.println("la tâche dont l'"+id+"est pas dans la bd");
                return;
            }

            String titre = request.getParameter("titre");
            String description = request.getParameter("description");
            String date = request.getParameter("date_echeance");
            String statut1 = request.getParameter("statut");

            
            
            existingTask.setTitre(titre);
            existingTask.setDescription(description);
            
            if (date != null && !date.isEmpty()) {
            	existingTask.setDate_echeance(Date.valueOf(date)); // Convertir String en Date
            }
            if (statut1 != null && !statut1.isEmpty()) {
            	existingTask.setStatut(Task.Statut.valueOf(statut1));
            }

            taskDao.updateTask(existingTask);
            request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        }
        
        //le cas de la suppression d'une tâche
        else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            taskDao.deleteTask(id, user.getId());
            request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        }
        
    }
}
