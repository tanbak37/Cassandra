package com.dpp.sb.cassandradb;

import java.util.Properties;



import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.dpp.gn.model.tw.DPPGNFriendDetails;
import com.dpp.gn.model.tw.DPPGNTweet;
import com.dpp.gn.model.fb.DPPGNLikeFacebook;
import com.dpp.gn.model.fb.DPPGNMusicFacebook;
import com.dpp.gn.model.fb.DPPGNFeedFacebook;
import com.dpp.gn.model.fb.DPPGNVideoFacebook;
import com.dpp.gn.model.fb.DPPGNMovieFacebook;
import com.dpp.gn.model.fb.DPPGNUserFacebook;
import com.dpp.gn.model.tw.DPPGNUserDetails;
import com.dpp.gn.utilities.KeyConstants;



public class DPPSBStoreToCassandra {

	Cluster cluster = null;
	Session session = null;
	public Properties properties;
		
	 /**
     * konek ke cassandra
     */
	
	public void connectToCassandraTW(final String node, final int port) {
		
		//try {
		   {

			      this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();

			      final Metadata metadata = cluster.getMetadata();

			      System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

			      for (final Host host : metadata.getAllHosts())

			      {

			         System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",

			            host.getDatacenter(), host.getAddress(), host.getRack());

			      }

			      session = cluster.connect();
		   }
	}
		
		
    public void connectToCassandraFB() {
        cluster = Cluster.builder().addContactPoints(KeyConstants.HOST_CASSANDRA)
                .build();
        session = cluster.connect(KeyConstants.KEY_SPACE_NAME);
        System.out.println("CONNECTED !! ! ");
    }
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*cluster = Cluster.builder().addContactPoints("localhost")
	                .build();
		session = cluster.connect("9042");
		System.out.println("CONNECTED");  
		*/
		
		 
		
		//catch (Exception e) {
			//System.out.println("ERROR NOT CONNECT");
		
	
	
		 /**
	     * @param userDetails
	     * @throws Exception
	     * Insert the detailsusers and friendlist
	     */	
		
		public void insertUserDetails(DPPGNUserDetails userDetails) throws Exception {
			
			BoundStatement boundStatement = null;
			PreparedStatement prepare_statement = null;
			String userName = userDetails.getScreenName();
			
			
			 /**
	         * insert user details
	         */
			
			prepare_statement = session.prepare("insert into kdstest.user_details(user_name, screen_name ,location ,twitter_id ,created_date ,url) values (?,?,?,?,?,?)");
			boundStatement = new BoundStatement(prepare_statement);
			session.execute(boundStatement.bind(userDetails.getUserName(),
					userName, userDetails.getLocation(), userDetails.getTweetId(),
					userDetails.getCreatedDate(), userDetails.getUrl()));
			/*
			prepare_statement = session
					.prepare("UPDATE twitter_username SET  user_name =? WHERE screen_name=?;");
			boundStatement = new BoundStatement(prepare_statement);
			session.execute(boundStatement.bind(userDetails.getUserName(), userName));
			
			*/
			 /**
	         * insert friend name as list
	         */
			
			prepare_statement = session.prepare("insert into kdstest.friend_list (screen_name, friends ) values (?,?)");
			
			boundStatement = new BoundStatement(prepare_statement);
			session.execute(boundStatement.bind(userName,
					userDetails.getFriendList().toString()));
			
			insertFriendsDetails(userDetails); //to insert friend details
			
	        /**
	         * insert followers name as list
	         */
			
			prepare_statement = session.prepare("insert into kdstest.follower_list (user_screen_name, followers) values (?,?)");
	        boundStatement = new BoundStatement(prepare_statement);
	        session.execute(boundStatement.bind(userName,
	                userDetails.getFollowerList()));
	        
	        System.out.println("Insert into twitter_follower_list");
	        
	        insertFollowersDetails(userDetails);
	        
		}
		
	    /**
	     * @param userDetails
	     *            insert details of followers of a user
	     */
		
		private void insertFollowersDetails(DPPGNUserDetails userDetails) {
			BoundStatement boundStatement = null;
			PreparedStatement prepare_statement = null;
	        String userName = userDetails.getScreenName();
	        for (DPPGNFriendDetails fd : userDetails.getFollowersList()) {
	        	
	        	prepare_statement = session
	        			.prepare("insert into kdstest.follower_details (user_screen_name,follower_screen_name, follower_user_name , location , twitter_id , created_date , url ) values (?,?,?,?,?,?,?)");
	        	boundStatement = new BoundStatement(prepare_statement);
	        	session.execute(boundStatement.bind(userName, fd.getScreen_name(),
	        			fd.getUserName(), fd.getLocation(), fd.getTweetID(),
	        			fd.getCreatedDate(), fd.getUrl()));
	        	
	        	  /**
	             * update followers count
	             */
	        	
	        	/*
	        	 prepare_statement = session
	                     .prepare("UPDATE follower_count SET  follower_count = follower_count+1 WHERE screen_name=?;");
	             boundStatement = new BoundStatement(prepare_statement);
	             session.execute(boundStatement.bind(userName));
	        	
	        	*/
	             /**
	              * insert user and screen name to the column family
	              
	             prepare_statement = session
	                     .prepare("UPDATE twitter_username SET  user_name =? WHERE screen_name=?;");
	             boundStatement = new BoundStatement(prepare_statement);
	             session.execute(boundStatement.bind(fd.getUserName(),
	                     fd.getScreen_name()));
	        	
	        	*/
	        	
	        }
		}
		
		 /**
	     * @param userDetails
	     *            insert details of friends of a user
	     */
		
		 private void insertFriendsDetails(DPPGNUserDetails userDetails) {
		        BoundStatement boundStatement = null;
		        PreparedStatement prepare_statement = null;
		        String userName = userDetails.getScreenName();
		        for (DPPGNFriendDetails fd : userDetails.getFriendsList()) {
		        	
		        	prepare_statement = session
		                    .prepare("insert into kdstest.friend_details (user_screen_name,friend_screen_name, friend_user_name , location , twitter_id , created_date , url ) values (?,?,?,?,?,?,?);");
		            boundStatement = new BoundStatement(prepare_statement);
		            session.execute(boundStatement.bind(userName, fd.getScreen_name(),
		                    fd.getUserName(), fd.getLocation(), fd.getTweetID(),
		                    fd.getCreatedDate(), fd.getUrl()));
		        	
		            /**
		             * update friends count
		             */
		            
		            /*prepare_statement = session
		                    .prepare("UPDATE friend_count SET  friend_count = friend_count+1 WHERE screen_name=?");
		            boundStatement = new BoundStatement(prepare_statement);
		            session.execute(boundStatement.bind(userName));
		            */
		            /**
		             * insert user and screen name to the column family
		             */
		            
		            /*prepare_statement = session
		                    .prepare("UPDATE twitter_username SET  user_name =? WHERE screen_name=?");
		            boundStatement = new BoundStatement(prepare_statement);
		            session.execute(boundStatement.bind(fd.getUserName(),
		                    fd.getScreen_name()));
		        	*/
		        }
		 }
		 
		 
		 /* insert profile facebook with params
		  * idn,name,locationid,agerange,devices,getfirstname,getmiddlename
		  * ,getlastname,gender,url,shorname,nameformat
		  * 
		  * butuh method insertfeed facebook,insertlikefb,insertmovie,insertvideo
		  */
		 
		 public void insertProfileFB(DPPGNUserFacebook userFacebook) throws Exception {
		        BoundStatement boundStatement = null;
		        PreparedStatement prepare_statement = null;

		        prepare_statement = session
		        		.prepare("insert into kdstest.fbuser "
		        				+ "(id, name , location_id, location, age_range, device, first_name, "
		        				+ "middle_name, last_name, gender, url, short_name, name_format) "
		        				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
		        boundStatement = new BoundStatement(prepare_statement);
		        session.execute(boundStatement.bind(
		        		userFacebook.getId(),
		        		userFacebook.getName(),
		        		userFacebook.getLocationId(),
		        		userFacebook.getLocation(),
		        		userFacebook.getAgeRange(),
		        		userFacebook.getDevice().toString(),
		        		userFacebook.getFirstName(),
		        		userFacebook.getMiddleName(),
		        		userFacebook.getLastName(),
		        		userFacebook.getGender(),
		        		userFacebook.getUrl(),
		        		userFacebook.getShortName(),
		        		userFacebook.getNameFormat()));
		        
		        insertFeedFB(userFacebook);	  
		        insertLikeFB(userFacebook);
		        insertMovieFB(userFacebook);
		        insertMusicFB(userFacebook);
		        insertVideoFB(userFacebook);
		    }
		 
		 public void insertFeedFB(DPPGNUserFacebook userFacebook) throws Exception {
		        BoundStatement boundStatement = null;
		        PreparedStatement prepare_statement = null;

		        for (DPPGNFeedFacebook feed : userFacebook.getListfeed()) {
			        prepare_statement = session
			        		.prepare("insert into kdstest.fbfeed "
			        				+ "(id, message, story, created_date, facebook_id, screen_name) "
			        				+ "values (?,?,?,?,?,?)");
			        boundStatement = new BoundStatement(prepare_statement);
			        session.execute(boundStatement.bind(
			        		feed.getId(),
			        		feed.getMessage(),
			        		feed.getStory(),
			        		feed.getCreatedDate(),
			        		feed.getFacebookId(),
			        		feed.getScreenName()));	
		        }
		    }
		 
		 public void insertLikeFB(DPPGNUserFacebook userFacebook) throws Exception {
		        BoundStatement boundStatement = null;
		        PreparedStatement prepare_statement = null;

		        for (DPPGNLikeFacebook like : userFacebook.getListlike()) {
			        prepare_statement = session
			        		.prepare("insert into kdstest.fblikes "
			        				+ "(id, name, created_date, facebook_id, screen_name) "
			        				+ "values (?,?,?,?,?)");
			        boundStatement = new BoundStatement(prepare_statement);
			        session.execute(boundStatement.bind(
			        		like.getId(),
			        		like.getName(),
			        		like.getCreatedDate(),
			        		like.getFacebookId(),
			        		like.getScreenName()));	
		        }
		    }
		 
		 public void insertMovieFB(DPPGNUserFacebook userFacebook) throws Exception {
		        BoundStatement boundStatement = null;
		        PreparedStatement prepare_statement = null;

		        for (DPPGNMovieFacebook movie : userFacebook.getListmovie()) {
			        prepare_statement = session
			        		.prepare("insert into kdstest.fbmovies "
			        				+ "(id, name, created_date, facebook_id, screen_name) "
			        				+ "values (?,?,?,?,?)");
			        boundStatement = new BoundStatement(prepare_statement);
			        session.execute(boundStatement.bind(
			        		movie.getId(),
			        		movie.getName(),
			        		movie.getCreatedDate(),
			        		movie.getFacebookId(),
			        		movie.getScreenName()));	
		        }
		    }
		 
		 public void insertMusicFB(DPPGNUserFacebook userFacebook) throws Exception {
		        BoundStatement boundStatement = null;
		        PreparedStatement prepare_statement = null;

		        for (DPPGNMusicFacebook music : userFacebook.getListmusic()) {
			        prepare_statement = session
			        		.prepare("insert into kdstest.fbmusic "
			        				+ "(id, name, created_date, facebook_id, screen_name) "
			        				+ "values (?,?,?,?,?)");
			        boundStatement = new BoundStatement(prepare_statement);
			        session.execute(boundStatement.bind(
			        		music.getId(),
			        		music.getName(),
			        		music.getCreatedDate(),
			        		music.getFacebookId(),
			        		music.getScreenName()));	
		        }
		    }
		 
		 public void insertVideoFB(DPPGNUserFacebook userFacebook) throws Exception {
		        BoundStatement boundStatement = null;
		        PreparedStatement prepare_statement = null;

		        for (DPPGNVideoFacebook video : userFacebook.getListvideo()) {
			        prepare_statement = session
			        		.prepare("insert into kdstest.fbvideos "
			        				+ "(id, description, updated_time, facebook_id, screen_name) "
			        				+ "values (?,?,?,?,?)");
			        boundStatement = new BoundStatement(prepare_statement);
			        session.execute(boundStatement.bind(
			        		video.getId(),
			        		video.getDescription(),
			        		video.getUpdatedTime(),
			        		video.getFacebookId(),
			        		video.getScreenName()));	
		        }
		    }
		 
		 
		 
		 
		 
		 
		 /**
		     * Disconnecting with Cassandra
		     */
		
		
		 public void disconnetFromCassandra() {
		        cluster.close();
		        System.out.println("Disconnected!!");
	
	

}
}