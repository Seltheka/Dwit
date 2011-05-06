package org.dwit.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;

public class PluginUtils {
	
	public static HashMap<String,File> getPluginList(String plugin){
		
		HashMap<String,File> pluginList = new HashMap<String,File>();
		
		File folder = new File(new File(plugin.replace('.', '/')).getAbsolutePath()+"/");
		
		File[] fileList = folder.listFiles();
		
		System.out.println(folder+ " " + fileList);
				
		for (int i=0;i<fileList.length;i++){
			if (fileList[i].isFile() && fileList[i].getName().endsWith(".class")){
				pluginList.put(plugin + "." + fileList[i].getName().replace(".class", ""),fileList[i]);
			}
		}
				
		return pluginList;
		
	}

}
