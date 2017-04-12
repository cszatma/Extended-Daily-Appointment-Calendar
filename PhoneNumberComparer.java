public class PhoneNumberComparer {
  public int compare(Person first, Person second) {
    return first.getTelephoneNumber().compareTo(second.getTelephoneNumber());
  }
}
