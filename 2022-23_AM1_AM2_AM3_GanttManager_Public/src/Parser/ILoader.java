package Parser;

import java.util.ArrayList;

import dom.Task;


public interface ILoader {

	public void load(String path,String delimiter);
	public String[] getHeaderLine();
	public ArrayList<Task> getTaskList();
	
}
