package AskingQueriesFP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.json.HTTP;
import org.json.JSONObject;


public class SendAllert {
	
	int send(int ID,String Date,String Time,String Level,String EventsLeading,String Action) throws Exception{	
		URL	url = new URL("http://127.0.0.1:5000/alerts/LogicSystemAlert/1"); //  http://127.0.0.1:5000/alerts/LogicSystemAlert/1  https://httpbin.org/post
		/*Map<String,String> params = new LinkedHashMap<String,String>();
	    params.put("\"Date\"", addB(Date));
	    params.put("\"Time\"", addB(Time));
	    params.put("\"Child id\"",String.valueOf(ID));
	    params.put("\"Level\"", addB(Level));
	    params.put("\"Events leading\"", addB(EventsLeading));
	    params.put("\"Action needed\"", addB(Action));
	    String JsonToString =params.toString();
	    JsonToString=JsonToString.replaceAll("=", ":");
	    System.out.println(JsonToString);*/
		
	    JSONObject object = new JSONObject();
	    object.put("Date", Date);
	    object.put("Time", Time);
	    object.put("Child id",String.valueOf(ID));
	    object.put("Level", Level);
	    object.put("Events leading", EventsLeading);
	    object.put("Action needed", Action);
	   
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charsets=UTF_8"); //  x-www-form-urlencoded   application/json; charsets=UTF_8
		conn.setRequestProperty("Content-Length", String.valueOf(object.length()));
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestProperty("Accept", "application/json; charsets=UTF_8");
		conn.setChunkedStreamingMode(0);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		System.out.println(object.toString());
		wr.write(object.toString());
		wr.flush();
		StringBuilder sb = new StringBuilder();  
		int HttpResult = conn.getResponseCode(); 
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;  
			while ((line = br.readLine()) != null) {  
				  sb.append(line);  
				  }
			br.close();

			JSONObject myResponse = new JSONObject(sb.toString());
			System.out.println(myResponse); // בעיה ראשונה יש בעיה לשלוח בעברית שניה המרכאות שאני שולח נשמרות עם הסימנים בגיסון
				} else {
				    System.out.println(conn.getResponseMessage());  
				}  
		return 0;
		
		
	}
	String addB(String s) {
		String temp = "\""+s+"\"";
		return temp;
	}
}
