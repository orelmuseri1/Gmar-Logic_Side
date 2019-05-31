package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Sleep extends BasicAlert {
	String allocatedTime;
	String type;
	String sleepingScope;

	public Sleep(int childID, String level, String alertTime, String alertDate,String actionNeeded,String allocatedTime,String type,String sleepingScope) {
		super(childID, level, alertTime, alertDate, actionNeeded);
		this.type=type;
		this.sleepingScope=sleepingScope;
		this.allocatedTime=allocatedTime;
	}

	public String getAllocatedTime() {
		return allocatedTime;
	}

	public void setAllocatedTime(String allocatedTime) {
		this.allocatedTime = allocatedTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSleepingScope() {
		return sleepingScope;
	}

	public void setSleepingScope(String sleepingScope) {
		this.sleepingScope = sleepingScope;
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
	    object.put("allocatedTime", this.getAllocatedTime());
	    object.put("sleepingScope", this.getSleepingScope());
	    return object;
		
	}

}
