import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.security.Identity;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
public class agentHome extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	JLabel lblNewLabel;
	private JTextField userToken;
	protected Component frame;
	private JTable table_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					agentHome frame = new agentHome();
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
	public agentHome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(288, 110, 794, 389);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New Tickets", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 44, 707, 229);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No:", "Ticket ID", "Catagory", "Description", "Status"
			}
		));
		
		JButton btnNewButton = new JButton("Accept Ticket");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int column=1;
				int row=table.getSelectedRow();
				String value=table.getModel().getValueAt(row,column).toString();
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
					Statement stmt=con.createStatement();
					stmt.executeUpdate("update tickettable set status='In Progress' where ticketid='"+ value+"'");
					
					ResultSet rs=stmt.executeQuery("select agentid from agenttable where email='"+lblNewLabel.getText()+"'");
					
					String id="";
					if(rs.next()) {
						id=rs.getString(1);
					}
					//System.out.print(id);
					stmt.executeUpdate("update tickethandle set agentid='"+id+"' where ticketid='"+value+"'");
					
					stmt.close();
					con.close();
					JOptionPane.showMessageDialog(frame,"Ticket have been assigned to your name!!");
				}
				catch(Exception e4) {
					System.out.print(e4);
				}
				
			}
		});
		btnNewButton.setFont(new Font("Lucida Handwriting", Font.PLAIN, 15));
		btnNewButton.setBounds(577, 283, 174, 38);
		panel.add(btnNewButton);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					model.setRowCount(0);
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select tkt.ticketid,ctgry.catagoryname,tkt.description,tkt.status from tickettable tkt join catagorytable ctgry on tkt.catagoryid=ctgry.catagoryid  where status='On Hold'");
					String ticketid="";
					String desc="";
					String status="";
					String catName="";
					int i=1;
					while(rs.next()) {
						ticketid=rs.getString(1);
						desc=rs.getString(3);
						catName=rs.getString(2);
						status=rs.getString(4);
						String[] row= {String.valueOf(i),ticketid,catName,desc,status};
						model.addRow(row);
					    i++;
					
				}
				}catch(Exception er) {
					System.out.println(er);
				}
			}
		});
		btnRefresh.setFont(new Font("Lucida Handwriting", Font.PLAIN, 15));
		btnRefresh.setBounds(633, 10, 118, 24);
		panel.add(btnRefresh);
		try {
			DefaultTableModel model=(DefaultTableModel)table.getModel();
			model.setRowCount(0);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select tkt.ticketid,ctgry.catagoryname,tkt.description,tkt.status from tickettable tkt join catagorytable ctgry on tkt.catagoryid=ctgry.catagoryid  where status='On Hold'");
			String ticketid="";
			String desc="";
			String status="";
			String catName="";
			int i=1;
			while(rs.next()) {
				ticketid=rs.getString(1);
				desc=rs.getString(3);
				catName=rs.getString(2);
				status=rs.getString(4);
				String[] row= {String.valueOf(i),ticketid,catName,desc,status};
				model.addRow(row);
			    i++;
		}
			con.close();
			rs.close();
		}
		catch(Exception e) {
			System.out.print(e);
		}
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Resolve Tickets", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(30, 38, 728, 223);
		panel_1.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No:", "Ticket ID", "Description", "Status"
			}
		));
		
		JButton btnNewButton_2 = new JButton("Refresh");
		btnNewButton_2.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				    DefaultTableModel model = (DefaultTableModel) table_1.getModel();
				    model.setRowCount(0); // clear existing data from the table
				    Class.forName("oracle.jdbc.driver.OracleDriver");
				    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "mca");
				    Statement stmt = con.createStatement();
				    
				    ResultSet rs = stmt.executeQuery("select agentid from agenttable where email='" + lblNewLabel.getText() + "'");
				    String agentID = "";
				    if (rs.next()) {
				        agentID = rs.getString(1);
				    }
				    
				    ResultSet rs2 = stmt.executeQuery("select tkt.ticketid,tkt.description,tkt.status from tickettable tkt join tickethandle tkthdl on tkt.ticketid=tkthdl.ticketid  where tkt.status='In Progress' and tkthdl.agentid='" + agentID + "'");
				    
				    String ticketid2 = "";
				    String desc2 = "";
				    String status2 = "";
				    String catName2 = "";
				    int i = 1;
				    while (rs2.next()) {
				        ticketid2 = rs2.getString(1);
				        desc2 = rs2.getString(2);
				        status2 = rs2.getString(3);
				        String[] row2 = {String.valueOf(i), ticketid2, desc2, status2};
				        model.addRow(row2);
				        i++;
				    }
				    con.close();
				    rs.close();
				} catch (Exception e6) {
				    System.out.print(e6);
				}


				
			}
		});
		btnNewButton_2.setBounds(612, 10, 126, 21);
		panel_1.add(btnNewButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("Enter User token to Resolve the issue");
		lblNewLabel_1.setBounds(30, 259, 232, 33);
		panel_1.add(lblNewLabel_1);
		
		userToken = new JTextField();
		userToken.setBounds(30, 287, 169, 26);
		panel_1.add(userToken);
		userToken.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Resolve");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int column=1;
					int row=table_1.getSelectedRow();
					String value=table_1.getModel().getValueAt(row,column).toString();
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
					Statement stmt=con.createStatement();
					ResultSet rs4=stmt.executeQuery("select key from tickettable where ticketid='"+value.toString()+"'");
					System.out.print(value);
					if(rs4.next()) {
						if(userToken.getText().toString().equals(rs4.getString(1))){
							DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
							Calendar obj = Calendar.getInstance();
							String endDate = formatter.format(obj.getTime());
							stmt.executeUpdate("update tickettable set status='Resolved' where ticketid='"+value+"'");
							stmt.executeUpdate("update tickethandle set enddate='"+endDate+"'");
							JOptionPane.showMessageDialog(frame,"Ticket have been Resolved");
						}
						else {
							JOptionPane.showMessageDialog(frame,"Invalid Token!!");
						}
					}
				}
				catch(Exception e7) {
					System.out.print(e7);
				}
				
			}
		});
		btnNewButton_3.setFont(new Font("Lucida Handwriting", Font.PLAIN, 15));
		btnNewButton_3.setBounds(30, 323, 102, 29);
		panel_1.add(btnNewButton_3);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Closed Tickets", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane_2.setBounds(35, 42, 720, 224);
		panel_2.add(scrollPane_2);
		
		
		
		
		table_2 = new JTable();
		table_2.setEnabled(false);
		scrollPane_2.setViewportView(table_2);
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No:", "Ticket ID", "Description", "Status"
			}
		));
		
		
		JButton btnNewButton_2_1 = new JButton("Refresh");
		btnNewButton_2_1.setFont(new Font("Lucida Handwriting", Font.PLAIN, 14));
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					DefaultTableModel model=(DefaultTableModel)table_2.getModel();
					model.setRowCount(0);
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
					Statement stmt=con.createStatement();
					
					ResultSet rs=stmt.executeQuery("select agentid from agenttable where email='"+lblNewLabel.getText()+"'");
					String agentID="";
					if(rs.next()) {
						agentID=rs.getString(1);
					}
					
					ResultSet rs2=stmt.executeQuery("select tkt.ticketid,tkt.description,tkt.status from tickettable tkt join tickethandle tkthdl on tkt.ticketid=tkthdl.ticketid  where tkt.status='Resolved' and tkthdl.agentid='"+agentID+"'");
					
					String ticketid2="";
					String desc2="";
					String status2="";
					String catName2="";
					int i=1;
					while(rs2.next()) {
						ticketid2=rs2.getString(1);
						desc2=rs2.getString(2);
						status2=rs2.getString(3);
						String[] row2= {String.valueOf(i),ticketid2,desc2,status2};
						model.addRow(row2);
					    i++;
				}
					con.close();
					rs.close();
				}
				catch(Exception e8) {
					System.out.print(e8);
				}
				
				
			}
		});
		btnNewButton_2_1.setBounds(629, 10, 126, 22);
		panel_2.add(btnNewButton_2_1);
		
		
		lblNewLabel = new JLabel("displaying name");
		lblNewLabel.setFont(new Font("Lucida Handwriting", Font.PLAIN, 15));
		lblNewLabel.setBounds(1237, 10, 187, 58);
		contentPane.add(lblNewLabel);
		
		
		
		JButton btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Home h=new Home();
				h.setVisible(true);
				setVisible(false);
				
			}
		});
		btnNewButton_1.setFont(new Font("Lucida Handwriting", Font.PLAIN, 16));
		btnNewButton_1.setBounds(1414, 28, 100, 21);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(0, 153, 255));
		lblNewLabel_2.setBounds(10, 0, 1540, 845);
		contentPane.add(lblNewLabel_2);
		
		
	}
}
