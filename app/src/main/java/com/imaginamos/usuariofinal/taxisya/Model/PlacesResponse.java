package com.imaginamos.usuariofinal.taxisya.Model;

import java.util.List;

public class PlacesResponse{
	private List<Object> htmlAttributions;
	private List<ResultsItem> results;
	private String status;

	public void setHtmlAttributions(List<Object> htmlAttributions){
		this.htmlAttributions = htmlAttributions;
	}

	public List<Object> getHtmlAttributions(){
		return htmlAttributions;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}