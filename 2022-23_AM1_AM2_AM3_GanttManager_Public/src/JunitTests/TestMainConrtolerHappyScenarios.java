package JunitTests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import backend.MainController;
import backend.MainControllerFactory;
import backend.ReportType;
import dom.Task;

import dom2app.SimpleTableModel;

public class TestMainConrtolerHappyScenarios {

	private ArrayList<Task> taskList =new ArrayList<Task>();
	private List<String[]> data= new ArrayList<String[]>();
	private SimpleTableModel simpleTableModel;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	

	@Test
	public final void testLoadHappy() {
		
		
		String inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		String delimiter="\t";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		taskList=mainController.getTopTaskList();
		assertEquals(3,taskList.size());
		
		Integer [] TopTaskId= {100,200,300};
		int i=0;
		Integer [] sumSubtasksPerTopTask ={6,2,3};
	
		Integer [] numberOfSubTaskPerTopTask=new Integer[3];
		for(Task topTask :taskList) { 
			
			assertEquals(TopTaskId[i],topTask.getTaskId());
			numberOfSubTaskPerTopTask[i] =topTask.getListSubTasks().size();
			
			assertEquals(sumSubtasksPerTopTask[i],numberOfSubTaskPerTopTask[i]);
			
			assertNotNull(topTask.getTaskText());
			assertNotNull(topTask.getTaskId());
			assertNotNull(topTask.getStart());
			assertNotNull(topTask.getEnd());
			assertNotNull(topTask.getCost());
			assertNotNull(topTask.getMamaId());	
			i++;
		}
		SimpleTableModel simple = mainController.getSimpleTableModel();
		String[] columnsNames= {"TaskId","TaskText","MamaId","Start","End","Cost"};
		extracted(simple, columnsNames);
		String path ="./src/main/resources/TestInput/ExpectedEggs.tsv";
		ArrayList<String []> expected =new ArrayList<String []>();
		
		initializeExpectedData(path,expected);
		data=mainController.getData();
		for(int k=0; k<expected.size(); k++) {
			for(int j=0; j<expected.get(0).length; j++) {
				assertEquals(expected.get(k)[j],data.get(k)[j]);
			}
		}
	
	}


	@SuppressWarnings("deprecation")
	private void extracted(SimpleTableModel simple, String[] columnsNames) {
		assertEquals(columnsNames,simple.getColumnNames());
	}

	
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetTasksByPrefixHappy() {
		String inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		String delimiter="\t";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
	
		String prefix ="P";
		simpleTableModel=mainController.getTasksByPrefix(prefix);
		List<String []> actualData =simpleTableModel.getData();
		List<String []> expected = new ArrayList<String []>();
		String [] task1 = { "100","Prepare Fry","0","1","12","60.0"};
		String [] task2 = { "200","Prepare the bread","0","10","12","20.0"};
		String [] task3 = { "301","Put bread in plate","300","13","13","10.0"};
		String [] task4 = { "302","Put eggs on bread","300","14","14","10.0"};
		expected.add(task1);
		expected.add(task2);
		expected.add(task3);
		expected.add(task4);
		
		assertEquals(task1,actualData.get(0));
		assertEquals(task2,actualData.get(1));
		assertEquals(task3,actualData.get(2));
		assertEquals(task4,actualData.get(3));
	}

