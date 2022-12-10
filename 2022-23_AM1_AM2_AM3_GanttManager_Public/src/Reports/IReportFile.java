package Reports;

import java.util.ArrayList;

import dom.Task;


public interface IReportFile {

	
	public int createReportFile(String path);
	public void setHeader(String[] columnNames);
	public void setTaskList(ArrayList<Task> taskList);

	
}
