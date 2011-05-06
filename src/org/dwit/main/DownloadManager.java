package org.dwit.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;

import org.dwit.model.Video;
import org.dwit.model.Videos;
import org.dwit.utils.DecodeLinks;
import org.dwit.utils.DownloadPages;
import org.dwit.utils.DownloadVideo;
import org.dwit.utils.PluginUtils;

public class DownloadManager extends Thread {
		
	public Queue<Integer> queue = new LinkedList();
	
	private Videos videos;
		
	private Map<String, File> hostPluginsList = Collections.synchronizedMap(new HashMap());
	
	public DownloadManager(){		
		
		synchronized(hostPluginsList){
		
			hostPluginsList = PluginUtils.getPluginList("plugins.hosts");
			
		}
		
	}
	
	public void setVideosReference(Videos videos){
		
		this.videos = videos;
		
	}
	
	public synchronized void run(){
		
		while(true){
					
			while(!queue.isEmpty()){
				
				int index = queue.poll();
				
				Video video;
				
				synchronized(videos){
					video = (Video)videos.getVideo(index);
				}
				
				try{
					DecodeLinks.analyze(video,hostPluginsList);
				} catch (Exception e){
					System.err.println("Erreur fetching");
					break;
				}
				
				if (video.getVideoAddress()==null){
					
					videos.removeVideoAt(index);
					System.err.println("No video address");
					break;
					
				}
				
				DownloadVideo dV = new DownloadVideo(videos,index);
				
				dV.start();
				//threadList.put(video.getVideoName(), dV);
				
				System.out.println("New DownloadThread started from: "+video.getVideoName());
								
			}
			
			System.out.println("waiting");
			
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("finished");
		
		}
		
	}
	
	public void addVideoToQueue(Video video){
		int index;
		synchronized(videos){
			videos.addVideo(video);
			index = videos.getIndexOf(video);
		}
		
		synchronized(queue){
			
			queue.offer(index);
			
		}
				
	}
	
	public Map getPlugins(){
		
		return hostPluginsList;

	}

}
