package com.dpp.sb.fbintegration;

import java.util.Date;
import java.util.List;

import com.dpp.gn.utilities.KeyConstants;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.Album;

public class DPPSBFBIntegration {

	public static void main(String[] args) throws Exception {

		 DPPSBFBClientGet FBClient = new DPPSBFBClientGet();
		 FBClient.process();

//		DPPSBFBIntegration dppsbfbintegration = new DPPSBFBIntegration();
//		dppsbfbintegration.getAlbumsFBClientWithSince()/;
//		dppsbfbintegration.getAlbumsFBClient();
//		dppsbfbintegration.getUserDataFacebookClient();

	}

	public void getAlbumsFBClientWithSince() {

		FacebookClient facebookClient = new DefaultFacebookClient(KeyConstants.ACCESS_TOKEN_FB);

		Date oneWeekAgo = new Date(System.currentTimeMillis() - 100000000L * 60L * 60L * 24L * 7L);

		Connection<Album> albumConnection = facebookClient.fetchConnection("me/albums", Album.class,
				Parameter.with("limit", 3), Parameter.with("since", oneWeekAgo));
		List<Album> albums = albumConnection.getData();

		for (Album album : albums) {
			System.out.println("Album name:" + album.getName());
		}

	}

	public void getAlbumsFBClient() {

		FacebookClient facebookClient = new DefaultFacebookClient(KeyConstants.ACCESS_TOKEN_FB);

		Connection<Album> albumConnection = facebookClient.fetchConnection("me/albums", Album.class);
		List<Album> albums = albumConnection.getData();

		for (Album album : albums) {
			System.out.println("Album name:" + album.getName());
		}

	}
	
public void getUserDataFacebookClient() {
		
		FacebookClient facebookClient = new DefaultFacebookClient(KeyConstants.ACCESS_TOKEN_FB);
 
        JsonObject userData = facebookClient.fetchObject("me",
                JsonObject.class, Parameter.with("fields", "name, first_name"));
        System.out.println("userData=" + userData);
        
        System.out.println("FirstName=" + userData.getString("first_name"));
        System.out.println("Name= " + userData.getString("name"));
	
	

}
}
