package Parser;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dom.SubTask;
import dom.Task;


public class NullLoader implements ILoader {

	private ArrayList<Task> taskList = new ArrayList<Task>();
	private ArrayList<SubTask> subtaskList = new ArrayList<SubTask>();
	private String[] headerLine;

	@Override
	public void load(String path, String delimiter) {
		// TODO Auto-generated method stub
		String message = "wrong Parameters in Load ";
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public String[] getHeaderLine() {
		// TODO Auto-generated method stub
		return headerLine;
	}

	@Override
	public ArrayList<Task> getTaskList() {
		// TODO Auto-generated method stub
		return taskList;
	}

}
