package monpackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatiereDAO {

    public void inserer(Matiere m) throws SQLException {
        String sql = "INSERT INTO matieres (code, libelle, coefficient, semestre) VALUES (?, ?, ?, ?)";
        try (Connection c = ConnexionBD.getConnexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getCode());
            ps.setString(2, m.getLibelle());
            ps.setInt(3, m.getCoefficient());
            ps.setInt(4, m.getSemestre());
            ps.executeUpdate();
        }
    }

    public List<Matiere> toutesLesMatieres() throws SQLException {
        List<Matiere> liste = new ArrayList<>();
        String sql = "SELECT * FROM matieres ORDER BY semestre, code";
        try (Connection c  = ConnexionBD.getConnexion();
             Statement   st = c.createStatement();
             ResultSet   rs = st.executeQuery(sql)) {
            while (rs.next()) liste.add(construireMatiere(rs));
        }
        return liste;
    }

    public void mettreAJour(Matiere m) throws SQLException {
        String sql = "UPDATE matieres SET libelle = ?, coefficient = ?, semestre = ? WHERE code = ?";
        try (Connection c = ConnexionBD.getConnexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getLibelle());
            ps.setInt(2, m.getCoefficient());
            ps.setInt(3, m.getSemestre());
            ps.setString(4, m.getCode());
            ps.executeUpdate();
        }
    }

    public boolean supprimer(String code) throws SQLException {
        String sql = "DELETE FROM matieres WHERE code = ?";
        try (Connection c = ConnexionBD.getConnexion();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            return ps.executeUpdate() > 0;
        }
    }

    private Matiere construireMatiere(ResultSet rs) throws SQLException {
        String code        = rs.getString("code");
        String libelle     = rs.getString("libelle");
        int    coefficient = rs.getInt("coefficient");
        int    semestre    = rs.getInt("semestre");
        return new Matiere(code, libelle, coefficient, semestre);
    }
}