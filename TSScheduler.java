import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 *  TS Daily Scheduler Simulator.
 *
 *  @author Tomas Seged
 */
public class TSScheduler {

	/**
	 * Two possible input modes.
	 */
	enum Mode {
		/**
		 * Keyboard input.
		 */
		KEYBOARD,

		/**
		 * File input.
		 */
		FILE};

	/**
	 * Divider string.
	 */
	private static String divider = "----------------------------------------\n";

	/**
	 * Scanner to get input from keyboard or file.
	 */
	private static Scanner scanner = null;

	/**
	 * Scheduler to be demo-ed.
	 */
	private static Scheduler Scheduler = null;

	/**
	 *  The main method that presents the UI.
	 *
	 *  @param args command line args: first arg can specify an input file
	 */
	public static void main(String[] args) {

		//Initialize an empty Scheduler
		Scheduler = new Scheduler();
		Mode myMode = Mode.FILE;

		if(args.length > 1){
			System.out.println("Usage: java TSScheduler [Input_File_Name]");
			System.exit(0);
		}
		else if (args.length == 1){
			try{
				// open file for input
				scanner = new Scanner(new File(args[0]));

			}catch(IOException e) {
				e.printStackTrace();
				System.exit(0);
			}

		}
		else{

			//keyboard
			scanner = new Scanner(System.in);
			myMode = Mode.KEYBOARD;

		}

		System.out.print(divider);
		System.out.println("------------- TS SCHEDULER -------------");
		System.out.print(divider);

		int option;
		while(true){
			displayTSMenu();

			if (myMode == Mode.FILE)
				enterToContinue();

			option = scanner.nextInt(); //get the next menu choice
			switch(option){
				case 1: //display
					System.out.print(divider);
					System.out.format("Current Scheduler has %d items/events(s).\n", Scheduler.size());
					System.out.print(divider);
					System.out.println(Scheduler.toString());
					break;
				case 2: //add an ScheduleItem
					processAddScheduleItem();
					break;
				case 3: //move an ScheduleItem
					processChangeStart();
					break;
				case 4: //change duration of an ScheduleItem
					processChangeDuration();
					break;
				case 5: //change description of an ScheduleItem
					processChangeDescription();
					break;
				case 6: //delete an ScheduleItem
					processRemoveScheduleItem();
					break;
				case 7: //exit
					System.out.println("Ciao! \n.\n.\n.\n\"Plans are nothing; planning is everything.\" ~Dwight D");
					return;
				default:
					System.out.println("Invalid Choice!");

			}
		}

	}

	/**
	 *  The method that displays the menu.
	 *
	 */
	private static void displayTSMenu(){
		System.out.println(divider);
		System.out.println("Select your choice from the following options:");
		System.out.println("1 - Display Schedule");
		System.out.println("2 - Add an event to schedule");
		System.out.println("3 - Change the start time of an event in your TS Schedule");
		System.out.println("4 - Change the duration of an event in your TS Schedule");
		System.out.println("5 - Change the description of an event in your TS Schedule");
		System.out.println("6 - Remove an event from your TS Schedule");
		System.out.println("7 - Quit TS Scheduler");
		System.out.print(divider);
		System.out.print("Enter numbers 1 to 7: ");
	}

	/**
	 *  The method that interact with the user to add a new item/event into Scheduler.
	 *
	 */
	private static void processAddScheduleItem(){
		TimeSimulator startTime, endTime;

		//get the starting time (hour/minute)
		System.out.print("Please enter the starting hour of the new event (0-23): ");
		int startHour = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Please enter the starting minute of the new event (0-59): ");
		int startMin = scanner.nextInt();
		scanner.nextLine();

		//verify input
		try{
			startTime = new TimeSimulator(startHour, startMin);
		}catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
			return;
		}

		//get the ending time (hour/minute)
		System.out.print("Please enter the ending hour of the new event (0-23): ");
		int endHour = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Please enter the ending minute of the new event (0-59): ");
		int endMin = scanner.nextInt();
		scanner.nextLine();