	@SuppressWarnings("deprecation")
	@Test
	public final void testGetTasksByIdHappy() {
		String inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		String delimiter="\t";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		
		Integer id =302;
		simpleTableModel=mainController.getTaskById(id);
		List<String []> actualData =simpleTableModel.getData();
		List<String []> expected = new ArrayList<String []>();
		String [] task1 = { "302","Put eggs on bread","300","14","14","10.0"};
		expected.add(task1);
		assertEquals(task1,actualData.get(0));
		
		id =100;
		simpleTableModel=mainController.getTaskById(id);
		actualData =simpleTableModel.getData();
		expected = new ArrayList<String []>();
		String [] array= { "100","Prepare Fry","0","1","12","60.0" };
		expected.add(array);
		assertEquals(array,actualData.get(0));
		
	
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public final void testGetTopLevelTasksHappy() {
		String inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		String delimiter="\t";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		simpleTableModel=mainController.getTopLevelTasks();
		List<String []> actualData =simpleTableModel.getData();
		List<String []> expected = new ArrayList<String []>();
		String [] task1 = { "100","Prepare Fry","0","1","12","60.0"};
		String [] task2 = { "200","Prepare the bread","0","10","12","20.0"};
		String [] task3 = { "300","Serve eggs",	"0",	"13","20",	"30.0"};
		expected.add(task1);
		expected.add(task2);
		expected.add(task3);
		assertEquals(task1,actualData.get(0));
		assertEquals(task2,actualData.get(1));
		assertEquals(task3,actualData.get(2));
		
	}
	
	
	@Test
	public final void testCreateReportTxtHappy() throws IOException {
		String inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		String delimiter="\t";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		
		String outputFileName = "./src/main/resources/TestOutput/EggsOutTest.txt";
		ReportType type =ReportType.TEXT;
		int status=mainController.createReport(outputFileName,type);
		assertEquals(1,status);
		int numberOfWrittedLineseExpected=16;
		int newLineEndOfFile=1;
		int headerLine=1;
		int numberOfWrittedLineseActual =mainController.getData().size()+ headerLine+newLineEndOfFile;
		assertEquals(numberOfWrittedLineseExpected,numberOfWrittedLineseActual);
		
		File outputFile = new File(outputFileName);
		File outputFileRef = new File("./src/main/resources/TestOutput/original.txt");
		
		Boolean localComparison = compareFiles(outputFile, outputFileRef, "testCreateReportTxtHappy()");
		assertEquals(true, localComparison);


		
		
		
		
	}
	
	
	
	@Test
	public final void testCreateReportMdHappy() throws IOException {
		String inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		String delimiter="\t";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		
		String outputFileName = "./src/main/resources/TestOutput/EggsOutTest.md";
		ReportType type =ReportType.MD;
		int status=mainController.createReport(outputFileName,type);
		assertEquals(1,status);
		int numberOfWrittedLineseExpected=16;
		int newLineEndOfFile=1;
		int headerLine=1;
		int numberOfWrittedLineseActual =mainController.getData().size()+ headerLine+newLineEndOfFile;
		assertEquals(numberOfWrittedLineseExpected,numberOfWrittedLineseActual);
		
		File outputFile = new File(outputFileName);
		File outputFileRef = new File("./src/main/resources/TestOutput/originalMd.md");
		
		Boolean localComparison = compareFiles(outputFile, outputFileRef, "testCreateReportMdHappy()");
		assertEquals(true, localComparison);
		
	}
	
	@Test
	public final void testCreateReportHtmlHappy() throws IOException {
		String inputFileName = "./src/main/resources/TestInput/Eggs.tsv";
		String delimiter="\t";
		MainControllerFactory factory =new MainControllerFactory();
		MainController mainController=(MainController) factory.createMainController();
		mainController.load(inputFileName, delimiter);
		
		String outputFileName = "./src/main/resources/TestOutput/EggsOutTest.html";
		ReportType type =ReportType.HTML;
		int status=mainController.createReport(outputFileName,type);
		assertEquals(1,status);
		int numberOfWrittedLineseExpected=16;
		int newLineEndOfFile=1;
		int headerLine=1;
		int numberOfWrittedLineseActual =mainController.getData().size()+ headerLine+newLineEndOfFile;
		assertEquals(numberOfWrittedLineseExpected,numberOfWrittedLineseActual);
		
		File outputFile = new File(outputFileName);
		File outputFileRef = new File("./src/main/resources/TestOutput/originalHtml.html");
		
		Boolean localComparison = compareFiles(outputFile, outputFileRef, "testCreateReportMdHappy()");
		assertEquals(true, localComparison);
		
	}
	
	

	
	
	private Boolean compareFiles(File outputFile, File outputFileRef, String caller) throws IOException {
		Boolean localComparison = false;
		localComparison = FileUtils.contentEquals(outputFile, outputFileRef);
		return localComparison;
	}//end compareFiles

	
	
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
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
