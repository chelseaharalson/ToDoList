package model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class ToDoListTest {
	@Test
	public void testIsValidDate1() {
		String input = "01/02/2017";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), true);
	}
	
	@Test
	public void testIsValidDate2() {
		String input = "01-02-2017";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate3() {
		String input = "2/2/2017";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate4() {
		String input = "02/30/2017";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate5() {
		String input = "02/29/2019";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate6() {
		String input = "02/29/2020";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), true);
	}
	
	@Test
	public void testIsValidDate7() {
		String input = "Chelsea";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate8() {
		String input = "2/14/2017";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate9() {
		String input = "05/1/2017";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate10() {
		String input = "08/19/20";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate11() {
		String input = "35/19/2022";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testIsValidDate12() {
		String input = "12/40/2045";
		ToDoList tdl = new ToDoList();
		assertEquals(tdl.isValidDate(input), false);
	}
	
	@Test
	public void testAddTask1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		ToDo lastToDo = tList.get(tList.size()-1);
		assertEquals(lastToDo.taskDescr, "Task1");
		assertEquals(lastToDo.detail, "Detail1");
		assertEquals(lastToDo.dateDue, "02/19/2018");
		assertEquals(lastToDo.timeDue, "3:33 AM");
	}
	
	@Test
	public void testAddTask2() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		ToDo lastToDo = tList.get(tList.size()-1);
		assertEquals(lastToDo.taskDescr, "Task2");
		assertEquals(lastToDo.detail, "Detail2");
		assertEquals(lastToDo.dateDue, "01/29/2017");
		assertEquals(lastToDo.timeDue, "2:00 PM");
	}
	
	@Test
	public void testDeleteAllTasks1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		tdl.deleteAllTasks();
		assertEquals(tList.size(), 0);
	}

	@Test
	public void testDeleteTask1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id = tList.get(tList.size()-1).taskID;
		Integer listSize = tList.size();
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		tdl.deleteTask(id);
		Integer newListSize = tdl.getToDoList().size();
		assertEquals(newListSize, listSize);
		assertEquals(tdl.getToDo(id), null);
	}

	@Test
	public void testEditTask1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		tdl.editTask(id, "EditTask1", "EditTask1Detail", "02/19/2018", "1:00 PM");
		assertEquals(tdl.getToDo(id).taskDescr, "EditTask1");
		assertEquals(tdl.getToDo(id).detail, "EditTask1Detail");
		assertEquals(tdl.getToDo(id).dateDue, "02/19/2018");
		assertEquals(tdl.getToDo(id).timeDue, "1:00 PM");
	}
	
	@Test
	public void testEditTask2() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		Integer id = tList.get(tList.size()-1).taskID;
		tdl.editTask(id, "EditTask2", "EditTask2Detail", "02/19/2018", "2:00 PM");
		assertEquals(tdl.getToDo(id).taskDescr, "EditTask2");
		assertEquals(tdl.getToDo(id).detail, "EditTask2Detail");
		assertEquals(tdl.getToDo(id).dateDue, "02/19/2018");
		assertEquals(tdl.getToDo(id).timeDue, "2:00 PM");
	}
	
	@Test
	public void testEditTask3() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		Integer id = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task3", "Detail3", "12/11/2020", "4:22 PM");
		tdl.editTask(id, "EditTask2", "EditTask2Detail", "02/19/2018", "2:00 PM");
		assertEquals(tdl.getToDo(id).taskDescr, "EditTask2");
		assertEquals(tdl.getToDo(id).detail, "EditTask2Detail");
		assertEquals(tdl.getToDo(id).dateDue, "02/19/2018");
		assertEquals(tdl.getToDo(id).timeDue, "2:00 PM");
	}

	@Test
	public void testCompleteTask1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		tdl.completeTask(id, false);
		assertEquals(tdl.getToDo(id).isComplete, false);
		tdl.completeTask(id, true);
		assertEquals(tdl.getToDo(id).isComplete, true);
	}
	
	@Test
	public void testCompleteTask2() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		tdl.completeTask(id, false);
		assertEquals(tdl.getToDo(id).isComplete, false);
		tdl.completeTask(id, false);
		assertEquals(tdl.getToDo(id).isComplete, false);
	}
	
	@Test
	public void testCompleteTask3() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		tdl.completeTask(id, true);
		assertEquals(tdl.getToDo(id).isComplete, true);
		tdl.completeTask(id, true);
		assertEquals(tdl.getToDo(id).isComplete, true);
	}

	@Test
	public void testCompleteAllTasks1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		tdl.addTask("Task3", "Detail3", "01/29/2017", "2:50 PM");
		tdl.completeAllTasks();
		ArrayList<ToDo> tList = tdl.getToDoList();
		for (int i = 0; i < tList.size(); i++) {
			assertEquals(tList.get(i).isComplete, true);
		}
	}

	@Test
	public void testShowAll1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		tdl.addTask("Task3", "Detail3", "01/29/2017", "2:50 PM");
		String s = tdl.showAll();
		assertEquals("{\"data\":[{\"id\":\"0\",\"taskDescr\":\"Math Homework 1\",\"detail\":\"Calculus problems 1-5\",\"dateDue\":\"05/22/2017\",\"timeDue\":\"4:22 AM\",\"isComplete\":\"false\",\"isOverdue\":\"true\"},{\"id\":\"1\",\"taskDescr\":\"Programming Assignment 1\",\"detail\":\"Credit card validation\",\"dateDue\":\"09/25/2017\",\"timeDue\":\"4:00 PM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"2\",\"taskDescr\":\"JUNIT tests\",\"detail\":\"\",\"dateDue\":\"09/25/2017\",\"timeDue\":\"6:00 AM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"3\",\"taskDescr\":\"Do laundry\",\"detail\":\"$4.00\",\"dateDue\":\"09/30/2017\",\"timeDue\":\"3:00 PM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"4\",\"taskDescr\":\"Get birthday present for brother\",\"detail\":\"Games\",\"dateDue\":\"04/24/2018\",\"timeDue\":\"9:00 AM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"5\",\"taskDescr\":\"Task1\",\"detail\":\"Detail1\",\"dateDue\":\"02/19/2018\",\"timeDue\":\"3:33 AM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"6\",\"taskDescr\":\"Task2\",\"detail\":\"Detail2\",\"dateDue\":\"01/29/2017\",\"timeDue\":\"2:00 PM\",\"isComplete\":\"false\",\"isOverdue\":\"true\"},{\"id\":\"7\",\"taskDescr\":\"Task3\",\"detail\":\"Detail3\",\"dateDue\":\"01/29/2017\",\"timeDue\":\"2:50 PM\",\"isComplete\":\"false\",\"isOverdue\":\"true\"}]}", s);
	}
	
	@Test
	public void testShowAll2() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id1 = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		Integer id2 = tList.get(tList.size()-1).taskID;
		tdl.completeTask(id1, true);
		tdl.completeTask(id2, true);
		tdl.addTask("Task3", "Detail3", "01/29/2017", "2:50 PM");
		String s = tdl.showAll();
		assertEquals("{\"data\":[{\"id\":\"0\",\"taskDescr\":\"Math Homework 1\",\"detail\":\"Calculus problems 1-5\",\"dateDue\":\"05/22/2017\",\"timeDue\":\"4:22 AM\",\"isComplete\":\"false\",\"isOverdue\":\"true\"},{\"id\":\"1\",\"taskDescr\":\"Programming Assignment 1\",\"detail\":\"Credit card validation\",\"dateDue\":\"09/25/2017\",\"timeDue\":\"4:00 PM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"2\",\"taskDescr\":\"JUNIT tests\",\"detail\":\"\",\"dateDue\":\"09/25/2017\",\"timeDue\":\"6:00 AM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"3\",\"taskDescr\":\"Do laundry\",\"detail\":\"$4.00\",\"dateDue\":\"09/30/2017\",\"timeDue\":\"3:00 PM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"4\",\"taskDescr\":\"Get birthday present for brother\",\"detail\":\"Games\",\"dateDue\":\"04/24/2018\",\"timeDue\":\"9:00 AM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"5\",\"taskDescr\":\"Task1\",\"detail\":\"Detail1\",\"dateDue\":\"02/19/2018\",\"timeDue\":\"3:33 AM\",\"isComplete\":\"true\",\"isOverdue\":\"false\"},{\"id\":\"6\",\"taskDescr\":\"Task2\",\"detail\":\"Detail2\",\"dateDue\":\"01/29/2017\",\"timeDue\":\"2:00 PM\",\"isComplete\":\"true\",\"isOverdue\":\"true\"},{\"id\":\"7\",\"taskDescr\":\"Task3\",\"detail\":\"Detail3\",\"dateDue\":\"01/29/2017\",\"timeDue\":\"2:50 PM\",\"isComplete\":\"false\",\"isOverdue\":\"true\"}]}", s);
	}

	@Test
	public void testShowNotCompleted1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id1 = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		Integer id2 = tList.get(tList.size()-1).taskID;
		tdl.completeTask(id1, true);
		tdl.completeTask(id2, true);
		String nc = tdl.showNotCompleted();
		assertEquals("{\"data\":[{\"id\":\"0\",\"taskDescr\":\"Math Homework 1\",\"detail\":\"Calculus problems 1-5\",\"dateDue\":\"05/22/2017\",\"timeDue\":\"4:22 AM\",\"isComplete\":\"false\",\"isOverdue\":\"true\"},{\"id\":\"1\",\"taskDescr\":\"Programming Assignment 1\",\"detail\":\"Credit card validation\",\"dateDue\":\"09/25/2017\",\"timeDue\":\"4:00 PM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"2\",\"taskDescr\":\"JUNIT tests\",\"detail\":\"\",\"dateDue\":\"09/25/2017\",\"timeDue\":\"6:00 AM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"3\",\"taskDescr\":\"Do laundry\",\"detail\":\"$4.00\",\"dateDue\":\"09/30/2017\",\"timeDue\":\"3:00 PM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"},{\"id\":\"4\",\"taskDescr\":\"Get birthday present for brother\",\"detail\":\"Games\",\"dateDue\":\"04/24/2018\",\"timeDue\":\"9:00 AM\",\"isComplete\":\"false\",\"isOverdue\":\"false\"}]}", nc);
	}

	@Test
	public void getDetails1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		Integer id1 = tList.get(tList.size()-1).taskID;
		tdl.addTask("Task2", "Detail2", "01/29/2017", "2:00 PM");
		Integer id2 = tList.get(tList.size()-1).taskID;
		String detail1 = tdl.getDetails(id1);
		String detail2 = tdl.getDetails(id2);
		assertEquals("Detail1", detail1);
		assertEquals("Detail2", detail2);
	}
	
	@Test
	public void checkOverdue1() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "02/19/2018", "3:33 AM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		ToDo lastToDo = tList.get(tList.size()-1);
		assertEquals(lastToDo.isOverdue, false);
	}
	
	@Test
	public void checkOverdue2() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "10/19/2015", "1:33 PM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		ToDo lastToDo = tList.get(tList.size()-1);
		assertEquals(lastToDo.isOverdue, true);
	}
	
	@Test
	public void checkOverdue3() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "09/24/2017", "1:33 PM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		ToDo lastToDo = tList.get(tList.size()-1);
		assertEquals(lastToDo.isOverdue, true);
	}
	
	@Test
	public void checkOverdue4() {
		ToDoList tdl = new ToDoList();
		tdl.addTask("Task1", "Detail1", "09/24/2019", "9:33 PM");
		ArrayList<ToDo> tList = tdl.getToDoList();
		ToDo lastToDo = tList.get(tList.size()-1);
		assertEquals(lastToDo.isOverdue, false);
	}

}
