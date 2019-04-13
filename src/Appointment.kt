import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

class Appointment(date: Calendar, var description: String, var person: Person?) : Comparable<Appointment> {
	companion object {
		val DATE_FORMAT = SimpleDateFormat("H:mm")
	}
	var date = date.clone() as Calendar

	constructor(year: Int, month: Int, day: Int, hour: Int, minute: Int, description: String, person: Person) :
		this(GregorianCalendar(year, month, day, hour, minute), description, person)

	//Creates a string representation of the appointment
	fun print() =
			"${DATE_FORMAT.format(date.getTime())} $description${if (person != null) " WITH: $person" else ""}"

	fun occursOnDay(otherDate: Calendar) =
			date.year == otherDate.year && date.month == otherDate.month &&
					date.dayOfMonth == otherDate.dayOfMonth

	fun occursOnDayAtTime(otherDate: Calendar, hour: Int, minute: Int) =
			occursOnDay(otherDate) && date.hourOfDay == hour && date.minute == minute

//	fun occursOnDate(year: Int, month: Int, day: Int) =
//			date.year == year && date.month == month && date.dayOfMonth == day
//
//	fun occursOn(year: Int, month: Int, day: Int, hour: Int, minute: Int) =
//			occursOnDate(year, month, day) && date.hourOfDay == hour &&
//					date.minute == minute

	override operator fun compareTo(other: Appointment) = date.compareTo(other.date)

	override fun equals(other: Any?): Boolean {
		if (other == null || other !is Appointment) {
			return false
		}

		return date == other.date && description == other.description
	}
}
