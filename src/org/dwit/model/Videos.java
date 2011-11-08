/**
 *	This class stores the Video objects and acts as an AbstractTableModel
 */

package org.dwit.model;

import org.dwit.model.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

public class Videos extends AbstractTableModel {
	
	private ArrayList<Video> videos = new ArrayList<Video>();
	
	private ResourceBundle lM;
	
	private final String[] header = new String[]{"file_name","file_host","file_status","file_progress"};
	
	public Videos(Map threadList){
		
		synchronized(threadList){
			lM = (ResourceBundle) threadList.get("languagesManager");
		}
		
		for (int i=0;i<header.length;i++){
			header[i] = lM.getString(header[i]);			
		}
		
	}
	
	public Videos(){
		
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return header.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return videos.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
		// TODO problem here when adding video with unknown host
		
		Video video = ((Video)videos.get(arg0));
				
		switch (arg1) {
    	
			case 0:	return video.getVideoName();
			case 1: return video.getVideoHost();
			case 2: return video.getVideoStatus();
			case 3: return video.getVideoProgress();
			
			default: return null;
		
		}
		
	}
	
	public String getColumnName(int index){
		
		return header[index];
		
	}
	
	
	public Video getVideo(int arg0){
		
		return videos.get(arg0);
		
	}
	
	public void setVideoAt(Video video, int arg0){
		
		videos.set(arg0, video);
		
		this.fireTableRowsUpdated(arg0, arg0);
		
	}
	
	public void addVideo(Video video){
				
		videos.add(video);
		
		this.fireTableRowsInserted(getRowCount(), getRowCount());
		
	}
	
	public int getIndexOf(Video video){
		
		return videos.indexOf(video);
		
	}
	
	public void removeVideoAt(int arg0){
		
		videos.remove(arg0);
		
		this.fireTableRowsDeleted(arg0, arg0);
		
	}

}
