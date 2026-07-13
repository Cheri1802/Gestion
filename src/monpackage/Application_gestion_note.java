package monpackage;
import java.util.*;
public class Application_gestion_note {
    
	public static void main(String[] args) {
		// creation du gestionnaire
				Gestion_note gestion= new Gestion_note();
				
				// Creation des matieres
				Matiere algebre = new Matiere("101" , "Agebre 1 " , 3,1);
				Matiere ALGO =new Matiere("102", "Algorithme et SD 1", 2,1);
				Matiere Analyse = new Matiere("103", "Analyse 1",2,1 );
				Matiere Base_Info=new Matiere("104" , "BASE INFORMATIQUE", 1, 1);
				Matiere Ang=new Matiere("105", "Anglais generale", 1,1);
				Matiere CE=new Matiere("106", "Circuit_Electriques" ,4,1);
				Matiere EMS=new Matiere("107","Electro_Magneto statique",4,1);
				Matiere ET=new Matiere("108","Environnement telecom",1,1);
				Matiere ITA =new Matiere("109","Initiation Telecom et Art",1,1);
				Matiere MG=new Matiere("110","Mecanique generale",2,1);
				Matiere droit =new Matiere("111","Notion de droit",1,1);
				Matiere Optique=new Matiere("112","Optique",2,1);
				Matiere P=new Matiere("113","Programmation 1",2,1);
				Matiere TEEO =new Matiere("114","Francais",1,1);
				
				
				Matiere algeb = new Matiere("115" , "Agebre2 " , 3,2);
				Matiere SI=new Matiere("116" ,"Science Ingenieur",2,2);
				Matiere SL=new Matiere("117","Systeme Logique",2,2);
				Matiere T=new Matiere("118","Thermodynamique",3,2);
				Matiere EA=new Matiere("119","Electronique Analogique",3,2);
				Matiere Analys = new Matiere("120", "Analyse2",2,2 );
				Matiere An=new Matiere("121", "Anglais technique", 2,2);
				Matiere ALG =new Matiere("123", "Algorithme et SD 2", 3,2);
				Matiere dr =new Matiere("122","Droit des TIC",1,2);
				Matiere Pr=new Matiere("133","Programmation 2 ",2,2);
				
				
				
				
				//Matiere Test=new Matiere("120","TESTER",1);
					//Ajout des matieres	
				gestion.ajoutermatiere(algebre);
				gestion.ajoutermatiere(ALGO);
				gestion.ajoutermatiere(Analyse);
				gestion.ajoutermatiere(Base_Info);
				gestion.ajoutermatiere(Ang);
				gestion.ajoutermatiere(CE);
				gestion.ajoutermatiere(EMS);
				gestion.ajoutermatiere(ET);
				gestion.ajoutermatiere(ITA);
				gestion.ajoutermatiere(MG);
				gestion.ajoutermatiere(droit);
				gestion.ajoutermatiere(Optique);
				gestion.ajoutermatiere(P);
				gestion.ajoutermatiere(TEEO);
				
				gestion.ajoutermatiere(algeb);
				gestion.ajoutermatiere(Analys);
				gestion.ajoutermatiere(An);
				gestion.ajoutermatiere(dr);
				gestion.ajoutermatiere(Pr);
				gestion.ajoutermatiere(EA);
				gestion.ajoutermatiere(SI);
				gestion.ajoutermatiere(SL);
				gestion.ajoutermatiere(T);
				gestion.ajoutermatiere(ALG);
				//gestion.ajoutermatiere(Test);
				
				//Modifier une matiere
				//gestion.modifier_matiere("101", "MATHS", 4);
				
				//Supprimer une matiere
				//gestion.supprimer_matiere("120");
				

				
				
				//Creation  des etudiant;
				Etudiant e1 = new Etudiant( "Khaled", "Cherifa", "5000","CPI 1");
				Etudiant e2=new Etudiant("MAHAMAN","Lawan","5050","LPTI 1");
				Etudiant e3=new Etudiant("Mahaman","Mariane","5060","DTS");
				
				//Ajouter des etudiant
				gestion.ajouterEtudiant(e1);
				gestion.ajouterEtudiant(e2);
				gestion.ajouterEtudiant(e3);
				
				
				// Modifier un etudiant
				//gestion.modifier_etudiant("5000", "FATOU", "IBRA", "CPI1");
				
				//SUPPRIMER UN ETUDIANT
				//gestion.supprimerEtudiant("5000");
				
				//Rechercher un etudiant par nom
				List<Etudiant> etudiants=gestion.rechercherEtudiant("MAHAMAN");
				if(etudiants.isEmpty()) {
					System.out.println("Aucun etudiant trouvé");
					System.out.println();
					
				}else {
					System.out.println("Etudiant trouvé(s) :");
					for(Etudiant e:etudiants) {
						
						e.afficherInfos();
						System.out.println();
					}
				}
				
				//Rechercher un etudiant par matricule
				Etudiant e=gestion.rechercheEtudiant("5050");
				if(e!=null) {
					System.out.print("Etudiant trouvé :");
					e.afficherInfos();
					System.out.println();
				}else {
					System.out.println("Etudiant introuvable");
				}
				
				
				 //SAISIE DES NOTES  S1
				gestion.saisirNote("5000", "101", 13.0, 13.0);
				gestion.saisirNote("5000", "102", 14.5, 14.5);
				gestion.saisirNote("5000", "103",14.25 ,14.25);
				gestion.saisirNote("5000", "104",16.5 ,16.5);
				gestion.saisirNote("5000", "105", 18.5, 18.5);
				gestion.saisirNote("5000", "106",13.0 , 13.0);
				gestion.saisirNote("5000", "107", 14.0, 14.0);
				gestion.saisirNote("5000", "108",12.0 ,12.0);
				gestion.saisirNote("5000", "109", 17.5,17.5 );
				gestion.saisirNote("5000", "110", 15.0,15.0 );
				gestion.saisirNote("5000", "111", 16.25, 16.25);
				gestion.saisirNote("5000", "112", 16, 16);
				gestion.saisirNote("5000", "113", 16.75,16.75 );
				gestion.saisirNote("5000", "114",14.0 ,14.0);
				
				
				//gestion.saisirNote("5050", "101", 11.0, 11.0);
				//gestion.saisirNote("5050", "102", 11.0, 11.0);
				
				
				//POUR S2
				
				gestion.saisirNote("5000", "115", 11, 11);
				gestion.saisirNote("5000", "116", 18.50,18.50 );
				gestion.saisirNote("5000", "117", 14.5, 14.5);
				gestion.saisirNote("5000", "118",11.5 ,11.5);
				gestion.saisirNote("5000", "119", 10, 14);
				gestion.saisirNote("5000", "120",17 ,17 );
				gestion.saisirNote("5000", "121", 18, 18);
				gestion.saisirNote("5000", "122", 19, 19);
				gestion.saisirNote("5000", "123", 15.75, 15.75);
				gestion.saisirNote("5000", "133", 16.50,16.50 );
				
				
				
				
				
				
				// Generation du releve de notes
				Releve_notes releve=gestion.genererReleve("5000");//RECUPERER L ETUDIANT
				
				if(releve !=null) {
					System.out.print("Releve de note de : ");
					e1.afficherInfos();
					System.out.println();
					
					//SEMESTRE 1
					System.out.println("=====Semestre 1=====");
					//MOYENNE PAR MATIERE
					for(Matiere mat : gestion.getMatieres()) {
						if(mat.getSemestre()==1) {
							double moyenne_mat=releve.calculer_moyenne_matiere(mat);
							System.out.printf("Matiere : %s | Moyenne : %.2f%n" , mat.getLibelle(),moyenne_mat );
							
							
							
					}
					
				}
						double moyenne_semestre1=releve.calculer_moyenne_semestre(1);
						System.out.printf("Moyenne S1 : %.2f%n " ,moyenne_semestre1);
					
						//SEMESTRE 2
						System.out.println("=====Semestre 2=====");
						//MOYENNE PAR MATIERE
						for(Matiere mat : gestion.getMatieres()) {
							if(mat.getSemestre()==2) {
								double moyenne_mat=releve.calculer_moyenne_matiere(mat);
								System.out.printf("Matiere : %s | Moyenne : %.2f%n" , mat.getLibelle(),moyenne_mat );
								
								
								
							 }
						
						}
								double moyenne_semestre2=releve.calculer_moyenne_semestre(2);
								System.out.printf("Moyenne S2 : %.2f%n" ,moyenne_semestre2);
								System.out.println();
					
								//MOYENNE GENEREALE
								double moyenne_generale=releve.calculerMoyenne();
								System.out.printf("Moyenne generale : %.2f%n" ,moyenne_generale );
								System.out.println();
								//MENTION
								
								System.out.println("Mention : " + releve.genererMention());
					
				}
				
				
				
				
				
		        
				

				
			}
			
			
			
			
			
			  
}
			
		


