package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import dao.AuthDAO;
import dao.impl.AuthDAOImpl;

import javax.swing.JButton;
import java.awt.event.InputMethodListener;
import java.sql.SQLException;
import java.awt.event.InputMethodEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JLabel lblNewLabel_2;
	private JTextField txtEnterPassword;
	private AuthDAO authDAO;

	public Login() {
		this.authDAO=new AuthDAOImpl();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 370);
		contentPane = new JPanel();
		setLocationRelativeTo(null);
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(34, 38, 76));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 27));
		lblNewLabel.setBounds(148, 11, 107, 32);
	
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_1.setBounds(36, 73, 82, 14);
		contentPane.add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		txtUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				JTextField txTextField=(JTextField)e.getComponent();
				if(txTextField.getText().equals("Enter username"))
					txTextField.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent e)
			{
				JTextField txTextField=(JTextField)e.getComponent();
				if(txTextField.getText().isBlank())
					txTextField.setText("Enter username");
	
				
			}
		});
	
		txtUsername.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		txtUsername.setText("Enter username");
		txtUsername.setForeground(new Color(255, 255, 255));
		txtUsername.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		txtUsername.setBackground(new Color(34, 38, 76));
		txtUsername.setBounds(36, 92, 285, 32);
		
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_2.setBounds(36, 148, 82, 14);
		contentPane.add(lblNewLabel_2);
		
		txtEnterPassword = new JPasswordField();
		txtEnterPassword.setText("Enter password");
		txtEnterPassword.setForeground(Color.WHITE);
		txtEnterPassword.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		txtEnterPassword.setColumns(10);
		txtEnterPassword.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		txtEnterPassword.setBackground(new Color(34, 38, 76));
		txtEnterPassword.setBounds(36, 167, 285, 32);
		
		txtEnterPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				JTextField txTextField=(JTextField)e.getComponent();
				if(txTextField.getText().equals("Enter password"))
					txTextField.setText("");
			}
			
			@Override
			public void focusLost(FocusEvent e)
			{
				JTextField txTextField=(JTextField)e.getComponent();
				if(txTextField.getText().isBlank())
					txTextField.setText("Enter password");
	
				
			}
		});
		contentPane.add(txtEnterPassword);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(e->loginClick(e));
		btnNewButton.setUI(new BasicButtonUI());
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBackground(new Color(99, 69, 155));
		btnNewButton.setBounds(129, 244, 107, 37);
		contentPane.add(btnNewButton);
	}
	
	private void loginClick(ActionEvent e)
	{
		String username=txtUsername.getText();
		String password=txtEnterPassword.getText();
		
		boolean status=false;
		try {
			status=this.authDAO.login(username,password);
		} catch(SQLException a) {
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(status)
		{
			HomePage homePage=new HomePage();
			this.setVisible(false);
			homePage.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Pogresni kredencijali", "Greška",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	
	
	
}
