package monpackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    // ─── INSERT ou UPDATE si la note existe deja (upsert) ──
    public void enregistrer(String matricule, String codeMatiere, double noteControle, double noteExamen) throws SQLException {
        String sql = "INSERT INTO notes (matricule_etudiant, code_matiere, note_controle, note_examen) "
                   + "VALUES (?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE note_controle = VALUES(note_controle), note_examen = VALUES(note_examen)";
        try (Connection c = ConnexionBD.getConnexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, matricule);
            ps.setString(2, codeMatiere);
            ps.setDouble(3, noteControle);
            ps.setDouble(4, noteExamen);
            ps.executeUpdate();
        }
    }

    // ─── SELECT * (reconstruction avec les etudiants/matieres deja charges) ──
    public List<Note> toutesLesNotes(List<Etudiant> etudiants, List<Matiere> matieres) throws SQLException {
        List<Note> liste = new ArrayList<>();
        String sql = "SELECT * FROM notes";
        try (Connection c  = ConnexionBD.getConnexion();
             Statement   st = c.createStatement();
             ResultSet   rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String matricule = rs.getString("matricule_etudiant");
                String codeMat   = rs.getString("code_matiere");
                double controle  = rs.getDouble("note_controle");
                double examen    = rs.getDouble("note_examen");

                Etudiant e = trouverEtudiant(etudiants, matricule);
                Matiere  m = trouverMatiere(matieres, codeMat);
                if (e != null && m != null) {
                    Note note = new Note(e, m, controle, examen);
                    e.ajouterNote(note);
                    liste.add(note);
                }
            }
        }
        return liste;
    }

    public boolean supprimer(String matricule, String codeMatiere) throws SQLException {
        String sql = "DELETE FROM notes WHERE matricule_etudiant = ? AND code_matiere = ?";
        try (Connection c = ConnexionBD.getConnexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, matricule);
            ps.setString(2, codeMatiere);
            return ps.executeUpdate() > 0;
        }
    }

    private Etudiant trouverEtudiant(List<Etudiant> etudiants, String matricule) {
        for (Etudiant e : etudiants) if (e.getMatricule().equals(matricule)) return e;
        return null;
    }

    private Matiere trouverMatiere(List<Matiere> matieres, String code) {
        for (Matiere m : matieres) if (m.getCode().equals(code)) return m;
        return null;
    }
}