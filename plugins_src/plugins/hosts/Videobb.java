package plugins.hosts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

public abstract class Videobb {
	
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
		
		Pattern pat = Pattern.compile("http://(?:www.|)videobb.com/video/(.*)");
		Matcher match = pat.matcher(address);
		
		if (match.find()){
			return new String("http://videobb.com/player_control/settings.php?v="+match.group(1));
		}
		
		return null;
		
	}
	
	/**
	 * This method processes the page to get the download link of the file
	 * @return String fileAddress
	 */
	public static String[] processPageReturn(String data){
		
		String videoAddress = null;
		
		Pattern pat = Pattern.compile(",\"res\":\\[(.*?)\\]");
		Matcher match = pat.matcher(data);

		if (match.find()){
			String temp = match.group(match.groupCount());
			
			pat = Pattern.compile("\"u\":\"(.*?)\"");
			match = pat.matcher(temp);
			
			if (match.find()){
				Base64 b64 = new Base64();
				videoAddress =  new String(b64.decode(match.group(1)));
			}
			
		} else {
			
			System.err.println("error");
			
		}
		
		String videoTitle = decodePattern(data,"\"video_details\":\\{\"video\":\\{\"title\":\"(.*?)\"");
		
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
