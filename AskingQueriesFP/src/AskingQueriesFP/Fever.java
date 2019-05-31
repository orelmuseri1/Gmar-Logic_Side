package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Fever extends BasicAlert {
	String tempreture;

	public Fever(int childID, String level, String alertTime, String alertDate, String actionNeeded,String tempreture) {
		super(childID, level, alertTime, alertDate,actionNeeded);
		this.tempreture= tempreture;
	}

	public String getTempreture() {
		return tempreture;
	}

	public void setTempreture(String tempreture) {
		this.tempreture = tempreture;
	}

	JSONObject getJson() throws JSONException {
		JSONObject object = new JSONObject();
	    object.put("alertDate", this.getAlertDate());
	    object.put("alertTime", this.getAlertTime());
	    object.put("childID",String.valueOf(this.getChildID()));
	    object.put("level", this.getLevel());
	    object.put("actionNeeded", this.getActionNeeded());
	    object.put("alertID", this.getAlertID());
	    object.put("Tempreture", this.getTempreture());
	    return object;
	}
}
