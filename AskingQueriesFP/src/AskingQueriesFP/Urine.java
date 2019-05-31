package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Urine extends BasicAlert {
	String amount;
	String color;
	String fragrance;

	public Urine(int childID, String level, String alertTime, String alertDate, String actionNeeded,String amount,String color,String fragrance) {
		super(childID, level, alertTime, alertDate,actionNeeded);
		this.amount =amount;
		this.color= color;
		this.fragrance=fragrance;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFragrance() {
		return fragrance;
	}

	public void setFragrance(String fragrance) {
		this.fragrance = fragrance;
	}
	
	JSONObject getJson() throws JSONException {
		JSONObject object = new JSONObject();
	    object.put("alertDate", this.getAlertDate());
	    object.put("alertTime", this.getAlertTime());
	    object.put("childID",String.valueOf(this.getChildID()));
	    object.put("level", this.getLevel());
	    object.put("actionNeeded", this.getActionNeeded());
	    object.put("alertID", this.getAlertID());
	    object.put("amount", this.getAmount());
	    object.put("fragrance", this.getFragrance());
	    object.put("color", this.getColor());
	    return object;
		
	}

}
