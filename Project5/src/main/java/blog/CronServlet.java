package blog;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
@SuppressWarnings("serial")
public class CronServlet extends HttpServlet {
	
    
	private static final Logger _logger = Logger.getLogger(CronServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
	try {
		//Test22 letsee = new Test22();
		//letsee.doGet(req, resp);
		_logger.info("Cron Job has been executeda");
		String blogName = req.getParameter("blogName");
		 if (blogName == null) {

		        blogName = "default";

		    }
		 _logger.info("Cron Job has been executedb");
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	
	_logger.info("Cron Job has been executedc1");
    Key blogKey = KeyFactory.createKey("Blog", blogName);
    _logger.info("Cron Job has been executedc2");
    Query query = new Query("Greeting", blogKey).addSort("date", Query.SortDirection.DESCENDING);
    _logger.info("Cron Job has been executedc3");

    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(20000));
    _logger.info("Cron Job has been executedd");
   
    Date date = new Date();
    LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    localDateTime = localDateTime.minusHours(24);
    date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    _logger.info("Cron Job has been executede");
    StringBuilder newemails = new StringBuilder();
    _logger.info("Cron Job has been executedf");
    for(Entity greeting: greetings) {
    	if(date.compareTo((Date) greeting.getProperty("date"))<0) {
    		newemails.append(greeting.getProperty("user")+" wrote:\n"+greeting.getProperty("date")+"\n"+greeting.getProperty("title")+"\n"+greeting.getProperty("content")+"\n\n");
    		
    	}
    	
    	_logger.info("Cron Job has been executedg");
    	
    	
    	
	
	}
    if(newemails.length()!=0) {
    	_logger.info("Cron Job has been executedh");
    /*Iterator<String> it = Listee.subs.iterator();
    String [] to2 = new String [Listee.subs.size()];
    int i =0;
  		  while(it.hasNext()){
  			 
					to2[i]=it.next();
					i++;
				}
				*/ 
  		DatastoreService datastore1 = DatastoreServiceFactory.getDatastoreService();
    	Query query1 = new Query("email");
    	List<Entity> emais = datastore1.prepare(query1).asList(FetchOptions.Builder.withLimit(20000));
    	Properties properties = new Properties();
  		Session session = Session.getDefaultInstance(properties, null);
    	for(Entity ex : emais) {
    		
      	    try{  
      	    	Message message = new MimeMessage(session);
           	  message.setFrom(new InternetAddress("anything@blog-196401.appspotmail.com")); 
            message.setRecipient(Message.RecipientType.TO, new InternetAddress((String) ex.getProperty("user")));  
            message.setSubject("Daily Blog Updates");  
            message.setText(newemails.toString());  
            Transport.send(message);
      	    }catch (MessagingException mex) {mex.printStackTrace();} 
    	}
   
  		_logger.info("Cron Job has been executedi");  
  		  
  		//Properties properties = new Properties();
  		//Session session = Session.getDefaultInstance(properties, null);
  	  //  try{  
  	    	_logger.info("Cron Job has been executedj");
      	   
     	/*  for(int j = 0; j<to2.length;j++) {
     		 Message message = new MimeMessage(session);
         	  message.setFrom(new InternetAddress("anything@blog-196401.appspotmail.com")); 
          message.setRecipient(Message.RecipientType.TO, new InternetAddress(to2[j]));  
          message.setSubject("Daily Blog Updates");  
          message.setText(newemails.toString());  
          Transport.send(message);
      	  }
      	  */
          // message.setRecipient(Message.RecipientType.TO, new InternetAddress("guizamo@hotmail.com"));
         
      	  
    
           // Send message  
           
      //  }catch (MessagingException mex) {mex.printStackTrace();}  
  	    
	}
	
	}
	
	catch (Exception ex) {
	//Log any exceptions in your Cron Job
	}
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
	    	doGet(req, resp);
	    	}
	    
	}
	
