package plugins.hosts;

public abstract class SamplePlugin {
	
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
		
		return null;
		
	}
	
	/**
	 * This method processes the page to get the download link of the file
	 * @return String fileAddress
	 */
	public static String processPageReturn(String data){
		
		return null;
		
	}
	
	
}
