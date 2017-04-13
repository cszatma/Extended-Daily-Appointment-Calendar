import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;


public class Contacts {
  private LinkedList<Person> people;

  public Contacts() {
    this.people = new LinkedList<Person>();
  }

  public LinkedList<Person> getPeople() {
    return this.people;
  }

  public void setPeople(LinkedList<Person> people) {
    this.people = people;
  }

  public Person findPerson(String lastName, String firstName) {
    for (Person p : this.people) {
      System.out.println(p);
      if (p.compareTo(new Person(lastName, firstName, "", "", "")) == 0) {
        return p;
      }
    }
    return null;
  }

  public Person findPersonWithNumber(String telephoneNumber) {
    final PhoneNumberComparer comparer = new PhoneNumberComparer();
    for (Person p : this.people) {
      if (comparer.compare(p, new Person("", "", "", telephoneNumber, "")) == 0) {
        return p;
      }
    }
    return null;
  }

  public Person findPersonWithEmail(String email) {
    final EmailComparer comparer = new EmailComparer();
    for (Person p : this.people) {
      if(comparer.compare(p, new Person("", "", "", "", email)) == 0) {
      return p;
      }
    }
    return null;
  }

  public void readContactsFile() throws FileNotFoundException, NumberFormatException  {
    Scanner in = new Scanner(new File("contacts.txt"));
    int numOfContacts = Integer.parseInt(in.nextLine());
    for (int i = 0; i < numOfContacts; i++) {
      Person currentPerson = new Person(in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine());
      this.people.add(currentPerson);
    }

  }

}
