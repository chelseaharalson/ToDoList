var ncTable;
var currentRowId;
var viewMode = "nc";
$(document).ready(function() {
    ncTable = $("#taskTable").DataTable( {		// Draw the jQuery datatable and set options
        "rowId": "id",
        "bFilter": false,
        "bInfo": false,
        "bLengthChange": true,
        "bPaginate": false,
        "bSort": false,
        "columns": [
            {
              "data": null,
               defaultContent: ''
            },
            { "data": "taskDescr" },
            { "data": "dateDue" },
            { "data": "timeDue" },
            {
            	   "data": null,
                className: "center",
                defaultContent: '<center><button id="btnDeleteTask" onClick=deleteTask($(this).closest(\'tr\').index()) style="border: none; background: none;"><img src="images/delete.png"></button></center>'
            },
            {
               "data": null,
                className: "center",
                defaultContent: '<a href="#openEdit" id="btnEditTask" onClick=editTask($(this).closest(\'tr\').index())><img src="images/edit.png"></a>'
            },
            {
               "data": null,
                className: "center",
                defaultContent: '<a href="#openDetails" id="btnViewDetails" onClick=viewDetails($(this).closest(\'tr\').index())><img src="images/details.png"></a>'
            }
        ],
        columnDefs: [ 
        	{
            orderable: false,
            className: 'select-checkbox',
            targets:   0
        } ],
        select: {
            style:    'multi',
            selector: 'td:first-child'
        },
        order: [[ 1, 'asc' ]]
        } );
      
    viewMode = 'nc';		// Default mode on page load set to button "show not completed"
	  $.ajax({
      url: 'displayData',
      type: 'post',
      method: 'post',
      data: {
      	'type': 'btnShowNotCompleted',
      	'viewMode': viewMode
      },
      dataType: 'text',
      success: function(data) {
      		ncTable.clear().draw();
      		var jsonObj = JSON.parse(data);
      		for (var i = 0; i < jsonObj.data.length; i++) {
  		        var newRow = ncTable.rows.add([
        	    	{
        	    		'taskDescr': jsonObj.data[i].taskDescr,
        	    		'dateDue': jsonObj.data[i].dateDue,
        	    		'timeDue': jsonObj.data[i].timeDue
        	    	}
        	    ]).draw();
  		        var temp = document.getElementById("taskTable").tBodies[0].rows.length-1;
             	var lastRow = document.getElementById("taskTable").tBodies[0].rows[temp];
             	lastRow.setAttribute('id', jsonObj.data[i].id);
             	if (jsonObj.data[i].isOverdue == "true") {
             		$(lastRow.closest('tr')).css('color', '#FF0000');
             	}
      		 }
      	     ncTable.draw();
  	    }
  });
} );

$(function() {		// Button is to add a new item
    $('#formAddTask').on('submit', function(event) {
        event.preventDefault();
        var txbAddDes = document.getElementById("txbAddTaskDes").value;		// Get parameters
        var txbAddDet = document.getElementById("txtAreaAddDetails").value;
        var txbDateDue = document.getElementById("txbDateDue").value;
        var ddlHoursElement = document.getElementById("ddlAddHour");
        var ddlHoursVal = ddlHoursElement.options[ddlHoursElement.selectedIndex].value;
        var ddlMinutesElement = document.getElementById("ddlAddMinute");
        var ddlMinutesVal = ddlMinutesElement.options[ddlMinutesElement.selectedIndex].value;
        var ddlAmPmElement = document.getElementById("ddlAddAmPm");
        var ddlAmPmVal = ddlAmPmElement.options[ddlAmPmElement.selectedIndex].value;
      	  $.ajax({
            url: 'displayData',
            type: 'post',
            method: 'post',
            data: { 			// Send this data to the server
                'type': 'btnAddTask',
                'txbAddTaskDes': txbAddDes,
                'txtAreaAddDetails': txbAddDet,
                'txbDateDue': txbDateDue,
                'ddlAddHour': ddlHoursVal,
                'ddlAddMinute': ddlMinutesVal,
                'ddlAddAmPm': ddlAmPmVal
            },
            dataType: 'text',
            success: function(data) {
          	    //console.log(data);
          	    var jsonObj = JSON.parse(data);		// Parse the JSON string from the server
          	    if (jsonObj.validDate == "false") {
            		    alert('Invalid date. Please enter in mm/dd/yyyy format.');
            	    }
          	    else if (jsonObj.emptyTask == "false") {
          	    		alert('Please enter a task description.');
          	    }
          	    else {		// Add new row to datatable
              	    var newRow = ncTable.rows.add([
              	    	{
              	    		'taskDescr': jsonObj.taskDescr,
              	    		'dateDue': jsonObj.dateDue,
              	    		'timeDue': jsonObj.timeDue
              	    	}
              	   ]).draw();
              	   var temp = document.getElementById("taskTable").tBodies[0].rows.length-1;
              	   var lastRow = document.getElementById("taskTable").tBodies[0].rows[temp];
              	   lastRow.setAttribute('id', jsonObj.id);
              	   if (jsonObj.isOverdue == "true") {		// If overdue, set text color to red
              		   $(lastRow.closest('tr')).css('color', '#FF0000');
                	   }
              	   newRow.draw();
              	   document.getElementById("formAddTask").reset();
          	    }
        	    }
        });
});
});

