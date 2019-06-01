package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class LogicSystemAlert extends BasicAlert {
	JSONObject eventsLeading;
	String type;
	String rule;
	public LogicSystemAlert(int childID, String level, String alertDate, String actionNeeded,JSONObject eventsLeading,String type,String rule) {
		super(childID, level,alertDate, actionNeeded,type);
		this.rule=rule;
		this.type=type;
		this.eventsLeading=eventsLeading;
	}
	

	public JSONObject getEventsLeading() {
		return eventsLeading;
	}

	public void setEventsLeading(JSONObject eventsLeading) {
		this.eventsLeading = eventsLeading;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
