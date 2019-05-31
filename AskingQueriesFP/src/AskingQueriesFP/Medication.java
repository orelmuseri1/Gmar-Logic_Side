package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Medication extends BasicAlert {
	String details;
	String area;

	public Medication(int childID, String level, String alertTime, String alertDate,JSONObject EventsLeading,
			String actionNeeded,String details,String area) {
		super(childID, level, alertTime, alertDate, actionNeeded);
		this.area=area;
		this.details=details;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	JSONObject getJson() throws JSONException {
		JSONObject object = new JSONObject();
	    object.put("alertDate", this.getAlertDate());
	    object.put("alertTime", this.getAlertTime());
	    object.put("childID",String.valueOf(this.getChildID()));
	    object.put("level", this.getLevel());
	    object.put("actionNeeded", this.getActionNeeded());
	    object.put("alertID", this.getAlertID());
	    object.put("details", this.getDetails());
	    object.put("area", this.getArea());
	    return object;
		
	}

}
