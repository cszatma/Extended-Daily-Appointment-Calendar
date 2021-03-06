import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment implements Comparable<Appointment> {
	private Calendar date;
	private String description;
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("H:mm");
	private Person person;

	//Constructor
	public Appointment(GregorianCalendar date, String description, Person person) {
		this.date = (Calendar) date.clone();
		this.description = description;
		this.person = person;
	}

	//Alternate Constructor
	public Appointment(int year, int month, int day, int hour, int minute, String description, Person person) {
		this(new GregorianCalendar(year, month, day, hour, minute), description, person);
	}

	//Returns the date value
	public Calendar getDate() {
		return this.date;
	}

	//Sets the date value
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	//Returns the description value
	public String getDescription() {
		return this.description;
	}

	//Sets the description value
	public void setDescription(String description) {
		this.description = description;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	//Creates a string representation of the appointment
	public String print() {
		return DATE_FORMAT.format(date.getTime()) + " " + description + (person != null ? " WITH: " + person : "");
	}

	public boolean occursOnDate(int year, int month, int day) {
		return date.get(Calendar.YEAR) == year && date.get(Calendar.MONTH) == month && date.get(Calendar.DAY_OF_MONTH) == day;
	}

	public boolean occursOn(int year, int month, int day, int hour, int minute) {

		return occursOnDate(year, month, day) && date.get(Calendar.HOUR_OF_DAY) == hour && date.get(Calendar.MINUTE) == minute;
	}

	@Override
	public int compareTo(Appointment a) {
		return this.date.compareTo(a.getDate());
	}

	public boolean equals(Appointment a) {
		return this.date.equals(a.getDate()) && this.description.equals(a.getDescription());
	}

}
