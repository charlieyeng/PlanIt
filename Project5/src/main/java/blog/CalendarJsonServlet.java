package blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;

public class CalendarJsonServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ArrayList<CalendarDTO> dts = new ArrayList<>();
		 ArrayList<Date> d1 = (ArrayList<Date>) request.getSession().getAttribute("startingcal");
		 ArrayList<Date> d2 = (ArrayList<Date>) request.getSession().getAttribute("endingcal");
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 for(int i = 0; i<d1.size(); i++) {
			 CalendarDTO x = new CalendarDTO();
			 x.setId(i);
			 x.setStart(dateFormat.format(d1.get(i)));
			 x.setEnd(dateFormat.format(d2.get(i)));
			 x.setTitle("Freetime");
			 dts.add(x);
		 }
		
	 
		 
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8");
		 PrintWriter out = response.getWriter();
		 out.write(new Gson().toJson(dts));
		 }
		 
		}

