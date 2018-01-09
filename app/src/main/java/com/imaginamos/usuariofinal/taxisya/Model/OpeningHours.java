package com.imaginamos.usuariofinal.taxisya.Model;

import java.util.List;

public class OpeningHours{
	private boolean openNow;
	private List<Object> weekdayText;

	public void setOpenNow(boolean openNow){
		this.openNow = openNow;
	}

	public boolean isOpenNow(){
		return openNow;
	}

	public void setWeekdayText(List<Object> weekdayText){
		this.weekdayText = weekdayText;
	}

	public List<Object> getWeekdayText(){
		return weekdayText;
	}
}