function editTask(rowId) {		// Editing a task
	currentRowId = rowId;		// Get current row ID
    var txbEditTaskDes = document.getElementById('txbEditTaskDes');		// Get parameters
	var txbEditDateDue = document.getElementById('txbEditDateDue');
	var ddlEditHoursElement = document.getElementById("ddlEditHour");
	var ddlEditHoursVal = ddlEditHoursElement.options[ddlEditHoursElement.selectedIndex].value;
	var ddlEditMinutesElement = document.getElementById("ddlEditMinute");
	var ddlEditMinutesVal = ddlEditMinutesElement.options[ddlEditMinutesElement.selectedIndex].value;
	var ddlEditAmPmElement = document.getElementById("ddlEditAmPm");
	var ddlEditAmPmVal = ddlEditAmPmElement.options[ddlEditAmPmElement.selectedIndex].value;
	var ncCells = taskTable.rows.item(currentRowId+1).cells;
	txbEditTaskDes.value = ncCells[1].innerHTML.trim();
	txbEditDateDue.value = ncCells[2].innerHTML.trim();
 	var thisRow = document.getElementById("taskTable").tBodies[0].rows[currentRowId];
	// Populate details textarea
	$.ajax({
	    type: "post",
	    method: "post",
	    url:"displayData",
	    dataType: "text",
	    data: {
	      	'type': 'btnViewDetails',
	    		'rowId': thisRow.getAttribute('id')
	    },
	    success: function(data) {
	        var jsonObj = JSON.parse(data);
	    	    $("#txtAreaEditDetails").val(jsonObj.details);
	    }
	});
	var timeArr = ncCells[3].innerHTML.trim().split(":");		// Get time
	var hour = timeArr[0];
	var tempMin = timeArr[1].split(" ");
	var min = tempMin[0];
	var amPm = tempMin[1];
	
	ddlEditHoursElement.value = hour;
	ddlEditMinutesElement.value = min;
	ddlEditAmPmElement.value = amPm;
	
	$('#formEditTask').on('submit', function(event) {
		event.preventDefault();
	    var txbEditDes = document.getElementById("txbEditTaskDes").value;		// Get parameter values
	    var txbEditDet = document.getElementById("txtAreaEditDetails").value;
	    var txbEditDateDue = document.getElementById("txbEditDateDue").value;
	    var ddlEditHoursElement = document.getElementById("ddlEditHour");
	    var ddlEditHoursVal = ddlEditHoursElement.options[ddlEditHoursElement.selectedIndex].value;
	    var ddlEditMinutesElement = document.getElementById("ddlEditMinute");
	    var ddlEditMinutesVal = ddlEditMinutesElement.options[ddlEditMinutesElement.selectedIndex].value;
	    var ddlEditAmPmElement = document.getElementById("ddlEditAmPm");
	    var ddlEditAmPmVal = ddlEditAmPmElement.options[ddlEditAmPmElement.selectedIndex].value;
	    	var thisRow = document.getElementById("taskTable").tBodies[0].rows[currentRowId];
	  	  $.ajax({
	        url: 'displayData',
	        type: 'post',
	        method: 'post',
	        data: { 		// Send this data to the server
	        		'rowId': thisRow.getAttribute('id'),
	            'type': 'btnSubmitEditTask',
	            'txbEditTaskDes': txbEditDes,
	            'txtAreaEditDetails': txbEditDet,
	            'txbEditDateDue': txbEditDateDue,
	            'ddlEditHour': ddlEditHoursVal,
	            'ddlEditMinute': ddlEditMinutesVal,
	            'ddlEditAmPm': ddlEditAmPmVal
	        },
	        dataType: 'text',
	        success: function(data) {
	      	    //console.log(data);
	      	    var jsonObj = JSON.parse(data);		// Parse JSON string from server
	      	    if (jsonObj.validDate == "false") {
	        		    alert('Invalid date. Please enter in mm/dd/yyyy format.');
	        	    }
	      	    else if (jsonObj.emptyTask == "false") {
	      	    		alert('Please enter a task description.');
	      	    }
	      	    else if (jsonObj.successEdit == "true") {		// Populate textboxes and dropdown list with previously selected values for that ID
	      	    	    var taskCells = taskTable.rows.item(currentRowId+1).cells;
	      	    	    taskCells[1].innerHTML = txbEditDes;
	      	    	  	taskCells[2].innerHTML = txbEditDateDue;
	      	    		var strTime = ddlEditHoursVal + ":" + ddlEditMinutesVal + " " + ddlEditAmPmVal;
	      	    		taskCells[3].innerHTML = strTime;
	      	    		if (jsonObj.overdue == "true") {		// If overdue, set color to red
	      	    			$(taskTable.rows.item(currentRowId+1).closest('tr')).css('color', '#FF0000');
	                 }
	      	    		else {
	      	    			$(taskTable.rows.item(currentRowId+1).closest('tr')).css('color', '#000000');
	      	    		}
	      	    		$('#taskTable').DataTable().draw();
	      	    		alert("Edit was successful.");
	      	    }
	      	    else if (jsonObj.successEdit == "false") {
	      	    		alert("Edit unsucessful. Data not found.");
	      	    		$('#taskTable').DataTable().row(currentRowId).remove().draw();
	      	    }
		    }
	  	});
});
}
	
