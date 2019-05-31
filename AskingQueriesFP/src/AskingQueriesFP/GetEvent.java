package AskingQueriesFP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;


public class GetEvent {
	JSONArray getJsonsWithDate(String table,String date) throws Exception{	
		JSONArray jsonarray;
		JSONObject myResponse;
		//System.out.println("http://127.0.0.1:5000/events/"+ table +"EventByDate/"+date);
		URL	url = new URL("http://193.106.55.183/events/"+ table +"EventByDate/"+date);//+date 
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json;");
		StringBuilder response = new StringBuilder();  
		int HttpResult = conn.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			} 
			in.close();
		 }
		myResponse = new JSONObject(response.toString());
		if(table.equals("LiquidFood")) {
			jsonarray = myResponse.getJSONArray("liquidFoodEvent"); 
		}else if(table.equals("SolidFood")){
			jsonarray = myResponse.getJSONArray("solidFoodEvent"); 
		}else {
			 jsonarray = myResponse.getJSONArray(table.toLowerCase()+"Event"); 
		}
		return jsonarray;
	}


	JSONArray getJsons(String table) throws Exception{	
	URL	url;
	
	url = new URL("http://127.0.0.1:5000/events/"+table+"Events");
	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	conn.setRequestMethod("GET");
	conn.setRequestProperty("Content-Type", "application/json;");
	StringBuilder response = new StringBuilder();  
	int HttpResult = conn.getResponseCode(); 
	if (HttpResult == HttpURLConnection.HTTP_OK) {
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		} 
		in.close();
	 }
	JSONObject myResponse = new JSONObject(response.toString());
	JSONArray jsonarray = myResponse.getJSONArray("vomitusEvent"); 
	 return jsonarray;
	}

}
