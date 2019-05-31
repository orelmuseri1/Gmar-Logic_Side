package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Vomitus extends BasicAlert {
	String type;
	String proper;
	
	public Vomitus(int childID, String level, String alertTime, String alertDate, String actionNeeded,String type,String proper) {
		super(childID, level, alertTime, alertDate,actionNeeded);
		this.proper=proper;
		this.type=type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProper() {
		return proper;
	}
	public void setProper(String proper) {
		this.proper = proper;
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
	    object.put("proper", this.getProper());
	    return object;
	}
}
