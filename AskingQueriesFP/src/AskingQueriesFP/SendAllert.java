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
	float counterEvents=0,statColors=0;
	
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
	
	
	float checkColorsAlerts(Connection myConn,String[] date) throws Exception {
		LiquidFoods(myConn,date);
		Parasites(myConn,date);
		Cough(myConn,date);
		Feces(myConn,date);
		Secretion(myConn,date);
		SolidFood(myConn,date);
		Vomitus(myConn,date);          //didnt do check on general behavior, general note, and medication,
		Urine(myConn,date);				
		Sleep(myConn,date);
		Fever(myConn,date);
		Water(myConn,date);
		Disease(myConn,date);
		Rash(myConn,date);
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ solution to get the date for event@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//
		/*Vomitus.next();
		String[] dateEvent,temp;
		dateEvent = Vomitus.getString("eventDate").split("/");									//getting the date and the time of the event a
		temp = dateEvent[2].toString().split(",");
		dateEvent[2]=temp[0];
		if(date[0].equals(dateEvent[0])&&date[1].equals(dateEvent[1])&& date[2].equals(dateEvent[2])) {
			System.out.println("hi"+Vomitus.getString("type"));
		}*/
		
		
		if(counterEvents !=0.0)return statColors/counterEvents;
		else return 0;
	}
	
	
	//======================================LiquidFoods===========================================//	
	
	
	
	
	

	
	void LiquidFoods(Connection myConn,String[] date) throws Exception {
		ResultSet LiquidFoods=  getSet(myConn,"LiquidFood",date);
		int color = 1;
		while(LiquidFoods.next()) {
			if(LiquidFoods.getString("eventColor")==null) {
				counterEvents++;
				if(LiquidFoods.getString("consumedAmount").equals("לא אכל")) {
					color = 3;
					}
				else if(LiquidFoods.getString("consumedAmount").equals("מתחת לחצי מנה")) {
					color= 3;
				}
				else if(LiquidFoods.getString("consumedAmount").equals("מעלה לחצי מנה")) {
					color= 2;
				}
				else if(LiquidFoods.getString("consumedAmount").equals("סיים מנה")) {
					color= 1;
				}
				else if(LiquidFoods.getString("consumedAmount").equals("אכל מעבר למנה")) {
					color= 2;
				}
				//sendColorAlert(LiquidFoods.getString("eventId"),"LiquidFood",color);
				statColors+=color;
			}
		}
	}
	
	//=======================================Parasites============================================//
	void Parasites(Connection myConn,String[] date) throws Exception {
		ResultSet Parasites=  getSet(myConn,"Parasites",date);
		int color=1;
		while(Parasites.next()) {
			if(Parasites.getString("eventColor")==null) {
				color = 1;
				counterEvents++;
				if(Parasites.getString("type").equals("כינים")) {
					color = 3;
				}
				else if(Parasites.getString("type").equals("תולעים")) {
					color= 3;
				}
				//sendColorAlert(Parasites.getString("eventId"),"Parasites",color);
				statColors+=color;
			}
		}	
	}
	
	//=========================================Cough==============================================//
	void Cough(Connection myConn,String[] date) throws Exception {
		int color =1;
		ResultSet Cough =  getSet(myConn,"Cough",date);
		while(Cough.next()) {
			if(Cough.getString("eventColor")==null) {
				color = 1;
				counterEvents++;
				if(Cough.getString("type").equals("טורדני")) {
					color = 3;
				}
				else if(Cough.getString("type").equals("לח")) {
					color= 2;
				}
				//sendColorAlert(Cough.getString("eventId"),"Cough",color);
				statColors+=color;
			}
		}
	}
	
	//=========================================Feces==============================================//
	void Feces(Connection myConn,String[] date) throws Exception {
		int color =1;
		ResultSet Feces=  getSet(myConn,"Feces",date);
		while(Feces.next()) {
			if(Feces.getString("eventColor")==null) {
				counterEvents++;
				color=1;
				if(Feces.getString("texture").equals("רירי")) {
					color = 3;
				}
				else if(Feces.getString("texture").equals("שילשול/מיימי ")) {
					color = 2;
				}
				if(color!=3) {
					if(Feces.getString("color").equals("אדום/ורוד")) {
						color= 3; //אלא אם הוא אכל מזונות אדומים למשל סלק
					}
					else if(Feces.getString("color").equals("אדום בוהק")) {
						color= 3;//אלא אם אכל מזונות אדומים למשל סלק 
					}
					else if(Feces.getString("color").equals("לבן אפור/חיוורת בצבע חימר")) {
						color= 3;
					}
					else if(Feces.getString("color").equals("חום בהיר")) {
						color= 2;
					}
				}
				if(color!=3) {
					if(Feces.getString("amount").equals("ללא")) {
						color= 3; //אלא אם הוא אכל מזונות אדומים למשל סלק
					}
					else if(Feces.getString("amount").equals("מריחה/כמות קטנה ")) {
						color= 2; //אלא אם הוא אכל מזונות אדומים למשל סלק
						}				
				}
				//sendColorAlert(Feces.getString("eventId"),"Feces",color);
				statColors+=color;
			}
		}	
	}
	
	//========================================Secretion===========================================//
	void Secretion(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Secretion=  getSet(myConn,"Secretion",date);
		while(Secretion.next()) {
			if(Secretion.getString("eventColor")==null) {
				counterEvents++;
				color=1;
				if(Secretion.getString("type").equals("דם")) {
					color = 3;
				}
				else if(Secretion.getString("type").equals("מוגלה")) {
					color = 3;
				}
				else if(Secretion.getString("type").equals("נזלת")) {
					color = 3;
				}
				//sendColorAlert(Secretion.getString("eventId"),"Secretion",color);
				statColors+=color;
			}
		}
	}
	
	//========================================SolidFood===========================================//
	void SolidFood(Connection myConn,String[] date) throws Exception {
		int color =1;
		ResultSet SolidFood=  getSet(myConn,"SolidFood",date);
		while(SolidFood.next()) {
			if(SolidFood.getString("eventColor")==null) {
				color = 1;
				counterEvents++;
				if(SolidFood.getString("consumedAmount").equals("לא אכל")) {
					color = 3;
				}
				else if(SolidFood.getString("consumedAmount").equals("מתחת לחצי מנה")) {
					color= 3;
				}
				else if(SolidFood.getString("consumedAmount").equals("מעלה לחצי מנה")) {
					color= 2;
				}
				else if(SolidFood.getString("consumedAmount").equals("סיים מנה")) {
					color= 1;
				}
				else if(SolidFood.getString("consumedAmount").equals("אכל מעבר למנה")) {
					color= 2;
				}
				//sendColorAlert(SolidFood.getString("eventId"),"SolidFood",color);
				statColors+=color;
			}
		}
	}
	
	//=========================================Vomitus============================================//
	void Vomitus(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Vomitus =  getSet(myConn,"Vomitus",date);
		while(Vomitus.next()) {
		 	if(Vomitus.getString("eventColor")==null) {
			System.out.println(color);
			color = 1;
			counterEvents++;
			if(Vomitus.getString("type").equals("הקאה")) {
				color = 3;
				}
			else if(Vomitus.getString("type").equals("פליטה מוגברת")) {
				color= 2;
			}
			//sendColorAlert(Vomitus.getString("eventId"),"Vomitus",color);
			statColors+=color;
			}
		}
	}
	
	//==========================================Urine=============================================//
	void Urine(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Urine=  getSet(myConn,"Urine",date);
		while(Urine.next()) {
			if(Urine.getString("eventColor")==null) {
				counterEvents++;
				color=1;
				if(Urine.getString("color").equals("צהוב כהה עד חום בהיר")) {
					color= 2; 
				}
				else if(Urine.getString("color").equals("אדום")) {
					color= 3; 
				}
				if(color!=3) {
					if(Urine.getString("fragrance").equals("חריף")) {
						color= 3;
					}
				}
				if(color!=3) {
					if(Urine.getString("amount").equals("ללא")) {
						color= 3; 
					}
					if(Urine.getString("amount").equals("מריחה/כמות קטנה ")) {
						color= 2;
					}	
				}
				//sendColorAlert(Urine.getString("eventId"),"Urine",color);
				statColors+=color;
			}
		}
	}

	//==========================================Sleep=============================================//
	void Sleep(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Sleep=  getSet(myConn,"Sleep",date);
		while(Sleep.next()) {
			if(Sleep.getString("eventColor")==null) {
				counterEvents++;
				color=1;
				if(Sleep.getString("sleepingScope").equals("אי שינה")) {
					color= 3; 
				}
				else if(Sleep.getString("sleepingScope").equals("שינה חלקית לא שקטה")) {
					color= 2; 
				}
				//sendColorAlert(Sleep.getString("eventId"),"Sleep",color);
				statColors+=color;
			}
		}
	}
	
	//==========================================Fever=============================================//
	void Fever(Connection myConn,String[] date) throws Exception{
		int color = 1;
		ResultSet Fever=  getSet(myConn,"Fever",date);
		while(Fever.next()) {
			if(Fever.getString("eventColor")==null) {
				counterEvents++;
				color=1;
				if(Fever.getString("tempreture").equals("מתחת לטווח תקין")) {
					color= 3; 
				}
				else if(Fever.getString("tempreture").equals("חום נמוך")) {
					color= 2; 
				}
				else if(Fever.getString("tempreture").equals("מעל טווח תקין")) {
					color= 3; 
				}
				//sendColorAlert(Fever.getString("eventId"),"Fever",color);
				statColors+=color;
			}
		}
	}
	
	//==========================================Water=============================================//
		void Water(Connection myConn,String[] date) throws Exception{
			int color = 1;
			ResultSet Water=  getSet(myConn,"Water",date);
			while(Water.next()) {
				if(Water.getString("eventColor")==null) {
					counterEvents++;
					color=1;
					if(Water.getString("consumedAmount").equals("לא שתה")) {
						color= 3; 
					}
					else if(Water.getString("consumedAmount").equals("שתה מתחת לחצי בקבוק")) {
						color= 2; 
					}
					else if(Water.getString("consumedAmount").equals("שתה מעל חצי בקבוק")) {
						color= 2; 
					}
					//sendColorAlert(Water.getString("eventId"),"Water",color);
					statColors+=color;
				}
			}
		}
	
	//==========================================Disease=============================================//
		void Disease(Connection myConn,String[] date) throws Exception{
			ResultSet Disease=  getSet(myConn,"Disease",date);
			while(Disease.next()) {
				if(Disease.getString("eventColor")==null) {
					counterEvents++;
					//sendColorAlert(Disease.getString("eventId"),"Disease",3);
					statColors+=3;
				}
			}
		}
		
	//==========================================Disease=============================================//
		void Rash(Connection myConn,String[] date) throws Exception{
			ResultSet Rash=  getSet(myConn,"Rash",date);
			while(Rash.next()) {
				if(Rash.getString("eventColor")==null) {
					counterEvents++;
					//sendColorAlert(Rash.getString("eventId"),"Rash",3);
					statColors+=3;
				}
			}
		}
		
		
		
		
		
	ResultSet getSet(Connection myConn,String tableName,String[] date) throws SQLException {
		Statement mystmt = myConn.createStatement();
		String giveMeAllEvents= "SELECT * FROM "+tableName+" WHERE eventDate = "+date[0]+"/"+date[1]+"/"+date[2];
		ResultSet events= mystmt.executeQuery(giveMeAllEvents);//sent the query to get all the kids
		return events;
	}
	
	
	String addB(String s) {
		String temp = "\""+s+"\"";
		return temp;
	}
}
