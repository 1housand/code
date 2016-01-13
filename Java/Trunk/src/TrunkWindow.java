import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.DefaultTableModel;
//import com.teamdev.jxbrowser.chromium.Browser;
//import com.teamdev.jxbrowser.chromium.swing.BrowserView;
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
import java.awt.event.*;
import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.Vector;
//import java.util.concurrent.Semaphore;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import net.miginfocom.swing.MigLayout;

/*
 * This TrunkWindow is the main interface window that contains
 * all the GUI objects
 */
public class TrunkWindow {
	private JFrame frame;
	private JTextField textFieldProductNameSearch;
	private JScrollPane scrollPane;	
	private JTable table;
	protected static JTextField txtFieldProductname;
	private JTextField txtFieldProductRating;
	private JButton btnNewButton;
	private JButton btnUpdateDatabase;

	/*
	 * Create the application.
	 */
	public TrunkWindow() {
		initialize();
	}

	/*
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/*
		 * This action method takes in a web address and displays it on the browser
		 * for the user to select. 
		 */
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try {
					if(textFieldProductNameSearch.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Please enter a valid item name.");
					} else {
						Trunk.productName = textFieldProductNameSearch.getText();
						ItemBrowser.main(new String[] {Trunk.amazonUrl + Trunk.productName});
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		};
		/*
		 * connectToDatabase is an action listener that creates a 
		 * new ConnectToDatabaseWindow GUI window so that a connection 
		 * to the SQL database can be established.
		 */
		ActionListener connectToDatabase = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ConnectToDatabaseWindow loginWindow = new ConnectToDatabaseWindow();
				loginWindow.setVisible(true);
			}
		};
		/*
		 * The updateDatabase action listener is called when the btnUpdateDatabase is pressed.
		 * If you are changing a value in the table to be updated in the database, you must press Enter
		 * to save changes and then press the btnUpdateDatabase button. 
		 */
		ActionListener updateDatabase = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					Statement statement = Trunk.connection.createStatement();
					statement.executeUpdate("update product set product_name='"+txtFieldProductname.getText()+
							"', product_rating='"+txtFieldProductRating.getText()+"' where product_id="+Trunk.productPrimaryKey);
					Trunk.sqlLogger.logSqlCommands("update product set product_name='"+txtFieldProductname.getText()+
							"', product_rating='"+txtFieldProductRating.getText()+"' where product_id="+Trunk.productPrimaryKey);
					
					ResultSet rs = statement.executeQuery("select * from description");
					rs.next();
					int descriptionPrimaryKey = rs.getInt(1);
					for (int i = 0; i < table.getModel().getRowCount(); i++){
						statement.executeUpdate("update description set description_text='"+table.getModel().getValueAt(i, 0)+
								"' where product_id="+Trunk.productPrimaryKey+" and description_id="+ (descriptionPrimaryKey+i));
						Trunk.sqlLogger.logSqlCommands("update description set description_text='"+table.getModel().getValueAt(i, 0)+
								"' where product_id="+Trunk.productPrimaryKey+" and description_id="+ (descriptionPrimaryKey+i));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		
		frame = new JFrame();
		frame.setBounds(100, 100, 680, 726);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewConnectToDatabase = new JMenuItem("Connect to Database");
		mntmNewConnectToDatabase.addActionListener(connectToDatabase);
		mnFile.add(mntmNewConnectToDatabase);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputToFile.bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmExit);
		frame.getContentPane().setLayout(new MigLayout("", "[99.00px][242.00px,grow][46px][300px,grow]", "[20px][][grow]"));
		
		JLabel lblProductNameSearch = new JLabel("Product Name:");
		frame.getContentPane().add(lblProductNameSearch, "cell 0 0,alignx right,aligny center");
		
		textFieldProductNameSearch = new JTextField();
		textFieldProductNameSearch.addActionListener(action);
		frame.getContentPane().add(textFieldProductNameSearch, "cell 1 0,grow");
		textFieldProductNameSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(action);
		frame.getContentPane().add(btnSearch, "cell 2 0,alignx center,aligny center");
		
		btnNewButton = new JButton("Connect to SQL Database");
		btnNewButton.addActionListener(connectToDatabase);
		frame.getContentPane().add(btnNewButton, "cell 3 0,alignx center,aligny center");
		
		btnUpdateDatabase = new JButton("Update Database");
		btnUpdateDatabase.setToolTipText("Updated SQL database with current information in the textfields.");
		btnUpdateDatabase.addActionListener(updateDatabase);
		frame.getContentPane().add(btnUpdateDatabase, "cell 0 1");
		
		txtFieldProductname = new JTextField();
		txtFieldProductname.setHorizontalAlignment(SwingConstants.CENTER);
		txtFieldProductname.setText("Product Name");
		frame.getContentPane().add(txtFieldProductname, "cell 1 1 2 1,grow");
		txtFieldProductname.setColumns(10);
		
		txtFieldProductRating = new JFormattedTextField();
		txtFieldProductRating.setHorizontalAlignment(SwingConstants.CENTER);
		txtFieldProductRating.setText("Product Rating");
		frame.getContentPane().add(txtFieldProductRating, "cell 3 1,growx");
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 2 4 1,grow");
		
		table = new JTable(new DefaultTableModel(new Object[]{"Product Description"}, 0));
		scrollPane.setViewportView(table);
		
		frame.setVisible(true);
		

	}
	/*
	 * updateTrunkWindow updates the SQL database with the values in the TrunkWindow object fields:
	 * 		txtFieldProductname
	 * 		txtFieldProductRating
	 * 		table
	 */
	protected void updateTrunkWindow(){
		try {
			String query = "select product_name, product_rating from product"; 
			PreparedStatement pst = Trunk.connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				txtFieldProductname.setText(rs.getString(1));
				txtFieldProductRating.setText(rs.getString(2));
			}
			
			query = "select description_text from description";
			pst = Trunk.connection.prepareStatement(query);
			rs = pst.executeQuery();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while(rs.next()){
				model.addRow(new Object[] {rs.getString(1)});
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