$(function() {		// Delete all tasks
	$('#formDeleteAllTask').on('submit', function(event) {
        event.preventDefault();
      	  $.ajax({
            url: 'displayData',
            type: 'post',
            method: 'post',
            data: {
            	'type': 'btnDeleteAll'
            },
            dataType: 'text',
            success: function(data) {
            		var jsonObj = JSON.parse(data);
            		if (jsonObj.deleteAll == "success") {	// If successful in backend, then clear the datatable
            			$('#taskTable').DataTable().clear().draw();
            			//alert("All tasks deleted succesfully.");
            		}
            		else {
            			alert("Please try again.");
            		}
        	    }
        });
});
});
	
function deleteTask(rowId) {		// Delete a specific task by row ID
var thisRow = document.getElementById("taskTable").tBodies[0].rows[rowId];	// Gets row
$(thisRow).addClass('selected');
	$.ajax({
     url: 'displayData',
     type: 'post',
     method: 'post',
     data: {
     	'type': 'btnDeleteTask',
     	'rowId': thisRow.getAttribute('id')
     },
     dataType: 'text',
     success: function(data) {
    		var jsonObj = JSON.parse(data);
    		if (jsonObj.deleteTask == "success") {
    			$('#taskTable').DataTable().row(rowId).remove().draw();		// Removes this row from datatable if successful in backend
    			//alert("Deleted task succesfully.");
    		}
    		else {
    			alert("Please try again.");
    		}
     }    
	});
}

