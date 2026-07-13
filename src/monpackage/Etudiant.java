package monpackage;
import java.util.ArrayList;
import java.util.List;
public class Etudiant extends Personne {
private String matricule;
private String classe;
private List<Note> listeNotes;

		// CONSTRUCTEUR
public Etudiant(String nom, String prenom , String matricule , String classe){
	super(nom, prenom);
	this.matricule=matricule;
	this.classe=normaliserClasse(classe);
	this.listeNotes=new ArrayList<>();
}
	
	//GETTERS ET SETTERS
	
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule=matricule;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = normaliserClasse(classe);
	}

	// Normalise le nom de la classe : espaces uniformises, casse uniforme
	// pour eviter que "LPTI1", "LPTI 1", "lpti 1" soient traites comme des classes differentes
	private String normaliserClasse(String classe) {
		if (classe == null) return "";
		String c = classe.trim().replaceAll("\\s+", " ").toUpperCase();
		// Insere un espace entre les lettres et les chiffres colles (ex: "LPTI1" -> "LPTI 1")
		c = c.replaceAll("([A-Z])(\\d)", "$1 $2");
		return c;
	}

	public List<Note>getListeNotes(){
		return listeNotes;
	}
	public void ajouterNote(Note note) {
		listeNotes.add(note);
	}
	
	
	@Override
	public void afficherInfos() {
	super.afficherInfos();//APPEL DE LA METHODE DE PERSONNE
	System.out.println("MATRICULE : " + matricule + " , CLASSE : " + classe);
	}
	
	public void ajouterote(Note note) {
		listeNotes.add(note);
	}
	
	//Utiliséé pour vider les notes avant de les recharger depuis la base
	public void viderNotes() {
		listeNotes.clear();
	}
	

}
