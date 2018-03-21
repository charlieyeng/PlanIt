<!DOCTYPE html>
<%@ page import = "java.util.ArrayList"%>
<%@ page import = "java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
// retrieve your list from the request, with casting 
<% 
//ArrayList<String> listem = (ArrayList<String>) request.getAttribute("emaillist");
//ArrayList<Date> liste = (ArrayList<Date>) request.getAttribute("ending");
//ArrayList<Date> lists = (ArrayList<Date>) request.getAttribute("starting");

// print the information about every category of the list
//for(Date item : list) 
//{
   // do your work
//}
%>
<body>
Select a time
<form action="/email" method = "post">

<div>
<table border="1">
<tr>
<th>Starting times</th>
<th>Ending times</th>
<th>Select</th>
</tr>

<c:forEach items="${starting}" var="stime" varStatus="loop">
<c:set var="etime" value="${ending[loop.index]}" />

<tr> 
<td><c:out value="${stime}" /></td><td><c:out value="${etime}" /></td>
<td><input type = "radio" name="radioButton" value="${loop.count}"></td>

</tr>

</c:forEach>

</table>
</div>
<input type="submit" value="Send an email to notify all parties!">
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
<a href="/calendar.jsp" target="_blank">Calendar View</a>
</body>