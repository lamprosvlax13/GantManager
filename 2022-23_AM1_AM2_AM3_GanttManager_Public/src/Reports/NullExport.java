package Reports;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import dom.Task;


public class NullExport implements IReportFile {

	private String[] header;
	private ArrayList<Task> taskList;

	@Override
	public int createReportFile(String path) {
		// TODO Auto-generated method stub

		String message = "wrong Parameters in Reports ";
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);

		return 0;
	}

	@Override
	public void setHeader(String[] header) {
		this.header = header;
	}

	@Override
	public void setTaskList(ArrayList<Task> taskList) {
		// TODO Auto-generated method stub
		this.taskList = taskList;
	}

}
