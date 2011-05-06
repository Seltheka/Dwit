package org.dwit.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.dwit.main.DownloadManager;
import org.dwit.main.Main;
import org.dwit.utils.PluginUtils;

public class Preferences extends JFrame{
	
	private String[] labels = {"Nombre de téléchargements simultanés"};
	
	private Map threadList = Collections.synchronizedMap(new HashMap());
	
	private Map pluginsMap = Collections.synchronizedMap(new HashMap());


	public Preferences(Map threadList){
		
		this.threadList = threadList;
		
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
		
		/*SpringLayout layout = new SpringLayout();
		
		panel.setLayout(layout);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		SpringUtilities.makeCompactGrid(panel,
										labels.length, 2,
										5, 5,
										5, 5);
		
		
		JLabel label = new JLabel("Nombre de téléchargements simultanés:");
		layout.putConstraint(SpringLayout.EAST, panel, 5, SpringLayout.EAST, label);
		panel.add(label);
		
		layout.putConstraint(SpringLayout.WEST, label,
                5,
                SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, label,
                5,
                SpringLayout.NORTH, panel);
		
		JTextField textField = new JTextField("bwaaaaaaaah",15);
		
		panel.add(textField);
		
		layout.putConstraint(SpringLayout.WEST, textField,
                5,
                SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.NORTH, textField,
                5,
                SpringLayout.NORTH, panel);
		
		/*panel.add(new JTextField(), GridBagConstraints.LINE_END);
		
		panel.add(new JButton(), GridBagConstraints.LAST_LINE_END);*/
		
		pane.add(tabbedPane, BorderLayout.CENTER);
		
	}
	
	private JPanel generalForm(){
		
		String[] labels = {"Nombre de téléchargements simultanés max."};
		
		JPanel panel = new JPanel();
		
		SpringLayout layout = new SpringLayout();
		
		panel.setLayout(layout);
		
		//panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		JLabel label = new JLabel("Nombre de téléchargements simultanés:");
		layout.putConstraint(SpringLayout.EAST, panel, 5, SpringLayout.EAST, label);
		panel.add(label);
		
		layout.putConstraint(SpringLayout.WEST, label,
                5,
                SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, label,
                10,
                SpringLayout.NORTH, panel);
		
		JTextField textField = new JTextField("bwaaaaaaaah",15);
		
		panel.add(textField);
		
		layout.putConstraint(SpringLayout.WEST, textField,
                5,
                SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.NORTH, textField,
                5,
                SpringLayout.NORTH, panel);
		
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
