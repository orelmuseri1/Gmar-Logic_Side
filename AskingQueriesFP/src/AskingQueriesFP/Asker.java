package AskingQueriesFP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class Asker {
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
	
	
	int ask() {
		Connection myConn = null;
		int countermsg = 0; //counting how match msg he send to the system in this run
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:" + this.port + "/FinelProjectDB", this.userName, this.password);
			//countermsg+= firstquery(myConn);
			
			
			 if(checkTime(12,0,12,3)) {
			countermsg +=endDayWaterChack(myConn,1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return countermsg;
	}
	
	/*int firstquery(Connection myConn) {
		
		try {
			Statement mystmt = myConn.createStatement();
			String sql = "SELECT * FROM child";
			
			ResultSet myres= mystmt.executeQuery(sql);
			String [] today = LocalDate.now().toString().split("-");

			while(myres.next()) {
				System.out.println(myres.getString("firstName") + "," + myres.getString("childID")); // print the name of the kid
				String sql2 = "SELECT * FROM LiquidFood WHERE child = " + myres.getString("childID");//get all the event in LiquidFood for this kid
				String sql3 = "SELECT * FROM SolidFood WHERE child = " + myres.getString("childID");//get all the event in SolidFood for this kid
				Statement mystmt2 = myConn.createStatement();
				Statement mystmt3 = myConn.createStatement();
				ResultSet myres2= mystmt2.executeQuery(sql2);
				ResultSet myres3= mystmt3.executeQuery(sql3);
				String[] date,temp,time;
				while(myres2.next()) {
					date = myres2.getString("eventDate").split("/");
					temp = date[2].toString().split(",");
					date[2]=temp[0];
					time = temp[1].toString().split(":");
					for(int i =0;i<time.length;i++)
						System.out.println(time[i]);
					if(date[2].equals(today[0])) {
					System.out.println("the event for this kid: "+ myres2.getString("child")+"-"+ myres2.getString("eventType")+"-"+ myres2.getString("eventDate")+ "-" + myres2.getString("amount") + "-" + myres2.getString("staff"));
						//for(String s:)System.out.println(s);
					}
					}
					while(myres3.next())
					System.out.println("the event for this kid: "+ myres3.getString("child")+"-"+ myres3.getString("eventType")+"-"+ myres3.getString("eventDate")+ "-" + myres3.getString("amount") + "-" + myres3.getString("staff"));
				
			
			}
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
		}
		
		return 1;
	}
	*/
	
	
	
	int endDayWaterChack(Connection myConn, int time) { // needed to asked near to the end of the day
		int value=0;
		try {
			Statement mystmt = myConn.createStatement();
			String giveMeAllKids= "SELECT * FROM child";
			ResultSet kids= mystmt.executeQuery(giveMeAllKids);//sent the query to get all the kids
			String [] today = LocalDate.now().toString().split("-");
			String[] dateEvent,temp;
			int counterWater = 0;
			
			while(kids.next()) {																			// pass all the kids 
				System.out.println(kids.getString("firstName") + "," + kids.getString("childID"));          // print the name of the kid
				String giveMeAllEvents = "SELECT * FROM Water WHERE child = " + kids.getString("childID"); 	//the query to get all the event in Water for this kid
				Statement mystmt2 = myConn.createStatement(); 												//creating statement
				ResultSet events= mystmt2.executeQuery(giveMeAllEvents);									//execute the query
				while(events.next()) {																		//pass all the events
					dateEvent = events.getString("eventDate").split("/");									//getting the date and the time of the event a
					temp = dateEvent[2].toString().split(",");
					dateEvent[2]=temp[0];
					if(today[2].equals(dateEvent[0])&&today[1].equals(dateEvent[1])&& today[0].equals(dateEvent[2])) { //if the date of the event is today
						System.out.println(events.getString("child")+"-"+ events.getString("amount") + "-" + events.getString("consumedAmount") + "-" + events.getString("eventDate")); //print the id of the kid the amount of water he get and the amount he actualy drink
						if(events.getString("consumedAmount").equals("finish"))									//checking the amount he drink and sum the amount he drink all day
							counterWater += events.getInt("amount");
						else if(events.getString("consumedAmount").equals("more than helf"))
							counterWater += 0.6*events.getInt("amount");
						else if(events.getString("consumedAmount").equals("less than helf"))
							counterWater += 0.4*events.getInt("amount");
						else if(events.getString("consumedAmount").equals("nothing"))
							counterWater += 0*events.getInt("amount");
					}
				}
				if(time == 1) {
					if(counterWater<600) { 		// if he drink less than he actualy need
						System.out.println(counterWater);
						sender.send(kids.getString("firstName")+" "+kids.getString("lastName"), "the kid doesn't drink enough"); // the function that makes the alert
						value++;
					}else System.out.println(kids.getString("firstName")+ " "+counterWater);
						counterWater=0;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	boolean checkTime(int starthour,int startmin,int finishhour,int finishmin) {
		int hour,min; 
		String[] time;
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		time = timeStamp.split("_");									//getting the date and the time of the event a
		 hour = (time[1].charAt(0)-'0')*10 +time[1].charAt(1)-'0';
		 min = (time[1].charAt(2)-'0')*10 +time[1].charAt(3)-'0';
		 if((hour >= starthour&&min>=startmin) && (hour <= starthour&&min<=startmin))
		return true;
		 else return false;
	}
	
}
