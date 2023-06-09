package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;

import javax.swing.border.LineBorder;

import dao.TrainingDAO;
import dao.TrainingDayDAO;
import dao.impl.TrainingDAOImpl;
import dao.impl.TrainingDayDAOImpl;
import gui.TrainingCreator.ViewType;
import main.Main;
import models.Exercise;
import models.Training;
import models.TrainingDay;

import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TrainingPlanner extends JPanel {

	//private JPanel contentPane;
	private CalendarFrame calendar;
	private JLabel dateLabel;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblSelectTrainingFor;
	private JLabel lblTraining;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private JButton btnStart;
	private JLabel exerciseName;
	private JLabel durationLbl;
	private JComboBox<Training> comboBox;
	private TrainingDayDAO trainingDayDAO;
	private TrainingDAO trainingDAO;
	private List<Training> trainings;
	private TrainingDay trainingDay;
	private Training currentTraining;
	private boolean insertUpdate=true;
	
	public TrainingPlanner() {
		this.trainingDayDAO=new TrainingDayDAOImpl();
		this.trainingDAO=new TrainingDAOImpl();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setBounds(100, 100, 877, 565);
		//contentPane = new JPanel();
		this.setBackground(new Color(255, 255, 255));
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		//setContentPane(contentPane);
		this.setLayout(null);
		
		calendar=new CalendarFrame();
		calendar.setCallback(()->changeDate());
		calendar.setBounds(10,118,443,413);
		this.add(calendar,BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(81, 85, 123));
		panel.setBounds(0, 0, 864, 91);
		this.add(panel);
		
		JLabel lblTrainingPlanner = new JLabel("Training planner");
		lblTrainingPlanner.setForeground(Color.WHITE);
		lblTrainingPlanner.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblTrainingPlanner.setBackground(Color.WHITE);
		lblTrainingPlanner.setBounds(306, 25, 203, 42);
		panel.add(lblTrainingPlanner);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(463, 118, 388, 413);
		panel_1.setBackground(new Color(113, 170, 240));
		this.add(panel_1);
		panel_1.setLayout(null);
		
		dateLabel = new JLabel("Date");
		dateLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
		dateLabel.setForeground(new Color(255, 255, 255));
		dateLabel.setBounds(117, 11, 283, 28);
		panel_1.add(dateLabel);
		
		
		
		//imamo vec napravljen
		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		panel_3.setBounds(22, 50, 343, 200);
		panel_1.add(panel_3);
		panel_3.setBackground(new Color(113, 170, 240));
		panel_3.setLayout(null);
		panel_3.setVisible(false);
		
		lblTraining = new JLabel("Training");
		lblTraining.setForeground(Color.WHITE);
		lblTraining.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblTraining.setBackground(Color.WHITE);
		lblTraining.setBounds(126, 4, 100, 42);
		panel_3.add(lblTraining);
		
		lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1.setBounds(52, 50, 65, 19);
		panel_3.add(lblNewLabel_1);
		
		lblNewLabel = new JLabel("Duration:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel.setBounds(50, 80, 79, 19);
		panel_3.add(lblNewLabel);
		
		btnStart = new JButton("Start");
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnStart.setFocusPainted(false);
		btnStart.addActionListener(e->startClick(e));
		btnStart.setBackground(new Color(68, 157, 68));
		btnStart.setBounds(103, 127, 114, 42);
		panel_3.add(btnStart);
		
		exerciseName = new JLabel("");
		exerciseName.setForeground(Color.WHITE);
		exerciseName.setFont(new Font("Century Gothic", Font.BOLD, 16));
		exerciseName.setBounds(113, 50, 181, 19);
		panel_3.add(exerciseName);
		
		durationLbl = new JLabel("");
		durationLbl.setForeground(Color.WHITE);
		durationLbl.setFont(new Font("Century Gothic", Font.BOLD, 16));
		durationLbl.setBounds(126, 80, 182, 19);
		panel_3.add(durationLbl);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e->updateClick(e));
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBackground(new Color(243, 156, 18));
		btnUpdate.setBounds(227, 14, 106, 27);
		panel_3.add(btnUpdate);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(255, 255, 255), 2));
		panel_2.setBounds(22, 50, 343, 200);
		panel_2.setBackground(new Color(113, 170, 240));
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		lblSelectTrainingFor = new JLabel("Select training for this day");
		lblSelectTrainingFor.setForeground(Color.WHITE);
		lblSelectTrainingFor.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblSelectTrainingFor.setBackground(Color.WHITE);
		lblSelectTrainingFor.setBounds(45, 0, 262, 42);
		panel_2.add(lblSelectTrainingFor);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(135, 65, 150, 22);
		panel_2.add(comboBox);
		
		JLabel lblNewLabel_1_1 = new JLabel("Training");
		lblNewLabel_1_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(60, 64, 65, 19);
		panel_2.add(lblNewLabel_1_1);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(e->saveClick(e));
		btnSave.setForeground(Color.WHITE);
		btnSave.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnSave.setFocusPainted(false);
		btnSave.setBackground(new Color(68, 157, 68));
		btnSave.setBounds(112, 112, 114, 42);
		panel_2.add(btnSave);
		panel_2.setVisible(false);
		trainingsInit();

	}
	
	private void trainingComboBoxInit() {
		comboBox.setRenderer(new javax.swing.DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof Training) {
					Training objekt = (Training) value;
					value = objekt.getName();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
		DefaultComboBoxModel model = new DefaultComboBoxModel<>(this.trainings.toArray());
		comboBox.setSelectedItem(null);
		comboBox.setModel(model);
	}
	
	
	private void trainingsInit() {
		Main.threadPool.execute(() -> {
			try {
				this.trainings = this.trainingDAO.findAll();
				SwingUtilities.invokeLater(() -> {
					trainingComboBoxInit();
				});
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
						"Greška", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
	
	private void changeDate()
	{
		var date=calendar.getSelectedDate();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, YYYY");
		dateLabel.setText(formatter.format(date));
		TrainingDay trainingDay=null;
		try {
			trainingDay=trainingDayDAO.findByDate(date, Main.PLAN_ID);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
					"Greška", JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		if(trainingDay!=null)//true imamo podatke
		{
			this.trainingDay=trainingDay;
			currentTraining=this.trainings.stream().filter((t)->t.getId()==this.trainingDay.getTrainingId()).findFirst().get();
			showTrainingInfo();
		}
		else
		{
			this.insertUpdate=true;
			panel_3.setVisible(false);
			panel_2.setVisible(true);
		}
	}
	
	private void showTrainingInfo()
	{
		exerciseName.setText(currentTraining.getName());
		durationLbl.setText(currentTraining.getDuration()+"min");
		panel_2.setVisible(false);
		panel_3.setVisible(true);
	}
	
	private boolean getData()
	{
		Random random=new Random();
		return random.nextBoolean();
	}
	
	private void startClick(ActionEvent e)
	{
		TrainingCreator form=new TrainingCreator(ViewType.VIEW); form.viewTraining(currentTraining);
		form.setLocationRelativeTo(this);
		form.setModal(true);
		form.setVisible(true);
	}
	
	private void updateClick(ActionEvent e)
	{
		this.insertUpdate=false;
		panel_3.setVisible(false);
		panel_2.setVisible(true);
	}
	
	private void saveClick(ActionEvent e)
	{
		Training selected=(Training)comboBox.getSelectedItem();
		var date=calendar.getSelectedDate();
		TrainingDay trainingDay=new TrainingDay(Main.PLAN_ID,date,selected.getId());
		try {
			boolean status=false;
			if(insertUpdate)
				status=trainingDayDAO.insert(trainingDay);
			else 
				status=trainingDayDAO.update(trainingDay);
			if(status)
			{
				currentTraining=selected;
				this.trainingDay=trainingDay;
				showTrainingInfo();
			}
		
		} catch (SQLException a) {
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
					"Greška", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
