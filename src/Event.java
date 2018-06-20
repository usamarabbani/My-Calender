import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Class Event.
 */
public class Event implements Comparable<Event>, Serializable {

	/** The minute end. */
	private int year, month, day, hourStart, minuteStart, dayOfWeek,
	                              hourEnd, minuteEnd;
	
	/** The title. */
	private String title;
	
	/**
	 * Gets the hour start.
	 *
	 * @return the hour start
	 */
	public int getHourStart() {
		return hourStart;
	}

	/**
	 * Gets the minute start.
	 *
	 * @return the minute start
	 */
	public int getMinuteStart() {
		return minuteStart;
	}

	/**
	 * Sets the hour end.
	 *
	 * @param hourEnd the new hour end
	 */
	public void setHourEnd(int hourEnd) {
		this.hourEnd = hourEnd;
	}

	/**
	 * Sets the minute end.
	 *
	 * @param minuteEnd the new minute end
	 */
	public void setMinuteEnd(int minuteEnd) {
		this.minuteEnd = minuteEnd;
	}

	/**
	 * Gets the day of week.
	 *
	 * @return the day of week
	 */
	public int getDayOfWeek() {
		Calendar cal = new GregorianCalendar(year, month, day);
		this.dayOfWeek =  cal.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Gets the day.
	 *
	 * @return the day
	 */
	public int getDay() {
		return day;
	}



	/**
	 * Instantiates a new event.
	 *
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 * @param hourStart the hour start
	 * @param minuteStart the minute start
	 * @param title the title
	 */
	public Event (int year, int month, int day, int hourStart, 
			int minuteStart, String title) {
		
		this.year = year;
		this.month = month;
		this.day = day;
		this.hourStart = hourStart;
		this.minuteStart = minuteStart;
		
		this.title = title;
	}
	
	/**
	 * Instantiates a new event.
	 *
	 * @param year the year
	 * @param month the month
	 * @param day the day
	 */
	public Event (int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (Object e) {
		Event ev = (Event)e;
		if (this.year == ev.year && this.month == ev.month) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Event arg0) {
		if (this.year != arg0.year) {
			return this.year - arg0.year;
		}
		if (this.month != arg0.month) {
			return this.month - arg0.month;
		}
		if (this.day != arg0.day) {
			return this.day - arg0.day;
		}
		if (this.hourStart != arg0.hourStart) {
			return this.hourStart - arg0.hourStart;
		}
		if (this.minuteStart != arg0.minuteStart) {
			return this.minuteStart - arg0.minuteStart;
		}

		return 0;
	}
	
	/**
	 * Display event.
	 *
	 * @return the string
	 */
	public String displayEvent () {
		return title + " " + " " + hourStart + ":" + minuteStart + " - " + hourEnd + ":" + minuteEnd;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString () {
		return year + " " + MMyCalendarTester.DAYS_OF_WEEK[dayOfWeek] + " " + MyCalendarTester.MONTHS[month] +
				" " + hourStart + ":" + minuteStart + " - " + hourEnd + ":" + minuteEnd + " " + title;
	} // method
} // calss
