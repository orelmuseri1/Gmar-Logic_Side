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

import org.json.JSONObject;


public class SendAllert {
	
	int send(int ID,String Date,String Time,String Level,String EventsLeading,String Action) throws Exception{	
		URL	url = new URL("http://127.0.0.1:5000/alerts/LogicSystemAlert/1"); //  http://127.0.0.1:5000/alerts/LogicSystemAlert/1  https://httpbin.org/post
		/*Map<String,String> params = new LinkedHashMap<String,String>();
	    params.put("\"Date\"", addB(Date));
	    params.put("\"Time\"", addB(Time));
	    params.put("\"Child id\"",String.valueOf(ID));
	    params.put("\"Level\"", addB(Level));
	    params.put("\"Events leading\"", addB(EventsLeading));
	    params.put("\"Action needed\"", addB(Action));
	    String JsonToString =params.toString();
	    JsonToString=JsonToString.replaceAll("=", ":");
	    System.out.println(JsonToString);*/
		
	    JSONObject object = new JSONObject();
	    object.put("Date", Date);
	    object.put("Time", Time);
	    object.put("Child id",String.valueOf(ID));
	    object.put("Level", Level);
	    object.put("Events leading", EventsLeading);
	    object.put("Action needed", Action);
	   
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charsets=UTF_8"); //  x-www-form-urlencoded   application/json; charsets=UTF_8
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
			System.out.println(myResponse); // בעיה ראשונה יש בעיה לשלוח בעברית שניה המרכאות שאני שולח נשמרות עם הסימנים בגיסון
				} else {
				    System.out.println(conn.getResponseMessage());  
				}  
		return 0;
		
		
	}
	
	
	float chackColorsAlerts(Connection myConn,String[] date) throws Exception {
		int color = 0;
		float counter=0,stat=0;
		ResultSet LiquidFoods=  getSet(myConn,"LiquidFood",date);
		while(LiquidFoods.next()) {
			color = 1;
			counter++;
			if(LiquidFoods.getString("consumedAmount").equals("לא אכל")) {
				color = 3;
				//sendColorAlert(LiquidFoods.getString("child"),LiquidFoods.getString("child"),color);
				}
			else if(LiquidFoods.getString("consumedAmount").equals("מתחת לחצי מנה")) {
				color= 3;
				//sendColorAlert(LiquidFoods.getString("child"),LiquidFoods.getString("child"),color);
				}
			else if(LiquidFoods.getString("consumedAmount").equals("מעלה לחצי מנה")) {
				color= 2;
				//sendColorAlert(LiquidFoods.getString("child"),LiquidFoods.getString("child"),color);
				}
			else if(LiquidFoods.getString("consumedAmount").equals("סיים מנה")) {
				color= 1;
				//sendColorAlert(LiquidFoods.getString("child"),LiquidFoods.getString("child"),color);
				}
			else if(LiquidFoods.getString("consumedAmount").equals("אכל מעבר למנה")) {
				color= 2;
				//sendColorAlert(LiquidFoods.getString("child"),LiquidFoods.getString("child"),color);
				}
			stat+=color;
		}
		
		ResultSet Parasites=  getSet(myConn,"Parasites",date);
		while(Parasites.next()) {
			color = 1;
			counter++;
			if(Parasites.getString("type").equals("כינים")) {
				color = 3;
				//sendColorAlert(Parasites.getString("child"),Parasites.getString("child"),color);
				}
			else if(Parasites.getString("type").equals("תולעים")) {
				color= 3;
				//sendColorAlert(Parasites.getString("child"),Parasites.getString("child"),color);
				}
			stat+=color;
		}
		
		
		ResultSet Cough=  getSet(myConn,"Cough",date);
		while(Cough.next()) {
			color = 1;
			counter++;
			if(Cough.getString("type").equals("טורדני")) {
				color = 3;
				//sendColorAlert(Cough.getString("child"),Cough.getString("child"),color);
				}
			else if(Cough.getString("type").equals("לח")) {
				color= 2;
				//sendColorAlert(Cough.getString("child"),Cough.getString("child"),color);
				}
			stat+=color;
		}
		
		ResultSet Feces=  getSet(myConn,"Feces",date);
		while(Feces.next()) {
			counter++;
			color=1;
			if(Feces.getString("texture").equals("רירי")) {
				color = 3;
				//sendColorAlert(Feces.getString("child"),Feces.getString("child"),color);
				}
			if(color==1) {
				if(Feces.getString("color").equals("אדום/ורוד")) {
					color= 3; // אלא אם הוא אכל מזונות אדומים למשל סלק
					//sendColorAlert(Feces.getString("child"),Feces.getString("child"),color);
					}
				else if(Feces.getString("color").equals("אדום בוהק")) {
					color= 3;//אלא אם אכל מזונות אדומים למשל סלק 
					//sendColorAlert(Feces.getString("child"),Feces.getString("child"),color);
					}
				else if(Feces.getString("color").equals("לבן אפור/חיוורת בצבע חימר")) {
					color= 3;
					//sendColorAlert(Feces.getString("child"),Feces.getString("child"),color);
					}
				else if(Feces.getString("color").equals("חום בהיר")) {
					color= 2;
					//sendColorAlert(Feces.getString("child"),Feces.getString("child"),color);
					}
			}
			if(color==1) {
				//sendColorAlert(Feces.getString("child"),Feces.getString("child"),color);
			}
			stat+=color;
		}
		
		
		return stat/counter;
	}
	
	
	
	
	
	
	
	int sendColorAlert(String string,String table,int color) throws Exception{	
		URL	url = new URL("http://127.0.0.1:5000/alerts/LogicSystemAlert/1"); 
	    JSONObject object = new JSONObject();
	    object.put("Color",String.valueOf(color));
	    object.put("Table", table);
	    object.put("ID", String.valueOf(string));
	   
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
	
	
	ResultSet getSet(Connection myConn,String tableName,String[] date) throws SQLException {
		Statement mystmt = myConn.createStatement();
		String giveMeAllEvents= "SELECT * FROM "+tableName + "WHERE eventDate = "+date;
		ResultSet events= mystmt.executeQuery(giveMeAllEvents);//sent the query to get all the kids
		return events;
	}
	
	
	String addB(String s) {
		String temp = "\""+s+"\"";
		return temp;
	}
}
