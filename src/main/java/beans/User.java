package beans;

import java.util.List;

public class User {
	
	private int id;
	private String nom;
	private String password;
	private String role; // "user" ou "admin" 
	
	//OneToMany(mappedBy = "user")
	private List<Task> tasks;
	
	 public User() {
		 
	 }

    public User(int id) {
        this.id = id;
    }

	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	  

}
