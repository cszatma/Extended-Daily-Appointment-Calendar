data class Person(
		var firstName: String,
		var lastName: String,
		var telephoneNumber: String,
		var address: String,
		var email: String
) : Comparable<Person> {
	override fun toString() = "$firstName $lastName $telephoneNumber $email"

	override operator fun compareTo(other: Person): Int {
		val comparisonResult = lastName.compareTo(other.lastName)
		return if (comparisonResult != 0) comparisonResult else firstName.compareTo(lastName)
	}
}
