/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package org.dwit.ui;

/*
 * BorderLayoutDemo.java
 *
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.dwit.main.DownloadManager;
import org.dwit.model.Video;
import org.dwit.model.Videos;
import org.dwit.ui.DownloadTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class General implements ActionListener,MouseListener{
    public boolean RIGHT_TO_LEFT = false;
    
    protected JButton bd,bg;
    protected JPanel tempList;
    
    protected JFrame frame;
    
    protected JDialog newDownloads;
    
    private Map threadList = Collections.synchronizedMap(new HashMap());
    
    private Videos videosModel = new Videos();
    
    private Videos videos = new Videos();
    
    private Preferences preferences;
    
    public void addComponentsToPane(Container pane) {
        
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
        
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(
                    java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }       
        
        pane.add(createDownloadInfo(), BorderLayout.PAGE_START);
        
        JDialog dialog = new Dialog(frame,"Downloader",this);
        dialog.pack();
        
        tempList = new DownloadTable(videos);
        
        pane.add(tempList, BorderLayout.CENTER);
        
        newDownloads = new Dialog(frame,"Downloader",this);
        newDownloads.pack();
        
        
        JPanel pageEnd = new JPanel();
        
        JTextField nbrDL = new JTextField("4",3);
        
        nbrDL.setHorizontalAlignment(JTextField.RIGHT);
        
        pageEnd.add(nbrDL);
        
        pane.add(pageEnd, BorderLayout.PAGE_END);
        
        
    }
    
    public JPanel createDownloadInfo(){
    	
    	JPanel temp = new JPanel();
    	
    	temp.add(new JLabel("Test"));
    	
    	return temp;
    	
    }    
    
    public void setMenu(){
    	
    	 // Create the menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Fichier");
        menuBar.add(menu);
        
        JMenuItem item = new JMenuItem("Ajouter liens");
        
        item.setMnemonic(KeyEvent.VK_N);
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            	newDownloads.setLocationRelativeTo(frame);
                newDownloads.setVisible(true);
            }
        });
        
        menu.add(item);

        item = new JMenuItem("Quitter");
        
        item.setMnemonic(KeyEvent.VK_Q);
        item.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            	System.exit(0);
            }
        });
        
        menu.add(item);
        
        menu = new JMenu("Préférences");
        menuBar.add(menu);
        
        item = new JMenuItem("Générales");
        
        item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            	preferences.setVisible(true);
            }
        });
        
        menu.add(item);
        
        item = new JMenuItem("Plugins");
        menu.add(item);
        
        item = new JMenuItem("Avancé");
        menu.add(item);
        
        menu = new JMenu("Aide");
        menuBar.add(menu);
        
        item = new JMenuItem("Aide Générale");
        menu.add(item);
        
        item = new JMenuItem("Crédits");
        menu.add(item);

        // Install the menu bar in the frame
        frame.setJMenuBar(menuBar);
    	
    }
    
    public JPanel createDownloadForm(){
    	
    	JPanel temp = new JPanel();
    	
    	temp.setLayout(new BoxLayout(temp, BoxLayout.PAGE_AXIS));
    	temp.add(Box.createRigidArea(new Dimension(0,5)));
    	temp.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	
    	JLabel temp_label = new JLabel("Entrer les URLs dans la zone qui suit.");
    	temp_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    	
        temp.add(temp_label);
        
        JTextArea temp_text = new JTextArea();
        	
        temp_text.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        temp.add(temp_text);
        
        JPanel temp_buttonPane = new JPanel();
        
        temp_buttonPane.setLayout(new BoxLayout(temp_buttonPane, BoxLayout.LINE_AXIS));
        temp_buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        temp_buttonPane.add(Box.createHorizontalGlue());
        
        temp_buttonPane.add(new JButton("Start Download"));
        
        temp.add(temp_buttonPane);
    	
    	return temp;
    	
    }
    

    public General(Map threadList) {
        
    	this.threadList = threadList;
    	
        //Create and set up the window.
    	frame = new JFrame("BorderLayoutDemo");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        setMenu();
        addComponentsToPane(frame.getContentPane());
        
        preferences = new Preferences(threadList);
        
        
        //Use the content pane's default BorderLayout. No need for
        //setLayout(new BorderLayout());
        //Display the window.
        frame.setTitle("Dwit 0.0.5");
        frame.setVisible(true);
        frame.setResizable(true);
        
        //preferences.setVisible(true);
                
    	synchronized(threadList){
    		((DownloadManager)threadList.get("downloadManager")).setVideosReference(videos);
    	}

    }

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void processLinks(String userInput){
		
		String[] temp = userInput.split("\n");
		
		for (int i=0;i<temp.length;i++){
			synchronized(threadList){
				((DownloadManager)threadList.get("downloadManager")).addVideoToQueue(new Video(temp[i]));
			}
		}
		
		synchronized(((DownloadManager)threadList.get("downloadManager"))){
			((DownloadManager)threadList.get("downloadManager")).notify();
		}
		
	}
    
}