package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Rash extends BasicAlert {

	String area;
	String type;

	public Rash(int childID, String level, String alertTime, String alertDate,String actionNeeded,String area,String type) {
		super(childID, level, alertTime, alertDate,actionNeeded);
		this.area=area;
		this.type=type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
	    object.put("area", this.getArea());
	    return object;
		
	}
}
