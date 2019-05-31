package AskingQueriesFP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
	
	
	public static void main(String[] args) throws Exception {
		int port =   3306; 
		String userName = "root";
		String password = "orelmuseri1";
		Asker asker = new Asker(port,userName,password);
		int ellerts=asker.ask();
		System.out.println(ellerts);
		GetEvent g=new GetEvent();
		//g.send();
        

	}
	
	
	
}

