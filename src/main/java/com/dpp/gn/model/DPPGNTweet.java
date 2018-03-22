package com.dpp.gn.model;

import java.util.Date;


public class DPPGNTweet {

	   String screenName;
	   String link;
	   String actual_link;
	   String title;
	   Long status_id;
	   Date published_date;
	   Date dateHour;
	   Date dateDay;
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getActual_link() {
		return actual_link;
	}
	public void setActual_link(String actual_link) {
		this.actual_link = actual_link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getStatus_id() {
		return status_id;
	}
	public void setStatus_id(Long status_id) {
		this.status_id = status_id;
	}
	public Date getPublished_date() {
		return published_date;
	}
	public void setPublished_date(Date published_date) {
		this.published_date = published_date;
	}
	public Date getDateHour() {
		return dateHour;
	}
	public void setDateHour(Date dateHour) {
		this.dateHour = dateHour;
	}
	public Date getDateDay() {
		return dateDay;
	}
	public void setDateDay(Date dateDay) {
		this.dateDay = dateDay;
	}
	
	
	
}
