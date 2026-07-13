package monpackage;

public class Personne {
	private String nom;
	private String prenom;
	
	    //CONSTRUCTEUR
    public Personne(String nom, String prenom){
	this.nom=nom;
	this.prenom=prenom;
	
    }

     // GETTERS ET SETTERS
    public String getNom() {
	      return nom;
    }
    public void setNom(String nom){
	    this.nom=nom;
    }
    public String getPrenom() {
	return prenom;
    }
    public void setPrenom(String prenom) {
	this.prenom=prenom;
    }

    	//Methode pour afficher les informations d'un etudiant
    public void afficherInfos() {
	System.out.println(" NOM  : " + nom + " , PRENOM : " + prenom);
    }

}
