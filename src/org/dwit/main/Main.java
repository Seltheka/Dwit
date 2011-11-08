package org.dwit.main;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import org.dwit.db.DatabaseUtils;
import org.dwit.ui.General;
import org.dwit.utils.*;

public class Main {
	
	public static Logger logger;
	
	private static String reply;
	
	private static Map threadList = Collections.synchronizedMap(new HashMap());
	
	private final static Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();

	public static void main(String args[]) throws Exception{
		
		//ClipBoard cb = new ClipBoard();
		
		//initClipboard();
		
		initLogger();
		
		/*Runtime.getRuntime().exec("open .");
		logger.info(System.getProperty("os.name"));*/
		
		//DatabaseUtils.connect();
		
		// Adding Properties Manager to threadList
		PropertiesManager pM = new PropertiesManager();
		pM.list(System.out);
		threadList.put("propertiesManager", pM);
		logger.info("Properties loaded ("+pM.size()+")");
				
		// TODO: add default locale from property manager
		// Adding Languages Manager to threadList
		ResourceBundle lM = ResourceBundle.getBundle("lang.lang", Locale.FRANCE, new LanguageClassLoader());
		threadList.put("languagesManager", lM);
		logger.info("Language loaded ("+lM.getLocale()+")");

		/*Enumeration<String> eM = lM.getKeys();
			
		while (eM.hasMoreElements()) {
			String key = eM.nextElement();
			System.out.println("elem: "+key+"value: "+lM.getString(key));
		}*/
				
		// Adding Download Manager to threadList
		DownloadManager dM = new DownloadManager(threadList);
		threadList.put("downloadManager", dM);
		dM.start();
		logger.info("Download Manager started");
		
		logger.info("Initialization completed - Starting UserInterface");
		
		//Thread.sleep(5000);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                General ui = new General(threadList);
            }
        });
						
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
    
    private static void initClipboard(){
    	
    	
    	processClipboard(cp);
    	
		cp.addFlavorListener(new FlavorListener(){
			
			public void flavorsChanged(FlavorEvent e){
				processClipboard(cp);
			}
			
		});
    	
    }
    
    private static void initLogger(){
    	
    	try {

    	    FileHandler handler = new FileHandler("general.log", true);
    	    handler.setFormatter(new SimpleFormatter());
    	    logger = Logger.getLogger("org.dwit");
    	    logger.addHandler(handler);
    	    threadList.put("logger", logger);
    	    
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    private static void processClipboard(Clipboard temp){
    	
    	try {
			System.out.println("Patate: "+temp.getContents(null).getTransferData(DataFlavor.stringFlavor));
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
}
