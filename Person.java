
public class Person implements Comparable<Person> {

	private String firstName;
	private String lastName;
	private String telephoneNumber;
	private String address;
	private String email;

	public Person(String firstName, String lastName, String telephoneNumber, String address, String email) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setTelephoneNumber(telephoneNumber);
		this.setAddress(address);
		this.setEmail(email);
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
		return String.format("%s %s %s %s %s", firstName, lastName, telephoneNumber, address, email);
	}

	@Override
	public int compareTo(Person o) {
		int comparisionResult = this.lastName.compareTo(o.getLastName());
		return comparisionResult != 0 ? comparisionResult : this.firstName.compareTo(o.getFirstName());
	}

	public static int comparePhoneNumber(Person o) {
    return this.telephoneNumber.compareTo(o.getTelephoneNumber());
  }

	public static int compareEmail(Person o) {
    return this.email.compareTo(o.getEmail());
  }

}