function viewDetails(rowId) {		// View details button
	var thisRow = document.getElementById("taskTable").tBodies[0].rows[rowId];
	// Populate details textbox
	$.ajax({
	    type: "post",
	    method: "post",
	    url:"displayData",
	    dataType: "text",
	    data: {
	      	'type': 'btnViewDetails',
	    		'rowId': thisRow.getAttribute('id')
	    },
	    success: function(data) {
	        var jsonObj = JSON.parse(data);
	        //alert(jsonObj.details);
	    	    $("#txtAreaViewDetails").val(jsonObj.details);		// Populate the text area
	    }
	});
}

$(function() {		// Completing a specific task
	$('#formCompleteTask').on('submit', function(event) {
        	  event.preventDefault();
        	  var checkedList = [];		// Get a list with the checked values for each ID
        	  for (var i = 1; i < ncTable.rows().count()+1; i++) {
        	     var thisRow = taskTable.rows.item(i);
        	     var selected = thisRow.getAttribute('class');
        	     if (selected == "even selected" || selected == "odd selected") {
        	    	 	checkedList.push([thisRow.getAttribute('id'),true]);
        	     }
        	     else {
        	    	 	checkedList.push([thisRow.getAttribute('id'),false]);
        	     }
        	  }
        	  
        	  var jsonStr = "{\"checkedData\": [";		// JSON string of checked values to send to the server
        	  for (var j = 0; j < checkedList.length; j++) {
        		  //console.log(checkedList[j]);
        		  jsonStr += "{\"id\": \"" + checkedList[j][0] + "\", \"checked\": \"" + checkedList[j][1] + "\"}"; 
        		  if (j+1 != checkedList.length) {
        			  jsonStr += ",";
        		  }
        	  }
        	  jsonStr += "]}";
        	  //console.log(jsonStr);
        	  
	  	  $.ajax({
	        url: 'displayData',
	        type: 'post',
	        method: 'post',
	        data: {
	        	'type': 'btnComplete',
	        	'dataStr': jsonStr,
	        	'viewMode': viewMode
	        },
	        dataType: 'text',
	        success: function(data) {
	        		ncTable.clear().draw();
	        		var jsonObj = JSON.parse(data);
	        		for (var i = 0; i < jsonObj.data.length; i++) {
        		        var newRow = ncTable.rows.add([
              	    	{
              	    		'taskDescr': jsonObj.data[i].taskDescr,
              	    		'dateDue': jsonObj.data[i].dateDue,
              	    		'timeDue': jsonObj.data[i].timeDue
              	    	}
              	    ]).draw();
        		        var temp = document.getElementById("taskTable").tBodies[0].rows.length-1;
                   	var lastRow = document.getElementById("taskTable").tBodies[0].rows[temp];
                   	lastRow.setAttribute('id', jsonObj.data[i].id);
                   	if (jsonObj.data[i].isComplete == "true" && lastRow.getAttribute('class') == "even") {	// Set checkboxes for each row
                   		lastRow.setAttribute('class', 'even selected');
                   	}
                   	else if (jsonObj.data[i].isComplete == "true" && lastRow.getAttribute('class') == "odd") {
                   		lastRow.setAttribute('class', 'odd selected');
                   	}
                   	if (jsonObj.data[i].isOverdue == "true") {	// If overdue, change color to red
                 		$(lastRow.closest('tr')).css('color', '#FF0000');
                 	}
	        		 }
	        	     ncTable.draw();
	    	    }
        });
});
});

