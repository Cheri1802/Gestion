package monpackage;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
public class Gestion_note {
	//Singleton
	private static Gestion_note instance;
	public static Gestion_note getInstance() {
		if(instance == null) instance=new  Gestion_note();
		return instance;
	}
	//ATTRIBUTS
			private ArrayList<Etudiant>etudiants;
			private ArrayList<Matiere>matieres;
			private ArrayList<Note>listeNotes;
			
			//DAO couche de persistance JDBC
			private final EtudiantDAO etudiantDAO=new EtudiantDAO();
			private final MatiereDAO matiereDAO=new MatiereDAO();
			private final NoteDAO noteDAO=new NoteDAO();
			//CONSTRUCTEUR
			public Gestion_note() {
				etudiants=new ArrayList<>();
				matieres=new ArrayList<>();
				listeNotes=new ArrayList<>();
			}
			
			//GETTERS ET SETTERS
			public List<Etudiant> getEtudiants() {
				return etudiants;
			}
			public void setEtudiant(List<Etudiant> etudiants) {
				etudiants=new ArrayList<>();
				
			}
			public List<Matiere> getMatieres(){
				return matieres;
			}
			public void setMatieres(List<Matiere> matieres) {
				matieres=new ArrayList<>();
			}
			public List<Note> getNote(){
				return listeNotes;
			}
			public void setNote(List<Note> listeNotes) {
				listeNotes=new ArrayList<>();
			}
			//METHODE POUR RECHERCHER UN ETUDIANT PAR SON MATRICULE
			public Etudiant rechercheEtudiant(String matricule) {
						for(Etudiant e : etudiants) {
							if(e.getMatricule().equals(matricule)) 
								return e;
							}
							return null;
			
			}
						
					
			
					//METHODES POUR RECHERCHER UN ETUDIANT PAR SON NOM
			 public List<Etudiant> rechercherEtudiant(String nom) {
						List<Etudiant>liste_e=new ArrayList<>();
						for(Etudiant e : etudiants) {
							if(e.getNom().equalsIgnoreCase(nom)) {
								liste_e.add(e);
							}
					}
						return liste_e;
			}
					
					
			// METHODES POUR AJOUTER UN ETUDIANTS
			public boolean ajouterEtudiant(Etudiant e) {
				if(rechercheEtudiant(e.getMatricule()) != null) {
					System.out.println("Un etudiant avec ce matricule existe deja : " +e.getMatricule());
					return false;
				}
				etudiants.add(e);
				try {
					etudiantDAO.inserer(e);
				}catch (SQLException ex) {
					System.err.println("[BD] echec insertion etudiant :" +ex.getMessage());
				}
				return true;
				
			}
			
			//METHODES POUR SUPPRIMER UN ETUDIANT
			public boolean supprimerEtudiant(String matricule) {
				Etudiant e=rechercheEtudiant(matricule);
				if(e!=null) {
					etudiants.remove(e);
					try {
						etudiantDAO.supprimer(matricule);
					}catch (SQLException ex) {
						System.err.println("[BD] echec suppression etudiant :" +ex.getMessage());
					}
					return true;
				}else {
					System.out.println("Etudiant non trouve");
					return false;
				}
			 }
			
			//METHODES POUR MODIFIER UN ETUDIANT
			public boolean  modifier_etudiant(String matricule , String NewNom, String NewPrenom,String newClasse) {
				Etudiant e=rechercheEtudiant(matricule);
				if(e!=null) {
					e.setNom(NewNom);
					e.setPrenom(NewPrenom);
					e.setClasse(newClasse);
					try {
						etudiantDAO.mettreAJour(e);
					}catch (SQLException ex) {
						System.err.println("[BD] echec mise a jour  etudiant :" +ex.getMessage());
					}
					return true;
				}else {
					System.out.println("Introuvable");
					return false;
				}
			 }
			
			//METHODES POUR RECHERCHER UNE MATIERE
			
			public Matiere rechercheMatiere(String code) {
						for (Matiere m : matieres) {
							if(m.getCode().equals(code)) {
								return m;
							}
					}
						return null;
			}
					
			
			//METHODES POUR AJOUTER UNE MATIERE
			
