package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Parasites extends BasicAlert {
	String type;

	public Parasites(int childID, String level, String alertTime, String alertDate,String actionNeeded,String type) {
		super(childID, level, alertTime, alertDate,actionNeeded);
		this.type =type;
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
	    return object;
		
	}
}
