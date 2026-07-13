package monpackage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EtudiantDAO {
	//insert
	public void inserer(Etudiant e)throws SQLException {
		String sql = "INSERT INTO etudiants (matricule , nom,prenom,classe) VALUES(?,?,?,?)";
		try(Connection c = ConnexionBD.getConnexion();
				PreparedStatement ps = c.prepareStatement(sql)){
			ps.setString(1,e.getMatricule());
			ps.setString(2,e.getNom());
			ps.setString(3,e.getPrenom());
			ps.setString(4,e.getClasse());
			ps.executeUpdate();
			
		}
		
	}
	
	//SELECT * (tous les etudiants)
	public List<Etudiant> tousLesEtudiants() throws SQLException{
		List<Etudiant> liste = new ArrayList<>();
		String sql ="SELECT * FROM etudiants ORDER BY matricule";
		try(Connection c = ConnexionBD.getConnexion();
				Statement st = c.createStatement();
				ResultSet rs = st.executeQuery(sql)){
					while (rs.next()) liste.add(construireEtudiant(rs));
				}
				return liste;
		
	}
	//UPDATE
	public void mettreAJour(Etudiant e) throws SQLException{
		String sql = " UPDATE etudiants SET nom=?,prenom=?,classe=? WHERE matricule = ?";
		try(Connection c = ConnexionBD.getConnexion();
				PreparedStatement ps = c.prepareStatement(sql)){
			ps.setString(1,e.getNom());
			ps.setString(2,e.getPrenom());
			ps.setString(3,e.getClasse());
			ps.setString(4,e.getMatricule());
			
			
			
			ps.executeUpdate();
			
	}
		
			
	}
	

		//DELETE
		public boolean supprimer(String matricule) throws SQLException{
			String sql = " DELETE FROM etudiants WHERE matricule = ?";
			try(Connection c = ConnexionBD.getConnexion();
					PreparedStatement ps = c.prepareStatement(sql)){
				ps.setString(1,matricule);
				return ps.executeUpdate() >0;
		}
			
		}
			
			//HELPER 
			private Etudiant construireEtudiant(ResultSet rs) throws SQLException{
				String matricule = rs.getString("matricule");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String classe = rs.getString("classe");
				return new Etudiant (nom,prenom,matricule,classe);
				
			}
		
			

}
