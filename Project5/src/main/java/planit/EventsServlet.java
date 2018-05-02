package planit;
import com.google.appengine.api.datastore.DatastoreService;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

 

import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
public class EventsServlet extends HttpServlet{
	private static final Logger _logger = Logger.getLogger(TimeServlet.class.getName());
    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();
        String [] monthNames = {"January", "February", "March", "April", "May", "June",
      		  "July", "August", "September", "October", "November", "December"};
      String [] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        User user = userService.getCurrentUser();
        String email = user.getEmail();
        _logger.info(email);
 
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //modified test
        String start[] = req.getParameterValues("start[]");
        String end[] = req.getParameterValues("end[]");
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date d = new Date();
        
   
        Key blogKey = KeyFactory.createKey("Stamp", email);

        // Run an ancestor query to ensure we see the most up-to-date

        // view of the Greetings belonging to the selected Guestbook.
//        try {
        Query query = new Query("st", blogKey).addSort("starttime", Query.SortDirection.DESCENDING);
       
        //Query query = new Query(blogKey);
        

        List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(2000));
        
        /*if(greetings.isEmpty()) {
        	resp.sendRedirect("/errors.jsp");
        	return;
        }*/
        ArrayList<String> startTimes = new ArrayList<>();
        ArrayList<String> endTimes = new ArrayList<>();
        ArrayList<Date> s1 = new ArrayList<>();
        ArrayList<Date> e1 = new ArrayList<>();
        
        for(Entity gre : greetings) {
        	System.out.println(gre.getProperty("starttime").toString());
        	startTimes.add(gre.getProperty("starttime").toString());
        	s1.add((Date) gre.getProperty("starttime"));
        	System.out.println(gre.getProperty("endtime").toString());
        	endTimes.add(gre.getProperty("endtime").toString());
        	e1.add((Date) gre.getProperty("endtime"));
        	System.out.println(gre.getProperty("email").toString());
        }
        
        
        ArrayList<String> timeasstring1 = new ArrayList<>();
        ArrayList<String> timeasstring2 = new ArrayList<>();
    	for(int k = 0; k<startTimes.size();k++) {
    		String am = "AM";
    		Calendar calen = Calendar.getInstance();
    		calen.setTime(s1.get(k));
    		int hourtry = calen.get(Calendar.HOUR_OF_DAY);
    		int hourtry1 = calen.get(Calendar.HOUR);
    		if(hourtry!=hourtry1) {
    			am = "PM";
    		}
    		if(hourtry1==hourtry && hourtry==12) {
    			am="PM";
    		}
    		if(hourtry1==0) {
    			hourtry1 = 12;
    		}
    		String h = Integer.toString(hourtry1);
    		if(h.length()!=2) {
    			h ="0"+h;
    		}
    		String m = Integer.toString(calen.get(Calendar.MINUTE));
    		if(m.length()!=2) {
    			m ="0"+m;
    		}
    		
    		String varx = days[calen.get(Calendar.DAY_OF_WEEK)-1] + " " +monthNames[calen.get(Calendar.MONTH)] + " " + Integer.toString(calen.get(Calendar.DAY_OF_MONTH)) + " " + h + ":" + m + " " + am;
    		timeasstring1.add(varx);
    		am = "AM";
    		calen.setTime(e1.get(k));
    		hourtry = calen.get(Calendar.HOUR_OF_DAY);
    		hourtry1 = calen.get(Calendar.HOUR);
    		if(hourtry!=hourtry1) {
    			am = "PM";
    		}
    		if(hourtry1==hourtry && hourtry==12) {
    			am="PM";
    		}
    		if(hourtry1==0) {
    			hourtry1 = 12;
    		}
    		h = Integer.toString(hourtry1);
    		if(h.length()!=2) {
    			h ="0"+h;
    		}
    		m = Integer.toString(calen.get(Calendar.MINUTE));
    		if(m.length()!=2) {
    			m ="0"+m;
    		}
    		varx = days[calen.get(Calendar.DAY_OF_WEEK)-1] + " " +monthNames[calen.get(Calendar.MONTH)] + " " + Integer.toString(calen.get(Calendar.DAY_OF_MONTH)) + " " + h + ":" + m + " " + am;
    		timeasstring2.add(varx);
    	}
        
        
        req.getSession().setAttribute("start", timeasstring1);
        req.getSession().setAttribute("end", timeasstring2);
        req.getSession().setAttribute("startingcal", s1);
        req.getSession().setAttribute("endingcal", e1);
     //   }
//        catch (Exception e) {
//        	resp.sendRedirect("/errors.jsp");
//        	e.printStackTrace();
//        	return;
//        }
        resp.sendRedirect("/events.jsp");

    }

	
}
