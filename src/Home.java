import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField txt_name;
	private JTextField txt_email;
	private JTextField txt_tktno;
	protected Component frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLocation(-159, -128);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(148, 215, 605, 395);
		contentPane.add(tabbedPane_1);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("Register Complaint", null, panel, null);
		panel.setLayout(null);
		
		final JComboBox comboCatagory = new JComboBox();
		comboCatagory.setBounds(154, 116, 171, 24);
		panel.add(comboCatagory);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("Select catagoryName from catagoryTable");
			while(rs.next()) {
				comboCatagory.addItem(rs.getString(1));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		txt_name = new JTextField();
		txt_name.setColumns(10);
		txt_name.setBounds(154, 50, 171, 22);
		panel.add(txt_name);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		lblNewLabel.setBounds(61, 46, 159, 30);
		panel.add(lblNewLabel);
		
		txt_email = new JTextField();
		txt_email.setColumns(10);
		txt_email.setBounds(155, 82, 170, 24);
		panel.add(txt_email);
		
		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(61, 89, 159, 21);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Catagory");
		lblNewLabel_2.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(61, 120, 80, 24);
		panel.add(lblNewLabel_2);
		
		
		final JTextArea txt_desc = new JTextArea();
		txt_desc.setBounds(154, 152, 378, 122);
		panel.add(txt_desc);
		
		JLabel lblNewLabel_3 = new JLabel("Message");
		lblNewLabel_3.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(61, 154, 159, 24);
		panel.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txt_name.getText().equals("") && txt_email.getText().equals("") && txt_desc.getText().equals("")) {
					JOptionPane.showMessageDialog(frame,"Enter valid data!!!");
				}
				
				else {
					String email = txt_email.getText();
					Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
					Matcher matcher = pattern.matcher(email);
					if (!matcher.matches()) {
					    // email is invalid, display an error message
					    JOptionPane.showMessageDialog(null, "Invalid email address", "Error", JOptionPane.ERROR_MESSAGE);
					    return; // stop further processing
					}
					else {
					
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
						PreparedStatement stmt=con.prepareStatement("insert into userTable (userId,userName,userEmail) values(?,?,?)");
						Random rd = new Random();
						int randomNumber = rd.nextInt(8001) + 1000;
						String str=txt_name.getText().concat(String.valueOf(randomNumber));
						stmt.setString(1,txt_name.getText().concat(String.valueOf(randomNumber)));
						stmt.setString(2,txt_name.getText());
						stmt.setString(3,txt_email.getText());
						stmt.executeQuery();
						con.setAutoCommit(true);
						Thread.sleep(1000);
						
					
						//query to retrieve catagory id
						
						
						PreparedStatement catagoryStatement=con.prepareStatement("select catagoryId from catagoryTable where catagoryName=?");
						catagoryStatement.setString(1,comboCatagory.getSelectedItem().toString());
						ResultSet rs=catagoryStatement.executeQuery();
						PreparedStatement stmt2=con.prepareStatement("insert into ticketTable (ticketId,userId,description,catagoryId,status,key) values(?,?,?,?,'On Hold',?)");
						stmt2.setString(1,"tkt"+txt_name.getText().concat(String.valueOf(randomNumber)));
						stmt2.setString(2, str);
						stmt2.setString(3,txt_desc.getText() );
						if(rs.next()) {
							stmt2.setString(4, rs.getString(1));
						}
						
						stmt2.setString(5, "On hold");
						
						Random rd2 = new Random();
						int randomNumber2 = rd2.nextInt(8001) + 1000;
						stmt2.setString(5, String.valueOf(randomNumber2));
						stmt2.executeQuery();
						//
						
						
						//setting date
						DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
						Calendar obj = Calendar.getInstance();
						String strDate = formatter.format(obj.getTime());
						
						PreparedStatement stmt3=con.prepareStatement("insert into tickethandle (TICKETHANDLEID,TICKETID,STARTDATE) values(?,?,?)");
						stmt3.setString(1,"tkthandl"+txt_name.getText().concat(String.valueOf(randomNumber)));
						stmt3.setString(2,"tkt"+txt_name.getText().concat(String.valueOf(randomNumber)));
						stmt3.setString(3,strDate);
						stmt3.executeQuery();
						JOptionPane.showMessageDialog(frame,"Thank you for letting us know.\nTicket no: "+"tkt"+txt_name.getText().concat(String.valueOf(randomNumber))+"\nTicket Status: Processing\nOne time Key: "+String.valueOf(randomNumber2)+" Share this key only if the issue is resolved.");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
				}
				
				
			}
		});
		btnNewButton.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		btnNewButton.setBackground(new Color(64, 128, 128));
		btnNewButton.setBounds(154, 284, 171, 30);
		panel.add(btnNewButton);
		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setBounds(164, -145, 535, 625);
		panel.add(lblNewLabel_10);
		lblNewLabel_10.setOpaque(true);
		lblNewLabel_10.setIcon(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setFont(new Font("Lucida Handwriting", Font.PLAIN, 10));
		tabbedPane_1.addTab("Complaint Status", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Ticket No:");
		lblNewLabel_4.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(138, 108, 117, 27);
		panel_1.add(lblNewLabel_4);
		
		txt_tktno = new JTextField();
		txt_tktno.setBounds(223, 108, 183, 26);
		panel_1.add(txt_tktno);
		txt_tktno.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select tkt.ticketid,status,tkth.Agentid,tkt.description,to_char(tkth.startdate,'DD/MM/YYYY') from tickettable tkt left join tickethandle tkth on tkt.ticketid=tkth.ticketid where tkt.ticketid='"+txt_tktno.getText()+"'");
					String Agentname="";
					String ticketid="";
					String status="";
					String description="";
					String tktDate="";
					String phone="";
					if(rs.next()) {
						 ticketid=rs.getString(1);
						 status=rs.getString(2);
						 description=rs.getString(4);
						 tktDate=rs.getString(5);
					}
					ResultSet rsAgentName=stmt.executeQuery("Select agentName,phone from agenttable where agentid='"+rs.getString(3)+"'");
					if(rsAgentName.next()==true) {
						Agentname=rsAgentName.getString(1);
						phone=rsAgentName.getString(2);
					}
					JOptionPane.showMessageDialog(frame,"Ticket no: "+ ticketid+"\nStatus of the ticket: "+status+"\nIssue described: "+description+"\nIssue Registered on : "+tktDate+"\nContact :"+Agentname+"\nPhone: "+phone);
				}
				catch(Exception e3) {
					System.out.println(e3);
				}
				
			}
		});
		btnNewButton_1.setBounds(223, 145, 102, 27);
		panel_1.add(btnNewButton_1);
		
		JButton btn_login = new JButton("Agent Login");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agentLogin al = new agentLogin();
				al.setVisible(true);
				setVisible(false);
			}
		});
		
		btn_login.setFont(new Font("Lucida Handwriting", Font.BOLD, 15));
		btn_login.setBounds(1217, 360, 284, 43);
		contentPane.add(btn_login);
		
		JLabel lblNewLabel_5 = new JLabel("Service Now");
		lblNewLabel_5.setFont(new Font("Forte", Font.PLAIN, 60));
		lblNewLabel_5.setBounds(636, 34, 519, 156);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBackground(new Color(51, 153, 255));
		lblNewLabel_6.setBounds(-19, -44, 1195, 889);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBackground(new Color(0, 153, 153));
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setBounds(1156, 0, 443, 865);
		contentPane.add(lblNewLabel_7);
		
		
		
		
		
		
	}
}
