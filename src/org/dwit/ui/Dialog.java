package org.dwit.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.dwit.main.DownloadManager;

public class Dialog extends JDialog {
	
	private JDialog jDialog;
	
	private Map threadList = Collections.synchronizedMap(new HashMap());
	
	private final JTextArea master_text;

	public Dialog(JFrame parentFrame, String title, final General parentClass){
				
		//super(parent, true);
				
		getContentPane().setLayout(
			    new BorderLayout()
		);
		
		setTitle("Ajouter des liens");
				
		JPanel master = new JPanel();
		master.setLayout(new BorderLayout());
    	master.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
    	JLabel master_label = new JLabel("Entrer les URLs dans la zone qui suit. (un URL par ligne)");
    	master_label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    	
        master.add(master_label, BorderLayout.PAGE_START);
        
        master_text = new JTextArea(5,20);
        	
        master_text.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        master.add(master_text, BorderLayout.CENTER);
        
        JPanel master_buttonPane = new JPanel();
        
        master_buttonPane.setLayout(new BoxLayout(master_buttonPane, BoxLayout.LINE_AXIS));
        master_buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        master_buttonPane.add(Box.createHorizontalGlue());
        
        JButton start = new JButton("Start Download");
        
        start.addActionListener(
        		new ActionListener(){
	        		public void actionPerformed(ActionEvent ae){
	        			parentClass.processLinks(master_text.getText());
	        			master_text.setText("");
	        			setVisible(false);
	                }
        		}
        );
        
        
        master_buttonPane.add(start);
        
        master.add(master_buttonPane, BorderLayout.PAGE_END);
                        
        setContentPane(master);
        
        addComponentListener( new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
	
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
        		master_text.requestFocus();
			}
        });
		
	}

}
