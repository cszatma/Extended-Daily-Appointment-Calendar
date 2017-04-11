public abstract class EmailComparer {
  public static int compare(Person first, Person second) {
    return first.getEmail().compareTo(second.getEmail());
  }
}
