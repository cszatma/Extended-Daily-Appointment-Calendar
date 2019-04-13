import java.util.Calendar;

// String Extensions
fun String.isNumeric() = this.matches(Regex("^\\d+$"))

// Calendar Extensions
val Calendar.year: Int
    get() = this.get(Calendar.YEAR)

val Calendar.month: Int
    get() = this.get(Calendar.MONTH)

var Calendar.dayOfMonth: Int
    get() = this.get(Calendar.DAY_OF_MONTH)
    set(value) = this.set(Calendar.DAY_OF_MONTH, value)

val Calendar.hourOfDay: Int
    get() = this.get(Calendar.HOUR_OF_DAY)

val Calendar.minute: Int
    get() = this.get(Calendar.MINUTE)
