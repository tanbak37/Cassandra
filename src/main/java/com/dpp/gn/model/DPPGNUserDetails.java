package com.dpp.gn.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;





public class DPPGNUserDetails {

	
	
	String userName;
	String screenName;
	String location;
	Long tweetId;
	Date createdDate;
	String url;
	List<String> friendList = new LinkedList<String>();
	List<DPPGNFriendDetails> friendsList = new LinkedList<DPPGNFriendDetails>();
	List<String> followerList = new LinkedList<String>();
	List<DPPGNFriendDetails> followersList = new LinkedList<DPPGNFriendDetails>();
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String username) {
		this.userName = username;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getTweetId() {
		return tweetId;
	}
	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getFriendList() {
		return friendList;
	}
	public void setFriendList(List<String> friendList) {
		this.friendList = friendList;
	}
	public List<DPPGNFriendDetails> getFriendsList() {
		return friendsList;
	}
	public void setFriendsList(List<DPPGNFriendDetails> friendsList) {
		this.friendsList = friendsList;
	}
	public List<String> getFollowerList() {
		return followerList;
	}
	public void setFollowerList(List<String> followerList) {
		this.followerList = followerList;
	}
	public List<DPPGNFriendDetails> getFollowersList() {
		return followersList;
	}
	public void setFollowersList(List<DPPGNFriendDetails> followersList) {
		this.followersList = followersList;
	}
	
	
	
	
	
	
}
