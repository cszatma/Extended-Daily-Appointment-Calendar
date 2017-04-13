import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AppointmentFrame extends JFrame {

	enum ContactPanelAction {
		FIND, CLEAR
	}

	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 800;
	public static final int HGAP = 5;
	public static final int VGAP = 5;

	private Calendar currentDate;
	private SimpleDateFormat dateFormat;
	private final ArrayList<Appointment> appointments;
	private final Contacts contacts;
	private Person selectedPerson = null; // Person the user has currently found

	private JLabel currentDateLabel; //Label at the top which shows the current date
	private JTextArea appointmentsTextArea; //Text Area that shows all the appointments for the current date

	private JTextField dayField;
	private JTextField monthField;
	private JTextField yearField;
	private JTextField hourField;
	private JTextField minuteField;

	private JButton previousDayButton;
	private JButton nextDayButton;
	private JButton showButton;
	private JButton createButton;
	private JButton cancelButton;
	private JButton recallButton;

	private JTextArea descriptionArea;

	/* Contact Panel Objects */
	private JTextField lastNameField;
	private JTextField firstNameField;
	private JTextField telephoneNumberField;
	private JTextField emailField;
	private JTextField addressField;

	private JButton findButton;
	private JButton clearButton;
	/* End Contact Objects */

	//Constructor, set up main parts of frame
	public AppointmentFrame() {
		currentDate = new GregorianCalendar();
		dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
		appointments = new ArrayList<Appointment>();
		contacts = new Contacts();
		setupContacts();

		this.setLayout(new BorderLayout());

		currentDateLabel = new JLabel(dateFormat.format(currentDate.getTime()));
		this.add(currentDateLabel, BorderLayout.NORTH);

		appointmentsTextArea = new JTextArea();
		appointmentsTextArea.setEditable(false);
		this.add(appointmentsTextArea, BorderLayout.CENTER);

		JPanel leftControlPanel = new JPanel(new GridLayout(2, 1));
		setUpLeftControlPanel(leftControlPanel);
		this.add(leftControlPanel, BorderLayout.SOUTH);

		JPanel rightControlPanel = new JPanel(new GridLayout(2, 1));
		setUpRightControlPanel(rightControlPanel);
		this.add(rightControlPanel, BorderLayout.WEST);

		printAppointments();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	private void setUpLeftControlPanel(JPanel panel) {
		JPanel datePanel = setUpDatePanel();
		datePanel.setBorder(BorderFactory.createTitledBorder("Date"));
		panel.add(datePanel);
		JPanel actionPanel = setUpActionPanel();
		actionPanel.setBorder(BorderFactory.createTitledBorder("Appointment"));
		panel.add(actionPanel);
	}

	private void setUpRightControlPanel(JPanel panel) {
		JPanel contactPanel = setUpContactPanel();
		contactPanel.setBorder(BorderFactory.createTitledBorder("Contact"));
		panel.add(contactPanel);

		JPanel descriptionPanel = setUpDescriptionPanel();
		descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));
		panel.add(descriptionPanel);
	}

	private JPanel setUpDatePanel() {
		JPanel mainPanel = new JPanel(new GridLayout(3, 1));
		JPanel subPanel1 = new JPanel(new GridLayout(1, 3));
		previousDayButton = new JButton("<");
		previousDayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDate.set(Calendar.DAY_OF_MONTH, currentDate.get(Calendar.DAY_OF_MONTH) - 1);
				currentDateLabel.setText(dateFormat.format(currentDate.getTime()));
				printAppointments();
			}
		});
		nextDayButton = new JButton(">");
		nextDayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDate.set(Calendar.DAY_OF_MONTH, currentDate.get(Calendar.DAY_OF_MONTH) + 1);
				currentDateLabel.setText(dateFormat.format(currentDate.getTime()));
				printAppointments();
			}
		});
		subPanel1.add(previousDayButton);
		subPanel1.add(nextDayButton);
		mainPanel.add(subPanel1);

		JPanel subPanel2 = new JPanel(new GridLayout(1, 6));
		JLabel dayLabel = new JLabel("Day");
		JLabel monthLabel = new JLabel("Month");
		JLabel yearLabel = new JLabel("Year");
		dayField = new JTextField();
		monthField = new JTextField();
		yearField = new JTextField();
		subPanel2.add(dayLabel);
		subPanel2.add(dayField);
		subPanel2.add(monthLabel);
		subPanel2.add(monthField);
		subPanel2.add(yearLabel);
		subPanel2.add(yearField);
		mainPanel.add(subPanel2);

		JPanel subPanel3 = new JPanel(new GridLayout(1, 1));
		showButton = new JButton("Show");
		showButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				currentDate = new GregorianCalendar(Integer.parseInt(yearField.getText()), Integer.parseInt(monthField.getText()) - 1, Integer.parseInt(dayField.getText()));
				currentDateLabel.setText(dateFormat.format(currentDate.getTime()));
				printAppointments();
			}
		});
		subPanel3.add(showButton);
		mainPanel.add(subPanel3);

		return mainPanel;
	}

	private JPanel setUpActionPanel() {
		JPanel mainPanel = new JPanel(new GridLayout(2, 1));

		JPanel subPanel1 = new JPanel(new GridLayout(1, 4));
		JLabel hourLabel = new JLabel("Hour");
		JLabel minuteLabel = new JLabel("Minute");
		hourField = new JTextField();
		minuteField = new JTextField();

		subPanel1.add(hourLabel);
		subPanel1.add(hourField);
		subPanel1.add(minuteLabel);
		subPanel1.add(minuteField);
		mainPanel.add(subPanel1);

		JPanel subPanel2 = new JPanel(new GridLayout(1, 2));
		createButton = new JButton("CREATE");
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createAppointment();
			}
		});
		cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelAppointment();
			}
		});
		recallButton = new JButton("RECALL");
		recallButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				recallAppointment();
			}
		});
		subPanel2.add(createButton);
		subPanel2.add(cancelButton);
		mainPanel.add(subPanel2);

		return mainPanel;
	}

	private JPanel setUpDescriptionPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		descriptionArea = new JTextArea();
		mainPanel.add(descriptionArea, BorderLayout.CENTER);

		return mainPanel;
	}

	private JPanel setUpContactPanel(){
		JPanel mainPanel = new JPanel(new GridLayout(3, 1));
		JPanel topSubPanel = new JPanel(new GridLayout(1, 2));
		JPanel innerSubPanel = new JPanel(new GridLayout(4, 1));

		//Setup left side
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameField = new JTextField();
		JLabel telLabel = new JLabel("Telephone Number");
		telephoneNumberField = new JTextField();
		innerSubPanel.add(lastNameLabel);
		innerSubPanel.add(lastNameField);
		innerSubPanel.add(telLabel);
		innerSubPanel.add(telephoneNumberField);
		topSubPanel.add(innerSubPanel);

		//Setup left side
		innerSubPanel = new JPanel(new GridLayout(4, 1));
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameField = new JTextField();
		JLabel emailLabel = new JLabel("Email");
		emailField = new JTextField();
		innerSubPanel.add(firstNameLabel);
		innerSubPanel.add(firstNameField);
		innerSubPanel.add(emailLabel);
		innerSubPanel.add(emailField);
		topSubPanel.add(innerSubPanel);

		//Setup address Area
		JPanel middleSubPanel = new JPanel(new GridLayout(2, 1));
		JLabel addressLabel = new JLabel("Address");
		addressField = new JTextField();
		middleSubPanel.add(addressLabel);
		middleSubPanel.add(addressField);

		//Setup buttons
		JPanel bottomSubPanel = new JPanel(new GridLayout(1, 2));
		findButton = new JButton("Find");
		findButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contactHandler(ContactPanelAction.FIND);
			}
		});
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contactHandler(ContactPanelAction.CLEAR);
			}
		});
		bottomSubPanel.add(findButton);
		bottomSubPanel.add(clearButton);

		//Add all subpanels
		mainPanel.add(topSubPanel);
		mainPanel.add(middleSubPanel);
		mainPanel.add(bottomSubPanel);
		return mainPanel;
	}

	//Prints all the appointments in the arraylist to the main textarea
	private void printAppointments() {
		appointmentsTextArea.setText("");
		Collections.sort(appointments);
		for (Appointment a : appointments) {
			if (a.occursOnDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH))) {
				appointmentsTextArea.append(a.print() + "\n\n");
			}
		}
	}

	//Returns an appointment if one exists at the specified time
	private Appointment findAppointment(int year, int month, int day, int hour, int minute) {
		for (Appointment a : appointments) {
			if (a.occursOn(year, month, day, hour, minute)) {
				return a;
			}
		}
		return null;
	}

	//Creates a new appointments based on what the user entered
	private void createAppointment() {
		Appointment a = getAppointment();
		if (a == null) {
			int minute = (minuteField.getText().equals("")) ? 0 : Integer.parseInt(minuteField.getText());
			GregorianCalendar date = new GregorianCalendar(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH),
					Integer.parseInt(hourField.getText()), minute);
			a = new Appointment(date, descriptionArea.getText(), selectedPerson);
			appointments.add(a);
		} else {
			descriptionArea.setText("CONFLICT");
		}

		printAppointments();
	}

	//Cancels an appointment at a time the user specified
	private void cancelAppointment() {
		Appointment a = getAppointment();
		if (a != null) {
			appointments.remove(a);
		}
		printAppointments();
	}

	private void recallAppointment() {
		//TODO Add ability to recall appointments
	}

	//Helper method to return appointment at time user entered
	private Appointment getAppointment() {
		int minute = (minuteField.getText().equals("")) ? 0 : Integer.parseInt(minuteField.getText());
		return findAppointment(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH),
				Integer.parseInt(hourField.getText()), minute);
	}

	private void contactHandler(ContactPanelAction action) {
		//TODO handle find and clear button clicks
	}

	private void setupContacts() {
		try {
			contacts.readContactsFile();
		} catch (FileNotFoundException e) {
			descriptionArea.setText("Unable to find contacts.txt file.");
		} catch (Exception e) {
			descriptionArea.setText("Unable to read file.");
		}
	}

}
