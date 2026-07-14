TP - Gestion des Notes avec AWT + JDBC
Cours Java - ESCEP Niger
Auteur : Khaled Idi Karanta Cherifatou



STRUCTURE DES FICHIERS
-----------------------
Personne.java                 -> Classe mere : nom, prenom

Etudiant.java                 -> Sous-classe : matricule, classe, listeNotes

Matiere.java                  -> Modele : code, libelle, coefficient, semestre

Note.java                      -> Modele : note controle/examen (validee 0-20)

CalculMoyenne.java             -> Interface : contrat 

calculerMoyenne()

Evaluation.java                -> Classe abstraite : implements CalculMoyenne

Releve_notes.java              -> Calculs : moyennes matiere/semestre/generale

Gestion_note.java              -> Gestionnaire (Singleton + DAO)

ConnexionBD.java               -> Connexion MySQL (Singleton)

EtudiantDAO.java                -> CRUD JDBC table étudiants

MatiereDAO.java                 -> CRUD JDBC table matieres

NoteDAO.java                    -> CRUD JDBC table notes (upsert)

ApplicationGestionNotes.java    -> Fenetre principale (extends Frame)

Application_gestion_note.java  -> Ancienne demo console (conservee)

BASE DE DONNEES
-----------------------
Nom de la base : gestion_note_escep

Tables : etudiants, matieres, notes

Script de creation : gestion_note_escep.sql (a executer via phpMyAdmin

ou mysql -u root -p < gestion_note_escep.sql)

COMPILATION
-----------
1. Ouvrir un terminal dans le dossier contenant tous les fichiers .java
2. S'assurer que mysql-connector-j-8.4.0.jar est dans le classpath
3. Compiler tous les fichiers :

    javac -cp .;mysql-connector-j-8.4.0.jar monpackage/*.java

EXECUTION
---------
    java -cp .;mysql-connector-j-8.4.0.jar monpackage.ApplicationGestionNotes

FONCTIONNALITES
----------------
- Ajouter / modifier / supprimer un etudiant
- Ajouter / modifier / supprimer une matiere
- Saisir les notes (controle + examen) par matiere
- Calculer la moyenne par matiere, par semestre, generale
- Generer le releve de notes avec mention (Admis/Ajourne)
- Rechercher un etudiant (par nom ou matricule)
- Rechercher une matiere (par code ou libelle)
- Afficher la liste complete des matieres
- Classement automatique par classe (rang, meilleure/plus faible moyenne)
- Classement complet d'une classe via le menu Statistiques
- Persistance complete via MySQL (JDBC)