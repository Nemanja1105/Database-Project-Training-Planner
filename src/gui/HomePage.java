package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.CardLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomePage extends JFrame {

	private JPanel contentPane;
	private JPanel  panel_1;
	private CardLayout cardLayout;
	public HomePage() {
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1060, 599);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(81, 85, 123));
		panel.setBounds(0, 0, 179, 560);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Home");
		btnNewButton.addActionListener(e->homePageClicked(e));
		btnNewButton.setUI(new BasicButtonUI());
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setForeground(new Color(255,255,255));
		btnNewButton.setBackground(new Color(81, 85, 123));
		btnNewButton.setBounds(0, 63, 179, 63);
		btnNewButton.setBorder(null);
		panel.add(btnNewButton);
		
		JButton btnExercisesManager = new JButton("Exercises manager");
		btnExercisesManager.addActionListener(e->exercisePageClicked(e));
		btnExercisesManager.setUI(new BasicButtonUI());
		btnExercisesManager.setForeground(Color.WHITE);
		btnExercisesManager.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnExercisesManager.setBackground(new Color(81, 85, 123));
		btnExercisesManager.setBounds(0, 125, 179, 63);
		btnExercisesManager.setBorder(null);
		panel.add(btnExercisesManager);
		
		JButton btnTrainings = new JButton("Trainings");
		btnTrainings.addActionListener(e->trainingPageClicked(e));
		btnTrainings.setForeground(Color.WHITE);
		btnTrainings.setUI(new BasicButtonUI());
		btnTrainings.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnTrainings.setBorder(null);
		btnTrainings.setBackground(new Color(81, 85, 123));
		btnTrainings.setBounds(0, 199, 179, 63);
		panel.add(btnTrainings);
		
		JButton btnPlanner = new JButton("Planner");
		btnPlanner.addActionListener(e->plannerPageCLicked(e));
		btnPlanner.setForeground(Color.WHITE);
		btnPlanner.setUI(new BasicButtonUI());
		btnPlanner.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnPlanner.setBorder(null);
		btnPlanner.setBackground(new Color(81, 85, 123));
		btnPlanner.setBounds(0, 273, 179, 63);
		panel.add(btnPlanner);
		
		cardLayout = new CardLayout();
		panel_1 = new JPanel();
		panel_1.setLayout(cardLayout);
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(179, 0, 865, 565);
		contentPane.add(panel_1);
		//panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add("Home",new HomePanel());
		panel_1.add("Exercises",new Exercises());
		panel_1.add("Training",new TrainingPanel());
		panel_1.add("Planer",new TrainingPlanner());
	}
	
	private void homePageClicked(ActionEvent e)
	{
		cardLayout.show(panel_1, "Home");
	}
	
	private void exercisePageClicked(ActionEvent e)
	{
		cardLayout.show(panel_1, "Exercises");
		/*panel_1.removeAll();
		Exercises exercises=new Exercises();
		panel_1.add(exercises,BorderLayout.CENTER);
		panel_1.revalidate();
		panel_1.repaint();*/
		
	}
	
	private void trainingPageClicked(ActionEvent e)
	{
		/*panel_1.removeAll();
		TrainingPanel training=new TrainingPanel();
		panel_1.add(training,BorderLayout.CENTER);
		panel_1.revalidate();
		panel_1.repaint();*/
		cardLayout.show(panel_1, "Training");
	}
	
	private void plannerPageCLicked(ActionEvent e)
	{
		/*panel_1.removeAll();
		TrainingPlanner planner=new TrainingPlanner();
		panel_1.add(planner,BorderLayout.CENTER);
		panel_1.revalidate();
		panel_1.repaint();*/
		cardLayout.show(panel_1, "Planer");
	}
}
