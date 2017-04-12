<<<<<<< HEAD
import java.util.Comparator;

public class PhoneNumberComparer implements Comparator<Person> {
=======
public class PhoneNumberComparer {
>>>>>>> additions
  public int compare(Person first, Person second) {
    return first.getTelephoneNumber().compareTo(second.getTelephoneNumber());
  }
}
