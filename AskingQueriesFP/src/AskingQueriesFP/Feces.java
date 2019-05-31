package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Feces extends BasicAlert {
	String amount;
	String color;
	String texture;
	
	public Feces(int childID, String level, String alertTime, String alertDate, String actionNeeded,String amount,String color,String texture) {
		super(childID, level, alertTime, alertDate, actionNeeded);
		this.color=color;
		this.amount=amount;
		this.texture =texture;
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

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	JSONObject getJson() throws JSONException {
		JSONObject object = new JSONObject();
	    object.put("alertDate", this.getAlertDate());
	    object.put("alertTime", this.getAlertTime());
	    object.put("childID",String.valueOf(this.getChildID()));
	    object.put("level", this.getLevel());
	    object.put("actionNeeded", this.getActionNeeded());
	    object.put("alertID", this.getAlertID());
	    object.put("texture", this.getTexture());
	    object.put("color", this.getColor());
	    object.put("amount", this.getAmount());
	    return object;
		
	}

}
