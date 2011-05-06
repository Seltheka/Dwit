package org.dwit.ui;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DownloadTableRenderer extends JProgressBar implements TableCellRenderer {
		
	public DownloadTableRenderer(){
		
		setMaximum(100);
		
		setStringPainted(true);
		
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean cellHasFocus, int row, int column){
			
		if (value != null)
			setValue(Integer.parseInt(value.toString()));
		
		if (isSelected){

		} else {

		}

		return this;
		
	}
	
}
