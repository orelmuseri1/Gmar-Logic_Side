package AskingQueriesFP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class BasicAlert {
	int childID;
	String level;
	String AlertDate;
	String alertID;
	String  actionNeeded;
	String type;
	JSONObject EventsLeading;
	//constrctor
	public BasicAlert(int childID, String level, String alertDate,
			String actionNeeded,String type) {
		super();
		this.childID = childID;
		this.level = level;
		AlertDate = alertDate;
		String uniqueID = String.valueOf(childID+level.hashCode()+actionNeeded.hashCode()+AlertDate.hashCode());
		this.alertID = uniqueID;
		this.type=type;
		this.actionNeeded = actionNeeded;
		
	};
	
	//Getters & Setters
		public int getChildID() {
			return childID;
		}

		public void setChildID(int childID) {
			this.childID = childID;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getAlertDate() {
			return AlertDate;
		}

		public void setAlertDate(String alertDate) {
			AlertDate = alertDate;
		}

		public String getAlertID() {
			return alertID; 
		}

		public void setAlertID(String alertID) {
			this.alertID = alertID;
		}

		public String getActionNeeded() {
			return actionNeeded;
		}

		public void setActionNeeded(String actionNeeded) {
			this.actionNeeded = actionNeeded;
		};

		
		JSONObject getJson() throws JSONException {
			JSONObject object = new JSONObject();
		    object.put("alertDate", this.getAlertDate());
		    object.put("childID",String.valueOf(this.getChildID()));
		    object.put("level", this.getLevel());
		    object.put("actionNeeded", this.getActionNeeded());
		    object.put("alertID", this.getAlertID());
		    return object;
			
		}


}