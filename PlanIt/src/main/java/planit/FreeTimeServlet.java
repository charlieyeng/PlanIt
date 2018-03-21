package planit;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
public class FreeTimeServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws IOException {
		 UserService userService = UserServiceFactory.getUserService();
	     User user = userService.getCurrentUser();
		 String otherUser = req.getParameter("otherUser");
		 String userEmail;
		 req.setAttribute("otherUser",otherUser);
		 try {
			req.getRequestDispatcher("freeTime.jsp").forward(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 
		 
	}
}
