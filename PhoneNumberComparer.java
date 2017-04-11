public abstract class PhoneNumberComparer {
  public static int compare(Person first, Person second) {
    return first.getTelephoneNumber().compareTo(second.getTelephoneNumber());
  }
}
