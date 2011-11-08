package org.dwit.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dwit.model.Video;

public class DecodeLinks {
	
	private Video video;
	
	private static String data, className;
	
	private static String[] result;

	public static void analyze(Video video, Map<String,File> hostPluginsList){
		
		System.out.println("test: "+video.getAddress());
			
		getVideoHost(video);
		
		System.out.println(video.getVideoHost());
		
		className = "plugins.hosts." + video.getVideoHost().substring(0,1).toUpperCase() + video.getVideoHost().substring(1);
		
		
		if (!hostPluginsList.containsKey(className))
			return;
		
		DefaultClassLoader cl = new DefaultClassLoader();
		
		System.out.println("address: "+video.getAddress());
		
		
		// Generate link to page which contains download info
		try {
			
			Class Plugin = cl.loadClass(className,hostPluginsList.get(className),true);
			System.out.println("Class loaded");
			java.lang.reflect.Method processPageReturn = Plugin.getMethod("processUserAddress", new Class[] {String.class});
			
			video.setAddress((String) processPageReturn.invoke(null, new Object[]{video.getAddress()}));
			
			System.out.println("address: "+video.getAddress());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			data = DownloadPages.fetch(video);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			Class Plugin = cl.loadClass(className,hostPluginsList.get(className),true);
			System.out.println("Class loaded");
			java.lang.reflect.Method processPageReturn = Plugin.getMethod("processPageReturn", new Class[] {String.class});
			
			result = (String[]) processPageReturn.invoke(null, new Object[]{data});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		video.setVideoAddress(result[0]);
		
		video.setVideoName(result[1]);
		
	}
	
	
	
	@SuppressWarnings("deprecation")
	private static void youtube(String data, Video video){
		
		Pattern pat = Pattern.compile("flashvars=\"(.*?)\"");
		Matcher match = pat.matcher(data);

		if (match.find()){
			String temp = URLDecoder.decode(match.group(1));
			
			//System.out.println(temp);
			
			// Setting <fmt_code,URL> Hashmap
			pat = Pattern.compile("fmt_url_map=(.*?)&amp;");
			
			match = pat.matcher(temp);
			
			if (match.find()){
				
				String[] temp0 = match.group(1).split(",");
				
				/*Hashtable<String,String> hT = new Hashtable<String,String>();
				
				for (String s: temp0){
					
					String temp1[] = s.split("\\|");
					//System.out.println(s+" "+temp1[0]+" "+temp1[1]);
					hT.put(temp1[0],temp1[1]);
				}*/
				
				video.setVideoAddress(temp0[0].split("\\|")[1]);
				
				//System.out.println(hT.toString()+" "+hT.get("22"));
				
			}
			
			// Setting <fmt_code,video_res> Hashmap
			
			/*pat = Pattern.compile("fmt_map=(.*?)&amp;");
			
			match = pat.matcher(temp);
			
			if (match.find()){
				
				String[] temp0 = match.group(1).split(",");
				int[] biggest = {0,0};
								
				for (String s: temp0){
					
					String temp1[] = s.split("/");
					
					if (biggest[1]<Integer.parseInt(temp1[1].split("x")[0])){
						biggest[1] = Integer.parseInt(temp1[1].split("x")[0]);
						biggest[0] = Integer.parseInt(temp1[0]);
					}
					
					
				}
				
				video.setVideoAddress(hT0.get("22"));
				
				//System.out.println(hT.toString()+" "+hT.get("22"));
				
			}*/
			
		} else
			System.err.println("Error!");
		
		decodePattern(video, data,"<span id=\"eow-title\" class=\"(?:.*)\" dir=\"ltr\" title=\"(.*?)\">");

	}
	
	private static void videobb(String data, Video video){
		
		Pattern pat = Pattern.compile(",\"res\":[(.*?)]");
		Matcher match = pat.matcher(data);

		if (match.find())
			video.setVideoAddress(match.group(1));
		else
			System.err.println("Error!");
		
		decodePattern(video, data,"<h1 class=\"main-title main-sprite-img\">(.*?)</h1>");

	}
	
	private static void getVideoName(Video video){
		
		Pattern pat = Pattern.compile("/(.*?)$");
		Matcher match = pat.matcher(video.getVideoAddress());
		
		if (match.find())
			video.setVideoName(match.group(1));
		else
			System.err.println("Error!");
		
	}
	
	private static void getVideoHost(Video video){
		
		Pattern pat = Pattern.compile("http(?:s|)://(?:www.|)([xhamster|youtube|tube8|videobb]*).");
		Matcher match = pat.matcher(video.getAddress());
		
		if (match.find())
			video.setVideoHost(match.group(1));
		else
			System.err.println("Error!");
				
	}
	
	private static void decodePattern(Video video, String data, String pattern){
		
		Pattern pat = Pattern.compile(pattern);
		Matcher match = pat.matcher(data);

		if (match.find())
			video.setVideoName(match.group(1));
		else
			System.err.println("Error!");
		
	}
	
}
