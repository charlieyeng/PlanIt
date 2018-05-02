package planit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

public class EmailSend extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws IOException {
		
		String rad = req.getParameter("radioButton");
		int rad1 = Integer.parseInt(rad);
		
		ArrayList<String> d1 = (ArrayList<String>) req.getSession().getAttribute("starting");
		 ArrayList<String> d2 = (ArrayList<String>) req.getSession().getAttribute("ending");
		 ArrayList<String> d3 = (ArrayList<String>) req.getSession().getAttribute("emaillist");
		 
		
		
		StringBuilder newemails = new StringBuilder();
		newemails.append("Hi! Thank you for using PlanIt!\nWe are sending this email to confirm your meeting with: ");
		for(String s : d3) {
			newemails.append(s+", ");
		}
		newemails.append("\nYour meeting is confirmed from "+ d1.get(rad1-1) +" to " + d2.get(rad1-1)+ ".");
		Properties properties = new Properties();
  		Session session = Session.getDefaultInstance(properties, null);
for(String tosend : d3) {
    		
      	    try{  
      	    	Message message = new MimeMessage(session);
           	  message.setFrom(new InternetAddress("anything@planit-197222.appspotmail.com")); 
            message.setRecipient(Message.RecipientType.TO, new InternetAddress((String)tosend));  
            message.setSubject("PlanIt Confirmation");  
            message.setText(newemails.toString());  
            Transport.send(message);
      	    }catch (MessagingException mex) {mex.printStackTrace();} 
    	}
		
		
		
		
resp.sendRedirect("/conclude.jsp");	
		
	}
	}

