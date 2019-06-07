package AskingQueriesFP;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;



public class Asker {
	private static final int MIDLLEHOUR = 12;
	private static final int MIDLLEMIN = 0;
	private static final int FINELHOUR = 16;
	private static final int FINELMIN = 30;
	SendAllert sender;
	GetEvent getter=new GetEvent();
	float countercolorsEvents=0,statColors=0;
	
	//constructor
	Asker(){
		sender = new SendAllert();
		}
	
	
	int ask() throws Exception {
		int countermsg = 0;
		countermsg +=  checkColorsAlerts(WhatIsTheDate(0));
		countermsg += DailyVomitusCheck(1, WhatIsTheDate(0), getter.getKidsJsons());
		countermsg += DailyFoodCheck(WhatIsTheDate(0),2,getter.getKidsJsons());
		countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Secretion",2,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של נזלת היום","3","3",1);
		countermsg += FeverCheck(WhatIsTheDate(0),getter.getKidsJsons(),2,1);
		countermsg += DailyWaterCheck(1,WhatIsTheDate(0),getter.getKidsJsons()); 
		countermsg += DailyCheckMoreThenEventWithStringParm(WhatIsTheDate(0),getter.getKidsJsons(),"Feces",2,"אירועים חוזרים של צואה מסוג שילשול/מיימי","אירועים חוזרים של צואה מסוג שילשול/מיימי","3","3",1,"texture","שילשול/מיימי");
		countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Cough",2,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של שיעול היום","3","3",1);
		countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Rash",1,"קיים חשש לסוג אלרגיה לא ידוע","אירועים חוזרים של פריחה היום","3","3",1);
		if(checkTime(MIDLLEHOUR,MIDLLEMIN,MIDLLEHOUR,MIDLLEMIN+3)) {
			countermsg += FeverCheck(WhatIsTheDate(0),getter.getKidsJsons(),2,1);
			countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Secretion",2,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של נזלת היום","3","3",1);
			countermsg += DailyCheckMoreThenEventWithStringParm(WhatIsTheDate(0),getter.getKidsJsons(),"Feces",2,"אירועים חוזרים של צואה מסוג שילשול/מיימי","אירועים חוזרים של צואה מסוג שילשול/מיימי","3","3",1,"texture","שילשול/מיימי");
			countermsg += DailyCheckMoreThenEventWithDoubleParm(WhatIsTheDate(0),getter.getKidsJsons(),"Fever",1,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של חום היום","3","3",1,"tempreture",38);
			countermsg += DailyCheckMoreThenEventWithStringParm(WhatIsTheDate(0),getter.getKidsJsons(),"Urine",2,"לתת לילד מים","אירועים חוזרים של שתן כהה היום","2","3",1,"color","צהוב כהה/חום");
			countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Cough",2,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של שיעול היום","3","3",1);
			countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Rash",1,"קיים חשש לסוג אלרגיה לא ידוע","אירועים חוזרים של פריחה היום","3","3",1);
			countermsg += DailyDiaperCheck(WhatIsTheDate(0),1,getter.getKidsJsons());
			countermsg += DailyWaterCheck(1,WhatIsTheDate(0),getter.getKidsJsons()); 
			countermsg += DailyFoodCheck(WhatIsTheDate(0),1,getter.getKidsJsons());
			}
		if(checkTime(FINELHOUR,FINELMIN,FINELHOUR,FINELMIN+3)) {
			countermsg += FeverCheck(WhatIsTheDate(0),getter.getKidsJsons(),2,1);
			countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Secretion",2,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של נזלת היום","3","3",1);
			countermsg += DailyCheckMoreThenEventWithStringParm(WhatIsTheDate(0),getter.getKidsJsons(),"Feces",3,"אירועים חוזרים של צואה מסוג שילשול/מיימי","אירועים חוזרים של צואה מסוג שילשול/מיימי","3","3",1,"texture","שילשול/מיימי");
			countermsg += DailyCheckMoreThenEventWithStringParm(WhatIsTheDate(0),getter.getKidsJsons(),"Urine",2,"לתת לילד מים","אירועים חוזרים של שתן כהה היום","2","3",1,"color","צהוב כהה/חום");
			countermsg += DailyCheckMoreThenEventWithDoubleParm(WhatIsTheDate(0),getter.getKidsJsons(),"Fever",1,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של חום היום","3","3",1,"tempreture",38);
			countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Cough",2,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של שיעול היום","3","3",1);
			countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Rash",2,"קיים חשש לסוג אלרגיה לא ידוע","אירועים חוזרים של פריחה היום","3","3",1);
			countermsg += DailyVomitusCheck(1, WhatIsTheDate(0), getter.getKidsJsons());
			countermsg += DailyDiaperCheck(WhatIsTheDate(0),2,getter.getKidsJsons());
			countermsg += DailyWaterCheck(2,WhatIsTheDate(0),getter.getKidsJsons());
			countermsg += DailyFoodCheck(WhatIsTheDate(0),2,getter.getKidsJsons());
			if(!WhatIsTheDay().equals("Mon")||!WhatIsTheDay().equals("Sun")) {
				countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Secretion",5,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של נזלת בימים האחרונים","3","3",3);
				countermsg += DailyCheckMoreThenEventWithDoubleParm(WhatIsTheDate(0),getter.getKidsJsons(),"Fever",3,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של חום בימים האחרונים","3","3",3,"tempreture",38);
				countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Cough",5,"קיים חשש שהילד מפתח מחלה","אירועים חוזרים של שיעול בימים האחרונים","3","3",3);
				countermsg += DailyCheckMoreThenEvent(WhatIsTheDate(0),getter.getKidsJsons(),"Rash",4,"קיים חשש לסוג אלרגיה לא ידוע","אירועים חוזרים של פריחה בימים האחרונים","3","3",3);
				countermsg += XDaysEgoUrineDiaperCheck(getter.getKidsJsons());
				countermsg += XDaysEgoWaterCheck(getter.getKidsJsons());
				countermsg += DailyVomitusCheck(2,WhatIsTheDate(0), getter.getKidsJsons());
				}
			 } 
		return countermsg;
		}
	
	//=====================================================================Querys=====================================================================//
	int FeverCheck(String[] today,JSONArray kids,int amount,int days) throws Exception {
		int value=0,numOfEvents=1,counter = 0;
		try {
			JSONArray jsonEvent;
			 jsonEvent = getter.getJsonsWithDate("Fever",today[2]+"-"+today[1]+"-"+today[0]);
			if(days>1) {
			 for(int k=1;k<days;k++) {
				jsonEvent.put(getter.getJsonsWithDate("Fever",WhatIsTheDate(k)[2]+"-"+WhatIsTheDate(k)[1]+"-"+WhatIsTheDate(k)[0]));
				}
			}
			for(int j=0;j<kids.length();j++) {		
				JSONObject object = new JSONObject();
				for(int i=0;i<jsonEvent.length();i++) {
					if(jsonEvent.getJSONObject(i).getString("childID")==kids.getJSONObject(j).getString("childID")) {
						if(Double.parseDouble(jsonEvent.getJSONObject(i).getString("tempreture"))>=37.5 && Double.parseDouble(jsonEvent.getJSONObject(i).getString("tempreture"))<=38) {
							object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
							numOfEvents++;
							counter++;
						}
					}
				}
				if(counter>amount) { 
					try {
						sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5],"קיים חשש שהילד מפתח מחלה" ,object,"3","אירועים חוזרים של חום במידה מועטה חריג בימים האחרונים"));
						value++;
						for(int i=0;i<object.length();i++){
							JSONObject event=getter.getJsonsByID("fever",object.getString(String.valueOf(i+1)));
							if(event.length()>0) {
								JSONObject chengeColor = new JSONObject();
								chengeColor.put("level", 2);
								chengeColor.put("eventDate",event.getString("eventDate"));
								chengeColor.put("childID",event.getString("childID"));
								chengeColor.put("staffID",event.getString("staffID"));
								chengeColor.put("tempreture",event.getString("tempreture"));
								chengeColor.put("eventID", String.valueOf(event.getString("eventID")));
								sender.sendPutColor(event.getString("eventID"),chengeColor,"FeverEvent");
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						} // the function that makes the alert
					}
				
				numOfEvents=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	int DailyCheckMoreThenEventWithDoubleParm(String[] today,JSONArray kids,String Table,int amount,String ActionNeeded,String Rule,String level,String type,int days,String parm,int parmValue) throws Exception {
		int value=0,numOfEvents=1,counter = 0;
		try {
			JSONArray jsonEvent;

			 jsonEvent = getter.getJsonsWithDate(Table,today[2]+"-"+today[1]+"-"+today[0]);
			if(days>1) {
			 for(int k=1;k<days;k++) {
				 jsonEvent.put(getter.getJsonsWithDate(Table,WhatIsTheDate(k)[2]+"-"+WhatIsTheDate(k)[1]+"-"+WhatIsTheDate(k)[0]));
					}
			}
			for(int j=0;j<kids.length();j++) {		
				JSONObject object = new JSONObject();// pass all the kids 
				for(int i=0;i<jsonEvent.length();i++) {
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
						if(Double.parseDouble(jsonEvent.getJSONObject(i).getString(parm))>parmValue) {
							object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
							numOfEvents++;
							counter++;
						}
					}
				}	
				if(counter>amount) { 		// if he drink less than he actualy need near to the end of the day
				try {
					sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),level,WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000",ActionNeeded ,object,type,Rule));
					value++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					} // the function that makes the alert
				}
				numOfEvents=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	int DailyCheckMoreThenEventWithStringParm(String[] today,JSONArray kids,String Table,int amount,String ActionNeeded,String Rule,String level,String type,int days,String parm,String parmValue) throws Exception {
		int value=0,numOfEvents=1,counter = 0;
		try {
			JSONArray jsonEvent;
			jsonEvent = getter.getJsonsWithDate(Table,today[2]+"-"+today[1]+"-"+today[0]);
			if(days>1) {
			 for(int k=1;k<days;k++) {
				 jsonEvent.put(getter.getJsonsWithDate(Table,WhatIsTheDate(k)[2]+"-"+WhatIsTheDate(k)[1]+"-"+WhatIsTheDate(k)[0]));
					}
			}
			for(int j=0;j<kids.length();j++) {		
				JSONObject object = new JSONObject();// pass all the kids 
				for(int i=0;i<jsonEvent.length();i++) {
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
						if(jsonEvent.getJSONObject(i).getString(parm).equals(parmValue)) {
							object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
							numOfEvents++;
							counter++;
						}
					}
				}	
				if(counter>amount) { 		// if he drink less than he actualy need near to the end of the day
				try {
					sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),level,WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000",ActionNeeded ,object,type,Rule));
					value++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					} // the function that makes the alert
				}
				numOfEvents=1;
				counter=0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	int DailyCheckMoreThenEventWithIntParm(String[] today,JSONArray kids,String Table,int amount,String ActionNeeded,String Rule,String level,String type,int days,String parm,int parmValue) throws Exception {
		int value=0,numOfEvents=1,counter = 0;
		try {
			JSONArray jsonEvent;

			 jsonEvent = getter.getJsonsWithDate(Table,today[2]+"-"+today[1]+"-"+today[0]);
			if(days>1) {
			 for(int k=1;k<days;k++) {
				 jsonEvent.put(getter.getJsonsWithDate(Table,WhatIsTheDate(k)[2]+"-"+WhatIsTheDate(k)[1]+"-"+WhatIsTheDate(k)[0]));
					}
			}
			for(int j=0;j<kids.length();j++) {		
				JSONObject object = new JSONObject();// pass all the kids 
				for(int i=0;i<jsonEvent.length();i++) {
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
						if(jsonEvent.getJSONObject(i).getInt(parm)>parmValue) {
							object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
							numOfEvents++;
							counter++;
						}
					}
				}	
				if(counter>amount) { 		// if he drink less than he actualy need near to the end of the day
				try {
					sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),level,WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000",ActionNeeded ,object,type,Rule));
					value++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					} // the function that makes the alert
				}
				numOfEvents=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	int DailyCheckMoreThenEvent(String[] today,JSONArray kids,String Table,int amount,String ActionNeeded,String Rule,String level,String type,int days) throws Exception {
		int value=0,numOfEvents=1,counter = 0;
		try {
			JSONArray jsonEvent;

			 jsonEvent = getter.getJsonsWithDate(Table,today[2]+"-"+today[1]+"-"+today[0]);
			if(days>1) {
			 for(int k=1;k<days;k++) {
				 jsonEvent.put(getter.getJsonsWithDate("Fever",WhatIsTheDate(k)[2]+"-"+WhatIsTheDate(k)[1]+"-"+WhatIsTheDate(k)[0]));
					}
			}
			for(int j=0;j<kids.length();j++) {		
				JSONObject object = new JSONObject();
				for(int i=0;i<jsonEvent.length();i++) {
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
						object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
						numOfEvents++;
						counter++;
					}
				}	
				if(counter>amount) { 		
				try {
					sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),level,WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000",ActionNeeded ,object,type,Rule));
					value++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					} // the function that makes the alert
				}
				numOfEvents=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	int DailyVomitusCheck(int time ,String[] today,JSONArray kids) throws Exception {
		int value=0;
		try {
			int counterV=0,numOfEvents=1,numOfAlerts=0;
			JSONObject counetrAlerts = new JSONObject();
			JSONArray jsonEvent = getter.getJsonsWithDate("Vomitus",today[2]+"-"+today[1]+"-"+today[0]);
			if(time==2) {
				jsonEvent.put(getter.getJsonsWithDate("Vomitus",WhatIsTheDate(1)[0]+"-"+WhatIsTheDate(1)[1]+"-"+WhatIsTheDate(1)[2]));
				jsonEvent.put(getter.getJsonsWithDate("Vomitus",WhatIsTheDate(2)[0]+"-"+WhatIsTheDate(2)[1]+"-"+WhatIsTheDate(2)[2]));
			}
			for(int j=0;j<kids.length();j++) {	
				JSONObject object = new JSONObject();
				 for(int i=0;i<jsonEvent.length();i++) {
						if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
							object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
							numOfEvents++;
							if(jsonEvent.getJSONObject(i).getString("type").equals("פליטה מוגברת"))
							counterV++;
							if(jsonEvent.getJSONObject(i).getString("type").equals("הקאה"))
								counterV+=2;
						}
					}
				if(time==1) {
					if(counterV==2) {
						sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "לתת לילד מים",object,"3","מספר חריג חוזר של הקאות או פליטות"));
						numOfAlerts++;
						counetrAlerts.put(String.valueOf(numOfAlerts), kids.getJSONObject(j).getString("childID"));
						value++;
					}
					if(counterV>2) {
						sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"3",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "לתת לילד מים",object,"3","מספר חריג חוזר של הקאות או פליטות"));
						value++;
						numOfAlerts++;
						counetrAlerts.put(String.valueOf(numOfAlerts), kids.getJSONObject(j).getString("childID"));
					}
				}
				else if(time==2) {
					if(counterV>3) {
						sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"3",WhatIsTheDate(0)[0]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "לתת לילד מים",object,"3","מספר חריג חוזר של הקאות או פליטות"));
						value++;
						numOfAlerts++;
						counetrAlerts.put(String.valueOf(numOfAlerts), kids.getJSONObject(j).getString("childID"));
					}
				}
				counterV = 0;
				numOfEvents=1;
			
			if(numOfAlerts>3) {
				JSONObject kidsAlerts = new JSONObject();
				kidsAlerts.put("alerts", counetrAlerts);
				sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"3",WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "לבדוק את האוכל שהוגש היום לאותם ילדים",object,"3","מספר חריג של ילדים הקיאו היום"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	int DailyWaterCheck(int time,String[] today,JSONArray kids) throws Exception {
		int value=0,numOfEvents=1,consumedAmount = 0,amount=0;
		try {
			JSONArray jsonEvent = getter.getJsonsWithDate("Water",today[2]+"-"+today[1]+"-"+today[0]);
			for(int j=0;j<kids.length();j++) {	
				JSONObject object = new JSONObject();// pass all the kids 
				for(int i=0;i<jsonEvent.length();i++) {
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
						consumedAmount+= Integer.parseInt(jsonEvent.getJSONObject(i).getString("consumedAmount"));
						amount+= 100;//Integer.parseInt(jsonEvent.getJSONObject(i).getString("amount"));
						object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
						numOfEvents++;
					}
				}
				if(time == 1) {
					
					if(consumedAmount!=0 &&amount!=0) {
						if(amount/consumedAmount<0.75) { 		// if he drink less than he actualy need near to the end of the day
							try {
								sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "הילד לא שותה מספיק מים !",object,"3","הילד לא שתה את הכמות המומלצת היום"));
								value++;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // the function that makes the alert
						}
					}else{
						try {
							sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"3",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "לתת לילד מים",object,"1","הילד לא שתה היום"));
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
				else if(time == 2) {
					if(consumedAmount!=0&&amount!=0) {
						if(amount/consumedAmount<0.3) { 		// if he drink less than he actualy need in the half of the day
							try {
								sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "הילד לא שותה מספיק מים !",object,"3","הילד לא שתה את הכמות המומלצת היום"));
								value++;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
						}
					}else {
						try {
							sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"3",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "לתת לילד מים",object,"1","הילד לא שתה היום"));
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
						numOfEvents=1;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	int XDaysEgoWaterCheck(JSONArray kids) throws Exception { 
		int value=0;
		try {
			int counterWater = 0,numOfEvents=1;
			 JSONArray jsonEvent = getter.getJsonsWithDate("Water",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]);
			 jsonEvent.put(getter.getJsonsWithDate("Water",WhatIsTheDate(1)[2]+"-"+WhatIsTheDate(1)[1]+"-"+WhatIsTheDate(1)[0]));
			 jsonEvent.put(getter.getJsonsWithDate("Water",WhatIsTheDate(2)[2]+"-"+WhatIsTheDate(2)[1]+"-"+WhatIsTheDate(2)[0]));
			for(int j=0;j<kids.length();j++) {	
				 JSONObject object = new JSONObject();
				 for(int i=0;i<jsonEvent.length();i++) {
					 if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
							object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
							numOfEvents++;
							if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("finish"))									//checking the amount he drink and sum the amount he drink all day
								counterWater += jsonEvent.getJSONObject(i).getInt("amount");
							else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("more than half"))
								counterWater += 0.6*jsonEvent.getJSONObject(i).getInt("amount");
							else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("less than half"))
								counterWater += 0.4*jsonEvent.getJSONObject(i).getInt("amount");
							else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("nothing"))
								counterWater += 0*jsonEvent.getJSONObject(i).getInt("amount");
					 }
				 }
					if(counterWater<1400) { 		// if he drink less than he actualy need near to the end of the day
						try {
							sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "הילד לא שותה מספיק מים !",object,"3","הילד לא שתה את הכמות המומלצת היום בימים האחרונים")); // the function that makes the alert
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
	int DailyFoodCheck(String[] today,int time,JSONArray kids) throws Exception {
		int value=0,numOfEvents=1;
		try {
			float counterFood = 0;
			 JSONArray jsonEvent = getter.getJsonsWithDate("SolidFood",today[2]+"-"+today[1]+"-"+today[0]);
			 JSONArray jsonEvent2 = getter.getJsonsWithDate("LiquidFood",today[2]+"-"+today[1]+"-"+today[0]);
			for(int j=0;j<kids.length();j++) {																		// pass each kid
				 JSONObject object = new JSONObject();
				for(int i=0;i<jsonEvent.length();i++) {																			//pass all the events
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
						object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
						numOfEvents++;
						if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("סיים מנה"))	{								//checking the amount he drink and sum the amount he drink all day
							counterFood += 1;}
						else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מעל חצי מנה")) {
							counterFood += 0.6;}
						else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מתחת לחצי מנה")) {
							counterFood += 0.4;}
				
					}
				}
				// need to know what is the real amout of food that need to count
				for(int i=0;i<jsonEvent2.length();i++) {																			//pass all the events
					if(jsonEvent2.getJSONObject(i).getString("childID")==kids.getJSONObject(j).getString("childID")) {
						object.put(String.valueOf(numOfEvents), jsonEvent2.getJSONObject(i).getString("eventID"));
						numOfEvents++;
						if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("סיים מנה"))	{								//checking the amount he drink and sum the amount he drink all day
							counterFood += 1;}
						else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מעל חצי מנה")) {
							counterFood += 0.6;}
						else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מתחת לחצי מנה")) {
							counterFood += 0.4;}			
					}
				}
				if(time == 1) {
					if(counterFood< 0.5) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "הילד לא אכל היום מספיק!",object,"3","הילד לא אכל את הכמות המומלצת היום"));
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					counterFood=0;
				}
				if(time == 2) {
					if(counterFood< 1.0) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "הילד לא אכל היום מספיק!",object,"3","הילד לא אכל את הכמות המומלצת היום"));
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
					counterFood=0;
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
	int DailyDiaperCheck(String[] today,int time,JSONArray kids) throws Exception {
		int value=0,numOfEvents=1;
		try {
			float counterDiapers = 0;
			JSONArray jsonEvent = getter.getJsonsWithDate("Urine",today[2]+"-"+today[1]+"-"+today[0]);
			 JSONArray jsonEvent2 = getter.getJsonsWithDate("Feces",today[2]+"-"+today[1]+"-"+today[0]);
			for(int j=0;j<kids.length();j++) {																				// pass each kid
				JSONObject object = new JSONObject();
				for(int i=0;i<jsonEvent.length();i++){
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {//pass all the events
						object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
							numOfEvents++;
							if(jsonEvent.getJSONObject(i).getString("amount").equals("חיתול מלא"))	{								//checking the amount he drink and sum the amount he drink all day
								counterDiapers += 1.2;}
							else if(jsonEvent.getJSONObject(i).getString("amount").equals("רגילה")) {
								counterDiapers += 0.7;}
							else if(jsonEvent.getJSONObject(i).getString("amount").equals("קטנה")) {
								counterDiapers += 0.5;}				
					}
				}
				// need to know what is the real amout of food that need to count
				for(int i=0;i<jsonEvent2.length();i++) {
					if(jsonEvent2.getJSONObject(i).getString("childID")==kids.getJSONObject(j).getString("childID")) {
						object.put(String.valueOf(numOfEvents), jsonEvent2.getJSONObject(i).getString("eventID"));
						numOfEvents++;
						if(jsonEvent2.getJSONObject(i).getString("amount").equals("חיתול מלא")) {									//checking the amount he drink and sum the amount he drink all day
							counterDiapers += 1.0;}
						else if(jsonEvent2.getJSONObject(i).getString("amount").equals("רגילה")) {
							counterDiapers += 0.6;}
						else if(jsonEvent2.getJSONObject(i).getString("amount").equals("קטנה")) {
							counterDiapers += 0.4;}
						}
					}
				if(time == 1) {
					if(counterDiapers< 1.0) { 		// if he ate less than he actually need near to the end of the day
						try {
							sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "כמות הצואה/שתן קטנה",object,"3","אירוע חוזר של אי עשיית צואה"));
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else if(time == 2) {
					if(counterDiapers< 1.5) { 	
						try {
							sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"2",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "כמות הצואה/שתן קטנה",object,"3","אירוע חוזר של אי עשיית צואה"));
							value++;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				counterDiapers=0;
				numOfEvents=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	//function that get time of start and the end and check if now is between  them

	//function that check the amount of urine in cople days ego
	int XDaysEgoUrineDiaperCheck(JSONArray kids) throws Exception{
		int value=0,numOfEvents=1;
		try {
			float counterUrine = 0;
			JSONArray jsonEvent = getter.getJsonsWithDate("Urine",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]);
			jsonEvent.put(getter.getJsonsWithDate("Urine",WhatIsTheDate(1)[2]+"-"+WhatIsTheDate(1)[1]+"-"+WhatIsTheDate(1)[0]));
			 jsonEvent.put(getter.getJsonsWithDate("Urine",WhatIsTheDate(2)[2]+"-"+WhatIsTheDate(2)[1]+"-"+WhatIsTheDate(2)[0]));
			for(int j=0;j<kids.length();j++) {																				// pass each kid
				JSONObject object = new JSONObject();
				for(int i=0;i< jsonEvent.length();i++){																			//pass all the events of the kid
					if(jsonEvent.getJSONObject(i).getString("childID").equals(kids.getJSONObject(j).getString("childID"))) {
						object.put(String.valueOf(numOfEvents), jsonEvent.getJSONObject(i).getString("eventID"));
						numOfEvents++;
						if(jsonEvent.getJSONObject(i).getString("amount").equals("חיתול מלא"))	{							
							counterUrine += 1;}
						else if(jsonEvent.getJSONObject(i).getString("amount").equals("כמות רגילה")) {
							counterUrine += 0.6;}
						else if(jsonEvent.getJSONObject(i).getString("amount").equals("כמות קטנה")) {
							counterUrine += 0.4;}
						else if(jsonEvent.getJSONObject(i).getString("amount").equals("ללא")) {
							counterUrine += 0;}					
					}
				}
				if(counterUrine< 1.0) { 	
					try {
						sender.sendLogicAlert(new LogicSystemAlert(kids.getJSONObject(j).getInt("childID"),"3",WhatIsTheDate(0)[2]+"-"+WhatIsTheDate(0)[1]+"-"+WhatIsTheDate(0)[0]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000", "לתת לילד מים",object,"3","כמות שתן קטנה בימים האחרונים"));
						value++;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
//===================================================================COLOR FUNCTIONS==============================================================//
	
	//=====================================================================ColorsQuerys===============================================================//
	//the function that call all the colors querys
	
	float checkColorsAlerts(String[] date) throws Exception {
		LiquidFoods(date);
		Parasites(date);
		Cough(date);
		Feces(date);
		Secretion(date);
		SolidFood(date);
		Vomitus(date);          //didnt do check on general behavior, general note, and medication,
		Urine(date);				
		Sleep(date);
		Fever(date);
		Water(date);
		Disease(date);
		Rash(date);
		if(countercolorsEvents !=0.0)return statColors/countercolorsEvents;
		else return 0;
	}
	//==========================================LiquidFoods=============================================//
	void LiquidFoods(String[] date) throws Exception {
		JSONArray jsonEvent = getter.getJsonsWithDate("LiquidFood",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			int color = 1;

			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				countercolorsEvents++;
				if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("לא אכל")) {
					color = 3;
					}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מתחת לחצי מנה")) {
					color= 3;
				}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מעלה לחצי מנה")) {
					color= 2;
				}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("סיים מנה")) {
					color= 1;
				}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("אכל מעבר למנה")) {
					color= 2;
				}
				//sendColorAlert(LiquidFoods.getString("eventID"),"LiquidFood",color);
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("amount",jsonEvent.getJSONObject(i).getString("amount"));
			    object.put("consumedAmount",jsonEvent.getJSONObject(i).getString("consumedAmount"));
			    object.put("mealType",jsonEvent.getJSONObject(i).getString("mealType"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"LiquidFoodEvent");
				statColors+=color;
			}
		}
	}
	
	//=======================================Parasites============================================//
	//==========================================Parasites=============================================//
	void Parasites(String[] date) throws Exception {
		int color=1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Parasites",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				color = 1;
				countercolorsEvents++;
				if(jsonEvent.getJSONObject(i).getString("type").equals("כינים")) {
					color = 3;
					JSONObject eventsLeading=new JSONObject();
					eventsLeading.put(String.valueOf(1), jsonEvent.getJSONObject(i).getString("eventId"));
					sender.sendLogicAlert(new LogicSystemAlert(jsonEvent.getJSONObject(i).getInt("childID"),String.valueOf(color),WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5] +" +0000","קיימת חשש לבעיית כיניים לתת דיווח להורים",eventsLeading,"3","דיווח מזיקים"));
				}
				else if(jsonEvent.getJSONObject(i).getString("type").equals("תולעים")) {
					color= 3;
					JSONObject eventsLeading=new JSONObject();
					eventsLeading.put(String.valueOf(1), jsonEvent.getJSONObject(i).getString("eventId"));
					sender.sendLogicAlert(new LogicSystemAlert(jsonEvent.getJSONObject(i).getInt("childID"),String.valueOf(color),WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000","קיימת חשש לבעיית תולעים לתת דיווח להורים",eventsLeading,"3","דיווח מזיקים"));
				}
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("type",jsonEvent.getJSONObject(i).getString("type"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"ParasitesEvent");
				statColors+=color;
			}
		}	
	}
	
	//=========================================Cough==============================================//
	//==========================================Cough=================================================//
	void Cough(String[] date) throws Exception {
		int color =1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Cough",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				color = 1;
				countercolorsEvents++;
				if(jsonEvent.getJSONObject(i).getString("type").equals("טורדני")) {
					color = 3;
				}
				else if(jsonEvent.getJSONObject(i).getString("type").equals("לח")) {
					color= 2;
				}
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("type",jsonEvent.getJSONObject(i).getString("type"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"CoughEvent");
				statColors+=color;
			}
		}
	}
	
	//=========================================Feces==============================================//
	//==========================================Feces=================================================//
	void Feces(String[] date) throws Exception {
		int color =1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Feces",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++)  {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				countercolorsEvents++;
				color=1;
				if(jsonEvent.getJSONObject(i).getString("texture").equals("ריירי")) {
					color = 3;
				}
				else if(jsonEvent.getJSONObject(i).getString("texture").equals("שילשול/מיימי ")) {
					color = 2;
				}
				if(color!=3) {
					if(jsonEvent.getJSONObject(i).getString("color").equals("אדום")) {
						color= 3;//אלא אם אכל מזונות אדומים למשל סלק 
						JSONObject object=new JSONObject();
						object.put(String.valueOf(1), jsonEvent.getJSONObject(i).getString("eventID"));
						sender.sendLogicAlert(new LogicSystemAlert(jsonEvent.getJSONObject(i).getInt("childID"),String.valueOf(color),WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000","קיימת חשש לבעיה רפואית שדורש התייחסות בעקבות צבע הצואה",object,"3","דימום מע' עיכול (אלא אם אכל מזונות בצבע אדום)"));
					
					}
					else if(jsonEvent.getJSONObject(i).getString("color").equals("לבן אפור")) {
						color= 3;	
						JSONObject object=new JSONObject();
						object.put(String.valueOf(1), jsonEvent.getJSONObject(i).getString("eventID"));
						sender.sendLogicAlert(new LogicSystemAlert(jsonEvent.getJSONObject(i).getInt("childID"),String.valueOf(color),WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000","קיימת חשש לבעיה רפואית שדורש התייחסות בעקבות צבע הצואה",object,"3","בעיה בייצור מלחי מרה/תפקוד כיס המרה"));
					}
				}
				if(color!=3) {
					if(jsonEvent.getJSONObject(i).getString("amount").equals("קטנה")) {
						color= 3; //אלא אם הוא אכל מזונות אדומים למשל סלק
					}
					else if(jsonEvent.getJSONObject(i).getString("amount").equals("רגילה")) {
						color= 2; //אלא אם הוא אכל מזונות אדומים למשל סלק
						}				
				}
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("amount",jsonEvent.getJSONObject(i).getString("amount"));
			    object.put("color",jsonEvent.getJSONObject(i).getString("color"));
			    object.put("texture",jsonEvent.getJSONObject(i).getString("texture"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"FecesEvent");
				statColors+=color;
			}
		}	
	}
	
	//========================================Secretion===========================================//
	//==========================================Secretion=============================================//
	void Secretion(String[] date) throws Exception {
		int color = 1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Secretion",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				countercolorsEvents++;
				color=1;
				if(jsonEvent.getJSONObject(i).getString("type").equals("דם")) {
					color = 3;
				}
				else if(jsonEvent.getJSONObject(i).getString("type").equals("מוגלה")) {
					color = 3;
				}
				else if(jsonEvent.getJSONObject(i).getString("type").equals("נזלת")) {
					color = 3;
				}
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("area",jsonEvent.getJSONObject(i).getString("area"));
			    object.put("type",jsonEvent.getJSONObject(i).getString("type"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"SecretionEvent");
				statColors+=color;
			}
		}
	}
	
	//========================================SolidFood===========================================//
	//==========================================SolidFood=============================================//
	void SolidFood(String[] date) throws Exception {
		int color =1;
		JSONArray jsonEvent = getter.getJsonsWithDate("SolidFood",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				color = 1;
				countercolorsEvents++;
				if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("לא אכל")) {
					color = 3;
				}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מתחת לחצי מנה")) {
					color= 3;
				}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("מעלה לחצי מנה")) {
					color= 2;
				}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("סיים מנה")) {
					color= 1;
				}
				else if(jsonEvent.getJSONObject(i).getString("consumedAmount").equals("אכל מעבר למנה")) {
					color= 2;
				}
				//sendColorAlert(SolidFood.getString("eventId"),"SolidFood",color);
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("amount",jsonEvent.getJSONObject(i).getString("amount"));
			    object.put("consumedAmount",jsonEvent.getJSONObject(i).getString("consumedAmount"));
			    object.put("mealInMenu",jsonEvent.getJSONObject(i).getString("mealInMenu"));
			    object.put("mealType",jsonEvent.getJSONObject(i).getString("mealType"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"SolidFoodEvent");
				statColors+=color;
			}
		}
	}
	
	//=========================================Vomitus============================================//
	//==========================================Vomitus===============================================//
	void Vomitus(String[] date) throws Exception {
		int color = 1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Vomitus",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
		 	if(jsonEvent.getJSONObject(i).getInt("level")==0) {
			color = 1;
			countercolorsEvents++;
			if(jsonEvent.getJSONObject(i).getString("type").equals("הקאה")) {
				color = 3;
				}
			else if(jsonEvent.getJSONObject(i).getString("type").equals("פליטה מוגברת")) {
				color= 2;
			}
			JSONObject object = new JSONObject();
		    object.put("level",color);
		    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
		    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
		    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
		    object.put("type",jsonEvent.getJSONObject(i).getString("type"));
		    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
		    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"VomitusEvent");
			statColors+=color;
			}
		}
	}
	
	//==========================================Urine=============================================//
	//==========================================Urine=================================================//
	void Urine(String[] date) throws Exception {
		int color = 1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Urine",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				countercolorsEvents++;
				color=1;
				if(jsonEvent.getJSONObject(i).getString("color").equals("צהוב כהה עד חום בהיר")) {
					color= 2; 
				}
				else if(jsonEvent.getJSONObject(i).getString("color").equals("אדום")) {
					color= 3; 
				}
				if(color!=3) {
					if(jsonEvent.getJSONObject(i).getString("fragrance").equals("חריף")) {
						color= 3;
					}
				}
				if(color!=3) {
					if(jsonEvent.getJSONObject(i).getString("amount").equals("ללא")) {
						color= 3; 
					}
					if(jsonEvent.getJSONObject(i).getString("amount").equals("כמות קטנה")) {
						color= 2;
					}	
				}
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("amount",jsonEvent.getJSONObject(i).getString("amount"));
			    object.put("color",jsonEvent.getJSONObject(i).getString("color"));
			    object.put("fragrance",jsonEvent.getJSONObject(i).getString("fragrance"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"UrineEvent");
				statColors+=color;
			}
		}
	}

	//==========================================Sleep=============================================//
	//==========================================Sleep=================================================//
	void Sleep(String[] date) throws Exception {
		int color = 1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Sleep",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				countercolorsEvents++;
				color=1;
				if(jsonEvent.getJSONObject(i).getString("sleepingScope").equals("אי שינה")) {
					color= 3; 
				}
				else if(jsonEvent.getJSONObject(i).getString("sleepingScope").equals("שינה חלקית")) {
					color= 2; 
				}
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("allocatedTime",jsonEvent.getJSONObject(i).getString("allocatedTime"));
			    object.put("sleepingScope",jsonEvent.getJSONObject(i).getString("sleepingScope"));
			    object.put("type",jsonEvent.getJSONObject(i).getString("type"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
			    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"SleepingEvent");
				statColors+=color;
			}
		}
	}
	
	//==========================================Fever=============================================//
	//==========================================Fever=================================================//
	void Fever(String[] date) throws Exception{
		int color = 1;
		JSONArray jsonEvent = getter.getJsonsWithDate("Fever",date[2]+"-"+date[1]+"-"+date[0]);
		for(int i=0;i<jsonEvent.length();i++) {
			if(jsonEvent.getJSONObject(i).getInt("level")==0) {
				countercolorsEvents++;
				color=1;
				if(Double.parseDouble(jsonEvent.getJSONObject(i).getString("tempreture"))<36.5) {
					color= 3; 
					JSONObject eventsLeading=new JSONObject();
					eventsLeading.put(String.valueOf(1), jsonEvent.getJSONObject(i).getString("eventID"));
					System.out.println();
					sender.sendLogicAlert(new LogicSystemAlert(jsonEvent.getJSONObject(i).getInt("childID"),String.valueOf(color),WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5] +" +0000","להלביש את הילד/לכסות אותו",eventsLeading,"3","טמפרטורה מתחת לטווח תקין"));
				}
				else if(Double.parseDouble(jsonEvent.getJSONObject(i).getString("tempreture"))<=38 && Double.parseDouble(jsonEvent.getJSONObject(i).getString("tempreture"))>=37.5) { 
					JSONObject eventsLeading=new JSONObject();
					eventsLeading.put(String.valueOf(1), jsonEvent.getJSONObject(i).getString("eventID"));
					sender.sendLogicAlert(new LogicSystemAlert(jsonEvent.getJSONObject(i).getInt("childID"),String.valueOf(2),WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000","יש למדוד את חום הילד שוב בעוד כשעתיים",eventsLeading,"3","טמפרטורה קצת מעל לטווח תקין"));
				}
				else if(Double.parseDouble(jsonEvent.getJSONObject(i).getString("tempreture"))>38.0) {
					color= 3; 
					JSONObject eventsLeading=new JSONObject();
					eventsLeading.put(String.valueOf(1), jsonEvent.getJSONObject(i).getString("eventID"));
					sender.sendLogicAlert(new LogicSystemAlert(jsonEvent.getJSONObject(i).getInt("childID"),String.valueOf(color),WhatIsTheDate(0)[0]+"/"+WhatIsTheDate(0)[1]+"/"+WhatIsTheDate(0)[2]+" "+WhatIsTheDate(0)[3]+":"+WhatIsTheDate(0)[4]+ ":" + WhatIsTheDate(0)[5]+" +0000","עדכון הורים להגיע לאסוף",eventsLeading,"3","טמפרטורה מעל לטווח תקין"));
				}
				JSONObject object = new JSONObject();
			    object.put("level",color);
			    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
			    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
			    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
			    object.put("tempreture",jsonEvent.getJSONObject(i).getString("tempreture"));
			    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
				sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"FeverEvent");
				statColors+=color;
			}
		}
	}
	//==========================================Water=================================================//
	void Water(String[] date) throws Exception{
			int color = 1;
			JSONArray jsonEvent = getter.getJsonsWithDate("Water",date[2]+"-"+date[1]+"-"+date[0]);
			//System.out.println(jsonEvent.length()+" "+date[0]+"-"+date[1]+"-"+date[2]);
			for(int i=0;i<jsonEvent.length();i++) {
				//System.out.println(Integer.parseInt(jsonEvent.getJSONObject(i).getString("level")));
				if(jsonEvent.getJSONObject(i).getInt("level")==0) {
					countercolorsEvents++;
					color=1;
					
					if(Integer.parseInt(jsonEvent.getJSONObject(i).getString("consumedAmount"))<Integer.parseInt(jsonEvent.getJSONObject(i).getString("amount"))*0.1) {
						color= 3; 
					}
					else if(Integer.parseInt(jsonEvent.getJSONObject(i).getString("consumedAmount"))<Integer.parseInt(jsonEvent.getJSONObject(i).getString("amount"))*0.6) {
						color= 2; 
					}
					JSONObject object = new JSONObject();
				    object.put("level",color);
				    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
				    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
				    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
				    object.put("amount",jsonEvent.getJSONObject(i).getString("amount"));
				    object.put("consumedAmount",jsonEvent.getJSONObject(i).getString("consumedAmount"));
				    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
				    sender.	sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"WaterEvent");
					statColors+=color;
				}
			}
		}
	//==========================================Disease===============================================//
	void Disease(String[] date) throws Exception{
			JSONArray jsonEvent = getter.getJsonsWithDate("Disease",date[2]+"-"+date[1]+"-"+date[0]);
			for(int i=0;i<jsonEvent.length();i++) {
				if(jsonEvent.getJSONObject(i).getInt("level")==0) {
					countercolorsEvents++;
					//sendColorAlert(Disease.getString("eventId"),"Disease",3);
					JSONObject object = new JSONObject();
				    object.put("level",String.valueOf(3));
				    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
				    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
				    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
				    object.put("details",jsonEvent.getJSONObject(i).getString("details"));
				    object.put("type",jsonEvent.getJSONObject(i).getString("type"));
				    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
				    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"DiseaseEvent");
					statColors+=3;
				}
			}
		}
	//==========================================Rash==================================================//
	void Rash(String[] date) throws Exception{
			JSONArray jsonEvent = getter.getJsonsWithDate("Rash",date[2]+"-"+date[1]+"-"+date[0]);
			for(int i=0;i<jsonEvent.length();i++)  {
				if(jsonEvent.getJSONObject(i).getInt("level")==0) {
					countercolorsEvents++;
					JSONObject object = new JSONObject();
				    object.put("level",String.valueOf(3));
				    object.put("eventDate",jsonEvent.getJSONObject(i).getString("eventDate"));
				    object.put("childID",jsonEvent.getJSONObject(i).getString("childID"));
				    object.put("staffID",jsonEvent.getJSONObject(i).getString("staffID"));
				    object.put("area",jsonEvent.getJSONObject(i).getString("area"));
				    object.put("type",jsonEvent.getJSONObject(i).getString("type"));
				    object.put("eventID", String.valueOf(jsonEvent.getJSONObject(i).getString("eventID")));
				    sender.sendPutColor(jsonEvent.getJSONObject(i).getString("eventID"),object,"RashEvent");
				   statColors+=3;
				}
			}
		}
		
	
	
	//=====================================================================using function====================================================================//

	//=====================================================================TIMEFunctions====================================================================//
	//functions the check if the time now is between 2 times
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
	//function that give the 3 first latter of the name of today 
	String WhatIsTheDay() throws ParseException { 
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
		Date myDate = sdf.parse(timeStamp);
		sdf.applyPattern("EEE");
		String sMyDate = sdf.format(myDate);
		return sMyDate;
	}
	//function the give me the date minus the number you give (0 is today)
	String[] WhatIsTheDate(int days) {
		String [] time,sendDate,date,temp;
		sendDate= new String[6];
		
		final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -days);
	    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
	    temp=dateFormat.format(cal.getTime()).split(",");
	    date=temp[0].split("/");
	    time=temp[1].split(":");
	    sendDate[0]=date[0];
	    sendDate[1]=date[1];
	    sendDate[2]=date[2];
	    sendDate[3]=time[0];
	    sendDate[4]=time[1];
	    sendDate[5]=time[2];
	  	return sendDate;
	}
	
	
	//=======================================================================DBFunctions========================================================================//
	ResultSet getKids(Connection myConn) throws SQLException {
		Statement mystmt = myConn.createStatement();
		String giveMeAllKids= "SELECT * FROM child";
		ResultSet kids= mystmt.executeQuery(giveMeAllKids);//sent the query to get all the kids
		return kids;
	}
	
	ResultSet getSet(Connection myConn,String kid,String tableName,String[] date) throws SQLException {
		Statement mystmt = myConn.createStatement();
		//String giveMeAllEvents2= "SELECT (regexp_split_to_array(p.eventDate,E'[,]'))[1] FROM "+tableName+" AS p WHERE childID = "+kid + " AND (regexp_split_to_array(p.eventDate,E'[,]'))[1]="+ "\""+date[0]+"/"+date[1]+"/"+date[2]+"\"";
		String giveMeAllEvents= "SELECT * FROM "+tableName+" WHERE childID = "+kid + " AND STRCMP(SUBSTRING(eventDate, 1,10),\""+date[0]+"/"+date[1]+"/"+date[2]+"\")=0";
		ResultSet events= mystmt.executeQuery(giveMeAllEvents);//sent the query to get all the kids
		return events;
	}
	
	ResultSet getAllSet(Connection myConn,String kid,String tableName) throws SQLException {
		Statement mystmt = myConn.createStatement();
		String giveMeAllEvents= "SELECT * FROM "+tableName+" WHERE child = "+kid;
		ResultSet events= mystmt.executeQuery(giveMeAllEvents);//sent the query to get all the kids
		return events;
	}
	
	
}