package Reports;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import dom.Task;


public class ExportAsMarkDown implements IReportFile {

	private String[] header;
	private ArrayList<Task> taskList;

	@Override
	public int createReportFile(String path) {
		// TODO Auto-generated method stub

		try {
			FileWriter myWriter = new FileWriter(path);

			String headerTokens = "";
			for (String token : this.header) {
				headerTokens = headerTokens + "*" + token + "*" + "\t";
			}
			myWriter.write(headerTokens);
			myWriter.write("\n");

			for (Task task : this.taskList) {

				String[] taskArray = task.toString().split("\t");
				String taskString = "";
				for (String token : taskArray) {
					taskString = taskString + "**" + token + "**" + " ";

				}
				myWriter.write(taskString);
				myWriter.write("\n");

				for (Task subtask : task.getListSubTasks()) {

					String[] subTaskArray = subtask.toString().split("\t");
					String subtaskString = "";
					for (String token : subTaskArray) {
						subtaskString = subtaskString + token + " ";
					}
					myWriter.write(subtaskString);
					myWriter.write("\n");
				}

			}

			myWriter.close();
			return 1;
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return -1;
		}

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
