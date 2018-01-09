package com.imaginamos.usuariofinal.taxisya.Model;

public class Geometry{
	private Viewport viewport;
	private Location location;

	public void setViewport(Viewport viewport){
		this.viewport = viewport;
	}

	public Viewport getViewport(){
		return viewport;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}
}
