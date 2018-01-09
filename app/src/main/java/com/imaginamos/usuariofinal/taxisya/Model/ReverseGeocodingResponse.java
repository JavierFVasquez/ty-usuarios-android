package com.imaginamos.usuariofinal.taxisya.Model;

import java.util.List;

public class ReverseGeocodingResponse{
	private List<ResultsItem> results;
	private String status;

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

	@Override
 	public String toString(){
		return 
			"ReverseGeocodingResponse{" + 
			"results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}