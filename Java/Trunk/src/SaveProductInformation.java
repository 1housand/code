import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * SaveProductInformation class extracts the necessary information for 
 * the product from the html file created using regular expressions.
 * SQL commands are then used to save data into the database.
 */
public class SaveProductInformation {

	private static Pattern pattern;
	private static Matcher matcher;
	private static String getItemNameRegex = "(?<=\"title\"\\scontent=\"Amazon.com:\\s)(\\w.+?)(?=,|:)";
	private static String getItemDescriptionRegex = "(?<=span\\sclass=\"a-list-item\">\\s)(\\w.+?)(?=<)";
	private static String getItemRatingRegex = "(?<=noUnderline\"\\stitle=\")(\\d.+?)(?=\")";
	
	/*
	 * main saves necessary data to SQL database and pulls that data from
	 * the very same database to load them into the fields.
	 */
	public static void main(String[] args) throws IOException{
		getItemInformation();
		Trunk.grabDataFromTable();
	}
	/*
	 * Using regular expressions, the title, description, and rating are extracted
	 * from HTML text file, then it is inserted into the SQL database.
	 */
	private static void getItemInformation() throws IOException{
		String html = readFile(Trunk.file.getName(), Charset.defaultCharset());
		try {
			pattern = Pattern.compile(getItemNameRegex);
			matcher = pattern.matcher(html);
			matcher.find();
			
			PreparedStatement preparedStatement = Trunk.connection.prepareStatement("INSERT into product (product_name, product_rating) VALUES (?,?)", 
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, matcher.group());
			
			pattern = Pattern.compile(getItemRatingRegex);
			matcher = pattern.matcher(html);
			matcher.find();
			preparedStatement.setString(2, matcher.group());
			
			preparedStatement.executeUpdate();
			
			Trunk.sqlLogger.logSqlCommands(preparedStatement.toString());
			
			ResultSet tableKeys = preparedStatement.getGeneratedKeys();
			tableKeys.next();
			Trunk.productPrimaryKey = tableKeys.getInt(1);

			pattern = Pattern.compile(getItemDescriptionRegex);
			matcher = pattern.matcher(html);
			preparedStatement = Trunk.connection.prepareStatement("INSERT into description (product_id, description_text) VALUES (?,?)", 
					Statement.RETURN_GENERATED_KEYS);
			while(matcher.find()){
				preparedStatement.setInt(1, Trunk.productPrimaryKey);
				preparedStatement.setString(2, matcher.group());
				preparedStatement.executeUpdate();
				Trunk.sqlLogger.logSqlCommands(preparedStatement.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * reads HTML text file into a string and returns the string.
	 */
	private static String readFile(String path, Charset encoding) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
}
