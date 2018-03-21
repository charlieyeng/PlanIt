package planit;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Event implements Comparable<Event>{
	   @Parent Key<Calendar> eventName;
	    @Id Long id;
	    @Index String title;
	    @Index User user;
	    @Index Date start;
	    @Index Date end;
	    private Event() {}
	    public Event(User user, String title, Date start, Date end) {
	    	
	        this.user = user;
	        this.title = title;
	        this.start =  start;
	        this.end = end;
	    }
	    public User getUser() {
	        return user;
	    }
	    public String getTitle() {
	        return title;
	    }
	    public Date getStart() {
	    	return start;
	    }
	    public Date getEnd() {
	    	return end;
	    }
	    
	    
		@Override
		public int compareTo(Event arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
}
