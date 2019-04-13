import java.util.LinkedList;
import java.util.Scanner;
import java.io.File;

class Contacts() {
  var people = LinkedList<Person>()

  fun findPerson(lastName: String, firstName: String) =
          people.find { it.lastName == lastName && it.firstName == firstName }


  fun findPersonWithNumber(telephoneNumber: String) =
          people.find { it.telephoneNumber == telephoneNumber }

  fun findPersonWithEmail(email: String) =
          people.find { it.email == email }

  fun readContactsFile() {
    val scanner = Scanner( File("contacts.txt"));
    val numOfContacts = scanner.nextLine().toInt()

    for (i in 0 until numOfContacts) {
      people.add(Person(
              scanner.nextLine(),
              scanner.nextLine(),
              scanner.nextLine(),
              scanner.nextLine(),
              scanner.nextLine()
      ))
    }
  }

}
