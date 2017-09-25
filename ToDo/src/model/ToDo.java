package model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;

/**
 *
 * @author chelseametcalf
 */
public class ToDo {
    public Integer taskID;
    public String taskDescr;
    public String detail;
    public String dateDue;
    public String timeDue;
    public LocalDate currentDate;
    public LocalTime currentTime;
    public boolean isComplete;
    public boolean isOverdue;

    public ToDo(Integer pTaskID, String pTaskDescr, String pDetail, String pDateDue, String pTimeDue) {
        taskID = pTaskID;
        taskDescr = pTaskDescr;
        detail = pDetail;
        dateDue = pDateDue;
        timeDue = pTimeDue;
        currentDate = LocalDate.now();
        currentTime = LocalTime.now();
        isComplete = false;
        
        checkOverdue();
    }
    
    public void checkOverdue() {
        // Setting if the task is overdue based on date and time
		try {
			Date dateDueObj = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateDue);
			LocalDate localDateDue = new java.sql.Date(dateDueObj.getTime()).toLocalDate();
	        Date timeDueObj = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).parse(timeDue);
	        Instant instant = Instant.ofEpochMilli(timeDueObj.getTime());
	        LocalTime localTimeDue = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
	        /*System.out.println("currentDate: " + currentDate);
	        System.out.println("currentTimeDue: " + currentTime);
	        System.out.println("localDateDue: " + localDateDue);
	        System.out.println("localTimeDue: " + localTimeDue);*/
	        if (currentDate.isEqual(localDateDue) && currentTime.isAfter(localTimeDue)) {
	            isOverdue = true;
	            //System.out.println("Task is overdue");
	        }
	        else if (currentDate.isAfter(localDateDue)) {
	            isOverdue = true;
	            //System.out.println("Task is overdue");
	        }
	        else {
	        		isOverdue = false;
	        		//System.out.println("Task is NOT overdue");
	        }
		} catch (ParseException e) {
			//e.printStackTrace();
			System.out.println("Parse exception");
		}
    }
}
