package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dao.EquipmentDAO;
import dao.ExerciseCategoryDAO;
import dao.ExerciseDAO;
import dao.impl.EquipmentDAOImpl;
import dao.impl.ExerciseCategoryDAOImpl;
import dao.impl.ExerciseDAOImpl;
import models.Equipment;
import models.EquipmentDTO;
import models.Exercise;
import models.ExerciseCategory;
import models.enums.Difficulty;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ExercisesUpdateInsert extends JDialog {

	public Exercise result = null;

	private JPanel contentPane;
	private JTextField textField;
	private JComboBox<Equipment> equipmentComboBox;
	private JComboBox<ExerciseCategory> categoryComboBox;
	private JComboBox<Difficulty> diffComboBox;
	private JTextArea descText;
	private ExerciseCategoryDAO exerciseCategoryDAO;
	private EquipmentDAO equipmentDAO;
	private ExerciseDAO exerciseDAO;
	private List<ExerciseCategory> categories;
	private List<Equipment> equipments;
	private boolean type;

	/**
	 * Create the frame.
	 */
	public ExercisesUpdateInsert(boolean type) {
		this.exerciseCategoryDAO = new ExerciseCategoryDAOImpl();
		this.equipmentDAO = new EquipmentDAOImpl();
		this.exerciseDAO = new ExerciseDAOImpl();
		this.type = type;
		try {
			this.categories = exerciseCategoryDAO.findAll();
			this.equipments = equipmentDAO.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
					JOptionPane.ERROR_MESSAGE);
			dispose();
		}

		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 387, 508);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 435, 62);
		panel.setBackground(new Color(81, 85, 123));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblExercisesUpdateinsert = new JLabel("Exercises Update/Insert");
		lblExercisesUpdateinsert.setBounds(75, 11, 227, 26);
		lblExercisesUpdateinsert.setForeground(Color.WHITE);
		lblExercisesUpdateinsert.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblExercisesUpdateinsert.setBackground(Color.WHITE);
		panel.add(lblExercisesUpdateinsert);

		JLabel nameLabel = new JLabel("Name");
		nameLabel.setForeground(SystemColor.controlDkShadow);
		nameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		nameLabel.setBounds(24, 73, 65, 19);
		contentPane.add(nameLabel);

		textField = new JTextField();
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textField.setColumns(10);
		textField.setBounds(136, 72, 150, 20);
		contentPane.add(textField);

		JLabel difficultiLabel = new JLabel("Difficulty");
		difficultiLabel.setForeground(SystemColor.controlDkShadow);
		difficultiLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		difficultiLabel.setBounds(24, 114, 65, 19);
		contentPane.add(difficultiLabel);

		// tezina
		diffComboBox = new JComboBox(Difficulty.values());
		diffComboBox.setBounds(136, 114, 150, 22);
		diffComboBox.setSelectedItem(null);
		contentPane.add(diffComboBox);

		// kategorije
		categoryComboBox = new JComboBox();
		categoryComboBox.setBounds(136, 157, 150, 22);
		categoryComboBoxInit();
		contentPane.add(categoryComboBox);

		JLabel lblNewLabel_1_1_1 = new JLabel("Category");
		lblNewLabel_1_1_1.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(24, 157, 65, 19);
		contentPane.add(lblNewLabel_1_1_1);

		equipmentComboBox = new JComboBox();
		equipmentComboBox.setBounds(136, 200, 150, 22);
		equipmentComboBoxInit();
		contentPane.add(equipmentComboBox);

		JLabel lblNewLabel_1_1_2 = new JLabel("Equipment");
		lblNewLabel_1_1_2.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_1_2.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_1_2.setBounds(24, 200, 97, 19);
		contentPane.add(lblNewLabel_1_1_2);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("Description");
		lblNewLabel_1_1_2_1.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_1_2_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_1_2_1.setBounds(24, 273, 97, 19);
		contentPane.add(lblNewLabel_1_1_2_1);

		JButton btnInsert = new JButton();
		if (type)
			btnInsert.setText("Insert");
		else {
			btnInsert.setText("Update");
		}

		btnInsert.setForeground(Color.WHITE);
		btnInsert.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnInsert.setFocusPainted(false);
		btnInsert.setBackground(new Color(68, 157, 68));
		btnInsert.addActionListener(e -> insertUpdateClick(e));
		btnInsert.setBounds(24, 382, 114, 42);

		contentPane.add(btnInsert);

		JButton btnCancel = new JButton("Cancel");

		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnCancel.setFocusPainted(false);
		btnCancel.setBackground(new Color(201, 48, 44));
		btnCancel.setBounds(224, 382, 114, 42);
		btnCancel.addActionListener((e) -> {
			this.dispose();
		});
		contentPane.add(btnCancel);

		descText = new JTextArea();
		descText.setLineWrap(true);
		descText.setBorder(new LineBorder(UIManager.getColor("TextField.shadow")));
		descText.setFont(new Font("Century", Font.PLAIN, 14));
		descText.setBounds(136, 248, 202, 88);
		contentPane.add(descText);

	}

	public void updateInit(Exercise e) {
		this.result=e;
		textField.setText(e.getName());
		diffComboBox.setSelectedItem(e.getDifficulty());
		categoryComboBox.setSelectedItem(e.getCategory());
		equipmentComboBox.setSelectedItem(e.getEquipment());
		descText.setText(e.getDescription());
	}

	private void categoryComboBoxInit() {
		categoryComboBox.setRenderer(new javax.swing.DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof ExerciseCategory) {
					ExerciseCategory objekt = (ExerciseCategory) value;
					value = objekt.getName();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
		DefaultComboBoxModel model = new DefaultComboBoxModel<>(this.categories.toArray());
		categoryComboBox.setModel(model);
		categoryComboBox.setSelectedItem(null);
	}

	private void equipmentComboBoxInit() {
		equipmentComboBox.setRenderer(new javax.swing.DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof Equipment) {
					Equipment objekt = (Equipment) value;
					value = objekt.getName();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
		DefaultComboBoxModel model = new DefaultComboBoxModel<>(this.equipments.toArray());
		equipmentComboBox.setModel(model);
		equipmentComboBox.setSelectedItem(null);
	}

	private void insertUpdateClick(ActionEvent e) {
		if (type)
			insertExerciseClick();
		else 
			updateExerciseClick();
	}

	private Exercise checkAndCreate() {
		String name = textField.getText();
		if (name.isBlank()) {
			JOptionPane.showMessageDialog(null, "Naziv vjezbe ne moze biti prazan", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		var diff = (Difficulty) diffComboBox.getSelectedItem();
		if (diff == null) {
			JOptionPane.showMessageDialog(null, "Nije odabrana tezina vjezbe", "Greška", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		var category = (ExerciseCategory) categoryComboBox.getSelectedItem();
		if (category == null) {
			JOptionPane.showMessageDialog(null, "Nije odabrana kategorija vjezbe", "Greška", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		var equpment = (Equipment) equipmentComboBox.getSelectedItem();
		var desc = descText.getText();
		if(!type)
			return new Exercise(result.getId(),name, desc, diff, equpment, category);
		else
			return new Exercise(name, desc, diff, equpment, category);
	}

	private void insertExerciseClick() {
		Exercise exercise = checkAndCreate();
		if (exercise == null)
			return;
		try {
			exerciseDAO.insert(exercise);
		} catch (SQLException a) {
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.result = exercise;
		dispose();
	}

	private void updateExerciseClick() {
		Exercise exercise = checkAndCreate();
		if (exercise == null)
			return;
		try {
			if(exerciseDAO.update(exercise));
				this.result=exercise;
		} catch (SQLException a) {
			System.out.println(a.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.result = exercise;
		dispose();
	}

}
