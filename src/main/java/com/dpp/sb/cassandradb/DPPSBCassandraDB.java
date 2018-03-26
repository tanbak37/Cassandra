package com.dpp.sb.cassandradb;

import com.dpp.gn.utilities.DPPGNCassandraConnector;
import com.dpp.gn.utilities.DPPGNUtilities;

public class DPPSBCassandraDB {
	
	
	public static void createTableFBIntegration(DPPGNUtilities client) {
		   final String createCql =
				   "CREATE TABLE kdstest.dppfbintegration (id varchar, message varchar, story varchar, "
				   + "created_time varchar, PRIMARY KEY (id, created_time))";
		   client.getSession().execute(createCql);
	   }
	   
	   public static void insertDataFBIntegration(final String id, final String message, final String story, 
			   final String created_time, DPPGNUtilities client){
		   
	      client.getSession().execute(
	         "INSERT INTO kdstest.dppfbintegration (id, message, story, created_time) VALUES (?, ?, ?, ?)",
	         id, message, story, created_time);	    

}
}
