package monpackage;

import java.util.ArrayList;
import java.util.List;

public class Releve_notes extends Evaluation {
	// ATTRIBUTS
		private Etudiant etudiant;
		private List<Note> listeNotes;
		private List<Matiere>matieres;
		
		// CONSTRUCTEUR
		
	public Releve_notes(Etudiant etudiant,List<Note> listeNotes, List<Matiere>matieres) {
		this.etudiant=etudiant;
		this.listeNotes=listeNotes;
		this.matieres=matieres;
		
		}
		//GETTERS ET SETTERS
	public Etudiant getEtudiant() {
		return etudiant;
		}
	public void setEtudiant(Etudiant etudiant) {
	    this.etudiant=etudiant;
	    		 
	    }
	 public List<Note>getListeNotes(){
	 		return listeNotes;
	 	}
	 public void setNote(List<Note> listeNotes) {
	    	 this.listeNotes=new ArrayList<>();
	    }
	 public List<Matiere>getMatieres(){
		 return matieres;
	 }
	 
	      //METHODE POUR ARRONDIR LES VALEURS
	      protected double arrondir(double valeur) {
	    	  return Math.round(valeur*100.0)/100.0;
	      }
	 
	 
	 
	// METHODE POUR CALCULER LA MOYENNE PAR MATIERE
		  public double calculer_moyenne_matiere(Matiere matiere) {
			double note_total=0;
			int nb_note=0;
			    //Parcours de la liste de notes de l etudiant
				for(Note n : listeNotes) {
					//Verifie si la note appartient a la matiere demandée
					if(n.getMatiere().getCode().equals(matiere.getCode())) {
						//Aditionne la moyenne simple
						double moyenne=(n.getNoteControle()+n.getNoteExamen())/2.0;
					          note_total+=moyenne;
						     nb_note++; 
					
				      }
			     }
			                if(nb_note==0) {
					         return 0; // Aucune note trouvée 
					         }
			                
				        return arrondir(note_total/nb_note); //Moyenne finale pour cette matiere
		   }
		  
		  
		  
		  
		  
		  
		//MOYENNE PAR SEMESTRE
	      public double calculer_moyenne_semestre(int semestre) {
			double note_total = 0;
			int coefficient_total=0;
			for(Matiere m : matieres) {
				
				if(m.getSemestre()==semestre) {
					double moy=calculer_moyenne_matiere(m);
					int coeff= m.getCoefficient();
					note_total+=moy*coeff;
					coefficient_total +=coeff;
			     } 
							
			  }
			           if(coefficient_total==0) 
					        return 0;	 
		                                 
			         return arrondir(note_total/coefficient_total);
				
			
	        }
	 
	 
	 
	 
	     
	     //MOYENNE ANNUELLE OU GENERALE
	     @Override
	  public double calculerMoyenne() {
	    	 double moyenne_s1=calculer_moyenne_semestre(1);
	 		double moyenne_s2=calculer_moyenne_semestre(2);
	 		return arrondir(moyenne_s1+moyenne_s2)/2;
	 		
	 		
	 		
		
	   }

	  
	    //MENTION
		public String genererMention() {
				if(calculerMoyenne()>=10 ) {
					return "ADMIS";
				 }
				    else 
				    {
					return "AJOURNE";
				    }
				
					
	     }
		
			
}
		
