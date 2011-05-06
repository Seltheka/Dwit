package org.dwit.model;

public class Video extends Object{
	
	/* user-input URL */
	private String address;
	
	/* final video URL */
	private String videoAddress;
	
	/* final video name */
	private String videoName = "DŽcodage...";
	
	/* video host */
	private String videoHost = "unknown";
	
	private Long size = null;
	
	private int videoProgress = 0;
	
	private String videoStatus = "En attente";
	
	
	
	public Video(String address){
		this.setAddress(address);
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getVideoAddress(){
		return this.videoAddress;
	}
	
	public void setVideoAddress(String videoAddress){
		this.videoAddress = videoAddress;
	}
	
	public String getVideoName(){
		return this.videoName;
	}
	
	public void setVideoName(String videoName){
		this.videoName = videoName;
	}
	
	public String getVideoHost(){
		return this.videoHost;
	}
	
	public void setVideoHost(String videoHost){
		this.videoHost = videoHost;
	}
	
	public String getVideoStatus(){
		return this.videoStatus;
	}
	
	public void setVideoStatus(String videoStatus){
		this.videoStatus = videoStatus;
	}
	
	public int getVideoProgress(){
		return this.videoProgress;
	}
	
	public void setVideoProgress(int videoProgress){
		this.videoProgress = videoProgress;
	}
	
	
}
