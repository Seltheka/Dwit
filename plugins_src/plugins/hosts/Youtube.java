package plugins.hosts;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Youtube {
	
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
		
		Pattern pat = Pattern.compile("flashvars=\"(.*?)\"");
		Matcher match = pat.matcher(data);

		if (match.find()){
			String temp = URLDecoder.decode(match.group(1));
						
			pat = Pattern.compile("fmt_url_map=(.*?)&amp;");
			
			match = pat.matcher(temp);
			
			if (match.find()){
				
				String[] temp0 = match.group(1).split(",");
				
				videoAddress = temp0[0].split("\\|")[1];
				
			}
			
		} else {
			
			return null;
			
		}
			
		String videoTitle = decodePattern(data,"<span id=\"eow-title\" class=\"(?:.*)\" dir=\"ltr\" title=\"(.*?)\">");
		
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
