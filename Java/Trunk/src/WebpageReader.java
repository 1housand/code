import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class WebpageReader {

	 private static String webpage = null;
	 public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6";
	
	 public static InputStream getURLInputStream(String sURL) throws Exception {
	       URLConnection oConnection = (new URL(sURL)).openConnection();
	       oConnection.setRequestProperty("User-Agent", USER_AGENT);
	       return oConnection.getInputStream();
	 } // getURLInputStream
	
	 public static BufferedReader read(String url) throws Exception {
	       InputStream content = (InputStream)getURLInputStream(url);
	       return new BufferedReader (new InputStreamReader(content));
	 } // read
	
	public static void main (String[] args) throws Exception{
	
		if (args.length == 0) {
			System.out.println("No URL inputted.");
	        System.exit(1);
		} // any inputs?
		
		webpage = args[0];
		System.out.println("Contents of the following URL: "+webpage+"\n");
		
		BufferedReader reader = read(webpage);
		String line = reader.readLine();
	
		while(Trunk.file.exists()){
			Trunk.file = new File(new StringBuilder(Trunk.FILENAME).insert(Trunk.FILENAME.length()-4, 
					("("+ ++Trunk.counter)+")").toString());
		}
		Trunk.counter = 0;
		
		try {
			Trunk.file.createNewFile();
			FileWriter fw = new FileWriter(Trunk.file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			while (line != null) {
				bw.write(line);
				line = reader.readLine();
			} // while
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	
	} // main









}
