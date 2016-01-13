import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/*
 * Logs all SQL commands sent to SQL database into a text file.
 * Textfile name is Trunk.logFile.
 */
public class OutputToFile {

	protected static BufferedWriter bw;
	
	public OutputToFile() throws IOException {
		Trunk.logFile.createNewFile();
		FileWriter fw = new FileWriter(Trunk.logFile.getAbsoluteFile());
		bw = new BufferedWriter(fw);
	}
	
	protected void logSqlCommands(String data){
		try {
			bw.write(data);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
