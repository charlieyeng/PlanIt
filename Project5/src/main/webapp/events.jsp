<!DOCTYPE html>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<% 
// retrieve your list from the request, with casting
//ArrayList<String> listem = (ArrayList<String>) request.getAttribute("emaillist");
//ArrayList<Date> liste = (ArrayList<Date>) request.getAttribute("ending");
//ArrayList<Date> lists = (ArrayList<Date>) request.getAttribute("starting");

// print the information about every category of the list
//for(Date item : list) 
//{
   // do your work
//}
%>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>PlanIt Events</title>
  <link rel="stylesheet" href="/stylesheets/main.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<div class="container">
  <div class="bs-docs-section">
    <div class="bs-component">
  
    <div class="row">
    <div class="col-lg-3">
    </div>
    <div class="col-lg-6">
      <div class="page-header">
	  <h1 id="tables">A List of All Your Events</h1>
	</div>
		<form action="/email" method = "post">
		 
		  <div>
		    <table border="1" class="table table-hover">
		     <thread>
		        <tr>
		          <th scope="col">Starting times</th>
		          <th scope="col">Ending times</th>
		        </tr>
		      </thread>
			  <tbody>
		      <c:forEach items="${start}" var="stime" varStatus="loop">
		      <c:set var="etime" value="${end[loop.index]}" />
		
		        <tr class="table-secondary"> 
		          <td><c:out value="${stime}" /></td><td><c:out value="${etime}" /></td>
		        </tr>
		
		      </c:forEach>
		      </tbody>
		    </table>
		  </div>
		
		</form>
		
		<script>
			function correct(time){
			var monthNames = ["January", "February", "March", "April", "May", "June",
					  "July", "August", "September", "October", "November", "December"];
			var days = ["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];
					
			var day = days[time.getDay];
			var month = monthNames[time.getMonth()];
			var hours = time.getHours() > 12 ? time.getHours() - 12 : time.getHours();
			var am_pm = time.getHours() >= 12 ? "PM" : "AM";
			hours = hours < 10 ? "0" + hours : hours;
			var minutes = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();
			var seconds = time.getSeconds() < 10 ? "0" + time.getSeconds() : time.getSeconds();
			
			time1 = day + month + time.getDate()+ hours + ":" + minutes + ":" + seconds + " " + am_pm;
			return time1;
			}
		
		</script>
		   <div class="d-flex justify-content-between">
		    <div>
		    <a href="/index.jsp" target="_blank">Main Page</a>
		    </div>
		    <div>
			  <a href="/calendar.jsp" target="_blank">Calendar View</a>
			  </div>
		  </div>
    	</div>
    	</div>
	  <div class="col-lg-3">
	</div>
	</div>
    </div>
  </div>
</div>
</body>
</html>