package AskingQueriesFP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;



public class Asker {
	private static final int MIDLLEHOUR = 12;
	private static final int MIDLLEMIN = 0;
	private static final int FINELHOUR = 16;
	private static final int FINELMIN = 30;
	int port;
	String userName;
	String password;
	SendAllert sender;
	
	//constructor
	Asker(int port, String userName, String password){
		this.port=port;
		this.userName = userName;
		this.password = password;
		sender = new SendAllert();
		}
	
	
	int ask() throws Exception {
		Connection myConn = null;
		int countermsg = 0; //counting how match msg he send to the system in this run
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:" + this.port + "/FinelProjectDB", this.userName, this.password);	
		countermsg +=  sender.checkColorsAlerts(myConn,WhatIsTheDate());
		if(checkTime(MIDLLEHOUR,MIDLLEMIN,MIDLLEHOUR,MIDLLEMIN+3)) {
			countermsg += DailyDiaperCheck(myConn,WhatIsTheDate(),1,getKids(myConn));
			countermsg += DailyWaterCheck(myConn,1,WhatIsTheDate(),getKids(myConn)); 
			countermsg += DailyFoodCheck(myConn,WhatIsTheDate(),1,getKids(myConn));
			}
		if(checkTime(FINELHOUR,FINELMIN,FINELHOUR,FINELMIN+3)) {
			countermsg += DailyVomitusCheck(myConn,1, WhatIsTheDate(), getKids(myConn));
			countermsg +=DailyDiaperCheck(myConn,WhatIsTheDate(),2,getKids(myConn));
			countermsg += DailyWaterCheck(myConn,2,WhatIsTheDate(),getKids(myConn));
			countermsg += DailyFoodCheck(myConn,WhatIsTheDate(),2,getKids(myConn));
			if(!WhatIsTheDay().equals("Mon")||!WhatIsTheDay().equals("Sun")) {
				countermsg += XDaysEgoWaterCheck(myConn,WhatIsTheDate(),getKids(myConn));
				countermsg += DailyVomitusCheck(myConn,2, WhatIsTheDate(), getKids(myConn));
				}
			 }
		return countermsg;
		}
	
