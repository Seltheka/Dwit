package org.dwit.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


import org.dwit.model.Video;
import org.dwit.ui.General;
import org.dwit.utils.*;

public class Main {
	
	public Logger logger = Logger.getLogger("MyLog");
	
	private static String reply;
	
	private static Map threadList = Collections.synchronizedMap(new HashMap());

	public static void main(String args[]) throws Exception{
		
		DownloadManager dM = new DownloadManager();
		
		threadList.put("downloadManager", dM);
		
		dM.start();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                General ui = new General(threadList);
            }
        });
		
		
		
		
		
		/*ask(new String[] {
				"Bienvenue dans Pron Downloader 0.0.1",
    			"Veuillez choisir le mode",
    			"- avec avancement (true)",
    			"- sans avancement (false)"
    	});
		
		Settings settings = new Settings(Boolean.valueOf(reply));
		
		ask(new String[] {
				"\n",
    			"Veuillez indiquer le lien de la video"
    	});
		
		Video video = new Video(reply);
		
		DownloadPages.Fetch(video);
		
		DownloadVideo.fetch(video);*/
						
	}
	
    private static void ask(String[] args){

        for (int i=0;i<args.length;i++)
            System.out.println(args[i]);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
				reply = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}


    }
	
}
