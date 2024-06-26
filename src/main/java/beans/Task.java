package beans;

import java.io.Serializable;
import java.sql.Date;


public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int id;
    protected String titre;
    protected String description;
    protected Date date_echeance;
    protected Statut statut;
    
    private User user;
    
    // Déclaration de l'enum Statut
    public enum Statut {
        EN_COURS,
        EN_ATTENTE,
        TERMINE;
    }
    
    public Date getDate_echeance() {
        return date_echeance;
    }
    public void setDate_echeance(Date date_echeance) {
        this.date_echeance = date_echeance;
    }
    public Statut getStatut() {
        return statut;
    }
    public void setStatut(Statut statut) {
        this.statut = statut;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	} 


}
