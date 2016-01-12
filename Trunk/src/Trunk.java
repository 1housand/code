import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/*
 * Trunk is a program that has the user select the product from amazon.com 
 * and uses the data from that product to search other websites to centralize 
 * the important information into a mySQL database. 
 */
public class Trunk {
	/*
	 * This Trunk.java file is the main file that contains all the shared 
	 * objects that the other files will access. 
	 */
	protected static OutputToFile sqlLogger;
	private static TrunkWindow trunkWindow;
	protected static Connection connection = null;
	protected static String username = "root"; 
	protected static String password = null;
	protected static String databaseName = "javabase";
	protected static String databaseURL= "jdbc:mysql://localhost:3306/";
	protected static String FILENAME = "webpageHTML.txt";
	protected static File file = new File(FILENAME);
	protected static File logFile = new File("log.txt");
	protected static String amazonUrl = "http://www.amazon.com/s/field-keywords=";
	protected static String walmartUrl = "http://www.walmart.com/search/?query=";
	protected static String sqlTableNameProduct = "PRODUCT";
	protected static String sqlTableNameDescription = "DESCRIPTION";
	protected static String productName;
	protected static int productPrimaryKey;
	protected static int counter=0;

	/*
	 * This main method opens the main window called TrunkWindow with the GUI
	 */
	public static void main(String args[]){
		try {
			sqlLogger = new OutputToFile();
			trunkWindow = new TrunkWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * prepareDatabase method prepares the SQL database after connecting to it.
	 * It checks to see if the tables, "product" and "description", exist. 
	 * If those tables don't exist, it will create those tables with the necessary attributes.
	 */
	protected static void prepareDatabase(){
		try {
			DatabaseMetaData meta = connection.getMetaData();
			/*
			 * If there aren't any returns for getTables search on sqlTableNameProduct (PRODUCT)
			 * then create the table with the following attributes:
			 * 		product_id int primary key AUTO_INCREMENT, 
			 * 		product_name varchar(30), 
			 * 		product_rating varchar(30)
			 */
			ResultSet res = meta.getTables(null, null, sqlTableNameProduct, 
					new String[] {"TABLE"});
			if(!res.next()){
				String newTableProduct =
						"create table " + sqlTableNameProduct + 
						" (product_id int primary key AUTO_INCREMENT, product_name varchar(255), product_rating varchar(30))";
				Statement statementNewTable = connection.createStatement();
				if(statementNewTable.execute(newTableProduct)){
					sqlLogger.logSqlCommands(newTableProduct);
				};
				
				JOptionPane.showMessageDialog(null, "Table \"" + sqlTableNameProduct + "\" created successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "Table \"" + sqlTableNameProduct + "\" already exists. Using existing table in database.");
			}
			/*
			 * If there aren't any returns for getTables search on sqlTableNameDescription (DESCRIPTION)
			 * then create the table with the following attributes:
			 * 		description_id int primary key AUTO_INCREMENT, 
			 * 		product_id int(10), 
			 * 		foreign key(product_id) references sqlTableNameProduct (product_id), 
			 *		description_text varchar(255)
			 */
			res = meta.getTables(null, null, sqlTableNameDescription,
					new String[] {"TABLE"});
			if(!res.next()){
				String newTableDescription =
						"create table " + sqlTableNameDescription + 
						" (description_id int primary key AUTO_INCREMENT, product_id int(10), foreign key(product_id) references " + 
								sqlTableNameProduct + "(product_id), description_text varchar(255))";
				Statement statementNewTable = connection.createStatement();
				if(statementNewTable.execute(newTableDescription)){
					sqlLogger.logSqlCommands(newTableDescription);
				};
				JOptionPane.showMessageDialog(null, "Table \"" + sqlTableNameDescription + "\" created successfully.");
			} else {
				JOptionPane.showMessageDialog(null, "Table \"" + sqlTableNameDescription + "\" already exists. Using existing table in database.");
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, 
		    "<html><body><p style='width: 200px;'>"+e.getMessage()+"</p></body></html>", 
		    "Error", 
		    JOptionPane.ERROR_MESSAGE);
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/*
	 * The grabDataFromTables method calls the updateTrunkWindow method
	 * from pulls the data from the TrunkWindow object created globally
	 * and initialized in main.
	 */
	protected static void grabDataFromTable(){
		trunkWindow.updateTrunkWindow();
	}

	
}
