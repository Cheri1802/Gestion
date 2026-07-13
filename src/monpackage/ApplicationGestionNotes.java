package monpackage;

import java.awt.*;
import java.util.*;
import java.awt.event.*;


public class ApplicationGestionNotes extends Frame implements ActionListener {

    // Palette de couleurs
    private static final Color VIOLET       = new Color(147, 122, 219);
    private static final Color VIOLET_CLAIR       = new Color(237, 230, 250);
    private static final Color VIOLET_FONCE= new Color(90, 60, 150);
    private static final Color BLEU         = new Color(27, 63, 148);
    private static final Color BLEU_CLAIR   = new Color(225, 233, 250);

    private final Gestion_note gestion;
    private Etudiant etudiantSelectionne;

    private MenuItem miNouvelEtudiant, miModifierEtudiant, miSupprimerEtudiant, miQuitter;
    private MenuItem miNouvelleMatiere, miModifierMatiere, miSupprimerMatiere;
    private MenuItem miSaisirNote;
    private MenuItem miAPropos;
    private MenuItem miRechercherEtudiant;
    private MenuItem miRechercherMatiere;
    private MenuItem miListerMatiere;
    private MenuItem miClassementClasse;
    

    private java.awt.List listeEtudiants,listeMatieres;
    private Label labelMatricule, labelNomPrenom, labelClasse, labelStatut;
    private TextArea taReleve;

