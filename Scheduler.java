/**
 * This is a representation of the class Scheduler.
 */
public class Scheduler{

	/**
	 * array of ScheduleItems.
	 */
	private ScheduleSorter<ScheduleItem> ScheduleItems;

	/**
	 * Constructor with no arguments.
 	 */
	public Scheduler(){
		ScheduleItems = new ScheduleSorter<>();
	}

	/**
	 * Returns number of ScheduleItems.
	 * @return number of ScheduleItems.
	 */
	public int size(){
		return ScheduleItems.size();
	}

	/**
	 * Return the string representation of the Scheduler.
	 * @return string representation of Scheduler.
	 */
	public String toString(){

		StringBuilder output = new StringBuilder();

		for(int i=0; i<size(); i++){
			output.append("[" + i + "]" + getScheduleItem(i).toString() + "\n");
		}
		String out = output.toString();
		out = out.trim();
		return out;
	}

	/**
	 * Adds a new ScheduleItem into the list.
	 * @param ScheduleItem new ScheduleItem
	 */
	public void addScheduleItem(ScheduleItem ScheduleItem){

		if(ScheduleItem==null){
			throw new IllegalArgumentException("Null ScheduleItem object!");
		}
		ScheduleItems.add(ScheduleItem);
	}

	/**
	 * Moves the ScheduleItem at index to be start at newStart.
	 * @param index index
	 * @param newStart new start time
	 * @return true if successful, otherwise false.
	 */
	public boolean moveScheduleItem(int index, TimeSimulator newStart){
		if(index<0 || index>=size()){
			return false;
		}

		if(newStart==null){
			return false;
		}

		ScheduleItem theScheduleItem = ScheduleItems.get(index);
		ScheduleItem temp = theScheduleItem;
		theScheduleItem.moveStart(newStart);

		if(!theScheduleItem.moveStart(newStart)){
			return false;
		}

		if(newStart.compareTo(temp.getStart())<0){
			ScheduleItems.delete(index);
			ScheduleItems.add(theScheduleItem);
		}
		
		return true;
	}


	/**
	 * Changes the duration of ScheduleItem at index to be the given number of minutes.
	 * @param index index
	 * @param minute new duration
	 * @return true if successful, otherwise false.
	 */
	public boolean changeDuration (int index, int minute){

		if(index<0 || index>=size() || minute<0)
			return false;

		ScheduleItem myScheduleItem = ScheduleItems.get(index);
		myScheduleItem.changeDuration(minute);

		if(myScheduleItem.changeDuration(minute)==false)
			return false;

		return true;
	}

	/**
	 * Change the description of ScheduleItem at index.
	 * @param index index
	 * @param description new description
	 * @return true if successful, otherwise false.
	 */
	public boolean changeDescription(int index, String description){
		if(index<0 || index>size())
			return false;

		ScheduleItem myScheduleItem = ScheduleItems.get(index);

		if(description==null){
			myScheduleItem.setDescription("");
		}

		myScheduleItem.setDescription(description);

		return true;
	}

	/**
	 * Remove the ScheduleItem at index.
	 * @param index index
	 * @return true if successful, otherwise false.
	 */
	public boolean removeScheduleItem(int index){

		if(index<0 || index>size())
			return false;

		ScheduleItems.delete(index);
		return true;
	}

	/**
	 * Return the ScheduleItem at index.
	 * @param index index
	 * @return ScheduleItem at specified index.
	 */
	public ScheduleItem getScheduleItem(int index){

		if(index<0 || index>size())
			return null;

		return ScheduleItems.get(index);
	}

}