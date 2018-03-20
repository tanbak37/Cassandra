package com.dpp.sb.cassandradb;

import com.dpp.gn.utilities.DPPGNCassandraConnector;

public class DPPSBCassandraDB {
	
	
public static void insertPostsFB(final String id, final String message, final String story,final String created_time, DPPGNCassandraConnector client) {
		
		client.getSession().execute("insert into kdstest.postsfb(id,message,story,created_time)values(?,?,?,?)",
				id,message,story,created_time);

}
}
