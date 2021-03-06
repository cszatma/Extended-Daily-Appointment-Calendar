
public class Person implements Comparable<Person> {

	private String firstName;
	private String lastName;
	private String telephoneNumber;
	private String address;
	private String email;

	public Person(String lastName, String firstName, String address, String telephoneNumber, String email) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return String.format("%s %s %s %s", firstName, lastName, telephoneNumber, email);
	}

	@Override
	public int compareTo(Person o) {
		int comparisionResult = this.lastName.compareTo(o.getLastName());
		return comparisionResult != 0 ? comparisionResult : this.firstName.compareTo(o.getFirstName());
	}

	public boolean equals(Person p) {
		return this.lastName.equals(p.getLastName()) && this.firstName.equals(p.getFirstName()) && this.address.equals(p.getAddress()) && this.telephoneNumber.equals(p.getTelephoneNumber()) && this.email.equals(p.getEmail());
	}

}
