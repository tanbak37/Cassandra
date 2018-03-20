package com.dpp.sb.fbintegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import com.dpp.gn.utilities.DPPGNCassandraConnector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dpp.gn.utilities.KeyConstants;

public class DPPSBFBClientGet {

	public static void main(String[] args) {

		try {
			URL url = new URL(KeyConstants.URL_NAME_FB + KeyConstants.ACCESS_TOKEN_FB);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != KeyConstants.RESPONSE_SUCCESS) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Success : HTTP response code : "+ conn.getResponseCode() +" \n");
			System.out.println("Output from Server Grap FaceBook .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println("result :"+output);
				JSONObject object = new JSONObject(output);
				JSONArray listdata = object.getJSONArray("data");
				System.out.println("jumlah :"+listdata.toList());
				for (int x=0; x < listdata.length(); x++) {
					JSONObject data = listdata.getJSONObject(x);
					String message = "";
					String story = "";
					try {
						message = data.getString("message");	
					} catch (Exception e) {}
					
					try {
						story = data.getString("story");	
					} catch (Exception e) {}
					
					String created_time = data.getString("created_time");
					String id = data.getString("id");
					System.out.println("id :"+id);
					System.out.println("message :"+message);
					System.out.println("story :"+story);
					System.out.println("created_time"+created_time);
					System.out.println("*******************************************************************************");
					System.out.println(" \n");
					
					/*******  Insert DataBase Cassandra  *****/
		
					DPPGNCassandraConnector.connectAndInsert(id, message, story, created_time);
					
					/*****************************************/
				}
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();			
		}

	}
}
