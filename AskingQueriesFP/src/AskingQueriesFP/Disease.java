package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Disease extends BasicAlert {
	String details;
	String type;
	
	public Disease(int childID, String level, String alertTime, String alertDate, String actionNeeded,String details,String type) {
		super(childID, level, alertTime, alertDate,actionNeeded);
		this.details =details;
		this.type=type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	JSONObject getJson() throws JSONException {
		JSONObject object = new JSONObject();
	    object.put("alertDate", this.getAlertDate());
	    object.put("alertTime", this.getAlertTime());
	    object.put("childID",String.valueOf(this.getChildID()));
	    object.put("level", this.getLevel());
	    object.put("actionNeeded", this.getActionNeeded());
	    object.put("alertID", this.getAlertID());
	    object.put("type", this.getType());
	    object.put("details", this.getDetails());
	    return object;
		
	}
}
