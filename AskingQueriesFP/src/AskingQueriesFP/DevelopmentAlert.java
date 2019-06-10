package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class DevelopmentAlert extends BasicAlert {
	String timePast;
	public DevelopmentAlert(int childID, String level, String alertDate, String actionNeeded, String type,
			JSONObject EventsLeading,String timePast) {
		super(childID, level, alertDate, actionNeeded, type, EventsLeading);
		this.timePast=timePast;
	}


	public String getTimePast() {
		return timePast;
	}



	public void setTimePast(String timePast) {
		this.timePast = timePast;
	}
	
	JSONObject getJson() throws JSONException {
		JSONObject object = new JSONObject();
		 object.put("alertID", this.getAlertID());
	    object.put("alertDate", this.getAlertDate());
	    object.put("childID",String.valueOf(this.getChildID()));
	    object.put("level", this.getLevel());
	    object.put("type", this.getType());
	    object.put("timePast",this.getTimePast());
	    object.put("responsibleEvents", this.getEventsLeading());
	    object.put("actionNeeded",this.getActionNeeded());
	    return object;
		
	}



}