			public boolean ajoutermatiere(Matiere m) {
				if(rechercheMatiere(m.getCode()) !=null) {
					System.out.println("Une ùatiere avec ce matricule existe deja : " +m.getCode());
					return false;
				
				}
				matieres.add(m);
				try {
					matiereDAO.inserer(m);
				}catch (SQLException ex) {
					System.err.println("[BD] echec insertion matiere :" +ex.getMessage());
				}
				return true;
			}
			 
			//METHODES POUR MODIFIER UNE MATIERE
			public boolean modifier_matiere(String code ,String newLibelle,int newCoeff) {
				Matiere m=rechercheMatiere(code);
				if(m!=null) {
					m.setLibelle(newLibelle);
					m.setCoefficient(newCoeff);
					try {
						matiereDAO.mettreAJour(m);
					}catch (SQLException ex) {
						System.err.println("[BD] echec mise a jour matiere :" +ex.getMessage());
					}
					return true;
				}else {
					System.out.println("Matiere introuvable");
					return false;
				}
			}
			
			
			//METHODES POUR SUPPRIMER UNE MATIERE
			public boolean supprimer_matiere(String code) {
				Matiere m=rechercheMatiere(code);
				if(m!=null) {
					matieres.remove(m);
					try {
						matiereDAO.supprimer(code);
					}catch (SQLException ex) {
						System.err.println("[BD] echec suppression matiere :" +ex.getMessage());
					}
					return true;
				}else {
					System.out.println("Matiere introuvable");
					return false;
				}
			}
			//METHODES POUR SAISIR LES NOTES 
			public boolean saisirNote(String matricule , String codeMatiere,double noteControle,double noteExamen) {
				
				Etudiant e=rechercheEtudiant(matricule);
				Matiere m =rechercheMatiere(codeMatiere);
				if(e== null || m==null) {
					System.out.println("Erreur de saisie de notes");
					return false;
						
					}
				//SI UNE NOTE EXISTE DEJA POUR CE COUPLE ETUDIANT/MATIERE ON LA MET A JOUR
				Note existante =null;
				for(Note n : e.getListeNotes()) {
					if(n.getMatiere().getCode().equals(codeMatiere)) {
						existante=n;
						break;
					}
					
						
					}
				try {
		            if (existante != null) {
		                // On valide d'abord les DEUX notes ensemble (via un objet temporaire)
		                // avant de modifier quoi que ce soit sur la note existante.
		                new Note(e, m, noteControle, noteExamen);
		                existante.setNoteControle(noteControle);
		                existante.setNoteExamen(noteExamen);
		            } else {
		                Note note = new Note(e, m, noteControle, noteExamen);
		                e.getListeNotes().add(note);
		                listeNotes.add(note);
		            }
		        } catch (IllegalArgumentException ex) {
		            System.out.println("Note invalide : " + ex.getMessage());
		            return false;
		        }
					
					try {
						noteDAO.enregistrer(matricule, codeMatiere, noteControle, noteExamen);
					}catch (SQLException ex) {
						System.err.println("[BD] echec enregistrement note :" +ex.getMessage());
					}
					
				
				
				return true;	
				}
				


	       public Releve_notes genererReleve(String matricule) {
		    Etudiant e=rechercheEtudiant(matricule);
		       if(e==null) {
			      System.out.println("Etudiant introuvable");
			         return null;
			
		          }
		            return new Releve_notes(e,e.getListeNotes(), getMatieres());
	        }


			//Charger toutes les donness depuis MySQL au demarrage
	       public boolean chargerDepuisBD() {
	    	   try {
	    		   matieres=new ArrayList<>(matiereDAO.toutesLesMatieres());
	    		   etudiants=new ArrayList<>(etudiantDAO.tousLesEtudiants());
	    		   for(Etudiant e:etudiants)e.viderNotes();
	    		   listeNotes=new ArrayList<>(noteDAO.toutesLesNotes(etudiants, matieres));
	    		   return true;
	    	   }catch (SQLException ex) {
					System.err.println("[BD] echecdu chargement :" +ex.getMessage());
					return false;
				}
	       }
	       
	       //Retourne tous les etudiants appartenant a la meme classe( comparaison texte , insensible a la classe)
	       public List<Etudiant> getEtudiantParClasse(String classe){
	    	   List<Etudiant> resultat = new ArrayList<>();
	    	   for(Etudiant e : etudiants) {
	    		   if(e.getClasse().equalsIgnoreCase(classe)) {
	    			   resultat.add(e);
	    		   }
	    	   }
	    	   return resultat;
	    	   
	       }
			
			
	}