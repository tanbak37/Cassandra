//util


package com.dpp.gn.utilities;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class DPPGNUtilities {

	private Cluster cluster;	
	public Session session;
	final static String host_destination = "localhost";
	final static int port_destination = 9042;
	
	   public static void main(final String[] args)
	   {
	       final DPPGNUtilities client = new DPPGNUtilities();
	       final String ipAddress = args.length > 0 ? args[0] : host_destination;
	       final int port = args.length > 1 ? Integer.parseInt(args[1]) : port_destination;
	       System.out.println(" **** Connecting to IP Address " + ipAddress + ":" + port + "...");
	       client.connect(ipAddress, port);
//	       createTable(client);
	       insertData(
	    		   1, 
	    		   "TRX001", 
	    		   "Facebook" , 
	    		   "Rest Call", 
	    		   "10.60.170.75:5555", 
	    		   "http://10.60.170.75:5555/ws/telkom.nb.siebel.order.soapwss.stable:wsReceiveSWIOrder?WSDL", 
	    		   "-",
	    		   client);
	       client.close();
	   }
	
	
	   public void connect(final String node, final int port)
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
	   
	   public static void createTable(DPPGNUtilities client) {
		   final String createCql =
				   "CREATE TABLE kdstest.transaction (id int, trxid varchar, type varchar, "
				   + "api_desc varchar, hostname varchar, path varchar, desciption varchar, PRIMARY KEY (id, trxid))";
		   client.getSession().execute(createCql);
	   }
	   
	   public static void insertData(final int id, final String trxid, final String type, final String api_desc, 
			   final String hostname, final String path, final String desciption, DPPGNUtilities client){
		   
	      client.getSession().execute(
	         "INSERT INTO kdstest.transaction (id, trxid, type, api_desc, hostname, path, desciption) VALUES (?, ?, ?, ?, ?, ?, ?)",
	         id, trxid, type, api_desc, hostname, path, desciption);	     
	   }	
	   
	   /* Get Session */
	   
	   public Session getSession()
	   {
	      return this.session;
	   }

	   /* Close cluster */

	   public void close()
	   {
	      cluster.close();
	   }	
}