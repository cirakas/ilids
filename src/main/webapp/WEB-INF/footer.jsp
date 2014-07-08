<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="resources" value="/resources/" />

</div><!-- /#page-wrapper -->

    </div><!-- /#wrapper -->
    <!-- JavaScript-- path should be changed when everything works fine -->
    <script type="text/javascript" src="${resources}ilids-template/js/jquery-1.10.2.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${resources}ilids-template/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${resources}ilids-template/js/locales/bootstrap-datetimepicker.uk.js" charset="UTF-8"></script>
    <script src="${resources}ilids-template/js/bootstrap.js"></script>
    <!-- Page Specific Plugins-- Path should be changed when everything works fine -->
    <script src="${resources}ilids-template/js/raphel/raphael-min.js"></script>
    <script src="${resources}ilids-template/js/morris/morris.min.js"></script>
    <script src="${resources}ilids-template/js/morris/chart-data-morris.js"></script>
    <script src="${resources}ilids-template/js/tablesorter/jquery.tablesorter.js"></script>
    <script src="${resources}ilids-template/js/tablesorter/tables.js"></script>
    
<script type="text/javascript">
//in this line of code, to display the datetimepicker,  we used form_datetime as an argument to be 
//passed in javascript. This is for Date and Time.
    $('.form_datetime').datetimepicker({
        language:  'en',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
//this is for Date only	
 	$('.form_date').datetimepicker({
        language:  'en',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
//this is for Time Only	
	$('.form_time').datetimepicker({
        language:  'en',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		forceParse: 0
    });
</script>
  </body>
</html>
