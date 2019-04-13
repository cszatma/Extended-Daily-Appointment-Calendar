import java.awt.BorderLayout
import java.awt.GridLayout
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Stack
import java.io.FileNotFoundException

import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.JOptionPane
import javax.swing.JScrollPane

class AppointmentFrame : JFrame() {

	private enum class ContactPanelAction {
		FIND, CLEAR
	}

	private companion object {
		const val FRAME_WIDTH = 800
		const val FRAME_HEIGHT = 800
		const val HGAP = 5
		const val VGAP = 5
	}

	private var currentDate: Calendar
	private var dateFormat: SimpleDateFormat
	private val appointments: ArrayList<Appointment>
	private val contacts: Contacts
	private var selectedPerson: Person? = null // Person the user has currently found

	private var currentDateLabel: JLabel //Label at the top which shows the current date
	private var appointmentsTextArea: JTextArea //Text Area that shows all the appointments for the current date
	private var appointmentStack: Stack<Appointment>

	private lateinit var dayField: JTextField
	private lateinit var monthField: JTextField
	private lateinit var yearField: JTextField
	private lateinit var hourField: JTextField
	private lateinit var minuteField: JTextField

	private lateinit var previousDayButton: JButton
	private lateinit var nextDayButton: JButton
	private lateinit var showButton: JButton
	private lateinit var createButton: JButton
	private lateinit var cancelButton: JButton
	private lateinit var recallButton: JButton

	private lateinit var descriptionArea: JTextArea

	/* Contact Panel Objects */
	private lateinit var lastNameField: JTextField
	private lateinit var firstNameField: JTextField
	private lateinit var telephoneNumberField: JTextField
	private lateinit var emailField: JTextField
	private lateinit var addressField: JTextField

	private lateinit var findButton: JButton
	private lateinit var clearButton: JButton
	/* End Contact Objects */

	//Constructor, set up main parts of frame
	init {
		currentDate = GregorianCalendar()
		dateFormat = SimpleDateFormat("EEE, MMM d, yyyy")
		appointments = ArrayList()
		contacts = Contacts()
		setupContacts()
		appointmentStack = Stack()

		this.setLayout(GridLayout(1, 2))
		val leftPanel = JPanel(GridLayout(2, 1))
		val rightPanel = JPanel(GridLayout(1, 1))

		currentDateLabel = JLabel(dateFormat.format(currentDate.time))

		appointmentsTextArea = JTextArea()
		appointmentsTextArea.isEditable = false
		val scrollArea = JScrollPane(appointmentsTextArea)
		val mainViewPanel = JPanel(GridLayout(2, 1))
		mainViewPanel.add(currentDateLabel)
		mainViewPanel.add(scrollArea)
		leftPanel.add(mainViewPanel)

		val leftControlPanel = JPanel(GridLayout(2, 1))
		setUpLeftControlPanel(leftControlPanel)
		leftPanel.add(leftControlPanel)

		val rightControlPanel = JPanel(GridLayout(2, 1))
		setUpRightControlPanel(rightControlPanel)
		rightPanel.add(rightControlPanel)

		this.add(leftPanel)
		this.add(rightPanel)

		printAppointments()
		setSize(FRAME_WIDTH, FRAME_HEIGHT)
	}

	private fun setUpLeftControlPanel(panel: JPanel) {
		val datePanel = setUpDatePanel()
		datePanel.setBorder(BorderFactory.createTitledBorder("Date"))
		panel.add(datePanel)
		val actionPanel = setUpActionPanel()
		actionPanel.setBorder(BorderFactory.createTitledBorder("Appointment"))
		panel.add(actionPanel)
	}

	private fun setUpRightControlPanel(panel: JPanel) {
		val contactPanel = setUpContactPanel()
		contactPanel.setBorder(BorderFactory.createTitledBorder("Contact"))
		panel.add(contactPanel)

		val descriptionPanel = setUpDescriptionPanel()
		descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"))
		panel.add(descriptionPanel)
	}

