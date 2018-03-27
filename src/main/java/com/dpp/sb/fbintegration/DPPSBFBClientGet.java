package com.dpp.sb.fbintegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.dpp.gn.model.fb.DPPGNFeedFacebook;
import com.dpp.gn.model.fb.DPPGNUserFacebook;
import com.dpp.gn.model.fb.DPPGNLikeFacebook;
import com.dpp.gn.model.fb.DPPGNMovieFacebook;
import com.dpp.gn.model.fb.DPPGNMusicFacebook;
import com.dpp.gn.model.fb.DPPGNVideoFacebook;
import com.dpp.gn.utilities.KeyConstants;
import com.dpp.sb.cassandradb.DPPSBCassandraDB;
import com.dpp.sb.cassandradb.DPPSBStoreToCassandra;

import com.dpp.gn.utilities.DPPGNCassandraConnector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DPPSBFBClientGet {
	
	public static DPPSBStoreToCassandra storeToCassandra = null;
	
	

	public void process() throws Exception {
		
		storeToCassandra = new DPPSBStoreToCassandra();
	

		try {
			URL url = new URL(KeyConstants.URL_PERSONAL_FB + KeyConstants.ACCESS_TOKEN_FB);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			System.out.println("***** start Grap Facebook ******");

			if (conn.getResponseCode() != KeyConstants.RESPONSE_SUCCESS) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			DPPGNUserFacebook user = new DPPGNUserFacebook();
			List<DPPGNFeedFacebook> listfeed = new ArrayList<DPPGNFeedFacebook>();
			List<DPPGNLikeFacebook> listlike = new ArrayList<DPPGNLikeFacebook>();
			List<DPPGNMovieFacebook> listmovie = new ArrayList<DPPGNMovieFacebook>();
			List<DPPGNMusicFacebook> listmusic = new ArrayList<DPPGNMusicFacebook>();
			List<DPPGNVideoFacebook> listvideo = new ArrayList<DPPGNVideoFacebook>();
			SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			System.out.println("Success : HTTP response code : "+ conn.getResponseCode() +" \n");
			System.out.println("Output from Server Grap FaceBook .... \n");
			while ((output = br.readLine()) != null) {
				
				System.out.println("result :"+output);
				JSONObject object = new JSONObject(output);
				
				/* getjsobObject for firstname, middlename, dll with single object*/
				
				user.setId(object.getString("id"));
				user.setName(object.getString("name"));
				user.setFirstName(object.getString("first_name"));
				//user.setMiddleName(object.getString("middle_name"));
				user.setLastName(object.getString("last_name"));
				user.setGender(object.getString("gender"));
				user.setUrl(object.getString("gender"));
				user.setShortName(object.getString("short_name"));
				user.setNameFormat(object.getString("name_format"));
				
				
				/*get location data */
				
				JSONObject datalocation = object.getJSONObject("location");
				user.setLocationId(datalocation.getString("id"));
				user.setLocationName(datalocation.getString("name"));
				
				
				/* get Data Device WITH array type*/
				
				JSONArray datadevice = object.getJSONArray("devices");
				List<String> data = new ArrayList<String>();
				for (int x=0; x < datadevice.length(); x++) {
					JSONObject device = datadevice.getJSONObject(x);
					data.add(device.getString("os"));
				}
				user.setDevice(data);
				
				
				/******** get Data Feed -> JSONObject get JSONObject get JSONARRAY  ****/ 
				
				JSONObject feed = object.getJSONObject("feed");
				JSONArray datalistfeed = feed.getJSONArray("data");
				for (int x=0; x < datalistfeed.length(); x++) {
					JSONObject datafeed = datalistfeed.getJSONObject(x);

					DPPGNFeedFacebook feedFacebook = new DPPGNFeedFacebook();
					
					feedFacebook.setFacebookId(object.getString("id"));
					feedFacebook.setScreenName(object.getString("name"));
					feedFacebook.setId(datafeed.getString("id"));
					
					try {
						feedFacebook.setMessage(datafeed.getString("message"));		
					} catch (Exception e) {}
					
					try {
						feedFacebook.setStory(datafeed.getString("story"));	
					} catch (Exception e) {
					feedFacebook.setCreatedDate(formatdate.parse(datafeed.getString("created_time")));
					listfeed.add(feedFacebook);	
						
					}
					
					user.setListfeed(listfeed);
				}
					
					/* get Data Like WITH array type*/
					
					JSONObject likes = object.getJSONObject("likes");
					JSONArray datalistlikes = likes.getJSONArray("data");
					for (int x=0; x < datalistlikes.length(); x++) {
						JSONObject likedata = datalistlikes.getJSONObject(x);
						
						DPPGNLikeFacebook likeFacebook = new DPPGNLikeFacebook();
						
						likeFacebook.setFacebookId(object.getString("id"));
						likeFacebook.setScreenName(object.getString("name"));
						likeFacebook.setId(likedata.getString("id"));
						likeFacebook.setName(likedata.getString("name"));
						likeFacebook.setCreatedDate(formatdate.parse(likedata.getString("created_time")));
						listlike.add(likeFacebook);
					}
					
					user.setListlike(listlike);
					
					/* get data movie  object --> array data */
					
					JSONObject music = object.getJSONObject("music");
					JSONArray datalistmusic = music.getJSONArray("data");
					for (int x=0; x < datalistmusic.length(); x++) {
						JSONObject datamusic = datalistmusic.getJSONObject(x);
						
						DPPGNMusicFacebook musicFacebook = new DPPGNMusicFacebook();
						
						musicFacebook.setFacebookId(object.getString("id"));
						musicFacebook.setScreenName(object.getString("name"));
						musicFacebook.setId(object.getString("id"));
						musicFacebook.setCreatedDate(formatdate.parse(datamusic.getString("created_time")));
						listmusic.add(musicFacebook);
					}
					user.setListmusic(listmusic);
					
					/*    get data videos  ---> */
					
					/*user.setListmusic(listmusic);
					
					JSONObject videos = object.getJSONObject("videos");
					JSONArray listvideosdata = videos.getJSONArray("data");
					for (int x=0; x < listvideosdata.length(); x++) {
						JSONObject videosdata = listvideosdata.getJSONObject(x);
						
						VideoFacebook videosFacebook = new VideoFacebook();
						
						videosFacebook.setFacebookId(object.getString("id"));
						videosFacebook.setScreenName(object.getString("name"));
						videosFacebook.setId(videosdata.getString("id"));
						try {
							videosFacebook.setDescription(videosdata.getString("description"));	
						} catch (Exception e) {}
						try {
							videosFacebook.setUpdatedTime(formatter.parse(videosdata.getString("updated_time")));	
						} catch (Exception e) {}
						listvideo.add(videosFacebook);
					}
					
					user.setListvideo(listvideo);*/					
					
			
			}
			
			System.out.println("******* End Processs**************");
		
			
			storeToCassandra.connectToCassandraFB();
			storeToCassandra.insertProfileFB(user);
			storeToCassandra.disconnetFromCassandra();
		
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();			
		}

	}
	
	
	
	
}
