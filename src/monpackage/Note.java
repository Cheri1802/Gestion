package monpackage;

public class Note {
	//ATTRIBUTS
private Etudiant etudiant;
private Matiere matiere;
private double noteControle;
private double noteExamen;

//CONSTRUCTEUR

    public Note(Etudiant etudiant , Matiere matiere , double noteControle, double noteExamen) {
    	validerNote(noteControle,"Note de controle");
    	validerNote(noteExamen,"Note d examen");
    	
	this.etudiant=etudiant;
	this.matiere=matiere;
	this.noteControle=noteControle;
	this.noteExamen=noteExamen;
	
	}
    
    //verification des notes
    private void validerNote(double valeur , String nomChamp) {
    	if(valeur < 0 || valeur>20) {
    		throw new IllegalArgumentException(nomChamp + " doit etre comprise entre 0 et 20 (recue : " + valeur+")");
    	}
    }
	
	// GETTERS ET SETTERS
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant=etudiant;
	}
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere=matiere;
	}
	public double getNoteControle() {
		return noteControle;
	}
	public void setNoteControle(double noteControle) {
		validerNote(noteControle , "Note de controle");
		this.noteControle=noteControle;
	}
	public double getNoteExamen() {
		return noteExamen;
	}
	public void setNoteExamen(double noteExamen) {
		validerNote(noteExamen , "Note d examen");
		this.noteExamen=noteExamen;
	}
	
	public double calculerMoyenne() {
	return (noteControle+noteExamen)/2;
	}
	

}
