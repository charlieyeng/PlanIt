<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>

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

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<link type="text/css" rel="stylesheet" href = "/stylesheets/main.css"/>
</head>
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    
  </head>

  <body>
   
  <h1>Charlie and Gui's Blog</h1>
  <img alt="dog" src="https://novaservis.info/wp-content/uploads/2017/12/cute-dog-picsbest-25-cute-dog-pictures-ideas-on-pinterest-funny-dog-pictures-smart.jpg" width = "150" height = "200">
  
   
        <p><%

    String blogName = request.getParameter("blogName");

    if (blogName == null) {

        blogName = "default";

    }

    pageContext.setAttribute("blogName", blogName);

    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    if (user != null) {

      pageContext.setAttribute("user", user);
     %> 
      



<p>Hello, ${fn:escapeXml(user.nickname)}! (You can

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>

<table>
      <tr>
        <td colspan="2" style="font-weight:bold;">Available Servlets:</td>        
      </tr>
      <tr>
        <td><a href='/blog.jsp'>Post Your Blog</a></td>
        
        <td>
</table>

<%

    } else {

%>

<p>Hello!

<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>

to post your blog!</p>

<%

    }

%>

<% 
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Key blogKey = KeyFactory.createKey("Blog", blogName);

    // Run an ancestor query to ensure we see the most up-to-date

    // view of the Greetings belonging to the selected Guestbook.

    Query query = new Query("Greeting", blogKey).addSort("date", Query.SortDirection.DESCENDING);

    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(20000));

    if (greetings.isEmpty()) {

        %>

        <p>Blog '${fn:escapeXml(blogName)}' has no messages.</p>

        <%

    } else {

        %>

        <p>Messages in Blog '${fn:escapeXml(blogName)}'.</p>

        <%
		for(Entity greeting: greetings){
        	
        	pageContext.setAttribute("greeting_title",

                    greeting.getProperty("title"));
        	
        	pageContext.setAttribute("greeting_date",

                    greeting.getProperty("date"));

            pageContext.setAttribute("greeting_content",

                                     greeting.getProperty("content"));

            

                pageContext.setAttribute("greeting_user",

                                         greeting.getProperty("user"));

                %>

                <p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>
            <blockquote style="font-size:35px">${fn:escapeXml(greeting_title)}</blockquote>

			<blockquote>${fn:escapeXml(greeting_date)}</blockquote>

            <blockquote>${fn:escapeXml(greeting_content)}</blockquote>

                <%

            }

            %>
            
          
            
            
            

            <%

        }

    

%>
<form action="/sign" method="post">
 <input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>

    </form>
  </body>
</html>