package org.dwit.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.dwit.model.Video;
import org.dwit.model.Videos;

public class DownloadTable extends JPanel{
	
	public JTable table;
	
	public JScrollPane tablePane;
	
	public DownloadTable(Videos model){
		
		/* Setting layout */
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		GridBagConstraints layoutConstraints = new GridBagConstraints();		
		
		model.addVideo(new Video("http://patate.com"));
		
		table = new JTable(model);
		
		TableColumn col = table.getColumnModel().getColumn(3);
		col.setCellRenderer(new DownloadTableRenderer());
		
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(1, 1, 1, 1);
		//layoutConstraints.anchor = GridBagConstraints.CENTER;
		layoutConstraints.weightx = 1.0; layoutConstraints.weighty = 1.0;
		tablePane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane. HORIZONTAL_SCROLLBAR_NEVER);
		layout.setConstraints(tablePane, layoutConstraints);
				
		add(tablePane);
		
	}
	
}
