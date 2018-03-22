package com.dpp.sb.cassandradb;

import java.util.Properties;



import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.dpp.gn.model.DPPGNFriendDetails;
import com.dpp.gn.model.DPPGNTweet;
import com.dpp.gn.model.DPPGNUserDetails;



public class DPPSBStoreToCassandra {

	Cluster cluster = null;
	Session session = null;
	public Properties properties;
		
	 /**
     * konek ke cassandra
     */
	
	public void connectToCassandra(final String node, final int port) {
		
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*cluster = Cluster.builder().addContactPoints("localhost")
	                .build();
		session = cluster.connect("9042");
		System.out.println("CONNECTED");  
		*/
		
		} 
		
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
					userDetails.getFriendList()));
			
			insertFriendsDetails(userDetails); //to insert friend details
			
	        /**
	         * insert followers name as list
	         */
			
			prepare_statement = session.prepare("insert into kdstest.follower_list (screen_name, followers) values (?,?)");
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
		 
		 /**
		     * Disconnecting with Cassandra
		     */
		
		
		 public void disconnetFromCassandra() {
		        cluster.close();
		        System.out.println("Disconnected!!");
	
	

}
}