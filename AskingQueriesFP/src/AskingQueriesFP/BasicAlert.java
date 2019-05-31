package AskingQueriesFP;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class BasicAlert {
	int childID;
	String level;
	String AlertTime;
	String AlertDate;
	String alertID;
	String  actionNeeded;
	JSONObject EventsLeading;
	//constrctor
	public BasicAlert(int childID, String level, String alertTime, String alertDate,
			String actionNeeded) {
		super();
		String uniqueID = UUID.randomUUID().toString();
		this.childID = childID;
		this.level = level;
		AlertTime = alertTime;
		AlertDate = alertDate;
		this.alertID = uniqueID;
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

		public String getAlertTime() {
			return AlertTime;
		}

		public void setAlertTime(String alertTime) {
			AlertTime = alertTime;
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
		    object.put("alertTime", this.getAlertTime());
		    object.put("childID",String.valueOf(this.getChildID()));
		    object.put("level", this.getLevel());
		    object.put("actionNeeded", this.getActionNeeded());
		    object.put("alertID", this.getAlertID());
		    return object;
			
		}


}