		//=====================================================================querys=====================================================================//
	int DailyVomitusCheck(Connection myConn,int time ,String[] today,ResultSet kids) throws Exception {
		int value=0;
		try {
			String[] dateEvent,temp;
			int counter=0,numOfEvents=1;
			while(kids.next()) {															// pass all the kids 
				 JSONObject object = new JSONObject();
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));        // print the name of the kid
				ResultSet events= getSet(myConn, kids.getString("childID"), "Vomitus");
				while(events.next()) {																		//pass all the events
					dateEvent = events.getString("eventDate").split("/");									//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(time==1) {
						if(today[0].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])) { //if the date of the event is today
							object.put(String.valueOf(numOfEvents), events.getString("eventId"));
							numOfEvents++;
							counter++;
						}
					}
					else if(time==2) {
						if(today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])&&(today[0].equals(dateEvent[0])||today[0].equals(dateEvent[0]+1)||today[0].equals(dateEvent[0]+2))) {
							object.put(String.valueOf(numOfEvents), events.getString("eventId"));
							numOfEvents++;
							counter++;
						}
					}
					
				}if(time==1) {
					if(counter==2) {
						sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"סביר",object, " הילד פלט/הקיא מספר חריג של פעמים","Vomitus");
					value++;}
					if(counter>2) {
						sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object, "סכנת התייבשות לתת לילד מים!","Vomitus");
					value++;}
				}
				else if(time==2) {
					if(counter>3) {
						sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object, "בימים אחרונים הילד פלטֿֿ/הקיא מספר חריג של פעמים ","Vomitus");
					value++;}
					}
				counter = 0;
				numOfEvents=1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	 // check the amount of water every child drink in the end of the day and the midlle of the day
	int DailyWaterCheck(Connection myConn, int time,String[] today,ResultSet kids) throws Exception {
		int value=0,numOfEvents=1;
		try {
			String[] dateEvent,temp;
			int counterWater = 0;
			while(kids.next()) {															// pass all the kids 
				JSONObject object = new JSONObject();
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));        // print the name of the kid
				ResultSet events= getSet(myConn, kids.getString("childID"), "Water");
				while(events.next()) {																		//pass all the events
					dateEvent = events.getString("eventDate").split("/");									//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(today[0].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])) { //if the date of the event is today
						object.put(String.valueOf(numOfEvents), events.getString("eventId"));
						numOfEvents++;
						//System.out.println(events.getString("child")+"-"+ events.getString("amount") + "-" + events.getString("consumedAmount") + "-" + events.getString("eventDate")); //print the id of the kid the amount of water he get and the amount he actualy drink
						if(events.getString("consumedAmount").equals("finish"))									//checking the amount he drink and sum the amount he drink all day
							counterWater += events.getInt("amount");
						else if(events.getString("consumedAmount").equals("more than half"))
							counterWater += 0.6*events.getInt("amount");
						else if(events.getString("consumedAmount").equals("less than half"))
							counterWater += 0.4*events.getInt("amount");
						else if(events.getString("consumedAmount").equals("nothing"))
							counterWater += 0*events.getInt("amount");
					}
				}
				if(time == 1) {
					if(counterWater<600) { 		// if he drink less than he actualy need near to the end of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object, "הילד לא שתה מספיק מים היום!","Water");
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // the function that makes the alert
					}
				}
				else if(time == 2) {
					if(counterWater<300) { 		// if he drink less than he actualy need in the half of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object, "הילד לא שתה מספיק מים היום!","Water");
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // the function that makes the alert
					}
						counterWater=0;
						numOfEvents=1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	//check the amount of water every child drink in the pass coupl days(3)
	int XDaysEgoWaterCheck(Connection myConn,String[] today,ResultSet kids) throws Exception { 
		int value=0;
		try {
			String[] dateEvent,temp;
			int counterWater = 0,numOfEvents=1;

			while(kids.next()) {																			// pass all the kids 
				 JSONObject object = new JSONObject();
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));          // print the name of the kid and his ID
				ResultSet events=getSet(myConn, kids.getString("childID"), "Water");	
				while(events.next()) {																		//pass all the events
					dateEvent = events.getString("eventDate").split("/");									//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					
					if(today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])&&(today[0].equals(dateEvent[0])||today[0].equals(dateEvent[0]+1)||today[0].equals(dateEvent[0]+2))) { //if the date of the event is what we looking for
						object.put(String.valueOf(numOfEvents), events.getString("eventId"));
						numOfEvents++;
						if(events.getString("consumedAmount").equals("finish"))									//checking the amount he drink and sum the amount he drink all day
							counterWater += events.getInt("amount");
						else if(events.getString("consumedAmount").equals("more than half"))
							counterWater += 0.6*events.getInt("amount");
						else if(events.getString("consumedAmount").equals("less than half"))
							counterWater += 0.4*events.getInt("amount");
						else if(events.getString("consumedAmount").equals("nothing"))
							counterWater += 0*events.getInt("amount");
					}
				}
					if(counterWater<1350) { 		// if he drink less than he actualy need near to the end of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object, "הילד לא שותה מספיק מים !","Water"); // the function that makes the alert
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
					counterWater=0;
					numOfEvents=1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	
	
	//check the amount of food the every kid eat today
	int DailyFoodCheck(Connection myConn,String[] today,int time,ResultSet kids) throws Exception {
		int value=0,numOfEvents=1;
		try {
			String[] dateEvent,temp;
			float counterWater = 0;
			while(kids.next()) {																				// pass each kid
				 JSONObject object = new JSONObject();
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));            // print the name of the kid
				ResultSet events = getSet(myConn, kids.getString("childID"), "SolidFood");	
				while(events.next()) {																			//pass all the events
					object.put(String.valueOf(numOfEvents), events.getString("eventId"));
					numOfEvents++;
					dateEvent = events.getString("eventDate").split("/");										//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(today[0].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])) { //if the date of the event is today
						System.out.println(events.getString("consumedAmount")); //print the id of the kid the amount of water he get and the amount he actualy drink
						if(events.getString("consumedAmount").equals("finish"))	{								//checking the amount he drink and sum the amount he drink all day
							counterWater += 1;}
						else if(events.getString("consumedAmount").equals("more than half")) {
							counterWater += 0.6;}
						else if(events.getString("consumedAmount").equals("less than half")) {
							counterWater += 0.4;}
						else if(events.getString("consumedAmount").equals("nothing")) {
							counterWater += 0;}
					}					
				}
				// need to know what is the real amout of food that need to count
				ResultSet events2 =  getSet(myConn, kids.getString("childID"), "LiquidFood");		
				while(events2.next()) {
					object.put(String.valueOf(numOfEvents), events.getString("eventId"));
					numOfEvents++;
					dateEvent = events2.getString("eventDate").split("/");										//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(today[0].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])) {  //if the date of the event is today
						System.out.println(events2.getString("child")+ "-" + events2.getString("consumedAmount") + "-" + events2.getString("eventDate")+ " L"); //print the id of the kid the amount of water he get and the amount he actualy drink
						if(events2.getString("consumedAmount").equals("finish")) {									//checking the amount he drink and sum the amount he drink all day
							counterWater += 1;}
						else if(events2.getString("consumedAmount").equals("more than half")) {
							counterWater += 0.6;}
						else if(events2.getString("consumedAmount").equals("less than half")) {
							counterWater += 0.4;}
						else if(events2.getString("consumedAmount").equals("nothing")) {
							counterWater += 0;}
						
					}
				}
				if(time == 1) {
					if(counterWater< 0.5) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object,"הילד לא אכל היום מספיק!","Food"); // the function that makes the alert
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else System.out.println(kids.getString("firstName")+ " "+counterWater); // no need actuely to pay attention for else cuz its mean everything ok
						counterWater=0;
				}
				if(time == 2) {
					if(counterWater< 1.0) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object,"הילד לא אכל היום מספיק!","Food"); // the function that makes the alert
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else System.out.println(kids.getString("firstName")+ " "+counterWater); // no need actuely to pay attention for else cuz its mean everything ok
						counterWater=0;
						numOfEvents=1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	

	
	
	//check if the kid was" in the "toalet" today
	int DailyDiaperCheck(Connection myConn,String[] today,int time,ResultSet kids) throws Exception {
		int value=0,numOfEvents=1;
		try {
			String[] dateEvent,temp;
			float counterWater = 0;
			while(kids.next()) {																				// pass each kid
				JSONObject object = new JSONObject();
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));            // print the name of the kid											//creating statement
				ResultSet events = getSet(myConn, kids.getString("childID"), "Urine");	
				while(events.next()) {																			//pass all the events
					dateEvent = events.getString("eventDate").split("/");										//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(today[0].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])) { //if the date of the event is today
						object.put(String.valueOf(numOfEvents), events.getString("eventId"));
						numOfEvents++;
						if(events.getString("amount").equals("חיתול מלא"))	{								//checking the amount he drink and sum the amount he drink all day
							counterWater += 1;}
						else if(events.getString("amount").equals("כמות רגילה")) {
							counterWater += 0.6;}
						else if(events.getString("amount").equals("כמות קטנה")) {
							counterWater += 0.4;}
						else if(events.getString("amount").equals("ללא")) {
							counterWater += 0;}
					}					
				}
				// need to know what is the real amout of food that need to count
				ResultSet events2 =getSet(myConn, kids.getString("childID"), "Feces");						//execute the query
				while(events2.next()) {
					
					dateEvent = events2.getString("eventDate").split("/");										//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(today[0].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])) {  //if the date of the event is today
						object.put(String.valueOf(numOfEvents), events.getString("eventId"));
						numOfEvents++;
						if(events2.getString("amount").equals("חיתול מלא")) {									//checking the amount he drink and sum the amount he drink all day
							counterWater += 1;}
						else if(events2.getString("amount").equals("כמות רגילה")) {
							counterWater += 0.6;}
						else if(events2.getString("amount").equals("כמות קטנה")) {
							counterWater += 0.4;}
						else if(events2.getString("amount").equals("ללא")) {
							counterWater += 0;}
						
					}
				}
				if(time == 1) {
					if(counterWater< 0.5) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object,"כמות הצואה/שתן קטנה","Diaper"); // the function that makes the alert
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else if(time == 2) {
					if(counterWater< 1.0) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין",object,"כמות הצואה/שתן קטנה","Diaper"); // the function that makes the alert
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				counterWater=0;
				numOfEvents=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	//================================================================using function====================================================================//
	
	//function that get time of start and the end and check if now is between  them
	boolean checkTime(int starthour,int startmin,int finishhour,int finishmin) { 
		int hour,min; 
		String[] time;
		if((starthour>finishhour)||( starthour==finishhour&&startmin>finishmin)) {
			return false;}
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		time = timeStamp.split("_");	
		 hour = (time[1].charAt(0)-'0')*10 +time[1].charAt(1)-'0';
		 min = (time[1].charAt(2)-'0')*10 +time[1].charAt(3)-'0';
		 if(((hour > starthour) && (hour < finishhour))||((hour == starthour&&min>=startmin) && (hour == finishhour&&min<=finishmin))||((hour == starthour&&min>=startmin) &&(starthour!=finishhour) &&(hour <= finishhour))||((hour > starthour) && (hour == finishhour&&min<=finishmin))) {
		return true;
		}
		 else return false;
	}

	//function that give me the 3 first latter of the name of today 
	String WhatIsTheDay() throws ParseException { 
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
		Date myDate = sdf.parse(timeStamp);
		sdf.applyPattern("EEE");
		String sMyDate = sdf.format(myDate);
		return sMyDate;
	}
	
	String[] WhatIsTheDate() {
		String [] today = LocalDate.now().toString().split("-"),nowtime,now = null;
		String hour,min,sec;
		now= new String[6];
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		nowtime = timeStamp.split("_");									//getting the date and the time of the event 
		 hour = nowtime[1].charAt(0) + "" + nowtime[1].charAt(1);
		 min =  nowtime[1].charAt(2) + "" + nowtime[1].charAt(3);
		 sec =  nowtime[1].charAt(4) + "" + nowtime[1].charAt(5);
		now[0] = today[2];
		now[1] = today[1];
		now[2] = today[0];
		now[3] = hour;
		now[4] = min;
		now[5] = sec;
		return now;
	}


	ResultSet getKids(Connection myConn) throws SQLException {
		Statement mystmt = myConn.createStatement();
		String giveMeAllKids= "SELECT * FROM child";
		ResultSet kids= mystmt.executeQuery(giveMeAllKids);//sent the query to get all the kids
		return kids;
	}
	
	
	ResultSet getSet(Connection myConn,String kid,String tableName) throws SQLException {
		Statement mystmt = myConn.createStatement();
		String giveMeAllEvents= "SELECT * FROM "+tableName+" WHERE child = "+kid;
		ResultSet events= mystmt.executeQuery(giveMeAllEvents);//sent the query to get all the kids
		return events;
	}
	
}