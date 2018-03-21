package blog;



import com.google.appengine.api.datastore.DatastoreService;

import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.User;

import com.google.appengine.api.users.UserService;

import com.google.appengine.api.users.UserServiceFactory;

 

import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

 

public class SignEmailServlet extends HttpServlet {
	 

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {

        UserService userService = UserServiceFactory.getUserService();

        User user = userService.getCurrentUser();

 


        String email = user.getEmail();

        Key eKey = KeyFactory.createKey("Email", email);
        
        
        
        Entity em = new Entity("email", email);

        em.setProperty("user", email);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Query query1 = new Query("email");
      	List<Entity> emais = datastore.prepare(query1).asList(FetchOptions.Builder.withLimit(20000));
      	boolean x = false;
      	for(Entity ex : emais) {
      		if(ex.getProperty("user").equals(email)) {
      			x=true;
      		}
      	}
      	if(!x) {

        datastore.put(em);
      	}

   //   Test22 letsee = new Test22();
  //    letsee.doGet(req, resp);

        resp.sendRedirect("/index.jsp");

    }

}


