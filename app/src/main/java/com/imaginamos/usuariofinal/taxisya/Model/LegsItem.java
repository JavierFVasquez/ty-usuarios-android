package com.imaginamos.usuariofinal.taxisya.Model;

import java.util.List;

public class LegsItem{
	private Duration duration;
	private StartLocation start_location;
	private Distance distance;
	private String startAddress;
	private EndLocation end_location;
	private String endAddress;
	private List<Object> viaWaypoint;
	private List<StepsItem> steps;
	private List<Object> trafficSpeedEntry;

	public void setDuration(Duration duration){
		this.duration = duration;
	}

	public Duration getDuration(){
		return duration;
	}

	public void setStart_location(StartLocation start_location){
		this.start_location = start_location;
	}

	public StartLocation getStart_location(){
		return start_location;
	}

	public void setDistance(Distance distance){
		this.distance = distance;
	}

	public Distance getDistance(){
		return distance;
	}

	public void setStartAddress(String startAddress){
		this.startAddress = startAddress;
	}

	public String getStartAddress(){
		return startAddress;
	}

	public void setEnd_location(EndLocation end_location){
		this.end_location = end_location;
	}

	public EndLocation getEnd_location(){
		return end_location;
	}

	public void setEndAddress(String endAddress){
		this.endAddress = endAddress;
	}

	public String getEndAddress(){
		return endAddress;
	}

	public void setViaWaypoint(List<Object> viaWaypoint){
		this.viaWaypoint = viaWaypoint;
	}

	public List<Object> getViaWaypoint(){
		return viaWaypoint;
	}

	public void setSteps(List<StepsItem> steps){
		this.steps = steps;
	}

	public List<StepsItem> getSteps(){
		return steps;
	}

	public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry){
		this.trafficSpeedEntry = trafficSpeedEntry;
	}

	public List<Object> getTrafficSpeedEntry(){
		return trafficSpeedEntry;
	}
}