	private fun setUpDatePanel(): JPanel {
		val mainPanel = JPanel(GridLayout(3, 1))
		val subPanel1 = JPanel(GridLayout(1, 3))
		previousDayButton = JButton("<")
		previousDayButton.addActionListener {
			currentDate.dayOfMonth = currentDate.dayOfMonth - 1
			currentDateLabel.text = dateFormat.format(currentDate.time)
			printAppointments()
		}

		nextDayButton = JButton(">")
		nextDayButton.addActionListener {
			currentDate.dayOfMonth = currentDate.dayOfMonth + 1
			currentDateLabel.text = dateFormat.format(currentDate.time)
			printAppointments()
		}

		subPanel1.add(previousDayButton)
		subPanel1.add(nextDayButton)
		mainPanel.add(subPanel1)

		val subPanel2 = JPanel(GridLayout(1, 6))
		val dayLabel = JLabel("Day")
		val monthLabel = JLabel("Month")
		val yearLabel = JLabel("Year")
		dayField = JTextField()
		monthField = JTextField()
		yearField = JTextField()
		subPanel2.add(dayLabel)
		subPanel2.add(dayField)
		subPanel2.add(monthLabel)
		subPanel2.add(monthField)
		subPanel2.add(yearLabel)
		subPanel2.add(yearField)
		mainPanel.add(subPanel2)

		val subPanel3 = JPanel(GridLayout(1, 1))
		showButton = JButton("Show")
		showButton.addActionListener {
			currentDate = GregorianCalendar(yearField.text.toInt(), monthField.text.toInt() - 1, dayField.text.toInt())
			currentDateLabel.text = dateFormat.format(currentDate.time)
			printAppointments()
		}

		subPanel3.add(showButton)
		mainPanel.add(subPanel3)

		return mainPanel
	}

	private fun setUpActionPanel(): JPanel {
		val mainPanel = JPanel(GridLayout(2, 1))

		val subPanel1 = JPanel(GridLayout(1, 4))
		val hourLabel = JLabel("Hour")
		val minuteLabel = JLabel("Minute")
		hourField = JTextField()
		minuteField = JTextField()

		subPanel1.add(hourLabel)
		subPanel1.add(hourField)
		subPanel1.add(minuteLabel)
		subPanel1.add(minuteField)
		mainPanel.add(subPanel1)

		val subPanel2 = JPanel(GridLayout(1, 3))
		createButton = JButton("CREATE")
		createButton.addActionListener {
			createAppointment()
		}

		cancelButton = JButton("CANCEL")
		cancelButton.addActionListener {
			cancelAppointment()
		}

		recallButton = JButton("RECALL")
		recallButton.addActionListener {
			recallAppointment()
		}

		subPanel2.add(createButton)
		subPanel2.add(cancelButton)
		subPanel2.add(recallButton)
		mainPanel.add(subPanel2)

		return mainPanel
	}

	private fun setUpDescriptionPanel(): JPanel {
		val mainPanel = JPanel(BorderLayout())

		descriptionArea = JTextArea()
		mainPanel.add(descriptionArea, BorderLayout.CENTER)

		return mainPanel
	}

	private fun setUpContactPanel(): JPanel {
		val mainPanel = JPanel(GridLayout(3, 1))
		val topSubPanel = JPanel(GridLayout(1, 2))
		var innerSubPanel = JPanel(GridLayout(4, 1))

		//Setup left side
		val lastNameLabel = JLabel("Last Name")
		lastNameField = JTextField()
		val telLabel = JLabel("Telephone Number")
		telephoneNumberField = JTextField()
		innerSubPanel.add(lastNameLabel)
		innerSubPanel.add(lastNameField)
		innerSubPanel.add(telLabel)
		innerSubPanel.add(telephoneNumberField)
		topSubPanel.add(innerSubPanel)

		//Setup left side
		innerSubPanel = JPanel(GridLayout(4, 1))
		val firstNameLabel = JLabel("First Name")
		firstNameField = JTextField()
		val emailLabel = JLabel("Email")
		emailField = JTextField()
		innerSubPanel.add(firstNameLabel)
		innerSubPanel.add(firstNameField)
		innerSubPanel.add(emailLabel)
		innerSubPanel.add(emailField)
		topSubPanel.add(innerSubPanel)

		//Setup address Area
		val middleSubPanel = JPanel(GridLayout(2, 1))
		val addressLabel = JLabel("Address")
		addressField = JTextField()
		middleSubPanel.add(addressLabel)
		middleSubPanel.add(addressField)

		//Setup buttons
		val bottomSubPanel = JPanel(GridLayout(1, 2))
		findButton = JButton("Find")
		findButton.addActionListener {
			contactHandler(ContactPanelAction.FIND)
		}

		clearButton = JButton("Clear")
		clearButton.addActionListener {
			contactHandler(ContactPanelAction.CLEAR)
		}

		bottomSubPanel.add(findButton)
		bottomSubPanel.add(clearButton)

		//Add all subpanels
		mainPanel.add(topSubPanel)
		mainPanel.add(middleSubPanel)
		mainPanel.add(bottomSubPanel)
		return mainPanel
	}

