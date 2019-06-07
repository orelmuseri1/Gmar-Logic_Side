package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class LogicSystemAlert extends BasicAlert {
	String rule;
	public LogicSystemAlert(int childID, String level, String alertDate, String actionNeeded,JSONObject eventsLeading,String type,String rule) {
		super(childID, level,alertDate, actionNeeded,type,eventsLeading);
		this.rule=rule;

	}
	


	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	
	JSONObject getJson() throws JSONException {
		JSONObject object = new JSONObject();
		 object.put("alertID", this.getAlertID());
	    object.put("alertDate", this.getAlertDate());
	    object.put("childID",String.valueOf(this.getChildID()));
	    object.put("level", this.getLevel());
	    object.put("type", this.getType());
	    object.put("timePast","");
	    object.put("responsibleEvents", this.getEventsLeading());
	    object.put("actionNeeded",this.getRule());
	    return object;
		
	}

}
