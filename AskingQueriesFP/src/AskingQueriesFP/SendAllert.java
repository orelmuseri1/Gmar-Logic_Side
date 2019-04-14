package AskingQueriesFP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;


public class SendAllert {
	
	int send(int ID,String Date,String Time,String Level,String EventsLeading,String Action) throws Exception{		
		URL	url = new URL("https://httpbin.org/post");
		Map<String,String> params = new LinkedHashMap<String,String>();
	    params.put("Date", Date);
	    params.put("Time", Time);
	    params.put("Child id",String.valueOf(ID));
	    params.put("Level", Level);
	    params.put("Action needed", Action);
	    params.put("Events leading", EventsLeading);
	    StringBuilder postData = new StringBuilder();
	    	for (Map.Entry param : params.entrySet()) {
	    		if (postData.length() != 0) postData.append('&');
	        	postData.append(URLEncoder.encode((String) param.getKey(), "UTF-8"));  
	        	postData.append('=');
	       		postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));}
	        	byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			    HttpURLConnection conn;
			    conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
				conn.setDoOutput(true);
				conn.getOutputStream().write(postDataBytes);
				Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				for (int c; (c = in.read()) >= 0;)
					sb.append((char)c);
				String response = sb.toString();
				// System.out.println(response);
				JSONObject myResponse;
				myResponse = new JSONObject(response.toString());
			    JSONObject form_data = myResponse.getJSONObject("form");
			    System.out.println(form_data);
			   // System.out.println("level- "+form_data.getString("Level"));
		return 0;
	}
}
