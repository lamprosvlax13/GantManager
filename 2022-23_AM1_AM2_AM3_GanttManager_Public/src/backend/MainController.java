package backend;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Parser.ILoader;
import Parser.IloaderFactory;
import Reports.IReportFile;

import Reports.ReportFileFactory;

import dom.Task;

import dom2app.SimpleTableModel;

public class MainController implements IMainController {

	

	private ArrayList<Task> topTaskList = new ArrayList<Task>();
	private List<String[]> data = new ArrayList<String[]>();
	private SimpleTableModel simpleTableModel;
	String[] columnsNames = { "TaskId", "TaskText", "MamaId", "Start", "End", "Cost" };

	
	
	
	
	
	//public MainController() {
	//	super();
	//	this.simpleTableModel = new SimpleTableModel("", "", columnsNames, data);
	//}
	
	
	
	
	@Override
	public SimpleTableModel load(String fileName, String delimiter) {
		// TODO Auto-generated method stub
		this.data.clear();
		IloaderFactory factory = new IloaderFactory();
		ILoader loader = factory.createLoadFactory(fileName);
		loader.load(fileName, delimiter);
		initializeData(delimiter, loader);
		String[] headerLine = loader.getHeaderLine();
		if (headerLine != null) {
			columnsNames = headerLine;
		}
		this.simpleTableModel = new SimpleTableModel(fileName, fileName, columnsNames, this.data);
		return this.simpleTableModel;

	}

	private void initializeData(String delimiter, ILoader loader) {

		this.topTaskList = loader.getTaskList();
		for (Task task : this.topTaskList) {
			String[] taskAr = task.toString().split(delimiter);
			this.data.add(taskAr);
			for (Task subtask : task.getListSubTasks()) {
				String[] subtaskAr = subtask.toString().split(delimiter);
				this.data.add(subtaskAr);
			}
		}

	}

	@Override
	public SimpleTableModel getTasksByPrefix(String prefix) {
		int positionTaskText = 1;
		List<String[]> newData = new ArrayList<String[]>();
		for (String[] tasksPerLine : this.data) {
			if (tasksPerLine[positionTaskText].startsWith(prefix)) {
				newData.add(tasksPerLine);
			}
		}

		if (this.simpleTableModel == null) {
			this.simpleTableModel = new SimpleTableModel("", "", columnsNames, newData);
			String message = "fortwse kati prwta kai meta 4a3e me prefix: " + prefix;
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
			return this.simpleTableModel;
		}

		if (newData.size() == 0) {
			String message = "Not FOUND with prefix: " + prefix;
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
		}

		SimpleTableModel simpleTableModel = new SimpleTableModel(this.simpleTableModel.getName(),
				this.simpleTableModel.getPrjName(), this.simpleTableModel.getColumnNames(), newData);

		return simpleTableModel;
	}

	@Override
	public SimpleTableModel getTaskById(int id) {

		int positionTaskId = 0;
		List<String[]> newData = new ArrayList<String[]>();
		for (String[] tasksPerLine : data) {
			if (Integer.parseInt(tasksPerLine[positionTaskId]) == id) {
				newData.add(tasksPerLine);
			}
		}
		
		if (this.simpleTableModel == null) {
			String message = "fortwse kati prwta kai meta 4a3e me id: " + id;
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
			this.simpleTableModel = new SimpleTableModel("", "", columnsNames, newData);
			return this.simpleTableModel;
		}
		if (newData.size() == 0) {
			String message = "Not FOUND with Id: " + id;
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
		}

		SimpleTableModel simpleTableModel = new SimpleTableModel(this.simpleTableModel.getName(),
				this.simpleTableModel.getPrjName(), this.simpleTableModel.getColumnNames(), newData);

		return simpleTableModel;

	}

	@Override
	public SimpleTableModel getTopLevelTasks() {

		int positionMamaId = 2;
		List<String[]> newData = new ArrayList<String[]>();
		for (String[] tasksPerLine : data) {
			if (Integer.parseInt(tasksPerLine[positionMamaId]) == 0) {
				newData.add(tasksPerLine);
			}
		}
		if (this.simpleTableModel == null) {
			String message = "fortwse kati prwta kai meta 4a3e TopTasks: ";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
			this.simpleTableModel = new SimpleTableModel("", "", columnsNames, newData);
			return this.simpleTableModel;
		}
		SimpleTableModel simpleTableModel = new SimpleTableModel(this.simpleTableModel.getName(),
				this.simpleTableModel.getPrjName(), this.simpleTableModel.getColumnNames(), newData);

		return simpleTableModel;

	}

	@Override
	public int createReport(String path, ReportType type) {

		if (simpleTableModel != null) {
			ReportFileFactory factory = new ReportFileFactory();
			IReportFile reportFile = factory.createReportFile(type);
			reportFile.setHeader(this.simpleTableModel.getColumnNames());
			reportFile.setTaskList(topTaskList);
			int status = reportFile.createReportFile(path);
			return status;
		}
		String message = "den mporeis na apo9hkeuseis xwris na fortwseis";
		JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
		return -1;
	}

	// Tests
	public ArrayList<Task> getTopTaskList() {
		return topTaskList;
	}

	public List<String[]> getData() {
		return data;
	}

	public SimpleTableModel getSimpleTableModel() {
		return simpleTableModel;
	}

}
