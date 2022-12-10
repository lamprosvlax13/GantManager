package Parser;


import java.io.File;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Scanner;
import java.util.regex.Pattern;

import dom.SubTask;
import dom.Task;
import dom.TopTask;



public class LoaderTsvFile implements ILoader{

	private ArrayList<Task> taskList = new ArrayList<Task>();
	private ArrayList<Task> subtaskList = new ArrayList<Task>();
	private String[] headerLine;
	private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	@Override
	public void load(String path, String delimiter) {
		// TODO Auto-generated method stub
		ArrayList<String> loadedLines = new ArrayList<String>();
		try {
			File file = new File(path);
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				loadedLines.add(line);

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		concreteItemTask(delimiter, loadedLines);
		Collections.sort(subtaskList, SubTask.startSort);
		findSubTaskPerTask();
		Collections.sort(taskList, Task.startSort);
		
	}

	private void findSubTaskPerTask() {

		for (Task task : taskList) {
			for (Task subtask : subtaskList) {
			
				if (task.getTaskId().equals(subtask.getMamaId())) {
					task.addSubtask(subtask);
					
					
				}
			}
			
			initializeTaskFields( task);
		}
	}


	private void initializeTaskFields(Task task) {
		( task).findStart();
		( task).findStart();
		( task).findEnd();
		( task).findCost();
	}

	private void concreteItemTask(String delimiter, ArrayList<String> loadedLines) {

		for (String line : loadedLines) {
			String[] tokensInLine = line.split(delimiter);
			int positionTaskId = 0;
			int positionTaskText = 1;
			int positionMamaId = 2;
			int positionStart = 3;
			int positionEnd = 4;
			int positionCost = 5;

			if (isTask(tokensInLine)) {

				int taskId = Integer.parseInt(tokensInLine[positionTaskId]);
				String taskText = tokensInLine[positionTaskText];
				int mamaId = Integer.parseInt(tokensInLine[positionMamaId]);
				int fullNumberOfFields = 6;
				if (tokensInLine.length == fullNumberOfFields) {
					String start = tokensInLine[positionStart];
					String end = tokensInLine[positionEnd];
					double cost =Double.parseDouble(tokensInLine[positionCost]);

					Task task = new TopTask(taskId, taskText, mamaId, start, end, cost);
					taskList.add(task);

				} else {

					Task task = new TopTask(taskId, taskText);
					taskList.add( task);
				}

			} else if (isSubTask(tokensInLine)) {
				int taskId = Integer.parseInt(tokensInLine[positionTaskId]);
				String taskText = tokensInLine[positionTaskText];
				int mamaId = Integer.parseInt(tokensInLine[positionMamaId]);
				String start = tokensInLine[positionStart];
				String end = tokensInLine[positionEnd];
				double cost = Double.parseDouble(tokensInLine[positionCost]);//tokensInLine[positionCost];
				Task subTask = new SubTask(taskId, taskText, mamaId, start, end, cost);
				subtaskList.add(subTask);
			} else if (haveHeader(tokensInLine)) {
				this.headerLine = tokensInLine;
				System.out.println("exw perigrafh");

			}
		}

	}

	@Override
	public String[] getHeaderLine() {
		return headerLine;
	}

	@Override
	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	private boolean isSubTask(String[] tokensInLine) {
		if (pattern.matcher(tokensInLine[0]).matches()) {
			for (String token : tokensInLine) {
				if (token.equals("")) {
					return false;
				}
			}
			return true;
		}
		return false;

	}

	private boolean isTask(String[] tokensInLine) {
		try {

			if (pattern.matcher(tokensInLine[2]).matches()) {

				int mamaId = Integer.parseInt(tokensInLine[2]);
				if (mamaId == 0) {
					return true;
				} else {
					return false;
				}

			}
			return false;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	private boolean haveHeader(String[] tokensInLine) {
		for (String token : tokensInLine) {
			try {
				Integer.parseInt(token);
				return false;
			} catch (NumberFormatException e) {
				return true;
			}
		}
		return false;
	}

}
