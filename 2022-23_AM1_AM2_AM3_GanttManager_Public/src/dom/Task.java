package dom;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Task {

	private Integer taskId;
	private String taskText;
	private Integer MamaId;
	private String start;
	private String end;
	private double cost;
	private ArrayList<Task> listTasks = new ArrayList<Task>();
	
	

		
		public Task(Integer taskId, String taskText) {
			this.taskId = taskId;
			this.taskText = taskText;
			this.MamaId = 0;

		}

		public Task(int taskId, String taskText, int mamaId, String start, String end, double cost) {

			this.taskId = taskId;
			this.taskText = taskText;
			this.MamaId = mamaId;
			this.start = start;
			this.end = end;
			this.cost = cost;
		}

	
	
	
	
	
	public static Comparator<Task> startSort = new Comparator<Task>() {
		public int compare(Task task1, Task task2) {
			int start1 = Integer.parseInt(task1.getStart());
			int start2 = Integer.parseInt(task2.getStart());
			int taskId1 = task1.getTaskId();
			int taskId2 = task2.getTaskId();
			if (start1 - start2 == 0) {

				return taskId1 - taskId2;
			}
			return start1 - start2;
		}
	};
	public  String toString() {
		String firtLine = Integer.toString(this.taskId) + "\t" + this.taskText + "\t" + Integer.toString(this.MamaId)
		+ "\t" + this.start + "\t" + this.end + "\t" + this.cost;

         return firtLine;
	}
	
	public void addSubtask(Task subtask) {

		this.listTasks.add(subtask);
	}
	
	public ArrayList<Task> getListSubTasks() {
		return listTasks;
	}
	
	
	public String getTaskText() {
		return taskText;
	}


	public Integer getMamaId() {
		return MamaId;
	}


	public String getStart() {
		return start;
	}


	public String getEnd() {
		return end;
	}


	public double getCost() {
		return cost;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void findStart() {
		if (this.listTasks.size() != 0) {
			this.start = this.listTasks.get(0).getStart();
		}

	}

	public void findEnd() {
		if (this.listTasks.size() != 0) {
			this.end = this.listTasks.get(listTasks.size() - 1).getEnd();
		}

	}

	public void findCost() {

		if (this.listTasks.size() != 0) {
			double cost = 0;
			for (Task subtask : this.listTasks) {
				cost = subtask.getCost()+cost;
			}
			this.cost = cost;
		}
	}
	
}
