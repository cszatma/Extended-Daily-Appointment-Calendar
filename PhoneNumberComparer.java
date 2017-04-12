import java.util.Comparator;

public class PhoneNumberComparer implements Comparator<Person> {
  public int compare(Person first, Person second) {
    return first.getTelephoneNumber().compareTo(second.getTelephoneNumber());
  }
}
