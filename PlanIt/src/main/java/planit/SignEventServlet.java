package planit;

import static com.googlecode.objectify.ObjectifyService.ofy;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.servlet.http.*;

import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

public class SignEventServlet extends HttpServlet {

static {

        ObjectifyService.register(Event.class);

    }
	  private static final Logger log = Logger.getLogger(SignEventServlet.class.getName());

	  

	    public void doPost(HttpServletRequest req, HttpServletResponse resp)

	                throws IOException {

	        UserService userService = UserServiceFactory.getUserService();

	        User user = userService.getCurrentUser();

	        String title = req.getParameter("title");
	        String start = req.getParameter("start");
	        String end = req.getParameter("end");
	        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
	        DateFormat sdf = new SimpleDateFormat("hh:mm");
	        Date startTime = new Date();
			try {
				startTime = formatter.parse(start);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Date endTime = new Date();;
			try {
				endTime = formatter.parse(end);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Event event = new Event(user, title, startTime, endTime);
	        ofy().save().entities(event).now();
	        

	        resp.sendRedirect("/index.jsp");

	    }
}
