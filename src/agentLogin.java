import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.UIManager;
import javax.swing.JTextPane;
import java.awt.Panel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.sql.*;
import javax.swing.JPasswordField;
public class agentLogin extends JFrame {

	protected static final Component frame = null;
	private JPanel contentPane;
	JTextField txt_username;
	JPasswordField txt_password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					agentLogin frame = new agentLogin();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public agentLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_username = new JLabel("Username");
		lbl_username.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		lbl_username.setBounds(581, 331, 115, 32);
		contentPane.add(lbl_username);
		
		JLabel lbl_password = new JLabel("Password");
		lbl_password.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		lbl_password.setBounds(581, 387, 88, 37);
		contentPane.add(lbl_password);
		
		txt_username = new JTextField();
		txt_username.setBounds(679, 334, 188, 32);
		contentPane.add(txt_username);
		txt_username.setColumns(10);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username=txt_username.getText();
				String password=txt_password.getText();
				
				
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","mca");
					Statement stmt= con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from agenttable where Email='"+username+"' and agentpassword='"+password+"'");
					if(rs.next()) {
						agentHome ah=new agentHome();
						username=txt_username.getText();
						ah.lblNewLabel.setText(username);
						ah.setVisible(true);
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(frame,"Invalid Login Credential!!");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btn_login.setBackground(UIManager.getColor("Button.foreground"));
		btn_login.setFont(new Font("Lucida Handwriting", Font.BOLD, 15));
		btn_login.setBounds(710, 454, 114, 37);
		contentPane.add(btn_login);
		
		JButton btn_back_login = new JButton("Back");
		btn_back_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Home h=new Home();
				h.setVisible(true);
				setVisible(false);
			}
		});
		btn_back_login.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));
		btn_back_login.setBounds(30, 25, 103, 32);
		contentPane.add(btn_back_login);
		
		txt_password = new JPasswordField();
		txt_password.setBounds(679, 392, 188, 32);
		contentPane.add(txt_password);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(0, 153, 153));
		lblNewLabel_1.setBounds(0, 226, 1540, 349);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Agent Login");
		lblNewLabel_3.setFont(new Font("Forte", Font.BOLD, 60));
		lblNewLabel_3.setBounds(597, 37, 378, 179);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(0, 153, 255));
		lblNewLabel.setIcon(null);
		lblNewLabel.setBounds(-441, -157, 1981, 1002);
		contentPane.add(lblNewLabel);
		
		
		
		
		
		
		
		
		
		
	}
}
