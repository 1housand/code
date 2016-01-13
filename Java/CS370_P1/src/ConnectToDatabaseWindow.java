import javax.swing.*;
//import java.sql.*;
import java.awt.event.*;
//import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.*;

/*
 * This class is the GUI window to connect to the SQL database.
 */
public class ConnectToDatabaseWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JTextField textFieldDatabaseUrl;
	private JTextField textFieldDatabaseName;

	/**
	 * Create the frame.
	 */
	public ConnectToDatabaseWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 351);
		getContentPane().setLayout(null);
		
		/*
		 * This action method initializes the connection object in the Trunk class with
		 * the text from textFieldUsername, passwordField, textFieldDatabaseUrl, textFieldDatabaseName
		 */
		Action action = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent a) {
				try {
		        	Class.forName("com.mysql.jdbc.Driver");
					Trunk.connection = (com.mysql.jdbc.Connection) DriverManager.getConnection
							(textFieldDatabaseUrl.getText()+textFieldDatabaseName.getText()+"?allowMultiQueries=true", textFieldUsername.getText(), passwordField.getText());

					if(Trunk.connection!=null){
						JOptionPane.showMessageDialog(null, "Connection Successful");
						dispose();
						Trunk.prepareDatabase();
//						Trunk.grabDataFromTable();
					}
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		};
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(10, 34, 110, 33);
		getContentPane().add(lblUsername);
		
		textFieldUsername = new JTextField();
		textFieldUsername.addActionListener(action);
		textFieldUsername.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldUsername.setBounds(130, 32, 189, 33);
		textFieldUsername.setText(Trunk.username);
		getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(10, 90, 110, 33);
		getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.addActionListener(action);
		passwordField.setBounds(130, 89, 189, 33);
		passwordField.setText(Trunk.password);
		getContentPane().add(passwordField);
		
		JLabel lblDatabaseUrl = new JLabel("Database URL:");
		lblDatabaseUrl.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDatabaseUrl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDatabaseUrl.setBounds(10, 148, 110, 33);
		getContentPane().add(lblDatabaseUrl);
		
		textFieldDatabaseUrl = new JTextField();
		textFieldDatabaseUrl.addActionListener(action);
		textFieldDatabaseUrl.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldDatabaseUrl.setColumns(10);
		textFieldDatabaseUrl.setText(Trunk.databaseURL);
		textFieldDatabaseUrl.setBounds(130, 146, 314, 33);
		getContentPane().add(textFieldDatabaseUrl);

		JLabel lblDatabaseName = new JLabel("Database Name:");
		lblDatabaseName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDatabaseName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDatabaseName.setBounds(10, 206, 110, 33);
		getContentPane().add(lblDatabaseName);
		
		textFieldDatabaseName = new JTextField();
		textFieldDatabaseName.addActionListener(action);
		textFieldDatabaseName.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldDatabaseName.setColumns(10);
		textFieldDatabaseName.setText(Trunk.databaseName);
		textFieldDatabaseName.setBounds(130, 203, 248, 33);
		getContentPane().add(textFieldDatabaseName);
		
		JButton btnSubmit = new JButton("Login");
		btnSubmit.addActionListener(action);
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSubmit.setBounds(96, 262, 110, 33);
		getContentPane().add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancel.setBounds(243, 262, 110, 33);
		getContentPane().add(btnCancel);
		
		
	}
}
