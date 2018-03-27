//connector


package com.dpp.gn.utilities;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.dpp.sb.cassandradb.DPPSBCassandraDB;


public class DPPGNCassandraConnector {
	private Cluster cluster;
	public Session session;
	
	public static void main(String []args) {
		       final DPPGNCassandraConnector client = new DPPGNCassandraConnector();		       
		       final String ipAddress = args.length > 0 ? args[0] : "localhost";
		       final int port = args.length > 1 ? Integer.parseInt(args[1]) : 9042;
		       System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
		       client.connect(ipAddress, port);
//		       DPPSBCassandraDB.insertPostsFB(id, message, story, created_time, client);
		       
		       client.getSession().close();
		   
	}
	
	public static void connectAndInsert (String id, String message, String story, String created_time) {
	       final DPPGNCassandraConnector client = new DPPGNCassandraConnector();		       
	       final String ipAddress = "localhost";
	       final int port = 9042;
	       System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "...");
	       client.connect(ipAddress, port);
	       //DPPSBCassandraDB.insertPostsFB(id, message, story, created_time, client);
	       
	       client.getSession().close();
	   
}

	public void connect(final String node, final int port) {
		this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
		final Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
		for (final Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
					host.getRack());
		}
		session = cluster.connect();
	}

	public Session getSession() {
		return this.session;
	}

	/** Close cluster. */

	public void close() {
		cluster.close();
	}
}