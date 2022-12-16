/**
 * This class is a representation of ScheduleItem that implements the Comparable Interface.
	* Ths class represents a single item/event in the daily schedule.
	* Consists of a start time, end time, and description of event.
 */
public class ScheduleItem implements Comparable<ScheduleItem> {

	/**
	 * Start time.
	 */
	private TimeSimulator startTime;

	/**
	 * End time.
	 */
	private TimeSimulator endTime;

	/**
	 * Description of the specific item/event in the schedule.
	 */
	private String description;

	/**
	 * Constructor with start and end times. Sets description to be empty string "".
	 * @param startTime start time
	 * @param endTime end time
	 */
	public ScheduleItem(TimeSimulator startTime, TimeSimulator endTime){
		if(startTime.compareTo(endTime)>0)
			throw new IllegalArgumentException("End Time cannot come before Start Time!");

		if(endTime==null || startTime==null){
			throw new IllegalArgumentException("Null Time object!");
		}

		this.description = "";
		this.startTime = startTime;
		this.endTime = endTime;

	}

	/**
	 * Constructor with start time, end time, and description.
	 * @param startTime start time
	 * @param endTime end time
	 * @param description description
	 */
	public ScheduleItem(TimeSimulator startTime, TimeSimulator endTime, String description){
		if(startTime.compareTo(endTime)>0)
			throw new IllegalArgumentException("End Time cannot come before Start Time!");

		if(endTime==null || startTime==null){
			throw new IllegalArgumentException("Null Time object!");
		}

		if(description==null){
			this.description = "";
		}
		else{
			this.description = description;
		}

		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * return start time of ScheduleItem.
	 * @return start time
	 */
	public TimeSimulator getStart(){
		return this.startTime;
	}

	/**
	 * return end time of ScheduleItem.
	 * @return end time
	 */
	public TimeSimulator getEnd(){
		return this.endTime;
	}

	/**
	 * return description of ScheduleItem.
	 * @return description
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * A method that compares two times for ordering.
	 * @param otherScheduleItem time to be compared with ScheduleItem.
	 * @return -1 if this ScheduleItem is before argument ScheduleItem, 1 if opposite, and 0 if same.
	 */
	@Override 
	public int compareTo(ScheduleItem otherScheduleItem){
		if(otherScheduleItem==null)
			throw new IllegalArgumentException("Null ScheduleItem object!");

		if(this.startTime.compareTo(otherScheduleItem.startTime)<0)
			return -1;
		else if(this.startTime.compareTo(otherScheduleItem.startTime)>0)
			return 1;
		else
			return 0;
	}

	/**
	 * Move the start time of this ScheduleItem to be newStart but keep the same duration.
	 * @param newStart new start time
	 * @return true if successful, otherwise false.
	 */
	public boolean moveStart(TimeSimulator newStart){

		if(newStart==null)
			return false;

		TimeSimulator temp = this.startTime;

		int initialStart = this.startTime.getHour()*60 + this.startTime.getMin();
		int initialEnd = this.endTime.getHour()*60 + this.endTime.getMin();
		int diff = initialEnd - initialStart;

		this.startTime = newStart;

		int finalStart = getStart().getHour()*60 + getStart().getMin();
		int finalEnd = finalStart + diff;

		int newEndTimeHr = 0;
		int newEndTimeMin = 0;

		while(finalEnd>59){
			finalEnd-=60;
			newEndTimeHr++;
		}

		newEndTimeMin = finalEnd;

		int maxTime = 23*60 + 59;


		if(finalStart > maxTime || finalEnd > maxTime || newEndTimeHr<0 || newEndTimeHr>23 || newEndTimeMin<0 || newEndTimeMin>59 ){
			this.startTime = temp; //restore original startTime
			return false;
		}

		TimeSimulator newEnd = new TimeSimulator(newEndTimeHr, newEndTimeMin);

		if((newEnd.getHour()*60 + newEnd.getMin()) > maxTime){
			return false;
		}
		this.endTime = newEnd;

		return true;
	}

	/**
	 * Change the duration of ScheduleItem to be the given number of minutes.
	 * @param minute duration
	 * @return true if successful, otherwise false.
	 */
	public boolean changeDuration(int minute){

		if(minute<0)
			return false;

		int maxTime = 23*60 + 59;

		int initialStart = this.startTime.getHour()*60 + this.startTime.getMin();
		int initialEnd = this.endTime.getHour()*60 + this.endTime.getMin();
		int origDuration = initialEnd - initialStart;

		if((initialStart + minute) > maxTime){
			return false;
		}

		int newEndTime = initialStart + minute;

		int newEndTimeHr = 0;
		int newEndTimeMin = 0;

		while(newEndTime>59){
			newEndTime-=60;
			newEndTimeHr++;
		}
		newEndTimeMin = newEndTime;

		TimeSimulator newEnd = new TimeSimulator(newEndTimeHr,newEndTimeMin);

		this.endTime = newEnd;

		return true;

	}

	/**
	 * Set the description of this ScheduleItem.
	 * @param newDescription new description
	 */
	public void setDescription(String newDescription){

		if(newDescription==null)
			this.description="";

		this.description = newDescription;
	}

	/**
	 * Return a string representation of the ScheduleItem in the form of
	 * startTime-endTime/description.
	 * @return string representation of ScheduleItem
	 */
	public String toString(){
		String outp = "";
		outp = String.format("%02d:%02d-%02d:%02d/%s",this.startTime.getHour(), this.startTime.getMin(),this.endTime.getHour(), this.endTime.getMin(),getDescription());
		return outp;
	}


}