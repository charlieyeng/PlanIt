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

 

<html>
<head>
<link type="text/css" rel="stylesheet" href = "/stylesheets/main.css"/>
</head>

  <head>

  </head>

  <body>
   
  <h1>Charlie and Gui's Blog</h1>
 

  <body>

 

<%

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

<%

    } else {

%>

<p>Hello!

<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>

to include your name with greetings you post.</p>

<%

    }

%>

 

<%

   

%>
<script>
function validateForm() {
    var x = document.forms["myForm"]["title"].value;
    var y = document.forms["myForm"]["content"].value;
    if (x == null || x == "" ||y == null || y == "")  {
        alert("Cannot post empty blog!");
        return false;
    }
}
</script>

 

    <form name="myForm" action="/sign" onsubmit="return validateForm()" method="post">
    
      <div><textarea name="title" rows="1" cols="60"></textarea></div>

      <div><textarea name="content" rows="3" cols="60"></textarea></div>
     
      <div><input type="submit" value="Post Blog" /></div>

      <input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>

    </form>

 

  </body>

</html>