		//verify input
		try{
			endTime = new TimeSimulator(endHour, endMin);
		}catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
			return;
		}

		//create an ScheduleItem, exit on error
		ScheduleItem ScheduleItem;
		try{
			ScheduleItem = new ScheduleItem(startTime, endTime);
		}catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
			System.out.println("New event cannot be added!");
			return;
		}

		//get the description of the ScheduleItem
		System.out.println("Please enter a description of the new event: ");
		String description = scanner.nextLine();
		ScheduleItem.setDescription(description);

		//add ScheduleItem
		Scheduler.addScheduleItem(ScheduleItem);
		System.out.println("New event added!");
		System.out.println("New event details: " + ScheduleItem.toString());

	}

	/**
	 *  The method that interact with the user to remove an existing ScheduleItem.
	 *
	 */
	private static void processRemoveScheduleItem(){
		// get the index of ScheduleItem to be removed
		System.out.print("Please select the event/item number to remove: ");
		int ScheduleItemIndex = scanner.nextInt();
		scanner.nextLine();

		//verify index
		ScheduleItem toRemove = Scheduler.getScheduleItem(ScheduleItemIndex);
		if (toRemove == null){
			System.out.println("Invalid item number!");
			return;
		}

		//remove ScheduleItem
		if (Scheduler.removeScheduleItem(ScheduleItemIndex)){
			System.out.println("event removed!");
			System.out.println("Removed item details: " +toRemove.toString());
		}
		else{
			System.out.println("event cannot be removed!");

		}
	}


	/**
	 *  The method that interact with the user to move the starting time of
	 *  an existing ScheduleItem without changing its duration.
	 *
	 */
	private static void processChangeStart(){
		// get the index of ScheduleItem to be changed
		System.out.print("Please select the item number to change: ");
		int ScheduleItemIndex = scanner.nextInt();
		scanner.nextLine();

		//verify index
		ScheduleItem ScheduleItem = Scheduler.getScheduleItem(ScheduleItemIndex);
		if (ScheduleItem == null){
			System.out.println("Invalid item number!");
			return;
		}

		System.out.println("You select to change this item:");
		System.out.println(ScheduleItem);

		//get new starting time
		System.out.print("Please enter the new starting hour of the event (0-23): ");
		int newHour = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Please enter the new starting minute of the event (0-59): ");
		int newMin = scanner.nextInt();
		scanner.nextLine();

		//verify input
		TimeSimulator newStart;
		try{
			newStart = new TimeSimulator(newHour, newMin);
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex.getMessage());
			return;
		}

		//change start time of the ScheduleItem
		if (Scheduler.moveScheduleItem(ScheduleItemIndex, newStart)){
			System.out.println("event changed!");
		}
		else{
			System.out.println("event cannot be changed!");

		}

	}

	/**
	 *  The method that interact with the user to change the duration of an existing ScheduleItem.
	 *
	 */
	private static void processChangeDuration(){
		// get the index of ScheduleItem to be changed
		System.out.print("Please select the event number to change: ");
		int ScheduleItemIndex = scanner.nextInt();
		scanner.nextLine();

		//verify index
		ScheduleItem ScheduleItem = Scheduler.getScheduleItem(ScheduleItemIndex);
		if (ScheduleItem == null){
			System.out.println("Invalid event number!");
			return;
		}

		System.out.println("You select to change this event:");
		System.out.println(ScheduleItem);

		//get new duration (in minutes)
		System.out.print("Please enter the new duration in minutes: ");
		int newDuration = scanner.nextInt();
		scanner.nextLine();

		//change duration
		if (Scheduler.changeDuration(ScheduleItemIndex, newDuration)){
			System.out.println("Event changed!");
		}
		else{
			System.out.println("Event cannot be changed!");

		}

	}

	/**
	 *  The method that interact with the user to change the description of an existing ScheduleItem.
	 *
	 */
	private static void processChangeDescription(){
		// get the index of ScheduleItem to be changed
		System.out.print("Please select the event/item number to change: ");
		int ScheduleItemIndex = scanner.nextInt();
		scanner.nextLine();

		//verify index
		ScheduleItem ScheduleItem = Scheduler.getScheduleItem(ScheduleItemIndex);
		if (ScheduleItem == null){
			System.out.println("Invalid event number!");
			return;
		}

		System.out.println("You select to change this event:");
		System.out.println(ScheduleItem);

		//get new description
		System.out.print("Please enter the new description: ");
		String newDescription = scanner.nextLine();

		//change description
		if (Scheduler.changeDescription(ScheduleItemIndex, newDescription)){
			System.out.println("Event changed!");
		}
		else{
			System.out.println("Event cannot be changed!");

		}

	}

	/**
	 * The method that interacts with the user and returns when user presses enter/return.
	 *
	 */
	private static void enterToContinue() {
		System.out.print("Press enter to continue ...");
		Scanner s = new Scanner(System.in);
		s.nextLine();
	}



}