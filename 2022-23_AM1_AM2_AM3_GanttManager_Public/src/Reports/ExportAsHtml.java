package Reports;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import dom.Task;


public class ExportAsHtml implements IReportFile {
	private String[] header;
	private ArrayList<Task> taskList;

	@Override
	public int createReportFile(String path) {

		try {
			FileWriter myWriter = new FileWriter(path);

			myWriter.write("<!doctype html>\n");
			myWriter.write("<html>\n");
			myWriter.write("<head>\n");
			myWriter.write("<meta http-equiv=\"Content-Type\" content\"text/html; charset=windows-1253\">\n");
			myWriter.write("<title>Gantt Project Data</title>\n");
			myWriter.write("</head>\n");
			myWriter.write("<body>\n\n");
			myWriter.write("<table>\n");
			myWriter.write("<tr>\n");

			String headerTokens = "";
			for (String token : this.header) {
				headerTokens = headerTokens + "<td>" + token + "</td>" + "\t";
			}
			myWriter.write(headerTokens);
			myWriter.write("\n\n");

			for (Task task : this.taskList) {
				myWriter.write("<tr>\n");
				String[] taskArray = task.toString().split("\t");
				String taskString = "";
				for (String token : taskArray) {
					taskString = taskString + "<td>" + token + "</td>" + "\t";
				}
				myWriter.write(taskString);
				myWriter.write("\n\n");

				for (Task subtask : task.getListSubTasks()) {
					myWriter.write("<tr>\n");
					String[] subTaskArray = subtask.toString().split("\t");
					String subtaskString = "";
					for (String token : subTaskArray) {
						subtaskString = subtaskString + "<td>" + token + "</td>" + "\t";
					}
					myWriter.write(subtaskString);
					myWriter.write("\n\n");

				}

			}

			myWriter.write("</table></body>\n");
			myWriter.write("</html>");

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
