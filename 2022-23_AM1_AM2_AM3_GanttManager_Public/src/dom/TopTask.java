package dom;

public class TopTask extends Task{

	

	// Top
	public TopTask(Integer taskId, String taskText) {
		super(taskId, taskText);

	}

	public TopTask(int taskId, String taskText, int mamaId, String start, String end, double cost) {
		super(taskId, taskText, mamaId, start, end, cost);
	}

	

	
	@Override
	public String toString() {

	return	super.toString();	
		}
	@Override
	public Integer getTaskId() {
		return super.getTaskId();
	}

	

	@Override
	public void findStart() {
		super.findStart();
	}
	@Override
	public void findEnd() {
		super.findEnd();

	}
	@Override
	public void findCost() {

		super.findCost();

	}
	@Override
	public String getTaskText() {
		return super.getTaskText();
	}

	@Override
	public Integer getMamaId() {
		return super.getMamaId();
	}

	@Override
	public String getStart() {
		return super.getStart();
	}

	@Override
	public String getEnd() {
		return super.getEnd();
	}

	@Override
	public double getCost() {
		 return super.getCost();
	}



	
}
