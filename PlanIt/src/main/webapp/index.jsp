<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

<%@ page import="java.util.Collections" %>

<%@ page import="com.google.appengine.api.users.User" %>

<%@ page import="com.google.appengine.api.users.UserService" %>

<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>

<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>

<%@ page import="com.google.appengine.api.datastore.Query" %>

<%@ page import="com.google.appengine.api.datastore.Entity" %>

<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>

<%@ page import="com.google.appengine.api.datastore.Key" %>

<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import ="planit.Event" %>

<%@ page import = "com.googlecode.objectify.ObjectifyService" %>

<%@ page import = "com.googlecode.objectify.Objectify" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>PlanIT</title>
  </head>

  <body>
    <h1>Welcome to PlanIt!</h1>
    <p>Simply submit everyone's calendars and receive a calendar of everyone's free time!</p>
  </body>
  
  <form action="/free" method="post">
The email of the other user you'd like to share with:<br>
  <input type="text" name="otherUser"><br>
   <div><input type="submit" value="Find Free Time!" ></div>
 </form>
  
  <%
  String eventName = request.getParameter("eventName");

  if (eventName == null) {

      eventName = "default";

  }

  pageContext.setAttribute("eventName", eventName);
    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    if (user != null) {

      pageContext.setAttribute("user", user);

%>


<p>Hello, ${fn:escapeXml(user.nickname)}! (You can

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>

<%

    } else {

%>

<p>Hello!

<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>

to include your name with events you post.</p>

<%

    }

%>
<%


ObjectifyService.register(Event.class);

List<Event> events = ObjectifyService.ofy().load().type(Event.class).list();   

Collections.sort(events); 

    if (events.isEmpty()) {

        %>

        <p>Guestbook '${fn:escapeXml(eventName)}' has no messages.</p>

        <%

    } else {

        %>

        <p>Events in Calendar '${fn:escapeXml(eventName)}'.</p>

        <%

        for (Event event : events) {
        	
        	if(event.getUser().equals(user)){

            pageContext.setAttribute("eventTitle",

                                     event.getTitle());
            pageContext.setAttribute("startTime",

                    event.getStart());
            pageContext.setAttribute("endTime",

                    event.getEnd());

            if (event.getUser() == null) {

                %>

                <p>An anonymous person wrote:</p>

                <%

            } else {

                pageContext.setAttribute("event_user",

                                         event.getUser());

                %>

                <p><b>${fn:escapeXml(event_user.nickname)}</b> has this event:</p>

                <%

            }

            %>

            <blockquote>${fn:escapeXml(eventTitle)}</blockquote>
            
			<p>from: ${fn:escapeXml(startTime)} to ${fn:escapeXml(endTime)}</p>
            <%

        }
        	else 
        		continue;
       }

    }

%>

<form action="/sign" method="post">
Title:<br>
  <input type="text" name="title"><br>
	Start Time in the form "Jun 7, 2013 12:10:56 PM":<br>
	<div><textarea name="start" rows="1" cols="60"></textarea></div>
	End Time in the form "Jun 7, 2013 12:10:56 PM": <br>
   <div><textarea name="end" rows="1" cols="60"></textarea></div>
   <div><input type="submit" value="Post Event" ></div>
 </form>


 

  </body>
</html>