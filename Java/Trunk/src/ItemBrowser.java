import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

/*
 * This object opens the browser with the search address sent in args.
 * After browsing to the final product, the user must press the 
 * btnResearchThisItem button to select the product to research.
 */

public class ItemBrowser {
	
	private static Browser browser = new Browser();

	public static void main(String[] args) {
    	LoggerProvider.getChromiumProcessLogger().setLevel(Level.OFF);
    	LoggerProvider.getIPCLogger().setLevel(Level.OFF);
    	LoggerProvider.getBrowserLogger().setLevel(Level.OFF);
    	
    	BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        browser.loadURL(args[0]);
    
        /*
         * btnResearchThisItem selects current item in browser as the candidate
         * for research and calls class SaveProductInformation's main method.
         */
        JButton btnResearchThisItem = new JButton("Research This Item");
        btnResearchThisItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Trunk.connection == null) {
					JOptionPane.showMessageDialog(null, "Please establish a connection with the SQL database.");
					ConnectToDatabaseWindow loginWindow = new ConnectToDatabaseWindow();
					loginWindow.setVisible(true);
				} 
				else if (JOptionPane.showConfirmDialog(null, "Is this the item you want to research?")==0){
					frame.dispose();
					getBrowserHTML();
					try {
						SaveProductInformation.main(null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
        });
		frame.add(btnResearchThisItem, BorderLayout.NORTH);
    }
    /*
     * getBrowserHTML method extracts the HTML of the website the browser is on
     * and prints it to a text file with filename from Trunk.file 
     */
    private static void getBrowserHTML(){
		try {
			Trunk.file.createNewFile();
			FileWriter fw = new FileWriter(Trunk.file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(browser.getHTML());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
}