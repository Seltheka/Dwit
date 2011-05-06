package org.dwit.utils;

import org.dwit.main.*;
import org.dwit.model.Video;
import org.dwit.model.Videos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadVideo extends Thread {
	
	private Video video;
	
	private Videos videos;
	
	private int index;
	
	public DownloadVideo(Videos videos, int index ){
		this.videos = videos;
		this.index = index;
		
		updateLocalVideo();
	}

	public static void fetch0(Video video) throws MalformedURLException, IOException{
		 
		BufferedInputStream in = new BufferedInputStream(
				new URL("http://xhamster.com/flv2/"+video.getVideoAddress()).openStream()
				);
		
		FileOutputStream fos = new FileOutputStream(video.getVideoName());
		BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
		
		byte[] data = new byte[1024];
		
		int x=0;
		
		while((x=in.read(data,0,1024))>=0){
			bout.write(data,0,x);
		}
		
		bout.close();
		in.close();
		
	}
	
	public void run(){
		
		updateStatus("Téléchargement");
		
		InputStream input = null;
        FileOutputStream writeFile = null;
        
        System.out.println("Starting download from: "+video.getVideoAddress());

        try
        {
            URL url = new URL(video.getVideoAddress());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestProperty("Connection", "keep-alive");
            
            int fileLength = connection.getContentLength();

            if (fileLength == -1)
            {
                System.out.println("Invalid URL or file. Check your firewall");
                return;
            }
            
            System.out.println(fileLength);

            input = connection.getInputStream();
            //String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            String fileName = video.getVideoName().replace(" ", "_")+".flv";
            writeFile = new FileOutputStream(fileName);
                        
            byte[] buffer = new byte[1024];
            int read;int readyBytes = 0, percentage = 0, bpercentage = 0;

            while ((read = input.read(buffer)) > 0){
                writeFile.write(buffer, 0, read);
                readyBytes += buffer.length;
                percentage = (int)Math.floor((float)readyBytes*100/fileLength);
                if (percentage>bpercentage){
                	updateVideoProgress(percentage);
                }
                bpercentage=percentage;
            }
            writeFile.flush();
        }
        catch (IOException e)
        {
            System.out.println("Error while trying to download the file.");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writeFile.close();
                input.close();
                updateStatus("Terminé");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
		
	}
	
	private void updateVideoProgress(int videoProgress){
		
		video.setVideoProgress(videoProgress);
		
		synchronized(videos){
			
			videos.setVideoAt(video, index);
			
		}
		
	}
	
	private void updateLocalVideo(){
		
		synchronized(videos){
			
			this.video = (Video)videos.getVideo(index);
			
		}
		
	}
	
	private void updateStatus(String status){
		
		video.setVideoStatus(status);
		
		synchronized(videos){
			
			videos.setVideoAt(video, index);
			
		}
		
	}
	
}
