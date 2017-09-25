package model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author chelseametcalf
 */
public class ToDoList {
    private ArrayList<ToDo> toDoList;
    private Integer taskCount;
    
    public ToDoList() {
        toDoList = new ArrayList<ToDo>();
        taskCount = 0;
        
        addTask("Math Homework 1", "Calculus problems 1-52", "05/22/2017", "2:22 PM");
        addTask("Study for bioinformatics quiz", "Chapter 2", "10/25/2017", "6:00 AM");
        addTask("Programming Assignment 1", "Java", "09/20/2017", "4:30 PM");
        addTask("Do laundry", "$4.00", "09/30/2017", "3:00 PM");
        addTask("Get birthday present for brother", "Games", "04/24/2018", "9:00 AM");
        addTask("Buy plane ticket", "$$$", "12/20/2017", "1:00 AM");
    }
    
    // Return a specific ToDo object
    public ToDo getToDo(Integer pTaskID) {
    	   for (int i = 0; i < toDoList.size(); i++) {
            if (toDoList.get(i).taskID == pTaskID) {
                return toDoList.get(i);
            }
        }
    	   return null;
    }
    
    // Add task to arraylist of ToDo objects
    public String addTask(String pTaskDescr, String pDetail, String pDateDue, String pTimeDue) {
        Integer pTaskID = taskCount;
        taskCount++;        // Increment taskID so each new task has a unique ID
        toDoList.add(new ToDo(pTaskID, pTaskDescr, pDetail, pDateDue, pTimeDue));
        //System.out.println("Added new task: " + pTaskDescr);
        String resultString = toDoObjToAjaxString(toDoList.get(toDoList.size()-1));
        return resultString;
    }
    
    // Delete task
    public void deleteTask(Integer pTaskID) {
        for (int i = 0; i < toDoList.size(); i++) {
            if (toDoList.get(i).taskID == pTaskID) {
            		//System.out.println("Deleted task: " + toDoList.get(i).taskDescr);
                toDoList.remove(i);
                return;
            }
        }
    }
    
    // Edit task
    public boolean editTask(Integer pTaskID, String pTaskDescr, String pDetail, String pDateDue, String pTimeDue) {
        for (int i = 0; i < toDoList.size(); i++) {
            if (toDoList.get(i).taskID == pTaskID) {
                toDoList.get(i).taskDescr = pTaskDescr;
                toDoList.get(i).detail = pDetail;
                toDoList.get(i).dateDue = pDateDue;
                toDoList.get(i).timeDue = pTimeDue;
                toDoList.get(i).checkOverdue();	// Check to see if new time is overdue
                //System.out.println("Edited task: " + toDoList.get(i).taskDescr);
                return true;
            }
        }
        return false;
    }
    
    // Mark a task as completed
    public void completeTask(Integer pTaskID, boolean completeStatus) {
        for (int i = 0; i < toDoList.size(); i++) {
            if (toDoList.get(i).taskID == pTaskID) {
                toDoList.get(i).isComplete = completeStatus;
                //System.out.println("Marked task: " + toDoList.get(i).taskDescr + " as complete: " + completeStatus);
                return;
            }
        }
    }
    
    // Delete all tasks
    public void deleteAllTasks() {
        toDoList.clear();
        taskCount = 0;
        //System.out.println("Deleted all tasks.");
    }
    
    // Mark all tasks as completed
    public void completeAllTasks() {
        for (int i = 0; i < toDoList.size(); i++) {
            toDoList.get(i).isComplete = true;
        }
        //System.out.println("Completed all tasks.");
    }
    
    // Show all tasks, even completed ones
    public String showAll() {
	    	ArrayList<ToDo> allTaskList = new ArrayList<ToDo>();
        for (int i = 0; i < toDoList.size(); i++) {
        		toDoList.get(i).checkOverdue();
            allTaskList.add(toDoList.get(i));
        }
        
        // JSON string to send to client
        String resultString = "{\"data\":[";
        for (int i = 0; i < allTaskList.size(); i++) {
        		resultString += toDoObjToAjaxString(allTaskList.get(i));
        		if (i+1 < allTaskList.size()) {
        			resultString += ",";
        		}
        }
        resultString += "]}";
        //System.out.println(resultString);
        //System.out.println("Showing all tasks.");
        return resultString;
    }
    
    // Show only tasks that were not completed
    public String showNotCompleted() {
        ArrayList<ToDo> notCompletedList = new ArrayList<ToDo>();
        for (int i = 0; i < toDoList.size(); i++) {
        		toDoList.get(i).checkOverdue();
            if (toDoList.get(i).isComplete == false) {
                notCompletedList.add(toDoList.get(i));
            }
        }

        // JSON string to send to client
        String resultString = "{\"data\":[";
        for (int i = 0; i < notCompletedList.size(); i++) {
        		resultString += toDoObjToAjaxString(notCompletedList.get(i));
        		if (i+1 < notCompletedList.size()) {
        			resultString += ",";
        		}
        }
        resultString += "]}";
        //System.out.println(resultString);
        //System.out.println("Showing not completed tasks.");
        return resultString;
    }
    
    // Convert a specific ToDo object to a JSON string
    public String toDoObjToAjaxString(ToDo td) {
	    	String resultString = "";
    		resultString += "{\"id\":\"" + td.taskID + "\",\"taskDescr\":\"" + escapeQuotes(td.taskDescr) 
    				+ "\",\"detail\":\"" + escapeQuotes(td.detail) + "\",\"dateDue\":\"" + td.dateDue 
    				+ "\",\"timeDue\":\"" + td.timeDue
    				+ "\",\"isComplete\":\"" + td.isComplete + "\",\"isOverdue\":\"" + td.isOverdue 
    				+ "\"}";
	     return resultString;
    }
    
    // Takes care of strings that have quotes (like in the task description or details)
    public String escapeQuotes(String input) {
    		return input.replaceAll("\"", "\\\\\"");
    }
    
    // Retrieve details
    public String getDetails(Integer rowID) {
    	String resultString = "";
    		for (int i = 0; i < toDoList.size(); i++) {
            if (toDoList.get(i).taskID == rowID) {
                resultString = toDoList.get(i).detail;
                //System.out.println("Retrieving detail: " + toDoList.get(i).detail);
                return resultString;
            }
        }
    		return "";
    }
    
    // Check if date entered is a valid date in MM/dd/yyyy format
    public boolean isValidDate(String input) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate;
		try {
			formattedDate = sdf.format(sdf.parse(input));
			if (formattedDate.equals(input)) {
	            return true;
	        } 
	        else {
	            return false;
	        }
		} catch (ParseException e) {
			System.out.println("Invalid date");
			return false;
		}
        
    }
    
    // Returns to do list for JUNIT testing purposes
    public ArrayList<ToDo> getToDoList() {
    		return toDoList;
    }
}
