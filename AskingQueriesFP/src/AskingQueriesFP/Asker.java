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
import java.util.jar.JarException;



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
	
	
	int ask() throws ParseException, SQLException {
		Connection myConn = null;
		//sender.send(12,"10"+"/"+"4"+"/"+"2019","10"+":"+"12"+ ":" + "05","לא תקין","Water", "the kid doesn't drink enought today"); 	// the function that makes the alert
		int countermsg = 0; //counting how match msg he send to the system in this run
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:" + this.port + "/FinelProjectDB", this.userName, this.password);			
		countermsg += DailyFoodChack(myConn,WhatIsTheDate());
		if(checkTime(MIDLLEHOUR,MIDLLEMIN,MIDLLEHOUR,MIDLLEMIN+3)) {
			countermsg += DailyWaterChack(myConn,1,WhatIsTheDate()); 
			}
		if(checkTime(FINELHOUR,FINELMIN,FINELHOUR,FINELMIN+3)) {
			countermsg += DailyWaterChack(myConn,2,WhatIsTheDate());
			if(!WhatIsTheDay().equals("Mon")||!WhatIsTheDay().equals("Sun"))
			countermsg += XDaysEgoWaterChack(myConn,WhatIsTheDate());
			 }
			//countermsg+= firstquery(myConn);
		return countermsg;
		}
	
		
	
	 // check the amount of water every child drink in the end of the day and the midlle of the day
	int DailyWaterChack(Connection myConn, int time,String[] today) {
		int value=0;
		try {
			Statement mystmt = myConn.createStatement();
			String giveMeAllKids= "SELECT * FROM child";
			ResultSet kids= mystmt.executeQuery(giveMeAllKids);//sent the query to get all the kids
			String[] dateEvent,temp;
			int counterWater = 0;
			while(kids.next()) {																			// pass all the kids 
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));          // print the name of the kid
				String giveMeAllEvents = "SELECT * FROM Water WHERE child = " + kids.getString("childID"); 	//the query to get all the event in Water for this kid
				Statement mystmt2 = myConn.createStatement(); 												//creating statement
				ResultSet events= mystmt2.executeQuery(giveMeAllEvents);									//execute the query
				while(events.next()) {																		//pass all the events
					dateEvent = events.getString("eventDate").split("/");									//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(today[0].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])) { //if the date of the event is today
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
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין","Water", "הילד לא שתה מספיק מים היום!");
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // the function that makes the alert
					}else System.out.println(kids.getString("firstName")+ " "+counterWater); // no need actuely to pay attention for else cuz its mine everything ok
						counterWater=0;
				}
				if(time == 2) {
					if(counterWater<300) { 		// if he drink less than he actualy need in the half of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין","Water", "הילד לא שתה מספיק מים היום!");
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // the function that makes the alert
					}else System.out.println(kids.getString("firstName")+ " " + counterWater); // no need actuely to pay attention for else cuz its mine everything ok
						counterWater=0;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	//check the amount of water every child drink in the pass coupl days(3)
	// needed to asked near to the end of the day
	int XDaysEgoWaterChack(Connection myConn,String[] today) { 
		int value=0;
		try {
			Statement mystmt = myConn.createStatement();
			String giveMeAllKids= "SELECT * FROM child";
			ResultSet kids= mystmt.executeQuery(giveMeAllKids);//sent the query to get all the kids
			String[] dateEvent,temp;
			int counterWater = 0;

			while(kids.next()) {																			// pass all the kids 
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));          // print the name of the kid and his ID
				String giveMeAllEvents = "SELECT * FROM Water WHERE child = " + kids.getString("childID"); 	//the query to get all the event in Water for this kid
				Statement mystmt2 = myConn.createStatement(); 												//creating statement
				ResultSet events= mystmt2.executeQuery(giveMeAllEvents);									//execute the query
				while(events.next()) {																		//pass all the events
					dateEvent = events.getString("eventDate").split("/");									//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					
					if(today[1].equals(dateEvent[1])&& today[2].equals(dateEvent[2])&&(today[0].equals(dateEvent[0])||today[0].equals(dateEvent[0]+1)||today[0].equals(dateEvent[0]+2))) { //if the date of the event is what we looking for
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
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין","Water", "הילד לא שותה מספיק מים !"); // the function that makes the alert
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
					counterWater=0;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	
	
	int DailyFoodChack(Connection myConn,String[] today) {
		int value=0;
		try {
			Statement mystmt = myConn.createStatement();
			String giveMeAllKids= "SELECT * FROM child";
			ResultSet kids= mystmt.executeQuery(giveMeAllKids);//sent the query to get all the kids
			String[] dateEvent,temp;
			float counterWater = 0;
			while(kids.next()) {																				// pass each kid
				//System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));            // print the name of the kid
				String giveMeAllEvents = "SELECT * FROM SolidFood WHERE child = " + kids.getString("childID"); 	//the query to get all the event in Water for this kid
				Statement mystmt2 = myConn.createStatement(); 													//creating statement
				ResultSet events = mystmt2.executeQuery(giveMeAllEvents);										//execute the query
				while(events.next()) {																			//pass all the events
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
				String giveMeAllEvents2 = "SELECT * FROM LiquidFood WHERE child = " + kids.getString("childID"); 	//the query to get all the event in Water for this kid
				Statement mystmt3 = myConn.createStatement(); 													//creating statement
				ResultSet events2 = mystmt3.executeQuery(giveMeAllEvents2);										//execute the query
				while(events2.next()) {
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
				System.out.println(counterWater);
					if(counterWater< 1.0) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.send(kids.getInt("childID"),today[0]+"/"+today[1]+"/"+today[2],today[3]+":"+today[4]+ ":" + today[5],"לא תקין","Food","הילד לא אכל היום מספיק!"); // the function that makes the alert
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
				}else System.out.println(kids.getString("firstName")+ " "+counterWater); // no need actuely to pay attention for else cuz its mean everything ok
					counterWater=0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	
	
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
	
	String WhatIsTheDay() throws ParseException { //function that give me the 3 first latter of the name of today 
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
}
