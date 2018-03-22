package com.dpp.sb.twitterintegration;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dpp.sb.cassandradb.DPPSBStoreToCassandra;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import com.dpp.gn.model.DPPGNFriendDetails;
import com.dpp.gn.model.DPPGNTweet;
import com.dpp.gn.model.DPPGNUserDetails;



public class DPPSBReadTweets {
	
	public static final Log LOG = LogFactory.getLog(DPPSBReadTweets.class);
	public static DPPSBStoreToCassandra storeToCassandra = null;
	private static String oauthAccessToken = "87689098-7BtC1V0Ehjd092T0P7vZM9pl3udHpmfACwnqAa26y";
	private static String oauthAccessTokenSecret = "lczY0fdeaqGKBXs0KtJxxxYieAerRwycuQyli7ABzPvMc";
	private static String consumerKey = "CPmLmjUTjTunphmskTw7kI0kc";
	private static String consumerSecret = "bkVqjC9L7qVdZw7RdE4YhexK5VJkSNzvpp4wenU9yxqDRR0RkG";
	public static List<DPPGNTweet> tweets = new LinkedList<DPPGNTweet>();
	//Utils util = new Utils();
	
	
	  public static void main(String[] args) throws Exception {
	        DPPSBReadTweets twitterClient = new DPPSBReadTweets();
	        storeToCassandra = new DPPSBStoreToCassandra();
	        
	      
	        /**
	         * load the properties files
	         */
	        
	        Properties properties = twitterClient
	                .loadProperties("twitterCredentials");
	        storeToCassandra.properties = twitterClient.loadProperties("queries");
	        /**
	         * Assigning to the variables
	         */
	        oauthAccessToken = properties.getProperty("oauthAccessToken");
	        oauthAccessTokenSecret = properties
	                .getProperty("oauthAccessTokenSecret");
	        consumerKey = properties.getProperty("consumerKey");
	        consumerSecret = properties.getProperty("consumerSecret");
	 
	        twitterClient.process();
	        
	  }
	        
	        public void process() {
	        	 
	            ConfigurationBuilder cb = new ConfigurationBuilder();
	            cb.setDebugEnabled(true);
	            cb.setOAuthConsumerKey(consumerKey);
	            cb.setOAuthConsumerSecret(consumerSecret);
	            cb.setOAuthAccessToken(oauthAccessToken);
	            cb.setOAuthAccessTokenSecret(oauthAccessTokenSecret);
	            //cb.setUseSSL(true);
	            Twitter twitter = new TwitterFactory(cb.build()).getInstance();
	            System.out.println("**************success ");  
	            
	            
	        try {
	       
	        	 DPPGNUserDetails userDetails = new DPPGNUserDetails();
	        	 
	             userDetails.setScreenName(twitter.getScreenName());
	             userDetails.setTweetId((twitter.getId()));
	             userDetails
	                     .setUserName(twitter.showUser(twitter.getId()).getName());
	             userDetails.setLocation(twitter.showUser(twitter.getId())
	                     .getLocation());
	             userDetails.setCreatedDate(twitter.showUser(twitter.getId())
	                     .getCreatedAt());
	             userDetails.setUrl(twitter.showUser(twitter.getId()).getURL());
	             System.out.println("FOLLOWING ********" + twitter);
	  
	             List<DPPGNFriendDetails> friendsDetailsList = new LinkedList<DPPGNFriendDetails>();
	             List<String> friendsName = new LinkedList<String>();
	  
	             insertFriendsDetails(twitter, friendsName, friendsDetailsList, userDetails);
	             insertFollowersDetails(twitter, userDetails);
	  
	             System.out.println("Inserted!!");
	        	
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }	
	        }
	        /**
	         * Insert friends details in Cassandra based on user
	         * 
	         * @param twitter
	         * @param friendsName
	         * @param friendsDetailsList
	         * @param userDetails
	         * @throws Exception
	         */
	        
	        
	        
	        private void insertFriendsDetails(Twitter twitter,
	                List<String> friendsName, List<DPPGNFriendDetails> friendsDetailsList,
	                DPPGNUserDetails userDetails) throws Exception {
	            for (Long userId : twitter.getFriendsIDs(-1).getIDs()) {
	     
	                DPPGNFriendDetails friendsDetail = new DPPGNFriendDetails();
	                User user = twitter.showUser(userId);
	     
	                friendsDetail.setUserName(user.getName());
	                friendsName.add(user.getScreenName());
	                if (user.getURL() == null)
	                    friendsDetail.setUrl("");
	                else
	                    friendsDetail.setUrl(user.getURL());
	                friendsDetail.setCreatedDate(new Date());
	                        //.toString(), "EE MMM dd HH:mm:ss Z yyyy"));
	                friendsDetail.setScreen_name(user.getScreenName());
	                friendsDetail.setLocation(user.getLocation());
	                friendsDetail.setTweetID(user.getId());
	                friendsDetailsList.add(friendsDetail);
	            }
	     
	            userDetails.setFriendsList(friendsDetailsList);
	            userDetails.setFriendList(friendsName);
	            System.out.println("FOLLOWERS ********");
	     
	            storeToCassandra.connectToCassandra(); //
	            storeToCassandra.insertUserDetails(userDetails); //
	            storeToCassandra.disconnetFromCassandra();
	        }
	        
	        
	        private void insertFollowersDetails(Twitter twitter, DPPGNUserDetails userDetails)
	                throws Exception {
	            List<DPPGNFriendDetails> followerList = new LinkedList<DPPGNFriendDetails>();
	            List<String> followersName = new LinkedList<String>();
	            for (Long userId : twitter.getFollowersIDs(-1).getIDs()) {
	                DPPGNFriendDetails friendsDetail = new DPPGNFriendDetails();
	                User user = twitter.showUser(userId);
	                friendsDetail.setUserName(user.getName());
	                followersName.add(user.getScreenName());
	                if (user.getURL() == null)
	                    friendsDetail.setUrl("");
	                else
	                    friendsDetail.setUrl(user.getURL());
	                friendsDetail.setCreatedDate(new Date());
	                        //.toString(), "EE MMM dd HH:mm:ss Z yyyy"));
	                friendsDetail.setScreen_name(user.getScreenName());
	                friendsDetail.setLocation(user.getLocation());
	                friendsDetail.setTweetID(user.getId());
	                followerList.add(friendsDetail);
	            }
	            userDetails.setFollowersList(followerList);
	            userDetails.setFollowerList((followersName)); //
	            storeToCassandra.connectToCassandra(); //
	            storeToCassandra.insertUserDetails(userDetails); //
	            storeToCassandra.disconnetFromCassandra();
	     
	        }
	        
	        
	        /**
	         * 
	         * @param propertiesFileName
	         * @return the properties values as Properties Object
	         */
	        
	        
	        private Properties loadProperties(String propertiesFileName) {
	            Properties prop = new Properties();
	            try {
	                prop.load(new FileInputStream(propertiesFileName + ".properties"));
	            } catch (IOException ex) {
	                ex.printStackTrace();
	                System.err.println(ex.getMessage());
	            }
	     
	            return prop;
	        }
	        
	        
	        
	        
	        
	        
	        
	

}
			



	  
	  
	  
	  
	  
	  
