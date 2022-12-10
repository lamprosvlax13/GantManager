package JunitTests;
import static org.junit.Assert.assertEquals;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;
import backend.MainController;
import backend.MainControllerFactory;
import backend.ReportType;
import dom.Task;

import dom2app.SimpleTableModel;

public class TestMainControllerRainScenarios {

	
	
	
	private ArrayList<Task> taskList =new ArrayList<Task>();
	private List<String[]> data= new ArrayList<String[]>();
	private SimpleTableModel simpleTableModel;
;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	

	
	
	@Test
	public final void testLoadRain() {
		
		String inputFileName = null;
		String delimiter="";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		taskList=mainController.getTopTaskList();
		assertEquals(0,taskList.size());
		
		Integer [] sumSubtasksPerTopTask ={};
		Integer [] numberOfSubTaskPerTopTask=new Integer[0];
		extracted(sumSubtasksPerTopTask, numberOfSubTaskPerTopTask);
	
		SimpleTableModel simple = mainController.getSimpleTableModel();
		String[] columnsNames= {"TaskId","TaskText","MamaId","Start","End","Cost"};
		extracted(simple, columnsNames);
		String path ="";
		ArrayList<String []> expected =new ArrayList<String []>();
		
		initializeExpectedData(path,expected);
		data=mainController.getData();
		assertEquals(expected,data);
	
	}

	
	

	@Test
	public final void testGetTasksByPrefixRain() {
		String inputFileName = "";
		String delimiter = "";
		MainControllerFactory factory = new MainControllerFactory();
		MainController mainController = (MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);

		String prefix = null;
		simpleTableModel = mainController.getTasksByPrefix(prefix);
		List<String[]> actualData = simpleTableModel.getData();
		List<String[]> expected = new ArrayList<String[]>();
		assertEquals(expected, actualData);

		inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		delimiter = "\t";
		factory = new MainControllerFactory();
		mainController = (MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);

		prefix = "hrthrthjrtjtr";
		simpleTableModel = mainController.getTasksByPrefix(prefix);
		actualData = simpleTableModel.getData();
		expected = new ArrayList<String[]>();
		assertEquals(expected, actualData);

	}
	
	
	@Test
	public final void testGetTasksByIdRain() {
		String inputFileName = "";
		String delimiter = "";
		MainControllerFactory factory = new MainControllerFactory();
		MainController mainController = (MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);

		int id= 84848;
		simpleTableModel = mainController.getTaskById(id);
		List<String[]> actualData = simpleTableModel.getData();
		List<String[]> expected = new ArrayList<String[]>();
		assertEquals(expected, actualData);

		

	}
	
	
	
	@Test
	public final void tastcreateReportsRain() throws IOException {
		String inputFileName = "";
		String delimiter="";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		
		String outputFileName = null;
		ReportType type =null;
		int status=mainController.createReport(outputFileName,type);
		assertEquals(0,status);

		
	}
	
	
	
	
	
		
	
	
	

	@SuppressWarnings("deprecation")
	private void extracted(Integer[] sumSubtasksPerTopTask, Integer[] numberOfSubTaskPerTopTask) {
		assertEquals(sumSubtasksPerTopTask,numberOfSubTaskPerTopTask);
	}
	
	@SuppressWarnings("deprecation")
	private void extracted(SimpleTableModel simple, String[] columnsNames) {
		// TODO Auto-generated method stub
		assertEquals(columnsNames,simple.getColumnNames());
	}




	private void initializeExpectedData(String path, ArrayList<String[]> expected) {
		try {
			File file = new File(path);
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				expected.add(line.split("\t"));
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("problems with pathfile");
			e.printStackTrace();
		}
	}
	
	
	
	
}
