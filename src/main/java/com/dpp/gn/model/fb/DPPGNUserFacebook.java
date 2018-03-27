package com.dpp.gn.model.fb;


import java.util.LinkedList;
import java.util.List;


public class DPPGNUserFacebook {
	
	String id;
	String name;
	String location;
	String locationId;
	String locationName;
	Integer ageRange;
	String firstName;
	String middleName;
	String lastName;
	String gender;
	String url;
	String shortName;
	List<String> device = new LinkedList<String>();
	String nameFormat;
	
	List<DPPGNFeedFacebook> listfeed = new LinkedList<DPPGNFeedFacebook>();
	List<DPPGNLikeFacebook> listlike = new LinkedList<DPPGNLikeFacebook>();
	List<DPPGNMovieFacebook> listmovie = new LinkedList<DPPGNMovieFacebook>();
	List<DPPGNMusicFacebook> listmusic = new LinkedList<DPPGNMusicFacebook>();
	List<DPPGNVideoFacebook> listvideo = new LinkedList<DPPGNVideoFacebook>();
	
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<String> getDevice() {
		return device;
	}
	public void setDevice(List<String> device) {
		this.device = device;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String location) {
		this.locationName = locationName;
	}
	public Integer getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(Integer ageRange) {
		this.ageRange = ageRange;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getNameFormat() {
		return nameFormat;
	}
	public void setNameFormat(String nameFormat) {
		this.nameFormat = nameFormat;
	}
	public List<DPPGNFeedFacebook> getListfeed() {
		return listfeed;
	}
	public void setListfeed(List<DPPGNFeedFacebook> listfeed) {
		this.listfeed = listfeed;
	}
	public List<DPPGNLikeFacebook> getListlike() {
		return listlike;
	}
	public void setListlike(List<DPPGNLikeFacebook> listlike) {
		this.listlike = listlike;
	}
	public List<DPPGNMovieFacebook> getListmovie() {
		return listmovie;
	}
	public void setListmovie(List<DPPGNMovieFacebook> listmovie) {
		this.listmovie = listmovie;
	}
	public List<DPPGNMusicFacebook> getListmusic() {
		return listmusic;
	}
	public void setListmusic(List<DPPGNMusicFacebook> listmusic) {
		this.listmusic = listmusic;
	}
	public List<DPPGNVideoFacebook> getListvideo() {
		return listvideo;
	}
	public void setListvideo(List<DPPGNVideoFacebook> listvideo) {
		this.listvideo = listvideo;
	}
	
	
	
	

}
