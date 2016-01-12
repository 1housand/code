import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConnectToMySQL {
	
	public static Connection connector(){
		
		try {
        	Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(Trunk.databaseURL, Trunk.username, Trunk.password);
			return connection;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
		
	}
	
}
