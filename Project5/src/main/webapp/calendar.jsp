<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

 <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Timetable</title>
    <link rel='stylesheet' href='fullcalendar/fullcalendar.css' />
   
    <script src='fullcalendar/lib/jquery.min.js'></script>
    <script src='fullcalendar/lib/moment.min.js'></script>
    <script src='fullcalendar/lib/jquery-ui.min.js'></script>
    <script src='fullcalendar/fullcalendar.js'></script>
</head>   
<body>
    <script>
    
    $(document).ready(function() {
        $('#calendar').fullCalendar({
        	defaultView: 'agendaDay',
        	minTime: '08:00',
        	maxTime: '23:00',
            theme: true,
            editable: false,
            events: "/CalendarJsonServlet"
        });
 
    });
    
    </script>
  <div id="calendar"></div>
  
</body> 

</html>