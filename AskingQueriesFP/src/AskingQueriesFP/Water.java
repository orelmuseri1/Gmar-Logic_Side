package AskingQueriesFP;

import org.json.JSONException;
import org.json.JSONObject;

public class Water extends BasicAlert {
	String amount;
	String consumedAmount;

	public Water(int childID, String level, String alertTime, String alertDate,String actionNeeded,String amount,String consumedAmount) {
		super(childID, level, alertTime, alertDate, actionNeeded);
		this.amount=amount;
		this.consumedAmount=consumedAmount;
		
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getConsumedAmount() {
		return consumedAmount;
	}

	public void setConsumedAmount(String consumedAmount) {
		this.consumedAmount = consumedAmount;
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
	    object.put("consumedAmount", this.getConsumedAmount());
	    return object;
		
	}

}
