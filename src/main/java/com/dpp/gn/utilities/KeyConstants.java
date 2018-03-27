package com.dpp.gn.utilities;

public interface KeyConstants {
	
	
	static final String HOST_CASSANDRA = "localhost";
	static final int PORT_CASSANDRA = 9042;
	static final String KEY_SPACE_NAME = "kdstest";
	
	static final String ACCESS_TOKEN_FB = "EAACEdEose0cBAMorizh4EUsrDIaSKmCw2gEDuEOLD8X1mFnDnypWzROV9LTu5A9CXu5h6IQomXzgRYlqyhmCRNW26xMiRM7CXUfXesC9PSuZCHGY3kbBUbvGzMtKsu1OczhqyvM4W68Il9wvw93dI8svfs8OA3DWAfg3urkyOBVA8U6Aqlz0YUn4IqWUZD";
	static final String URL_PERSONAL_FB = "https://graph.facebook.com/v2.11/me?fields=id,name,location,age_range,devices,first_name,middle_name,last_name,feed,gender,hometown,link,video_upload_limits,likes,movies,music,short_name,name_format,videos&access_token=";
	static final String URL_POST_FB = "https://graph.facebook.com/me/posts?access_token=";
	static final String CONSUMER_KEY_STR = "CPmLmjUTjTunphmskTw7kI0kc";
	
	// Facebook App
	   public static final String MY_APP_ID = "594652347562923";
	  // public static final String MY_APP_SECRET = "<your app secret>";
	
	static final int RESPONSE_SUCCESS = 200;
	static final int RESPONSE_FAILED1 = 404;
	static final int RESPONSE_FAILED2 = 500;
	
}
