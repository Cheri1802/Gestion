package monpackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
	//URL JDBC :protocole ://hote:port/base?options
	private static final String URL ="jdbc:mysql://localhost:3306/gestion_note_escep" 
			+ "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	private static final String USER = "root";
    private static final String PASS = "";  // ← modifier selon votre config
    private static Connection connexion;  // instance unique (Singleton)

    private ConnexionBD() {}  // constructeur privé : pas d'instanciation
    public static Connection getConnexion() throws SQLException{
    	if(connexion == null || connexion.isClosed()) {
    		connexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("[BD] Connexion etablie.");
        }
        return connexion;
    }
    
    public static void fermer() {
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
                System.out.println("[BD] Connexion fermee.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur fermeture BD : " + e.getMessage());
        }
    }
    
    /** Teste si une connexion est possible (diagnostic). */
    public static boolean testerConnexion() {
        try { getConnexion(); return true; }
        catch (SQLException e) {
            System.err.println("[BD] Impossible : " + e.getMessage());
            return false;
        }
    }
    		
    	
    

}
