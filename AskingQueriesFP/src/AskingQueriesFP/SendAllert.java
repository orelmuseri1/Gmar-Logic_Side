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

import org.json.JSONObject;


public class SendAllert {
	float counterEvents=0,statColors=0;
	
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
			URL	url = new URL("http://127.0.0.1:5000/events/"+table+"/"+ID); 
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("PUT");
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
		if(counterEvents !=0.0)return statColors/counterEvents;
		else return 0;
	}
	
	
	
	//======================================LiquidFoods===========================================//		
	void LiquidFoods(Connection myConn,String[] date) throws Exception {
		ResultSet LiquidFoods=  getSet(myConn,"LiquidFood",date);
		while(LiquidFoods.next()) {
			int color = 1;
			if(LiquidFoods.getString("level")==null) {
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
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",LiquidFoods.getString("eventDate"));
			    object.put("eventTime",LiquidFoods.getString("eventTime"));
			    object.put("childID",LiquidFoods.getString("childID"));
			    object.put("staffID",LiquidFoods.getString("staffID"));
			    object.put("amount",LiquidFoods.getString("amount"));
			    object.put("consumedAmount",LiquidFoods.getString("consumedAmount"));
			    object.put("mealType",LiquidFoods.getString("mealType"));
			    object.put("eventId", String.valueOf(LiquidFoods.getString("eventId")));
				sendPutColor(LiquidFoods.getString("eventId"),object,"LiquidFoodEvent");
				statColors+=color;
			}
		}
	}
	
	//=======================================Parasites============================================//
	void Parasites(Connection myConn,String[] date) throws Exception {
		ResultSet Parasites=  getSet(myConn,"Parasites",date);
		int color=1;
		while(Parasites.next()) {
			if(Parasites.getString("level")==null) {
				color = 1;
				counterEvents++;
				if(Parasites.getString("type").equals("כינים")) {
					color = 3;
				}
				else if(Parasites.getString("type").equals("תולעים")) {
					color= 3;
				}
				//sendColorAlert(Parasites.getString("eventId"),"Parasites",color);
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",Parasites.getString("eventDate"));
			    object.put("eventTime",Parasites.getString("eventTime"));
			    object.put("childID",Parasites.getString("childID"));
			    object.put("staffID",Parasites.getString("staffID"));
			    object.put("type",Parasites.getString("type"));
			    object.put("eventId", String.valueOf(Parasites.getString("eventId")));
				sendPutColor(Parasites.getString("eventId"),object,"ParasitesEvent");
				statColors+=color;
			}
		}	
	}
	
	//=========================================Cough==============================================//
	void Cough(Connection myConn,String[] date) throws Exception {
		int color =1;
		ResultSet Cough =  getSet(myConn,"Cough",date);
		while(Cough.next()) {
			if(Cough.getString("level")==null) {
				color = 1;
				counterEvents++;
				if(Cough.getString("type").equals("טורדני")) {
					color = 3;
				}
				else if(Cough.getString("type").equals("לח")) {
					color= 2;
				}
				//sendColorAlert(Cough.getString("eventId"),"Cough",color);
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",Cough.getString("eventDate"));
			    object.put("eventTime",Cough.getString("eventTime"));
			    object.put("childID",Cough.getString("childID"));
			    object.put("staffID",Cough.getString("staffID"));
			    object.put("type",Cough.getString("type"));
			    object.put("eventId", String.valueOf(Cough.getString("eventId")));
				sendPutColor(Cough.getString("eventId"),object,"CoughEvent");
				statColors+=color;
			}
		}
	}
	
	//=========================================Feces==============================================//
	void Feces(Connection myConn,String[] date) throws Exception {
		int color =1;
		ResultSet Feces=  getSet(myConn,"Feces",date);
		while(Feces.next()) {
			if(Feces.getString("level")==null) {
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
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",Feces.getString("eventDate"));
			    object.put("eventTime",Feces.getString("eventTime"));
			    object.put("childID",Feces.getString("childID"));
			    object.put("staffID",Feces.getString("staffID"));
			    object.put("amount",Feces.getString("amount"));
			    object.put("color",Feces.getString("color"));
			    object.put("texture",Feces.getString("texture"));
			    object.put("eventId", String.valueOf(Feces.getString("eventId")));
				sendPutColor(Feces.getString("eventId"),object,"FecesEvent");
				statColors+=color;
			}
		}	
	}
	
	//========================================Secretion===========================================//
	void Secretion(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Secretion=  getSet(myConn,"Secretion",date);
		while(Secretion.next()) {
			if(Secretion.getString("level")==null) {
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
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",Secretion.getString("eventDate"));
			    object.put("eventTime",Secretion.getString("eventTime"));
			    object.put("childID",Secretion.getString("childID"));
			    object.put("staffID",Secretion.getString("staffID"));
			    object.put("rank",Secretion.getString("rank"));
			    object.put("area",Secretion.getString("area"));
			    object.put("type",Secretion.getString("type"));
			    object.put("eventId", String.valueOf(Secretion.getString("eventId")));
				sendPutColor(Secretion.getString("eventId"),object,"SecretionEvent");
				statColors+=color;
			}
		}
	}
	
	//========================================SolidFood===========================================//
	void SolidFood(Connection myConn,String[] date) throws Exception {
		int color =1;
		ResultSet SolidFood=  getSet(myConn,"SolidFood",date);
		while(SolidFood.next()) {
			if(SolidFood.getString("level")==null) {
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
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",SolidFood.getString("eventDate"));
			    object.put("eventTime",SolidFood.getString("eventTime"));
			    object.put("childID",SolidFood.getString("childID"));
			    object.put("staffID",SolidFood.getString("staffID"));
			    object.put("amount",SolidFood.getString("amount"));
			    object.put("consumedAmount",SolidFood.getString("consumedAmount"));
			    object.put("mealInMenu",SolidFood.getString("mealInMenu"));
			    object.put("mealType",SolidFood.getString("mealType"));
			    object.put("eventId", String.valueOf(SolidFood.getString("eventId")));
				sendPutColor(SolidFood.getString("eventId"),object,"SolidFoodEvent");
				statColors+=color;
			}
		}
	}
	
	//=========================================Vomitus============================================//
	void Vomitus(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Vomitus =  getSet(myConn,"Vomitus",date);
		while(Vomitus.next()) {
		 	if(Vomitus.getString("level")==null) {
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
			JSONObject object = new JSONObject();
		    object.put("level",color);
		    object.put("eventDate",Vomitus.getString("eventDate"));
		    object.put("eventTime",Vomitus.getString("eventTime"));
		    object.put("childID",Vomitus.getString("childID"));
		    object.put("staffID",Vomitus.getString("staffID"));
		    object.put("proper",Vomitus.getString("proper"));
		    object.put("eventId", String.valueOf(Vomitus.getString("eventId")));
			sendPutColor(Vomitus.getString("eventId"),object,"VomitusEvent");
			statColors+=color;
			}
		}
	}
	
	//==========================================Urine=============================================//
	void Urine(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Urine=  getSet(myConn,"Urine",date);
		while(Urine.next()) {
			if(Urine.getString("level")==null) {
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
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",Urine.getString("eventDate"));
			    object.put("eventTime",Urine.getString("eventTime"));
			    object.put("childID",Urine.getString("childID"));
			    object.put("staffID",Urine.getString("staffID"));
			    object.put("amount",Urine.getString("amount"));
			    object.put("color",Urine.getString("color"));
			    object.put("fragrance",Urine.getString("fragrance"));
			    object.put("eventId", String.valueOf(Urine.getString("eventId")));
				sendPutColor(Urine.getString("eventId"),object,"UrineEvent");
				statColors+=color;
			}
		}
	}

	//==========================================Sleep=============================================//
	void Sleep(Connection myConn,String[] date) throws Exception {
		int color = 1;
		ResultSet Sleep=  getSet(myConn,"Sleep",date);
		while(Sleep.next()) {
			if(Sleep.getString("level")==null) {
				counterEvents++;
				color=1;
				if(Sleep.getString("sleepingScope").equals("אי שינה")) {
					color= 3; 
				}
				else if(Sleep.getString("sleepingScope").equals("שינה חלקית לא שקטה")) {
					color= 2; 
				}
				//sendColorAlert(Sleep.getString("eventId"),"Sleep",color);
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",Sleep.getString("eventDate"));
			    object.put("eventTime",Sleep.getString("eventTime"));
			    object.put("childID",Sleep.getString("childID"));
			    object.put("staffID",Sleep.getString("staffID"));
			    object.put("allocatedTime",Sleep.getString("allocatedTime"));
			    object.put("sleepingScope",Sleep.getString("sleepingScope"));
			    object.put("type",Sleep.getString("type"));
			    object.put("eventId", String.valueOf(Sleep.getString("eventId")));
				sendPutColor(Sleep.getString("eventId"),object,"SleepEvent");
				statColors+=color;
			}
		}
	}
	
	//==========================================Fever=============================================//
	void Fever(Connection myConn,String[] date) throws Exception{
		int color = 1;
		ResultSet Fever=  getSet(myConn,"Fever",date);
		while(Fever.next()) {
			if(Fever.getString("level")==null) {
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
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",Fever.getString("eventDate"));
			    object.put("eventTime",Fever.getString("eventTime"));
			    object.put("childID",Fever.getString("childID"));
			    object.put("staffID",Fever.getString("staffID"));
			    object.put("tempreture",Fever.getString("tempreture"));
			    object.put("eventId", String.valueOf(Fever.getString("eventId")));
				sendPutColor(Fever.getString("eventId"),object,"FeverEvent");
				statColors+=color;
			}
		}
	}
	
	//==========================================Water=============================================//
		void Water(Connection myConn,String[] date) throws Exception{
			int color = 1;
			ResultSet Water=  getSet(myConn,"Water",date);
			while(Water.next()) {
				System.out.println(Water.getString("level"));
				if(Water.getString("level")==null) {
					counterEvents++;
					color=1;
					if(Water.getString("consumedAmount").equals("לא שתה")) {
						color= 3; 
					}
					else if(Water.getString("consumedAmount").equals("מתחת לחצי בקבוק")) {
						color= 2; 
					}
					else if(Water.getString("consumedAmount").equals("מעל חצי בקבוק")) {
						color= 2; 
					}
					//sendColorAlert(Water.getString("eventId"),"Water",color);
					JSONObject object = new JSONObject();
				    object.put("level",color);
				    object.put("eventDate",Water.getString("eventDate"));
				    object.put("eventTime",Water.getString("eventTime"));
				    object.put("childID",Water.getString("childID"));
				    object.put("staffID",Water.getString("staffID"));
				    object.put("amount",Water.getString("amount"));
				    object.put("consumedAmount",Water.getString("consumedAmount"));
				    object.put("eventId", String.valueOf(Water.getString("eventId")));
					sendPutColor(Water.getString("eventId"),object,"WaterEvent");
					statColors+=color;
				}
			}
		}
	
	//==========================================Disease=============================================//
		void Disease(Connection myConn,String[] date) throws Exception{
			ResultSet Disease=  getSet(myConn,"Disease",date);
			while(Disease.next()) {
				if(Disease.getString("level")==null) {
					counterEvents++;
					//sendColorAlert(Disease.getString("eventId"),"Disease",3);
					JSONObject object = new JSONObject();
				    object.put("level",String.valueOf(3));
				    object.put("eventDate",Disease.getString("eventDate"));
				    object.put("eventTime",Disease.getString("eventTime"));
				    object.put("childID",Disease.getString("childID"));
				    object.put("staffID",Disease.getString("staffID"));
				    object.put("details",Disease.getString("details"));
				    object.put("type",Disease.getString("type"));
				    object.put("eventId", String.valueOf(Disease.getString("eventId")));
					sendPutColor(Disease.getString("eventId"),object,"DiseaseEvent");
					statColors+=3;
				}
			}
		}
		
	//==========================================Disease=============================================//
		void Rash(Connection myConn,String[] date) throws Exception{
			ResultSet Rash=  getSet(myConn,"Rash",date);
			while(Rash.next()) {
				if(Rash.getString("level")==null) {
					counterEvents++;
					//sendColorAlert(Rash.getString("eventId"),"Rash",3);
					JSONObject object = new JSONObject();
				    object.put("level",String.valueOf(3));
				    object.put("eventDate",Rash.getString("eventDate"));
				    object.put("eventTime",Rash.getString("eventTime"));
				    object.put("childID",Rash.getString("childID"));
				    object.put("staffID",Rash.getString("staffID"));
				    object.put("area",Rash.getString("area"));
				    object.put("type",Rash.getString("type"));
				    object.put("eventId", String.valueOf(Rash.getString("eventId")));
					sendPutColor(Rash.getString("eventId"),object,"RashEvent");
					statColors+=3;
				}
			}
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