	//Prints all the appointments in the arraylist to the main textarea
	private fun printAppointments() {
		appointmentsTextArea.text = ""
		appointments.sort()

		for (a in appointments) {
			if (a.occursOnDay(currentDate)) {
				appointmentsTextArea.append("${a.print()}\n\n")
			}
		}
	}

	//Creates a new appointments based on what the user entered
	private fun createAppointment() {
		if (getAppointment() != null) {
			descriptionArea.setText("CONFLICT")
			return
		}

		if (minuteField.text == "") {
			minuteField.text = "0"
		}

		if (!checkInput(hourField) || !checkInput(minuteField)) {
			return
		}

		val date = GregorianCalendar(currentDate.year, currentDate.month, currentDate.dayOfMonth,
				hourField.text.toInt(), minuteField.text.toInt())
		val appointment = Appointment(date, descriptionArea.text, selectedPerson)
		appointments.add(appointment)
		appointmentStack.push(appointment)

		printAppointments()
	}

	//Cancels an appointment at a time the user specified
	private fun cancelAppointment() {
		val a = getAppointment()
		if (a == null) {
			return
		}

		appointments.remove(a)
		if (appointmentStack.peek() == a) {
			appointmentStack.pop()
		} else {
			val tempStack = Stack<Appointment>()
			var tempAppointment = appointmentStack.pop()

			while (tempAppointment != a) {
				tempStack.push(tempAppointment)
				tempAppointment = appointmentStack.pop()
			}

			while (!tempStack.isEmpty()) {
				appointmentStack.push(tempStack.pop())
			}
		}

		printAppointments()
	}

	private fun recallAppointment() {
		if (appointmentStack.isEmpty()) {
			return
		}

		val appt = appointmentStack.peek()
		currentDate = appt.date
		printAppointments()
		currentDateLabel.text = dateFormat.format(currentDate.time)
		hourField.text = appt.date.hourOfDay.toString()
		minuteField.text = appt.date.minute.toString()
		descriptionArea.text = appt.description
	}

	//Helper method to return appointment at time user entered
	private fun getAppointment(): Appointment? {
		val minute = if (minuteField.text == "") 0 else minuteField.text.toInt()
		return appointments.find {
			it.occursOnDayAtTime(currentDate, hourField.text.toInt(), minute)
		}
	}

	//Handles Find and Clear button actions
	private fun contactHandler(action: ContactPanelAction) {
		var p: Person? = null

		if (lastNameField.text != "" && firstNameField.text != "") {
			p = contacts.findPerson(lastNameField.text, firstNameField.text)
		} else if (telephoneNumberField.text != "") {
			p = contacts.findPersonWithNumber(telephoneNumberField.text)
		} else if (emailField.text != "") {
			p = contacts.findPersonWithEmail(emailField.text)
		}
		if (p == null && action == ContactPanelAction.FIND) {
			return
		}

		val isFind = action == ContactPanelAction.FIND

		lastNameField.text = if (isFind) p?.lastName else ""
		firstNameField.text = if (isFind) p?.firstName else ""
		telephoneNumberField.text = if (isFind) p?.telephoneNumber else ""
		emailField.text = if (isFind) p?.email else ""
		addressField.text = if (isFind) p?.address else ""
		selectedPerson = if (isFind) p else null
	}

	//Gets contacts from file
	private fun setupContacts() {
		try {
			contacts.readContactsFile()
		} catch (e: FileNotFoundException) {
			JOptionPane.showMessageDialog(null, "Unable to find contacts.txt file.", "Error", JOptionPane.INFORMATION_MESSAGE)
		} catch (e: Exception) {
			JOptionPane.showMessageDialog(null, "Unable to read contacts.", "Error", JOptionPane.INFORMATION_MESSAGE)
		}
	}

	private fun checkInput(field: JTextField): Boolean {
		if (!field.text.isNumeric()) {
			descriptionArea.text = "Please enter a valid positive number."
			return false
		}

		val value = field.text.toInt()

		if (field == hourField && value > 23) {
			descriptionArea.text = "Please enter a valid hour."
			return false
		} else if (field == minuteField && value > 59) {
			descriptionArea.text = "Please enter a valid minute."
			return false
		}

		return true
	}
}
