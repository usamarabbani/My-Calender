import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * The Class MyCalendarTester.
 */
public class MyCalendarTester {

	/** The Constant FILANAME. */
	private static final String FILANAME = "events.txt";
	
	/** The current date. */
	private static Event currentDate;
	
	/** The oos. */
	private static ObjectOutputStream oos;
	
	/** The ois. */
	private static ObjectInputStream ois;
	
	/** The events. */
	private static TreeSet<Event> events;
	
	/** The Constant WEEK_DAYS. */
	private static final String[] WEEK_DAYS = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
	
	/** The Constant DFS. */
	private static final DateFormatSymbols DFS = new DateFormatSymbols(Locale.ENGLISH);
	
	/** The Constant MONTHS. */
	public static final String[] MONTHS = DFS.getMonths();
	
	/** The Constant DAYS_OF_WEEK. */
	public static final String[] DAYS_OF_WEEK = DFS.getWeekdays();

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		events = new TreeSet<Event>();
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); 

		currentDate = new Event(year, month, day);

		String monthName = MONTHS[Calendar.getInstance().get(Calendar.MONTH)];
		ArrayList<Integer> days = new ArrayList<>();
		days.add(day);
		initialScreen(year, monthName, days);
		Scanner sc = null;
		try {
			main: while (true) {
				System.out.println("\nSelect one of the following options: "
						+ "[L]oad [V]iew by [C]reate [G]o to [E]vent list [D]elete [Q]uit");

				sc = new Scanner (System.in);
				switch (sc.nextLine()) {
				case "L":
					if (new File(FILANAME).length() == 0) {
						System.out.println("No such file because it is the first run.");
					} else {
						deSerial();
						System.out.println("File has been loaded.");
					}
					break;
				case "V":
					flag: while (true) {
						System.out.println("[D]ay view or [M]onth view ?");
						switch (sc.nextLine()) {
						case "D":
							printEvents(MONTHS[Calendar.getInstance().get(Calendar.MONTH)], 
									Calendar.getInstance().get(Calendar.YEAR));
							prevNext(sc, true);
							break flag;
						case "M":
							ArrayList<Integer> daysToHighlights = new ArrayList<>();
							highlightDays(daysToHighlights);
							initialScreen(year, monthName, daysToHighlights);
							prevNext(sc, false);
							break flag;
						default :
							System.out.println("Wrong selection!");
						} // switch
					} // while
				break;
				case "C":
					String title;
					while (true) {
						System.out.println("Enter a title (more than one word):");
						title = sc.nextLine();
						title = title.trim();
						String remWhites = title.replace(" ", "");
						if (title.length() == remWhites.length()) {
							System.out.println("Wrong title!");
						} else {
							break;
						}
					} // while
					System.out.println("Enter a date (MM/DD/YYYY):");
					String [] input = sc.nextLine().trim().split("[/.,;-]");
					try {
						int m = Integer.parseInt(input[0]) - 1, d = Integer.parseInt(input[1]), y = Integer.parseInt(input[2]);
						System.out.println("Enter start time and end time (h:m-h:m or h:m)");
						input = sc.nextLine().trim().split("[:-]");
						int sh = Integer.parseInt(input[0]), sm = Integer.parseInt(input[1]), eh, em;
						Event event = new Event(y, m, d, sh, sm, title);
						if (input.length == 4) {
							eh = Integer.parseInt(input[2]);
							em = Integer.parseInt(input[3]);
							event.setHourEnd(eh);
							event.setMinuteEnd(em);
						}
						events.add(event);	
						System.out.println("Event has been added successfully!");

					} catch (NumberFormatException e) {
						System.out.println("Wrong input!");
						continue;
					}
					break;
				case "G":
					System.out.println("Enter a date (MM/DD/YYYY):");
					String [] dInput = sc.nextLine().trim().split("[/.,;-]");
					currentDate = new Event(Integer.parseInt(dInput[2]), Integer.parseInt(dInput[0]), Integer.parseInt(dInput[1]));
					printEvents(MONTHS[currentDate.getMonth()], currentDate.getYear());
					break;
				case "E":
					for (Event ev: events)
							System.out.println(ev);
					break;
				case "D":
					flag: while (true) {
						System.out.println("[S]elected or [A]ll ?");
						switch (sc.nextLine()) {
						case "S":
							System.out.println("Enter a date (MM/DD/YYYY):");
							String [] dI = sc.nextLine().trim().split("[/.,;-]");
							try {
								currentDate = new Event(Integer.parseInt(dI[2]), Integer.parseInt(dI[0]), Integer.parseInt(dI[1]));
							} catch (NumberFormatException e) {
								System.out.println("Wrong date!");
								break;
							}
							Iterator<Event> it = events.iterator();
							while (it.hasNext()) {
								Event next;
								if ((next = it.next()).equals(currentDate) && next.getDay() == currentDate.getDay()) {
									events.remove(next);
									System.out.println("Event has been deleeted successfully!");
									break flag;
								}
							}
						case "A":
							events.clear();
							System.out.println("All events has been deleeted successfully!");
							break flag;
						default:
							System.out.println("Wrong selection!");
						} // switch
					} // while
				break;
				case "Q":
					serial ();
					break main;

				default:
					System.out.println("Wrong selection!");
				} // switch

			} // while
		} finally {
			if (sc != null)
				sc.close();
		}
	} // main

	/**
	 * To get previous or next
	 * day or month.
	 *
	 * @param sc the scanner
	 * @param isDay the is day
	 */
	private static void prevNext (Scanner sc, boolean isDay) {
		while (true) {
			System.out.println("[P]revious or [N]ext or [M]ain menu ?");
			switch (sc.nextLine()) {
			case "P":
				if (isDay) {

					Calendar gc = new GregorianCalendar(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay());
					gc.add(Calendar.DAY_OF_MONTH, -1);
					currentDate = new Event(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
					printEvents(
							MONTHS[gc.get(Calendar.MONTH)], currentDate.getYear());
					break;
				} else {
					Calendar gc = new GregorianCalendar(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay());
					gc.add(Calendar.MONTH, -1);
					currentDate = new Event(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
					ArrayList<Integer> daysToHighlights = new ArrayList<>();
					highlightDays(daysToHighlights);
					initialScreen(currentDate.getYear(), MONTHS[gc.get(Calendar.MONTH)], daysToHighlights);		
					break;
				}
			case "N":
				if (isDay) {

					Calendar gc = new GregorianCalendar(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay());
					gc.add(Calendar.DAY_OF_MONTH, 1);
					currentDate = new Event(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
					printEvents(
							MONTHS[gc.get(Calendar.MONTH)], currentDate.getYear());
					break;
				} else {
					Calendar gc = new GregorianCalendar(currentDate.getYear(), currentDate.getMonth(), currentDate.getDay());
					gc.add(Calendar.MONTH, 1);
					currentDate = new Event(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
					ArrayList<Integer> daysToHighlights = new ArrayList<>();
					highlightDays(daysToHighlights);
					initialScreen(currentDate.getYear(), MONTHS[gc.get(Calendar.MONTH)], daysToHighlights);		
					break;
				}
			case "M":
				return;
			default:
				System.out.println("Wrong selection!");
			}
		} // while
	}

	/**
	 * Serial.
	 */
	private static void serial () {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(FILANAME));
			oos.writeObject(events);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//catch
	}

	/**
	 * Highlight days.
	 *
	 * @param daysToHighlights the days to highlights
	 */
	private static void highlightDays (ArrayList<Integer> daysToHighlights) {
		for (Event event: events) {
			if (event.equals(currentDate)) {
				daysToHighlights.add(event.getDay());
			}
		}

	}

	/**
	 * De serial.
	 */
	@SuppressWarnings("unchecked")
	private static void deSerial () {
		try {
			ois = new ObjectInputStream(new FileInputStream(FILANAME));
			events  = (TreeSet<Event>)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//try/multi-catch

	} // method

	/**
	 * Prints the events.
	 *
	 * @param monthName the month name
	 * @param year the year
	 */
	private static void printEvents (String monthName, int year) {
		System.out.println(DAYS_OF_WEEK[currentDate.getDayOfWeek()] + ", " + monthName + " " + currentDate.getDay() + ", " + year);
		for (Event event: events) {
			if (event.equals(currentDate) && event.getDay() == currentDate.getDay()) {
				System.out.println(event.displayEvent());
			}
		}
	}

	/**
	 * Initial screen.
	 *
	 * @param year the year
	 * @param monthName the month name
	 * @param days the days
	 */
	private static void initialScreen (int year, String monthName, ArrayList<Integer> days) {

		int indent = 0;		
		System.out.println(monthName + " " + year);
		Calendar gc = new GregorianCalendar();
		gc.set(year, 1, Calendar.DATE);

		String dow = DAYS_OF_WEEK[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)];
		int monthDays = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int a = 0; a < WEEK_DAYS.length; a ++) {
			System.out.print(WEEK_DAYS[a] + " ");
			if (WEEK_DAYS[a].equals(dow.substring(0, 2))) {
				indent = a;
			}
		}
		System.out.println();

		for (int a = 1; a <= monthDays + indent - 1; a ++) {
			if (a >= indent) {
				if (a - indent + 1 < 10) {
					if (days.contains(a - indent + 1)) {
						System.out.print(" [" + (a - indent + 1) + "] ");
					} else {
						System.out.print(" " + (a - indent + 1) + " ");
					}
				} else {	
					if (days.contains(a - indent + 1)) {
						System.out.print("[" + (a - indent + 1) + "] ");
					} else {
						System.out.print((a - indent + 1) + " ");
					}
				}
			} else {
				System.out.print("   ");
			}
			if (a % 7 == 0) {
				System.out.println();
			}
		} // for
	}

} // class
