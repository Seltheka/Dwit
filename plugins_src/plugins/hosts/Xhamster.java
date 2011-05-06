package plugins.hosts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Xhamster {
	
	/**
	 * 	This methods returns basic information about the plugin
	 * @return String[]
	 */
	public static String[] getInfo(){
	
		return new String[]{
				"plugin_name",
				"plugin_host",
				"plugin_version",
				"plugin_author"
		};
				
	}
	
	public static String[] getProcess(){
		
		return null;
		
	}
	
	/**
	 * This method is taking the address entered by the user and returns the address of the page where it can get the info it needs
	 * If same, just return the entry
	 * @return String pageAddress
	 */
	public static String processUserAddress(String address){
		
		return address;
		
	}
	
	/**
	 * This method processes the page to get the download link of the file
	 * @return String fileAddress
	 */
	public static String[] processPageReturn(String data){
		
		String videoAddress = null;
		
		Pattern pat = Pattern.compile("\'file\': \'(.*?)\',");
		Matcher match = pat.matcher(data);

		if (match.find())
			videoAddress = "http://xhamster.com/flv2/"+match.group(1);
		else
			System.err.println("Error!");
		
		String videoTitle = decodePattern(data,"<h1>(.*?)</h1>");
		
		
		return new String[]{videoAddress,videoTitle};
		
	}
	
	private static String decodePattern(String data, String pattern){
		
		Pattern pat = Pattern.compile(pattern);
		Matcher match = pat.matcher(data);
	
		if (match.find())
			return match.group(1);
		else
			return null;
		
	}
	
	
}
