package org.dwit.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.dwit.main.DownloadManager;
import org.dwit.utils.PropertiesManager;

public class Preferences extends JFrame{
		
	private Map threadList = Collections.synchronizedMap(new HashMap());
	
	private Map pluginsMap = Collections.synchronizedMap(new HashMap());
	
	private PropertiesManager pM;
	
	private ResourceBundle lM;


	public Preferences(Map threadList){
		
		this.threadList = threadList;
		
		synchronized(threadList){
			this.pM = ((PropertiesManager)threadList.get("propertiesManager"));
			this.lM = ((ResourceBundle)threadList.get("languagesManager"));
		}
		
		setTitle("Preferences");
		
		setSize(500,500);
		
		setLocationRelativeTo(null);
		
		addComponentsToPane(this.getContentPane());
		
	}
	
	public void addComponentsToPane(Container pane) {
		
		pane.setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("General", null,
                          generalForm() ,
                          null); //tooltip text
        tabbedPane.addTab("Plugins", null,
        				  pluginsForm() ,
			              null); //tooltip text
        tabbedPane.addTab("Avancé", null,
        				new JPanel() ,
        				null); 
		
		pane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel save = new JPanel();
		
		save.add(new JButton("Enregistrer"), BorderLayout.CENTER);
		
		pane.add(save, BorderLayout.PAGE_END);
		
		
	}
	
	private JPanel generalForm(){
				
		JPanel panel = new JPanel(new SpringLayout());
		String code[] = {"max_download_threads","max_bandwidth","language","proxy_address","proxy_port","download_directory"};		
		Class<?> classes[] = {};
		JLabel l;
		
		
		int numPairs = code.length;
		
		for (int i = 0; i < numPairs; i++) {
			if (lM.containsKey(code[i]))
				l = new JLabel(lM.getString(code[i]), JLabel.TRAILING);
			else
				l = new JLabel(code[i], JLabel.TRAILING);
			panel.add(l);
			JComponent textField;
			/*if (labels[i].equals("Password")){
				textField = new JPasswordField(10);
			} else {*/
			if (pM.containsKey(code[i]))
				textField = new JTextField(pM.getProperty(code[i]), 10);
			else
				textField = new JTextField(null, 10);
			
			if (code[i].contains("directory")){
				
				//textField.setEnabled(false);
				textField.addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						JDirectoryChooser chooser = new JDirectoryChooser();
						chooser.setVisible(true);
						System.out.println("done");
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
		        });
			}
			
			l.setLabelFor(textField);
			panel.add(textField);
		}

		SpringUtils.makeCompactGrid(panel,
		                                        numPairs, 2, //rows, cols
		                                        10, 10,        //initX, initY
		                                        5, 5 );       //xPad, yPad
		
		return panel;
	}
	
	
	private JPanel pluginsForm(){
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
				
		synchronized(threadList){
			pluginsMap = Collections.synchronizedMap(((DownloadManager)threadList.get("downloadManager")).getPlugins());
		}
		
		List pluginsList;
		
		synchronized(pluginsMap){				
			pluginsList = new ArrayList(pluginsMap.keySet());
		}
			 
		JList pluginsJList = new JList(pluginsList.toArray());
		
		panel.add(pluginsJList, BorderLayout.CENTER);
		
		return panel;
		
	}
	
	
}
