public class EmailComparer {
  public int compare(Person first, Person second) {
    return first.getEmail().compareTo(second.getEmail());
  }
}
