package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ToDo;
import model.ToDoList;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Servlet implementation class DataTableServlet
 */
@WebServlet("/DataTableServlet")
public class DataTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ToDoList tdl = new ToDoList();

    /**
     * Default constructor. 
     */
    public DataTableServlet() {
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
        String tdlResponse = tdl.showNotCompleted();	// Get datatable
        response.getWriter().print(tdlResponse);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if (request.getParameter("type").equals("btnAddTask")) {		// Do if the button clicked is to add a new item
			String taskDesc = request.getParameter("txbAddTaskDes").trim();	// Get parameters of textboxes and dropdown lists
			String details = request.getParameter("txtAreaAddDetails").trim();
			String hour = request.getParameter("ddlAddHour");
			String minute = request.getParameter("ddlAddMinute");
			String amPm = request.getParameter("ddlAddAmPm");
			String timeDue = hour + ":" + minute + " " + amPm;	// Get time string
			String dateDue = request.getParameter("txbDateDue");
			boolean validDate = tdl.isValidDate(dateDue);
			if (validDate == true && !taskDesc.trim().equals("")) {		// Check if valid date and if the task description is not empty
				String addTask = tdl.addTask(taskDesc, details, dateDue, timeDue);
				//System.out.println(tdl.showNotCompleted());
				out.println(addTask);	// Send JSON string of task to client so row can be added to datatable
			}
			else if (validDate == false) {	// If the date is invalid, send error message / alert back to client to enter a valid date
				System.out.println("Invalid date.");		// Console logging
				out.println("{\n" + 
						"	\"validDate\": \"false\"\n" + 
						"}");
			}
			else if (taskDesc.trim().equals("")) {	// If the task description is empty, send error message / alert back to client to enter a task description
				System.out.println("Please enter a task description.");
				out.println("{\n" + 
						"	\"emptyTask\": \"false\"\n" + 
						"}");
			}
		}
		else if (request.getParameter("type").equals("btnDeleteAll")) {	// If the button clicked is the delete all button, delete all tasks from list
		    tdl.deleteAllTasks();
		    out.println("{\n" + 
					"	\"deleteAll\": \"success\"\n" + 
					"}");
		}
		else if (request.getParameter("type").equals("btnDeleteTask")) {	// If the button clicked is to delete a specific task, get row ID and then delete that specific one
			String strId = request.getParameter("rowId").trim();
			Integer id = Integer.parseInt(strId);
		    tdl.deleteTask(id);
		    out.println("{\n" + 
					"	\"deleteTask\": \"success\"\n" + 
					"}");
		}
		else if (request.getParameter("type").equals("btnSubmitEditTask")) {	// Submission of edit task
			String strId = request.getParameter("rowId");
			//System.out.println("ID: " + strId);
			Integer id = Integer.parseInt(strId);
			String taskDesc = request.getParameter("txbEditTaskDes").trim();		// Get parameters from textboxes and dropdown lists
			String details = request.getParameter("txtAreaEditDetails").trim();
			String hour = request.getParameter("ddlEditHour");
			String minute = request.getParameter("ddlEditMinute");
			String amPm = request.getParameter("ddlEditAmPm");
			String dueTime = hour + ":" + minute + " " + amPm;
			String dueDate = request.getParameter("txbEditDateDue");
			boolean validDate = tdl.isValidDate(dueDate);
			if (validDate == true && !taskDesc.trim().equals("")) {
				boolean editSuccess = tdl.editTask(id, taskDesc, details, dueDate, dueTime);
				if (editSuccess == true) {	// If the edit was successful, check if the new time or date makes it overdue
					ToDo t = tdl.getToDo(id);
					boolean overdue = false;
					if (t != null) {
						overdue = t.isOverdue;
					}
					//System.out.println(tdl.showNotCompleted());
					out.println("{\n" + 
							"	\"successEdit\": \"true\"," + 
							"	\"overdue\": \"" + overdue + "\"" +
							"}");
				}
				else {
					out.println("{\n" + 
							"	\"successEdit\": \"false\"\n" + 
							"}");
				}
			}
			else if (validDate == false) {
				System.out.println("Invalid date.");
				out.println("{\n" + 
						"	\"validDate\": \"false\"\n" + 
						"}");
			}
			else if (taskDesc.trim().equals("")) {
				System.out.println("Please enter a task description.");
				out.println("{\n" + 
						"	\"emptyTask\": \"false\"\n" + 
						"}");
			}
		}
		else if (request.getParameter("type").equals("btnViewDetails")) {		// If viewing details, get JSON string for client
			String strId = request.getParameter("rowId");
			Integer id = Integer.parseInt(strId);
			String resultString = tdl.escapeQuotes(tdl.getDetails(id));
			out.println("{\n" + 
					"	\"details\": \"" + resultString + "\"\n" + 
					"}");
		}
		else if (request.getParameter("type").equals("btnComplete")) {	// If completing tasks...
			String dataStr = request.getParameter("dataStr");
			JSONObject obj = new JSONObject(dataStr);
		    JSONArray checkedData = obj.getJSONArray("checkedData");		// Get JSON string and look at checked data
		    for (int i = 0; i < checkedData.length(); i++) {				
		      JSONObject task = checkedData.getJSONObject(i);
		      String strId = task.getString("id");
		      Integer id = Integer.parseInt(strId);
		      boolean completeStatus = Boolean.parseBoolean(task.getString("checked"));	// Find if task is complete or not
		      tdl.completeTask(id, completeStatus);		// Mark task as complete or not complete
		    }
		    if (request.getParameter("viewMode").equals("showAll")) {		// If the view mode is "show all", then show the whole list including ones marked complete
		    		String tdlResponse = tdl.showAll();
		        response.getWriter().print(tdlResponse);
		    }
		    else {	// Else show not completed tasks
		    		String tdlResponse = tdl.showNotCompleted();
		        response.getWriter().print(tdlResponse);
		    }
		}
		else if (request.getParameter("type").equals("btnCompleteAll")) {		// If button clicked is "complete all", then mark all tasks as complete
			tdl.completeAllTasks();
		    if (request.getParameter("viewMode").equals("showAll")) {		// View mode is "show all"
		    		String tdlResponse = tdl.showAll();
		        response.getWriter().print(tdlResponse);
		    }
		    else {
		    		String tdlResponse = tdl.showNotCompleted();	// View mode is to only show not completed tasks
		        response.getWriter().print(tdlResponse);
		    }
		}
		else if (request.getParameter("type").equals("btnShowAll")) {		// If button clicked is "show all", then show all tasks
		    		String tdlResponse = tdl.showAll();
		        response.getWriter().print(tdlResponse);
		}
		else if (request.getParameter("type").equals("btnShowNotCompleted")) {	// If button clicked is "show not completed", then show only non completed tasks
	    		String tdlResponse = tdl.showNotCompleted();
	        response.getWriter().print(tdlResponse);
		}
		else {	// Case that should never happen
			System.out.println("ERROR");
		}
		out.close();
	}

}
