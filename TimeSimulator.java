/**
 * This class represents TimeSimulator class that implements the Comparable Interface.
 */
public class TimeSimulator implements Comparable<TimeSimulator> {

	/**
	 * hour of time.
	 */
	private int hour;

	/**
	 * minute of time.
	 */
	private int min;

	/**
	 * Constructor that sets hour and minute to 0.
	 */
	public TimeSimulator(){
		this.hour = 0;
		this.min = 0;
	}

	/**
	 * Constructor that sets hour to given hour and minute to 0.
	 * @param hour hour
	 */
	public TimeSimulator(int hour){
		if(hour<0 || hour>23)
			throw new IllegalArgumentException("Hour must be within [0, 23]!");

		this.hour = hour;
		this.min=0;
	}

	/**
	 * Constructor that sets hour and minute to given hour and minute.
	 * @param hour hour
	 * @param min minute
	 */
	public TimeSimulator(int hour, int min){
		if(hour<0 || hour>23 || min<0 || min>59)
			throw new IllegalArgumentException("Hour must be within [0, 23]; Minute must be within [0, 59]!");

		this.hour = hour;
		this.min = min;
	}

	/**
	 * Private helper-mutator method.
	 * @param hour hour
	 */
	private void setHour(int hour){
		this.hour = hour;
	}

	/**
	 * private helper-mutator method.
	 * @param min minute
	 */
	private void setMin(int min){
		this.min = min;
	}

	/**
	 * Method that returns hour.
	 * @return hour
	 */
	public int getHour(){
		return this.hour;
	}

	/**
	 * Method that returns min.
	 * @return minute
	 */
	public int getMin(){
		return this.min;
	}

	/**
	 * Compares two times for ordering.
	 * @param otherTime time to compare with this time
	 * @return -1 if this time is before argument time, 1 if this time is before argument time, 0 otherwise.
	 */
	@Override 
	public int compareTo(TimeSimulator otherTime){
		if(otherTime==null)
			throw new IllegalArgumentException("Null Time object!");

		if((this.getHour()*60 + this.getMin()) < (otherTime.getHour()*60 + otherTime.getMin()))
			return -1;
		else if ((this.getHour()*60 + this.getMin()) > (otherTime.getHour()*60 + otherTime.getMin()))
			return 1;
		else
			return 0;
	}

	/**
	 * return the number of minutes starting from this Time and ending at endTime.
	 * @param endTime the end time
	 * @return duration from this time to end time.
	 */
	public int getDuration(TimeSimulator endTime){
		if(endTime==null){
			throw new IllegalArgumentException("Null Time object!");
		}
		if((endTime.getHour()*60 + endTime.getMin()) < (this.getHour()*60 + this.getMin()))
			return -1;

		return ((endTime.getHour()*60 + endTime.getMin()) - (this.getHour()*60 + this.getMin())); //default return, remove/change as needed
	}

	/**
	 * return a Time object that is duration minute from this Time.
	 * @param duration duration
	 * @return time that is duration away from this time.
	 */
	public TimeSimulator getEndTime(int duration){
		if(duration < 0){
			throw new IllegalArgumentException("Duration must be non-negative!");
		}

		int durHour=0;
		int durMin=0;

		while(duration>59){
			duration -= 60;
			durHour++;
		}
		durMin = duration;

		TimeSimulator toReturn = new TimeSimulator();

		int toRetHour = 0;
		int toRetMin = 0;

		toRetMin = this.getMin() + durMin;

		while(toRetMin>59){
			toRetMin -=60;
			toRetHour++;
		}

		toRetHour += this.getHour() + durHour;

		toReturn.setHour(toRetHour);
		toReturn.setMin(toRetMin);

		if(toReturn.getHour()>23)
			return null;

		return toReturn;
	}

	/**
	 * Return a String representation of this object in the form of hh:mm.
	 * @return the time of this object.
	 */
	public String toString() {

		String output = String.format("%02d:%02d",this.getHour(), this.getMin());
		
		return output;
	}

}