package AskingQueriesFP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;


public class SendAllert {
	float counterEvents=0,statColors=0;
	GetEvent getter=new GetEvent();
	 String send(int ID,String Date,String Time,String Level,JSONObject EventsLeading,String Action,String rule) throws Exception{	
		URL	url = new URL("http://127.0.0.1:5000/alerts/LogicSystemAlert/1"); //  http://127.0.0.1:5000/alerts/LogicSystemAlert/1  https://httpbin.org/post
		String uniqueID = UUID.randomUUID().toString();
		JSONObject object = new JSONObject();
	    object.put("alertDate", Date);
	    object.put("alertTime", Time);
	    object.put("childID",String.valueOf(ID));
	    object.put("level", Level);
	    object.put("eventsLeading", EventsLeading);//events leading miss and what is that timepast
	    object.put("actionNeeded", Action);
	    object.put("alertID", uniqueID);
	   object.put("rule",rule);
	    
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charsets=UTF_8"); //  x-www-form-urlencoded   application/json; charsets=UTF_8
		conn.setRequestProperty("Content-Length", String.valueOf(object.length()));
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestProperty("Accept", "application/json; charsets=UTF_8");
		conn.setChunkedStreamingMode(0);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		//System.out.println(object.toString());
		wr.write(object.toString());
		wr.flush();
		StringBuilder sb = new StringBuilder();  
		int HttpResult = conn.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;  
			while ((line = br.readLine()) != null) {  
				  sb.append(line);  
				  }
			br.close();

			JSONObject myResponse = new JSONObject(sb.toString());
			System.out.println(myResponse); 
				} else {
				    System.out.println(conn.getResponseMessage());  
				}  
		return uniqueID;
		
		
	}
	 
	 String sendLogicAlert(LogicSystemAlert alert) throws Exception{	
			URL	url = new URL("http://127.0.0.1:5000/alerts/LogicSystemAlert/1"); //  http://127.0.0.1:5000/alerts/LogicSystemAlert/1  https://httpbin.org/post
			JSONObject object = alert.getJson();
		    
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charsets=UTF_8"); //  x-www-form-urlencoded   application/json; charsets=UTF_8
			conn.setRequestProperty("Content-Length", String.valueOf(object.length()));
			conn.setDoOutput(true);
			conn.setDoInput(true); 
			conn.setRequestProperty("Accept", "application/json; charsets=UTF_8");
			conn.setChunkedStreamingMode(0);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			//System.out.println(object.toString());
			wr.write(object.toString());
			wr.flush();
			StringBuilder sb = new StringBuilder();  
			int HttpResult = conn.getResponseCode(); 
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				String line = null;  
				while ((line = br.readLine()) != null) {  
					  sb.append(line);  
					  }
				br.close();

				JSONObject myResponse = new JSONObject(sb.toString());
				System.out.println(myResponse); 
					} else {
					    System.out.println(conn.getResponseMessage());  
					}  
			return object.getString("alertID");
			
			
		}
	
	// sent alert that event color change in table and give the ID of the event that cange for the client
	int sendColorAlert(String ID,String Table,int Color) throws Exception{	
		URL	url = new URL("http://127.0.0.1:5000/alerts/LogicSystemAlert/1");  
	    JSONObject object = new JSONObject();
	    object.put("Color",Color);
	    object.put("Table", Table);
	    object.put("ID", String.valueOf(ID));
	   
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charsets=UTF_8");
		conn.setRequestProperty("Content-Length", String.valueOf(object.length()));
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestProperty("Accept", "application/json; charsets=UTF_8");
		conn.setChunkedStreamingMode(0);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		System.out.println(object.toString());
		wr.write(object.toString());
		wr.flush();
		StringBuilder sb = new StringBuilder();  
		int HttpResult = conn.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;  
			while ((line = br.readLine()) != null) {  
				  sb.append(line);  
				  }
			br.close();

			JSONObject myResponse = new JSONObject(sb.toString());
			System.out.println(myResponse);
				} else {
				    System.out.println(conn.getResponseMessage());  
				}
		return 0;
	}

	// sent put that Change the event color
	int sendPutColor(String ID, JSONObject object,String table) throws Exception{	
			//URL	url = new URL("http://127.0.0.1:5000/events/"+table+"/"+ID); 
			URL	url = new URL("http://193.106.55.183/events/"+table+"/"+ID); 
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json; charsets=UTF_8");
			conn.setRequestProperty("Content-Length", String.valueOf(object.length()));
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("Accept", "application/json; charsets=UTF_8");
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			System.out.println(object.toString());
			wr.write(object.toString());
			wr.flush();
			StringBuilder sb = new StringBuilder();  
			int HttpResult = conn.getResponseCode(); 
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
				String line = null;  
				while ((line = br.readLine()) != null) {  
					  sb.append(line);  
					  }
				br.close();

				JSONObject myResponse = new JSONObject(sb.toString());
				System.out.println(myResponse);
					} else {
					    System.out.println(conn.getResponseMessage());  
					}
			return 0;
		}
	
		
		
		
	ResultSet getSet(Connection myConn,String tableName,String[] date) throws SQLException {
		Statement mystmt = myConn.createStatement();
		//System.out.println("SELECT * FROM "+tableName+" WHERE eventDate = "+date[0]+"/"+date[1]+"/"+date[2]);
		String giveMeAllEvents= "SELECT * FROM "+tableName+" WHERE STRCMP(SUBSTRING(eventDate, 1,10),"+"\""+date[0]+"/"+date[1]+"/"+date[2]+"\")=0";
		ResultSet events= mystmt.executeQuery(giveMeAllEvents);//sent the query to get all the kids
		return events;
	}
	
	
	String addB(String s) {
		String temp = "\""+s+"\"";
		return temp;
	}
}
