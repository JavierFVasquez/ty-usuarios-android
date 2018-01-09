package com.imaginamos.usuariofinal.taxisya.Model;

import java.util.List;

public class AddressComponentsItem{
	private List<String> types;
	private String shortName;
	private String long_name;

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	public String getShortName(){
		return shortName;
	}

	public void setLong_name(String long_name){
		this.long_name = long_name;
	}

	public String getLong_name(){
		return long_name;
	}

	@Override
 	public String toString(){
		return 
			"AddressComponentsItem{" + 
			"types = '" + types + '\'' + 
			",short_name = '" + shortName + '\'' + 
			",long_name = '" + long_name + '\'' +
			"}";
		}
}