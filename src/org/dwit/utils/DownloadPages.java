package org.dwit.utils;

import org.dwit.model.Video;
import org.dwit.utils.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class DownloadPages {
	
	private static URL url = null;
	
	public static String fetch(Video video) throws IOException{
				
		url = new URL(video.getAddress());
		
		System.out.println(video.getAddress());
				
		HttpURLConnection uc = (HttpURLConnection) url.openConnection();
		
		uc.setRequestProperty("User-agent", "Mozilla/5.0");
		
		// Set request method
		uc.setRequestMethod("GET");
				
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));

		String decodedString;
		String end = "";

		while ((decodedString = in.readLine()) != null) {
			end += decodedString;
			//System.out.println(decodedString);
		}
		
		in.close();

		uc.disconnect();
				
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter("dump.tmp"));
		    out.write(end);
		    out.close();
		} catch (IOException e) {
			System.err.println("Error writing data!");
		}
		
		return end;
		
	}
	
	
	private static String addParameter(String URL, String key, String data) throws UnsupportedEncodingException {

		if (URL.indexOf("?") == -1)
			return URL += "?" + URLEncoder.encode(key, "utf-8") + "="
					+ URLEncoder.encode(data, "utf-8");
		else
			return URL += "&" + URLEncoder.encode(key, "utf-8") + "="
					+ URLEncoder.encode(data, "utf-8");
		
	}

}
