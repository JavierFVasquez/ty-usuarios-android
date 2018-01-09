package com.imaginamos.usuariofinal.taxisya.Model;

import com.imaginamos.usuariofinal.taxisya.comm.Connect;

import java.util.List;

public class ResultsItem{
	private String reference;
	private List<String> types;
	private String scope;
	private String icon;
	private String name;
	private OpeningHours openingHours;
	private double rating;
	private Geometry geometry;
	private String vicinity;
	private String id;
	private String placeId;
	private String formatted_address;
	private List<AddressComponentsItem> address_components;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setScope(String scope){
		this.scope = scope;
	}

	public String getScope(){
		return scope;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOpeningHours(OpeningHours openingHours){
		this.openingHours = openingHours;
	}

	public OpeningHours getOpeningHours(){
		return openingHours;
	}

	public void setRating(double rating){
		this.rating = rating;
	}

	public double getRating(){
		return rating;
	}

	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public void setVicinity(String vicinity){
		this.vicinity = vicinity;
	}

	public String getVicinity(){
		return vicinity;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}


	public List<AddressComponentsItem> getAddress_components() {
		return address_components;
	}

	public void setAddress_components(List<AddressComponentsItem> address_components) {
		this.address_components = address_components;
	}
}