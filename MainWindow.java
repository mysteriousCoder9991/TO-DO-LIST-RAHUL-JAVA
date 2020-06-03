package main1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainWindow {

	private JFrame frame;
	private JTextField TaskT;
	private JTextField taskD;
	private JTextField timeField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.RED);
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("To Do App :----- Made By Rahul");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel head = new JLabel("ToDoApp");
		head.setHorizontalAlignment(SwingConstants.CENTER);
		head.setBounds(82, 11, 233, 14);
		frame.getContentPane().add(head);
		
		JLabel TaskTitL = new JLabel("Task Title");
		TaskTitL.setHorizontalAlignment(SwingConstants.CENTER);
		TaskTitL.setBounds(10, 37, 75, 14);
		frame.getContentPane().add(TaskTitL);
		
		TaskT = new JTextField();
		TaskT.setBounds(133, 36, 253, 20);
		frame.getContentPane().add(TaskT);
		TaskT.setColumns(10);
		
		JLabel TDL = new JLabel("Task Description");
		TDL.setHorizontalAlignment(SwingConstants.CENTER);
		TDL.setBounds(10, 72, 86, 14);
		frame.getContentPane().add(TDL);
		
		JTextArea taskDescrip = new JTextArea();
		taskDescrip.setBounds(133, 67, 253, 54);
		frame.getContentPane().add(taskDescrip);
		
		JLabel lblNewLabel = new JLabel("Task Deadline");
		lblNewLabel.setBounds(10, 139, 86, 14);
		frame.getContentPane().add(lblNewLabel);
		
		taskD = new JTextField();
		taskD.setBounds(133, 136, 253, 20);
		frame.getContentPane().add(taskD);
		taskD.setColumns(10);
		
		JButton submit = new JButton("Add Tasks");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title=TaskT.getText();
				String dead=taskD.getText();
				String descrip=taskDescrip.getText();
				String dateT=String.valueOf(java.util.Calendar.getInstance().getTime());  
				try {
				TaskT.setText("");taskD.setText("");taskDescrip.setText("");
				Class.forName("oracle.jdbc.driver.OracleDriver");// loading of the class file
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Rahul",
						"Rahul"); // establishing the connection
				String tcq = "create table tasks(taskTitle varchar2(50) Not Null, Task_Description Varchar2(300)Not Null, Task_Deadline Varchar2(30),TaskSaved varchar2(30))";
				String in="insert into tasks values('"+title+"','"+dead+"','"+descrip+"','"+dateT+"')";
				Statement stmt = con.createStatement();// creating statement object
				//stmt.execute(tcq);
				stmt.execute(in);
				
				JOptionPane.showMessageDialog(frame, "Task Added Successfully");
			}
				catch(Exception r)
				{
					System.out.println("Exception = "+r);
				}
				}
		});
		submit.setBounds(82, 180, 125, 23);
		frame.getContentPane().add(submit);
		
		JButton view = new JButton("View Tasks");
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");// loading of the class file
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Rahul",
							"Rahul"); // 
					String res="select * from tasks";
					Statement stmt = con.createStatement();// creating statement object
					ResultSet rs=stmt.executeQuery(res);
					String sol="The tasks to be done are : \n";
					int i=1;
					while(rs.next())
					{
						sol+=i+"\n"+rs.getString(1)+"\n"+rs.getString(2)+"\n"+rs.getString(3)+"\n"+rs.getString(4)+"\n";
						i++;
					}
					JOptionPane.showMessageDialog(frame,sol);
			}
				catch(Exception  r1)
				{
					System.out.println("Exception caught "+r1);
				}
			}
		});
		view.setBounds(254, 180, 132, 23);
		frame.getContentPane().add(view);
		
		timeField = new JTextField();
		timeField.setBackground(Color.LIGHT_GRAY);
		timeField.setHorizontalAlignment(SwingConstants.CENTER);
		timeField.setEditable(false);
		timeField.setText("Current Time : "+String.valueOf(java.util.Calendar.getInstance().getTime()));
		timeField.setBounds(10, 230, 376, 20);
		frame.getContentPane().add(timeField);
		timeField.setColumns(10);
		
	}
}