    public ApplicationGestionNotes() {
        super("Gestion des Notes — Application");
        gestion = Gestion_note.getInstance();

        construireMenus();
        construireInterface();

        boolean ok = gestion.chargerDepuisBD();
        if (!ok) {
            System.out.println("Attention : impossible de charger depuis MySQL.");
        }
        rafraichirListeEtudiants();

        setSize(980, 640);
        centrerSurEcran();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ConnexionBD.fermer();
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // ─── Menus ────────────────────────────────────────────────
    private void construireMenus() {
        MenuBar barre = new MenuBar();

        Menu mEtudiant = new Menu("Etudiant");
        miNouvelEtudiant    = new MenuItem("Nouvel etudiant", new MenuShortcut(KeyEvent.VK_N));
        miModifierEtudiant  = new MenuItem("Modifier l'etudiant");
        miSupprimerEtudiant = new MenuItem("Supprimer l'etudiant");
        miRechercherEtudiant = new MenuItem("Rechercher un etudiant", new MenuShortcut(KeyEvent.VK_F));
        miQuitter           = new MenuItem("Quitter", new MenuShortcut(KeyEvent.VK_Q));

        miNouvelEtudiant.addActionListener(this);
        miModifierEtudiant.addActionListener(this);
        miSupprimerEtudiant.addActionListener(this);
        miRechercherEtudiant.addActionListener(this);
        miQuitter.addActionListener(this);

        mEtudiant.add(miNouvelEtudiant);
        mEtudiant.add(miModifierEtudiant);
        mEtudiant.add(miSupprimerEtudiant);
        mEtudiant.add(miRechercherEtudiant);
        mEtudiant.addSeparator();
        mEtudiant.add(miQuitter);

        Menu mMatiere = new Menu("Matiere");
        miNouvelleMatiere  = new MenuItem("Nouvelle matiere", new MenuShortcut(KeyEvent.VK_M));
        miModifierMatiere  = new MenuItem("Modifier une matiere", new MenuShortcut(KeyEvent.VK_K));
        miSupprimerMatiere = new MenuItem("Supprimer une matiere");
        miRechercherMatiere = new MenuItem("Rechercher une matiere", new MenuShortcut(KeyEvent.VK_F));
        miListerMatiere		=new  MenuItem("Liste des matieres");

        miNouvelleMatiere.addActionListener(this);
        miModifierMatiere.addActionListener(this);
        miSupprimerMatiere.addActionListener(this);
        miRechercherMatiere.addActionListener(this);
        miListerMatiere.addActionListener(this);
       

        mMatiere.add(miNouvelleMatiere);
        mMatiere.add(miModifierMatiere);
        mMatiere.add(miSupprimerMatiere);
        mMatiere.add(miRechercherMatiere);
        mMatiere.addSeparator();
        mMatiere.add(miListerMatiere);

        Menu mNotes = new Menu("Notes");
        miSaisirNote = new MenuItem("Saisir une note", new MenuShortcut(KeyEvent.VK_S));
        miSaisirNote.addActionListener(this);
        mNotes.add(miSaisirNote);
        
        Menu mStatistiques =new Menu("Statistiques");
        miClassementClasse = new MenuItem("Classement par classe");
        miClassementClasse.addActionListener(this);
        mStatistiques.add(miClassementClasse);
        
        
        

        Menu mAide = new Menu("Aide");
        miAPropos = new MenuItem("A propos...");
        miAPropos.addActionListener(this);
        mAide.add(miAPropos);

        barre.add(mEtudiant);
        barre.add(mMatiere);
        barre.add(mNotes);
        barre.add(mStatistiques);
        barre.add(mAide);
        setMenuBar(barre);
    }

    // ─── Interface generale ─────────────────────────────────────
    private void construireInterface() {
        setLayout(new BorderLayout(5, 5));
        add(construirePanelGauche(), BorderLayout.WEST);
        add(construirePanelCentre(), BorderLayout.CENTER);
        add(construireBandeauStatut(), BorderLayout.SOUTH);
    }

    private Panel construirePanelGauche() {
        Panel panel = new PanelDegrade (new BorderLayout(0, 5),VIOLET_CLAIR,VIOLET_FONCE);
       

        Label titre = new Label("  Etudiants", Label.LEFT);
        titre.setFont(new Font("SansSerif", Font.BOLD, 14));
        //titre.setBackground(VIOLET);
        titre.setForeground(Color.BLACK);

        listeEtudiants = new java.awt.List(16, false);
       
      
        listeEtudiants.setFont(new Font("Monospaced", Font.BOLD, 11));
        listeEtudiants.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) selectionnerEtudiant();
        });
        Panel panelBoutons = new Panel(new GridLayout(2,2,4,4));
        panelBoutons.setBackground(new Color(235,245,255));
        
        Button btnAjouterE = creerBouton("+ Etudiant", new Color(0,150,60));
        Button btnSupprimerE = creerBouton("- Etudiant", new Color(180,40,40));
        Button btnRechercheE   = creerBouton("Recherche Etudiant",new Color(0,100,180));
        Button btnAjouterM = creerBouton("+ Matiere",new Color(100,80,160));
        Button btnSupprimerM = creerBouton("- Matiere",new Color(0, 80 , 160));
        Button btnRechercherM = creerBouton("Recherche Matiere",new Color(0, 80 , 160));
        
        btnAjouterE.addActionListener(e -> creerNouvelEtudiant());
        btnSupprimerE.addActionListener(e ->supprimerEtudiant());
        btnRechercheE.addActionListener(e   -> rechercherEtudiant());
        btnAjouterM.addActionListener(e  -> creerNouvelleMatiere());
        btnSupprimerM.addActionListener(e -> supprimerMatiere());
        btnRechercherM.addActionListener(e -> rechercherMatiere());
        
        panelBoutons.add(btnAjouterE);     panelBoutons.add(btnAjouterM);
        panelBoutons.add(btnSupprimerE);   panelBoutons.add(btnSupprimerM);
        panelBoutons.add(btnRechercheE);   panelBoutons.add(btnRechercherM);
        

        panel.add(titre, BorderLayout.NORTH);
        panel.add(listeEtudiants, BorderLayout.WEST);
        panel.add(panelBoutons, BorderLayout.SOUTH);
        return panel;
    
        
        
        
    }
   
    

    private Panel construirePanelCentre() {
        Panel panel = new Panel(new BorderLayout(0, 5));

        Panel panelInfo = new PanelDegrade(new GridLayout(3, 1, 0, 2),VIOLET_CLAIR,VIOLET_FONCE);
        //panelInfo.setBackground(BLEU_CLAIR);

        labelMatricule = new Label("Selectionnez un etudiant", Label.CENTER);
        labelNomPrenom = new Label("", Label.CENTER);
        labelClasse    = new Label("", Label.CENTER);
        labelMatricule.setFont(new Font("SansSerif", Font.BOLD, 13));
        labelMatricule.setForeground(BLEU);

        panelInfo.add(labelMatricule);
        panelInfo.add(labelNomPrenom);
        panelInfo.add(labelClasse);

        taReleve = new TextArea("", 10, 60, TextArea.SCROLLBARS_VERTICAL_ONLY);
        taReleve.setFont(new Font("Monospaced", Font.PLAIN, 12));
        taReleve.setEditable(false);

        panel.add(panelInfo, BorderLayout.NORTH);
        panel.add(taReleve, BorderLayout.CENTER);
        return panel;
    }

    private Panel construireBandeauStatut() {
        Panel panel = new Panel(new BorderLayout());
        panel.setBackground(VIOLET_FONCE);
        labelStatut = new Label("", Label.LEFT);
        labelStatut.setForeground(Color.WHITE);
        panel.add(labelStatut, BorderLayout.CENTER);
        return panel;
    }

    private void centrerSurEcran() {
        Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((ecran.width - getWidth()) / 2, (ecran.height - getHeight()) / 2);
    }

    // ─── Rafraichissement de l'affichage ────────────────────────
    private void rafraichirListeEtudiants() {
        listeEtudiants.removeAll();
        java.util.List<Etudiant> etudiantsTries = new ArrayList<>(gestion.getEtudiants());
        etudiantsTries.sort((e1,e2) ->e1.getNom().compareToIgnoreCase(e2.getNom()));
                 
        for (Etudiant e : etudiantsTries) {
            listeEtudiants.add(e.getMatricule() + "  " + e.getNom() + " " + e.getPrenom());
        }
        mettreAJourStatut();
    }

    private void mettreAJourStatut() {
        labelStatut.setText("  " + gestion.getEtudiants().size() + " etudiant(s)  |  "
            + gestion.getMatieres().size() + " matiere(s)");
    }

    private void selectionnerEtudiant() {
        String sel = listeEtudiants.getSelectedItem();
        if (sel == null) return;
        String matricule = sel.trim().split("\\s+")[0];
        etudiantSelectionne = gestion.rechercheEtudiant(matricule);
        afficherInfoEtudiant();
        afficherReleve();
    }

    private void afficherInfoEtudiant() {
        if (etudiantSelectionne == null) return;
        labelMatricule.setText("Matricule : " + etudiantSelectionne.getMatricule());
        labelNomPrenom.setText(etudiantSelectionne.getNom() + " " + etudiantSelectionne.getPrenom());
        labelClasse.setText("Classe : " + etudiantSelectionne.getClasse());
    }

    private void afficherReleve() {
        if (etudiantSelectionne == null) return;
        Releve_notes releve = gestion.genererReleve(etudiantSelectionne.getMatricule());
        if (releve == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("===== Semestre 1 =====\n");
        for (Matiere m : gestion.getMatieres()) {
            if (m.getSemestre() == 1) {
                sb.append(String.format("%-30s | Moyenne : %5.2f%n", m.getLibelle(), releve.calculer_moyenne_matiere(m)));
            }
        }
        sb.append(String.format("Moyenne S1 : %.2f%n%n", releve.calculer_moyenne_semestre(1)));

        sb.append("===== Semestre 2 =====\n");
        for (Matiere m : gestion.getMatieres()) {
            if (m.getSemestre() == 2) {
                sb.append(String.format("%-30s | Moyenne : %5.2f%n", m.getLibelle(), releve.calculer_moyenne_matiere(m)));
            }
        }
        sb.append(String.format("Moyenne S2 : %.2f%n%n", releve.calculer_moyenne_semestre(2)));
        sb.append(String.format("Moyenne generale : %.2f%n", releve.calculerMoyenne()));
        sb.append("Mention : ").append(releve.genererMention()).append("\n\n");

        // ★ Classement dans la classe
        sb.append(construireTexteClassement());

        taReleve.setText(sb.toString());
    }

    private String construireTexteClassement() {
        String classe = etudiantSelectionne.getClasse();
        java.util.List<Etudiant> etudiantsClasse = gestion.getEtudiantParClasse(classe);

        if (etudiantsClasse.size() <= 1) {
            return "===== Classement (" + classe + ") =====\n"
                 + "Un seul etudiant dans cette classe, pas de classement possible.\n";
        }

        Map<Etudiant, Double> moyennes = new HashMap<>();
        for (Etudiant e : etudiantsClasse) {
            Releve_notes r = gestion.genererReleve(e.getMatricule());
            moyennes.put(e, (r != null) ? r.calculerMoyenne() : 0.0);
        }

        java.util.List<Etudiant> classement = new ArrayList<>(etudiantsClasse);
        classement.sort((e1, e2) -> Double.compare(moyennes.get(e2), moyennes.get(e1)));

      double moyenneSelectionne = moyennes.get(etudiantSelectionne);
      int rang = 1;
      for(Etudiant e : classement) {
    	  if(moyennes.get(e)>moyenneSelectionne) rang++;
      }
        Etudiant meilleur   = classement.get(0);
        Etudiant plusFaible = classement.get(classement.size() - 1);

        StringBuilder sb = new StringBuilder();
        sb.append("===== Classement (" + classe + ") =====\n");
        sb.append("Rang : ").append(rang).append(" / ").append(classement.size()).append("\n");
        sb.append(String.format("Meilleure moyenne  : %s %s (%.2f)%n",
            meilleur.getNom(), meilleur.getPrenom(), moyennes.get(meilleur)));
        sb.append(String.format("Plus faible moyenne : %s %s (%.2f)%n",
            plusFaible.getNom(), plusFaible.getPrenom(), moyennes.get(plusFaible)));

        return sb.toString();
    }

    private void afficherClassementComplet() {
        String classeSaisie = demanderTexte("Classement par classe", "Nom de la classe :");
        if (classeSaisie == null || classeSaisie.trim().isEmpty()) return;
        String classe = classeSaisie.trim().replaceAll("\\s+", " ").replaceAll("([A-Z])(\\d)", "$1 $2");

        java.util.List<Etudiant> etudiantsClasse = gestion.getEtudiantParClasse(classe);
        if (etudiantsClasse.isEmpty()) {
            afficherMessage("Classement", "Aucun etudiant trouve dans la classe : " + classe);
            return;
        }

        Map<Etudiant, Double> moyennes = new HashMap<>();
        for (Etudiant e : etudiantsClasse) {
            Releve_notes r = gestion.genererReleve(e.getMatricule());
            moyennes.put(e, (r != null) ? r.calculerMoyenne() : 0.0);
        }

        java.util.List<Etudiant> classement = new ArrayList<>(etudiantsClasse);
        classement.sort((e1, e2) -> Double.compare(moyennes.get(e2), moyennes.get(e1)));

        StringBuilder sb = new StringBuilder("Classement de la classe " + classe + " :\n\n");
        int rang = 1;
        for (Etudiant e : classement) {
            sb.append(String.format("%2d.  %-25s Moyenne : %5.2f%n",
                rang, e.getNom() + " " + e.getPrenom(), moyennes.get(e)));
            rang++;
        }
        double moyenneClasse = moyennes.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        sb.append(String.format("%nMoyenne de la classe : %.2f%n", moyenneClasse));

        afficherMessage("Classement — " + classe, sb.toString());
    }
    
  

    // ─── Dialogue : nouvel etudiant ─────────────────────────────
    private void creerNouvelEtudiant() {
        Dialog dlg = new Dialog(this, "Nouvel etudiant", true);
        dlg.setLayout(new GridLayout(5, 2, 8, 8));

        dlg.add(new Label("Nom :"));
        TextField tfNom = new TextField(15);
        dlg.add(tfNom);

        dlg.add(new Label("Prenom :"));
        TextField tfPrenom = new TextField(15);
        dlg.add(tfPrenom);

        dlg.add(new Label("Matricule :"));
        TextField tfMatricule = new TextField(15);
        dlg.add(tfMatricule);

        dlg.add(new Label("Classe :"));
        TextField tfClasse = new TextField(15);
        dlg.add(tfClasse);

        Button btnValider = creerBouton("Valider", VIOLET_FONCE);
        Button btnAnnuler = creerBouton("Annuler", VIOLET_CLAIR);
        dlg.add(btnValider);
        dlg.add(btnAnnuler);

        btnValider.addActionListener(ev -> {
            String nom = tfNom.getText().trim();
            String matricule = tfMatricule.getText().trim();
            if (nom.isEmpty() || matricule.isEmpty()) {
                afficherMessage("Erreur" , "Le nom et le matricule sont obligatoires.");
                return;
                
            }
            Etudiant e = new Etudiant(nom, tfPrenom.getText().trim(), matricule, tfClasse.getText().trim());
            boolean ok = gestion.ajouterEtudiant(e);
            if (ok) {
                rafraichirListeEtudiants();
                dlg.dispose();
                afficherMessage(" ","L etudiant est ajouter avec sucess.");
            } else {
            	afficherMessage("Erreur ","Un etudiant avec ce matricule existe deja.");
            }
        });
        

        btnAnnuler.addActionListener(ev -> dlg.dispose());
       

        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
        
    }

    // ─── Dialogue : modifier etudiant ────────────────────────────
    private void modifierEtudiant() {
        if (!verifierSelectionEtudiant()) return;
        Dialog dlg = new Dialog(this, "Modifier " + etudiantSelectionne.getMatricule(), true);
        dlg.setLayout(new GridLayout(4, 2, 8, 8));

        dlg.add(new Label("Nom :"));
        TextField tfNom = new TextField(etudiantSelectionne.getNom(), 15);
        dlg.add(tfNom);

        dlg.add(new Label("Prenom :"));
        TextField tfPrenom = new TextField(etudiantSelectionne.getPrenom(), 15);
        dlg.add(tfPrenom);

        dlg.add(new Label("Classe :"));
        TextField tfClasse = new TextField(etudiantSelectionne.getClasse(), 15);
        dlg.add(tfClasse);

        Button btnValider = creerBouton("Valider", VIOLET_FONCE);
        Button btnAnnuler = creerBouton("Annuler", VIOLET_CLAIR);
        dlg.add(btnValider);
        dlg.add(btnAnnuler);

        btnValider.addActionListener(ev -> {
            gestion.modifier_etudiant(etudiantSelectionne.getMatricule(), tfNom.getText().trim(),
                tfPrenom.getText().trim(), tfClasse.getText().trim());
            rafraichirListeEtudiants();
            afficherInfoEtudiant();
            dlg.dispose();
            afficherMessage(" ","L etudiant est modifier avec sucess.");
            
        });
        btnAnnuler.addActionListener(ev -> dlg.dispose());

        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
       
    }

    // ─── Suppression etudiant ─────────────────────────────────────
    private void supprimerEtudiant() {
        if (!verifierSelectionEtudiant()) return;
        boolean ok = gestion.supprimerEtudiant(etudiantSelectionne.getMatricule());
        if (ok) {
            etudiantSelectionne = null;
            taReleve.setText("");
            labelMatricule.setText("Selectionnez un etudiant");
            labelNomPrenom.setText("");
            labelClasse.setText("");
            rafraichirListeEtudiants();
            afficherMessage(" ","L etudiant est supprimer avec sucess.");
        }
    }
    private void rechercherEtudiant() {
        String terme = demanderTexte("Rechercher un etudiant", "Nom ou matricule :");
        if (terme == null || terme.trim().isEmpty()) return;
        terme = terme.trim();

        // On cherche d'abord par matricule exact, sinon par nom (partiel, insensible a la casse)
        Etudiant parMatricule = gestion.rechercheEtudiant(terme);
        java.util.List<Etudiant> resultats;
        if (parMatricule != null) {
            resultats = new ArrayList<>();
            resultats.add(parMatricule);
        } else {
            resultats = gestion.rechercherEtudiant(terme);
        }

        if (resultats.isEmpty()) {
            afficherMessage("Recherche", "Aucun etudiant trouve pour : " + terme);
            return;
        }

        StringBuilder sb = new StringBuilder("Resultats pour \"" + terme + "\" :\n\n");
        for (Etudiant e : resultats) {
            sb.append(e.getMatricule()).append("  ").append(e.getNom()).append(" ").append(e.getPrenom())
              .append("  (").append(e.getClasse()).append(")\n");
        }
        afficherMessage("Resultats de recherche", sb.toString());

        // Si un seul resultat, on le selectionne directement dans la liste principale
        if (resultats.size() == 1) {
            etudiantSelectionne = resultats.get(0);
            afficherInfoEtudiant();
            afficherReleve();
        }
        
        
    }
    


    
	// ─── Dialogue : nouvelle matiere ─────────────────────────────
    private void creerNouvelleMatiere() {
        Dialog dlg = new Dialog(this, "Nouvelle matiere", true);
        dlg.setLayout(new GridLayout(5, 2, 8, 8));

        dlg.add(new Label("Code :"));
        TextField tfCode = new TextField(10);
        dlg.add(tfCode);

        dlg.add(new Label("Libelle :"));
        TextField tfLibelle = new TextField(20);
        dlg.add(tfLibelle);

        dlg.add(new Label("Coefficient :"));
        TextField tfCoeff = new TextField("1");
        dlg.add(tfCoeff);

        dlg.add(new Label("Semestre (1 ou 2) :"));
        TextField tfSemestre = new TextField("1");
        dlg.add(tfSemestre);

        Button btnValider = creerBouton("Valider", VIOLET_FONCE);
        Button btnAnnuler = creerBouton("Annuler", VIOLET_CLAIR);
        dlg.add(btnValider);
        dlg.add(btnAnnuler);

        btnValider.addActionListener(ev -> {
            try {
                String code = tfCode.getText().trim();
                if (code.isEmpty() || gestion.rechercheMatiere(code) != null) {
                	afficherMessage("Erreur ","Code de matiere invalide ou deja existant.");
                    return;
                }
                Matiere m = new Matiere(code, tfLibelle.getText().trim(),
                    Integer.parseInt(tfCoeff.getText().trim()), Integer.parseInt(tfSemestre.getText().trim()));
                gestion.ajoutermatiere(m);
                mettreAJourStatut();
                dlg.dispose();
                afficherMessage(" ","La matiere est ajouter avec sucess.");
            } catch (NumberFormatException ex) {
            	afficherMessage("Erreur ","Coefficient et semestre doivent etre des nombres entiers.");
            }
        });
        btnAnnuler.addActionListener(ev -> dlg.dispose());

        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
        
    }

    // ─── Dialogue : modifier matiere ─────────────────────────────
    private void modifierMatiere() {
        String code = demanderTexte("Modifier une matiere", "Code de la matiere a modifier :");
        if (code == null || code.trim().isEmpty()) return;
        Matiere m = gestion.rechercheMatiere(code.trim());
        if (m == null) { afficherMessage("Erreur ","Matiere introuvable."); return; }

        Dialog dlg = new Dialog(this, "Modifier " + m.getCode(), true);
        dlg.setLayout(new GridLayout(3, 2, 8, 8));

        dlg.add(new Label("Libelle :"));
        TextField tfLibelle = new TextField(m.getLibelle(), 20);
        dlg.add(tfLibelle);

        dlg.add(new Label("Coefficient :"));
        TextField tfCoeff = new TextField(String.valueOf(m.getCoefficient()));
        dlg.add(tfCoeff);

        Button btnValider = creerBouton("Valider", VIOLET_FONCE);
        Button btnAnnuler = creerBouton("Annuler", VIOLET_CLAIR);
        dlg.add(btnValider);
        dlg.add(btnAnnuler);

        btnValider.addActionListener(ev -> {
            try {
                gestion.modifier_matiere(m.getCode(), tfLibelle.getText().trim(), Integer.parseInt(tfCoeff.getText().trim()));
                if (etudiantSelectionne != null) afficherReleve();
                dlg.dispose();
                afficherMessage(" ","La matiere est modifier avec sucess.");
            } catch (NumberFormatException ex) {
            	afficherMessage("Erreur ","Coefficient invalide.");
            }
        });
        btnAnnuler.addActionListener(ev -> dlg.dispose());

        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
       
    }

    // ─── Suppression matiere ──────────────────────────────────────
    private void supprimerMatiere() {
        String code = demanderTexte("Supprimer une matiere", "Code de la matiere a supprimer :");
        if (code == null || code.trim().isEmpty()) return;
        if (gestion.rechercheMatiere(code.trim()) == null) {
        	afficherMessage("Erreur ","Matiere introuvable.");
            return;
        }
        gestion.supprimer_matiere(code.trim());
        mettreAJourStatut();
        if (etudiantSelectionne != null) afficherReleve();
        afficherMessage(" ","La matiere est supprimer avec sucess.");
    }
    private void rechercherMatiere() {
        String terme = demanderTexte("Rechercher une matiere", "Code ou libelle :");
        if (terme == null || terme.trim().isEmpty()) return;
        terme = terme.trim();

        // Recherche par code exact, sinon par libelle (partiel, insensible a la casse)
        Matiere parCode = gestion.rechercheMatiere(terme);
        java.util.List<Matiere> resultats = new ArrayList<>();
        if (parCode != null) {
            resultats.add(parCode);
        } else {
            for (Matiere m : gestion.getMatieres()) {
                if (m.getLibelle().toLowerCase().contains(terme.toLowerCase())) {
                    resultats.add(m);
                }
            }
        }

        if (resultats.isEmpty()) {
            afficherMessage("Recherche", "Aucune matiere trouvee pour : " + terme);
            return;
        }

        StringBuilder sb = new StringBuilder("Resultats pour \"" + terme + "\" :\n\n");
        for (Matiere m : resultats) {
            sb.append(m.getCode()).append("  ").append(m.getLibelle())
              .append("  (Coeff. ").append(m.getCoefficient())
              .append(", Semestre ").append(m.getSemestre()).append(")\n");
        }
        afficherMessage("Resultats de recherche", sb.toString());
        
    }
    
    private void listerMatieres() {
    	if(gestion.getMatieres().isEmpty()) {
    		afficherMessage("Liste de matiere", "Aucune matiere ");
    		return;
    	}
    	StringBuilder sb = new StringBuilder("Toutes les matieres(" +gestion.getMatieres().size() +" ) :\n\n");
    	sb.append("===============Semestre1=====================\n");
    	for(Matiere m : gestion.getMatieres()) {
    		if(m.getSemestre() == 1) {
    			sb.append(String.format("%-6s %-30s Coeff.%d%n", m.getCode(),m.getLibelle(),m.getCoefficient()));
    		}
    	}
    	sb.append("\n===============Semestre2=====================\n");
    	for(Matiere m : gestion.getMatieres()) {
    		if(m.getSemestre() == 2) {
    			sb.append(String.format("%-6s %-30s Coeff.%d%n", m.getCode(),m.getLibelle(),m.getCoefficient()));
    		}
    	}
    	afficherMessage("Liste de matiere", sb.toString());
    	
    }
    

    // ─── Dialogue : saisir une note ──────────────────────────────
    private void saisirNote() {
        if (!verifierSelectionEtudiant()) return;
        if (gestion.getMatieres().isEmpty()) {
            afficherMessage("Erreur", "Aucune matiere disponible. Ajoutez d'abord une matiere.");
            return;
        }

        Dialog dlg = new Dialog(this, "Saisir les notes — " + etudiantSelectionne.getNom(), true);
        dlg.setLayout(new BorderLayout(5, 5));

        // ─── Zone defilante avec une ligne par matiere ───
        Panel panelMatieres = new Panel(new GridLayout(gestion.getMatieres().size() + 1, 3, 5, 3));
        panelMatieres.add(new Label("Matiere", Label.LEFT));
        panelMatieres.add(new Label("Controle (/20)", Label.CENTER));
        panelMatieres.add(new Label("Examen (/20)", Label.CENTER));

        java.util.List<TextField> champsControle = new ArrayList<>();
        java.util.List<TextField> champsExamen   = new ArrayList<>();

        for (Matiere m : gestion.getMatieres()) {
            panelMatieres.add(new Label(m.getCode() + " - " + m.getLibelle(), Label.LEFT));

            // Si une note existe deja pour cette matiere, on la pre-remplit
            String valControle = "";
            String valExamen   = "";
            for (Note n : etudiantSelectionne.getListeNotes()) {
                if (n.getMatiere().getCode().equals(m.getCode())) {
                    valControle = String.valueOf(n.getNoteControle());
                    valExamen   = String.valueOf(n.getNoteExamen());
                    break;
                }
            }
            TextField tfControle = new TextField(valControle, 5);
            TextField tfExamen   = new TextField(valExamen, 5);
            champsControle.add(tfControle);
            champsExamen.add(tfExamen);
            panelMatieres.add(tfControle);
            panelMatieres.add(tfExamen);
        }

        ScrollPane scroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scroll.add(panelMatieres);
        scroll.setSize(500, 400);

        Button btnValider = creerBouton("Valider tout", VIOLET_FONCE);
        Button btnAnnuler = creerBouton("Annuler", VIOLET_CLAIR);
        Panel panelBoutons = new Panel();
        panelBoutons.add(btnValider);
        panelBoutons.add(btnAnnuler);

        dlg.add(scroll, BorderLayout.CENTER);
        dlg.add(panelBoutons, BorderLayout.SOUTH);

        btnValider.addActionListener(ev -> {
            int nbEnregistrees = 0;
            int nbInvalides = 0;
            java.util.List<Matiere> matieres = gestion.getMatieres();

            for (int i = 0; i < matieres.size(); i++) {
                String txtControle = champsControle.get(i).getText().trim();
                String txtExamen   = champsExamen.get(i).getText().trim();

                // Ligne laissee vide : on saute cette matiere, ce n'est pas une erreur
                if (txtControle.isEmpty() && txtExamen.isEmpty()) continue;

                try {
                    double controle = Double.parseDouble(txtControle.replace(",", "."));
                    double examen   = Double.parseDouble(txtExamen.replace(",", "."));
                    boolean ok = gestion.saisirNote(etudiantSelectionne.getMatricule(),
                        matieres.get(i).getCode(), controle, examen);
                    if (ok) nbEnregistrees++; else nbInvalides++;
                } catch (NumberFormatException ex) {
                    nbInvalides++;
                }
            }

            afficherReleve();
            dlg.dispose();
            afficherMessage("Saisie des notes",
                nbEnregistrees + " note(s) enregistree(s) avec succes."
                + (nbInvalides > 0 ? "\n" + nbInvalides + " note(s) invalide(s) ou ignoree(s)." : ""));
        });

        btnAnnuler.addActionListener(ev -> dlg.dispose());

        dlg.setSize(550, 450);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    // ─── Utilitaires d'interface ───────────────────────────────────
    private Button creerBouton(String texte, Color couleur) {
        Button btn = new Button(texte);
        btn.setBackground(couleur);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        return btn;
    }

    private boolean verifierSelectionEtudiant() {
        if (etudiantSelectionne == null) {
        	afficherMessage("Erreur ","Veuillez d'abord selectionner un etudiant dans la liste.");
            return false;
        }
        return true;
    }

    private String demanderTexte(String titre, String question) {
        final String[] resultat = {null};
        Dialog dlg = new Dialog(this, titre, true);
        dlg.setLayout(new GridLayout(2, 2, 8, 8));
        dlg.add(new Label(question));
        TextField tf = new TextField(15);
        dlg.add(tf);
        Button btnOk = creerBouton("OK", VIOLET_FONCE);
        Button btnAnnuler = creerBouton("Annuler",VIOLET_CLAIR );
        dlg.add(btnOk);
        dlg.add(btnAnnuler);
        btnOk.addActionListener(ev -> { resultat[0] = tf.getText(); dlg.dispose(); });
        btnAnnuler.addActionListener(ev -> dlg.dispose());
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
        return resultat[0];
    }
    private void afficherMessage(String titre, String message) {
        Dialog dlg = new Dialog(this, titre, true);
        dlg.setLayout(new BorderLayout(10, 10));

        TextArea zoneTexte = new TextArea(message, 8, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
        zoneTexte.setEditable(false);
        dlg.add(zoneTexte, BorderLayout.CENTER);

        Button btnOk = creerBouton("OK", VIOLET_FONCE);
        btnOk.addActionListener(ev -> dlg.dispose());
        Panel panelBouton = new Panel();
        panelBouton.add(btnOk);
        dlg.add(panelBouton, BorderLayout.SOUTH);

        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }


    // ─── Gestion des clics de menu ──────────────────────────────
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == miNouvelEtudiant)         creerNouvelEtudiant();
        else if (src == miModifierEtudiant)  modifierEtudiant();
        else if (src == miSupprimerEtudiant) supprimerEtudiant();
        else if (src == miRechercherEtudiant) rechercherEtudiant();
        else if (src == miQuitter)           { ConnexionBD.fermer(); System.exit(0); }
        
        else if (src == miNouvelleMatiere)   creerNouvelleMatiere();
        else if (src == miModifierMatiere)   modifierMatiere();
        else if (src == miSupprimerMatiere)  supprimerMatiere();
        else if (src == miRechercherMatiere)  rechercherMatiere();
        else if (src == miListerMatiere)        listerMatieres();
        else if (src == miClassementClasse)       afficherClassementComplet();
        
        
        else if (src == miSaisirNote)        saisirNote();
        else if (src == miAPropos)
        	afficherMessage("A propos ","Gestion des Notes — Application Java AWT + JDBC (ESCEP Niger)");
    }

    public static void main(String[] args) {
        new ApplicationGestionNotes();
    }
}