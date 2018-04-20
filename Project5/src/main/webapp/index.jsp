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
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Animations</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker();
    $( "#anim" ).on( "change", function() {
      $( "#datepicker" ).datepicker( "option", "showAnim", $( this ).val() );
    });
  } );
  </script>
  
</head>

<title>Welcome to PlanIt!</title>
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    
  </head>

  <body>
   
  <h1>PlanIt</h1>

   
        <p><%
    
        


    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    

    if (user != null) {
    
      

      pageContext.setAttribute("user", user);
     %> 
      



<p>Hello, ${fn:escapeXml(user.nickname)}! (You can

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>

    
    <form action="/timeserv" method="post">
  Input all of your busy times
  <table></table>
  <div id="date1" style="display:inline-block;">
  Day: 
  <input type="date" id="datepicker" name="datepicker" size="30">
  </div>
  <div id="start1" style="display:inline-block;">
  Choose a starting time:
  <input type="time" name="start[]" step="1800" min="8:00" max="23:59">
  </div>
  <div id="end1" style="display:inline-block;">
  Choose an ending time:
  <input type="time" name="end[]" step="1800" min="8:00" max="23:59">
  <br>
 
  </div>
  
  <div style="clear:both;">&nbsp;</div>
  <div>
  <input type="button" value="Add another time input" onClick="addInput('start1', 'end1');">
  </div>
  
  <div>
      <input type="submit" value="Submit time">
  </div>
</form>
<form action = "/compare" method="post">
<div id = "email" style="display:inline-block;">
Enter your friend's email to find what time's you are both free!
<input type="text" name="users[]" >
</div>
<input type="button" value="Add another email" onClick="addInput1('email');">
<div>

<div id="date1" style="display:inline-block;">
  What day do you need? 
  <input type="date" id="date" name="date" size="30">
  </div>
<div>
  How long do you need? Hours:
  
  
  <input type="text" name="timeblockh" style="display:inline-block;">
  
  Minutes
  <input type="text" name="timeblockm" style="display:inline-block;">
  </div>
      <input type="submit" value="Submit time">
  </div>
  
</form>

<form action = "/events" method="post">
	<div>
	<input type = "submit" value="Click to see your events!">
	</div>
</form>
<script>
function addInput1(divName){
	 var newdiv = document.createElement('div');
    
     newdiv.innerHTML ="<br><input type='text' name='users[]'>";
     
     document.getElementById(divName).appendChild(newdiv);
     	
}
</script>
<script>

function addInput(divName, divName1){
     
          var newdiv = document.createElement('div');
          var newdiv1 = document.createElement('div1');
          newdiv.innerHTML ="<br>Choose a starting time: <input type='time' name='start[]'>";
          newdiv1.innerHTML ="<br>Choose an ending time: <input type='time' name='end[]'><br>";
          document.getElementById(divName).appendChild(newdiv);
          document.getElementById(divName1).appendChild(newdiv1);
          
     }
 
</script>


<%
    } else {
%>
<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to select your times!
<% 
    }

%>



     

            

           
            
          
            
            
            

            

        
    

    


<form action="/sign" method="post">
 <input type="hidden" name="blogName" value="${fn:escapeXml(blogName)}"/>

    </form>
    


</body>
</html>