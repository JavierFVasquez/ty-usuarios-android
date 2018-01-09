package com.imaginamos.usuariofinal.taxisya.Model;

import java.util.List;

public class RoutesItem{
	private String summary;
	private String copyrights;
	private List<LegsItem> legs;
	private List<Object> warnings;
	private Bounds bounds;
	private OverviewPolyline overview_polyline;
	private List<Object> waypoint_order;

	public void setSummary(String summary){
		this.summary = summary;
	}

	public String getSummary(){
		return summary;
	}

	public void setCopyrights(String copyrights){
		this.copyrights = copyrights;
	}

	public String getCopyrights(){
		return copyrights;
	}

	public void setLegs(List<LegsItem> legs){
		this.legs = legs;
	}

	public List<LegsItem> getLegs(){
		return legs;
	}

	public void setWarnings(List<Object> warnings){
		this.warnings = warnings;
	}

	public List<Object> getWarnings(){
		return warnings;
	}

	public void setBounds(Bounds bounds){
		this.bounds = bounds;
	}

	public Bounds getBounds(){
		return bounds;
	}

	public void setOverview_polyline(OverviewPolyline overview_polyline){
		this.overview_polyline = overview_polyline;
	}

	public OverviewPolyline getOverview_polyline(){
		return overview_polyline;
	}

	public void setWaypoint_order(List<Object> waypoint_order){
		this.waypoint_order = waypoint_order;
	}

	public List<Object> getWaypoint_order(){
		return waypoint_order;
	}
}