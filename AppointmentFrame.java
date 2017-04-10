import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AppointmentFrame extends JFrame {
	
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 800;
	public static final int HGAP = 5;
	public static final int VGAP = 5;
	
	private Calendar currentDate;
	private SimpleDateFormat dateFormat;
	private final ArrayList<Appointment> appointments;
	
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
	
	private JTextArea descriptionArea;
	
	//Constructor, set up main parts of frame
	public AppointmentFrame() {
		currentDate = new GregorianCalendar();
		dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
		appointments = generateRandomAppointments();
		
		this.setLayout(new BorderLayout());
		
		currentDateLabel = new JLabel(dateFormat.format(currentDate.getTime()));
		this.add(currentDateLabel, BorderLayout.NORTH);
		
		appointmentsTextArea = new JTextArea();
		appointmentsTextArea.setEditable(false);
		this.add(appointmentsTextArea, BorderLayout.CENTER);
		
		JPanel controlPanel = new JPanel(new GridLayout(3, 1));
		setUpControlPanel(controlPanel);
		this.add(controlPanel, BorderLayout.SOUTH);
		
		printAppointments();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	private void setUpControlPanel(JPanel panel) {
		JPanel datePanel = setUpDatePanel();
		datePanel.setBorder(BorderFactory.createTitledBorder("Date"));
		panel.add(datePanel);
		JPanel actionPanel = setUpActionPanel();
		actionPanel.setBorder(BorderFactory.createTitledBorder("Action"));
		panel.add(actionPanel);
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
			a = new Appointment(date, descriptionArea.getText());
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
	
	//Helper method to return appointment at time user entered
	private Appointment getAppointment() {
		int minute = (minuteField.getText().equals("")) ? 0 : Integer.parseInt(minuteField.getText());
		return findAppointment(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH), 
				Integer.parseInt(hourField.getText()), minute);
	}
	
	//Helper method to generate random appointments at the start of the program for demonstrational purposes
	private static ArrayList<Appointment> generateRandomAppointments() {
		 ArrayList<Appointment> appts = new ArrayList<Appointment>();
		 GregorianCalendar date = new GregorianCalendar();
		 appts.add(new Appointment(date, "Mark Assignment"));
		 date.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) + 2);
		 appts.add(new Appointment(date, "Lecture"));
		 date.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) - 3);
		 appts.add(new Appointment(date, "Quiz"));
		 date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) + 1);
		 date.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) + 5);
		 appts.add(new Appointment(date, "Exam"));
		 date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) - 2);
		 date.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) - 6);
		 appts.add(new Appointment(date, "Submit Assignment"));
		 return appts;
	}
	
}