$(function() {		// Completing all tasks
	$('#formCompleteAllTask').on('submit', function(event) {
        	  event.preventDefault();
	  	  $.ajax({
	        url: 'displayData',
	        type: 'post',
	        method: 'post',
	        data: {
	        	'type': 'btnCompleteAll',
	        	'viewMode': viewMode
	        },
	        dataType: 'text',
	        success: function(data) {
	        		ncTable.clear().draw();
	        		var jsonObj = JSON.parse(data);
	        		for (var i = 0; i < jsonObj.data.length; i++) {
        		        var newRow = ncTable.rows.add([
              	    	{
              	    		'taskDescr': jsonObj.data[i].taskDescr,
              	    		'dateDue': jsonObj.data[i].dateDue,
              	    		'timeDue': jsonObj.data[i].timeDue
              	    	}
              	    ]).draw();
        		        var temp = document.getElementById("taskTable").tBodies[0].rows.length-1;
                   	var lastRow = document.getElementById("taskTable").tBodies[0].rows[temp];
                   	lastRow.setAttribute('id', jsonObj.data[i].id);
                   	if (jsonObj.data[i].isComplete == "true" && lastRow.getAttribute('class') == "even") {	// Set checkboxes for each row
                   		lastRow.setAttribute('class', 'even selected');
                   	}
                   	else if (jsonObj.data[i].isComplete == "true" && lastRow.getAttribute('class') == "odd") {
                   		lastRow.setAttribute('class', 'odd selected');
                   	}
	        		 }
	        	     ncTable.columns.adjust().draw();
	    	    }
        });
});
});

$(function() {
	$('#formShowAllTask').on('submit', function(event) {
        	  event.preventDefault();
        	  viewMode = 'showAll';
	  	  $.ajax({
	        url: 'displayData',
	        type: 'post',
	        method: 'post',
	        data: {
	        	'type': 'btnShowAll',
	        	'viewMode': viewMode
	        },
	        dataType: 'text',
	        success: function(data) {
	        		ncTable.clear();
	        		var jsonObj = JSON.parse(data);
	        		for (var i = 0; i < jsonObj.data.length; i++) {
        		        var newRow = ncTable.rows.add([
              	    	{
              	    		'taskDescr': jsonObj.data[i].taskDescr,
              	    		'dateDue': jsonObj.data[i].dateDue,
              	    		'timeDue': jsonObj.data[i].timeDue
              	    	}
              	    ]).draw();
        		        var temp = document.getElementById("taskTable").tBodies[0].rows.length-1;
                   	var lastRow = document.getElementById("taskTable").tBodies[0].rows[temp];
                   	lastRow.setAttribute('id', jsonObj.data[i].id);
                   	if (jsonObj.data[i].isComplete == "true" && lastRow.getAttribute('class') == "even") {	// Set checkboxes for each row
                   		lastRow.setAttribute('class', 'even selected');
                   	}
                   	else if (jsonObj.data[i].isComplete == "true" && lastRow.getAttribute('class') == "odd") {
                   		lastRow.setAttribute('class', 'odd selected');
                   	}
                   	if (jsonObj.data[i].isOverdue == "true" && jsonObj.data[i].isComplete == "false") {
                   		$(lastRow.closest('tr')).css('color', '#FF0000');
                   	}
	        		 }
	        	     ncTable.draw();
	    	    }
        });
});
});

$(function() {
	$('#formShowNotCompleted').on('submit', function(event) {
        	  event.preventDefault();
        	  viewMode = 'nc';
	  	  $.ajax({
	        url: 'displayData',
	        type: 'post',
	        method: 'post',
	        data: {
	        	'type': 'btnShowNotCompleted',
	        	'viewMode': viewMode
	        },
	        dataType: 'text',
	        success: function(data) {
	        		ncTable.clear();
	        		var jsonObj = JSON.parse(data);
	        		for (var i = 0; i < jsonObj.data.length; i++) {
        		        var newRow = ncTable.rows.add([
              	    	{
              	    		'taskDescr': jsonObj.data[i].taskDescr,
              	    		'dateDue': jsonObj.data[i].dateDue,
              	    		'timeDue': jsonObj.data[i].timeDue
              	    	}
              	    ]).draw();
        		        var temp = document.getElementById("taskTable").tBodies[0].rows.length-1;
                   	var lastRow = document.getElementById("taskTable").tBodies[0].rows[temp];
                   	lastRow.setAttribute('id', jsonObj.data[i].id);
                   	if (jsonObj.data[i].isOverdue == "true") {
                   		$(lastRow.closest('tr')).css('color', '#FF0000');
                   	}
	        		 }
	        	     ncTable.draw();
	    	    }
        });
});
});