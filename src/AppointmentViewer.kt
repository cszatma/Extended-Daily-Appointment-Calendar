import javax.swing.JFrame;

/**
   This program allows the user to view font effects.
*/
fun main() {
   val frame = AppointmentFrame();
   frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
   frame.title = "Appointments"
   frame.isVisible = true;
}
