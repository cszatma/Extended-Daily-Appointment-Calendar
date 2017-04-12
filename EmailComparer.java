import java.util.Comparator;

public class EmailComparer implements Comparator<Person> {

  public int compare(Person first, Person second) {
    return first.getEmail().compareTo(second.getEmail());
  }
}
