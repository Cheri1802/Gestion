package monpackage;

public class Matiere {
	//ATTRIBUTS
	private String code;
	private String libelle;
	private int coefficient;
	private int semestre;
     //CONSTRUCTEURS
	public Matiere(String code,String libelle , int coefficient ,int semestre) {
		this.code=code;
		this.libelle=libelle;
		this.coefficient=coefficient;
		this.semestre=semestre;
	}
	//GETTERS ET SETTERS
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code=code;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle=libelle;
	}
	public int getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(int coefficient) {
		this.coefficient=coefficient;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int semestre) {
		this.semestre=semestre;
	}

}
