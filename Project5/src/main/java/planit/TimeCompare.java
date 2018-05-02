package planit;



import com.google.appengine.api.datastore.DatastoreService;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

 

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

 

public class TimeCompare extends HttpServlet {
	
	private static final Logger _logger = Logger.getLogger(TimeCompare.class.getName());

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {
    	_logger.info("hi");
    	ArrayList<Date> starttimes =new ArrayList<Date>();
        ArrayList<Date> endtimes =new ArrayList<Date>();
        String [] monthNames = {"January", "February", "March", "April", "May", "June",
                		  "July", "August", "September", "October", "November", "December"};
                String [] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key blogKey1 = KeyFactory.createKey("Stamp", user.getEmail());
        _logger.info(user.getEmail());
       /* Date d = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.WEEK_OF_YEAR, -1);
        d=now.getTime();
        Filter filter = new FilterPredicate("date", Query.FilterOperator.GREATER_THAN,d); */
        String date = req.getParameter("date");
        Date d = new Date();
        SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
        try {
			d = dateF.parse(date); 
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        Date eightam = d;
        Calendar eight = Calendar.getInstance();
        eight.setTime(eightam);
        eight.set(Calendar.HOUR_OF_DAY, 8);
        eight.set(Calendar.MINUTE, 0);
        eight.set(Calendar.SECOND,0);
        eightam = eight.getTime();
        Date nextday = d;
        Calendar next = Calendar.getInstance();
        next.setTime(nextday);
        next.set(Calendar.HOUR_OF_DAY, 23);
        next.set(Calendar.MINUTE, 0);
        next.set(Calendar.SECOND,0);
        nextday = next.getTime();
        
        Query query = new Query(blogKey1);
        List<Entity> emai = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(20000));
        if(emai.isEmpty()) {
        	resp.sendRedirect("/errors.jsp");
        	return;
        }else {
    	  for(Entity ex:emai) {
    		  Date eightcompare = (Date) ex.getProperty("starttime");
    		  if(eightcompare.compareTo(eightam)>=0 && eightcompare.compareTo(nextday)<=0) {
    		  starttimes.add((Date) ex.getProperty("starttime"));
    		  
    		  _logger.info(ex.getProperty("starttime").toString());
    		  endtimes.add((Date) ex.getProperty("endtime"));
    		  }
        }
        }
        if(starttimes.isEmpty()) {
        	resp.sendRedirect("/errors.jsp");
        	return;
        }
        _logger.info("passed");

        String users[] = req.getParameterValues("users[]");
        String hoursstamp = req.getParameter("timeblockh");
        String minutestamp = req.getParameter("timeblockm");
        int hstamp = Integer.parseInt(hoursstamp);
        int mstamp = Integer.parseInt(minutestamp);
        int timeblock = hstamp*60+mstamp;
        
        ArrayList<String> ppl2email = new ArrayList<>();
        ppl2email.add(user.getEmail());
        
        
        for(int i =0; i<users.length;i++) {
        Key blogKey = KeyFactory.createKey("Stamp", users[i]);
        ppl2email.add(users[i]);
        Query query1 = new Query(blogKey);
      	List<Entity> emais = datastore.prepare(query1).asList(FetchOptions.Builder.withLimit(20000));
      if(emais.isEmpty()) {
    	  resp.sendRedirect("/errors.jsp");
    	  return;
      }else {
    	  for(Entity ex:emais) {
    		  Date eightcompare = (Date) ex.getProperty("starttime");
    		  if(eightcompare.compareTo(eightam)>=0 && eightcompare.compareTo(nextday)<=0 ) {
    		  starttimes.add((Date) ex.getProperty("starttime"));
    		  _logger.info(ex.getProperty("starttime").toString());
    		  endtimes.add((Date) ex.getProperty("endtime"));
    		  }
        }
        }
      if(starttimes.isEmpty()) {
      	resp.sendRedirect("/errors.jsp");
      	return;
      }
        }
        
        
        Collections.sort(starttimes);
        Collections.sort(endtimes);
        ArrayList<Date> availabledates1 = new ArrayList<Date>();
        ArrayList<Date> availabledates2 = new ArrayList<Date>();
        Date begin = d;
        Calendar cal = Calendar.getInstance();
	    cal.setTime(begin);
	    cal.set(Calendar.HOUR_OF_DAY,8);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    begin = cal.getTime();
	    Date finish = d;
	    cal.setTime(finish);
	    cal.set(Calendar.HOUR_OF_DAY,23);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    finish = cal.getTime();
	    Date first = starttimes.get(0);
	    cal.setTime(first);
	    cal.add(Calendar.HOUR_OF_DAY,-8);
	    int check = cal.get(Calendar.HOUR_OF_DAY);
	    int check1 = cal.get(Calendar.MINUTE);
	    cal.setTime(first);
	    
	    int check2 =check*60+check1;
	    if(check2>=timeblock) {
	    	
	    	
	    	for(int increment = timeblock; increment<=check2;increment+=timeblock) {
	    	cal.setTime(begin);
	    	availabledates1.add(begin);
	    	_logger.info("next: "+begin.toString());
	    	cal.add(Calendar.MINUTE, timeblock);
	    	begin = cal.getTime();
	    	availabledates2.add(begin);
	    	}
	    }
	    for(int j = 0; j<starttimes.size()-1;j++) {
	    if(endtimes.get(j).compareTo(starttimes.get(j+1))>0) {
	    	continue;
	    }
	    else {
	    	Date x = starttimes.get(j+1);
	    	cal.setTime(x);
	    	check = cal.get(Calendar.HOUR_OF_DAY);
	    	check1 = cal.get(Calendar.MINUTE);
	    	check1 = check*60+check1;
	    	Date y = endtimes.get(j);
	    	cal.setTime(y);
	    	check = cal.get(Calendar.HOUR_OF_DAY);
	    	check2 = cal.get(Calendar.MINUTE);
	    	check2 = check*60+check2;
	    	if(check1-check2>=timeblock) {
	    		for(int increment = timeblock; increment<=check1-check2; increment+=timeblock) {
	    	    	cal.setTime(y);
	    	    	availabledates1.add(y);
	    	    	cal.add(Calendar.MINUTE, timeblock);
	    	    	y = cal.getTime();
	    	    	availabledates2.add(y);
	    	    	}
	    		
	    	}
	    }
	    }
	    Date fin = endtimes.get(endtimes.size()-1);
	    cal.setTime(fin);
	    check = cal.get(Calendar.HOUR_OF_DAY);
    	check1 = cal.get(Calendar.MINUTE);
    	check1 = check*60+check1;
    	cal.setTime(finish);
    	check = cal.get(Calendar.HOUR_OF_DAY);
    	check2 = cal.get(Calendar.MINUTE);
    	check2 = check*60+check2;
    	if(check2-check1>=timeblock) {
    		for(int increment = timeblock; increment<=check2-check1; increment+=timeblock) {
    	    	cal.setTime(fin);
    	    	availabledates1.add(fin);
    	    	cal.add(Calendar.MINUTE, timeblock);
    	    	fin = cal.getTime();
    	    	availabledates2.add(fin);
    	    	}
    		
    		
    	}
    	
    	ArrayList<String> timeasstring1 = new ArrayList<>();
    	ArrayList<String> timeasstring2 = new ArrayList<>();
    	for(int k = 0; k<availabledates1.size();k++) {
    		String am = "AM";
    		Calendar calen = Calendar.getInstance();
    		calen.setTime(availabledates1.get(k));
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
    		calen.setTime(availabledates2.get(k));
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
        

        

    	req.getSession().setAttribute("startingcal", availabledates1);
    	req.getSession().setAttribute("endingcal", availabledates2); 
    	req.getSession().setAttribute("starting", timeasstring1); 
    	req.getSession().setAttribute("ending", timeasstring2);
    	req.getSession().setAttribute("emaillist", ppl2email);
    	
    	
    	
    	ServletContext context= getServletContext();
    	RequestDispatcher rd= context.getRequestDispatcher("/display.jsp");
    	try {
			rd.forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

    
        

}
}


