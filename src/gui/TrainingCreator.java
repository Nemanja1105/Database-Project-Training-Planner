package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import dao.ExerciseDAO;
import dao.TraingingSetDAO;
import dao.TrainingDAO;
import dao.impl.ExerciseDAOImpl;
import dao.impl.TraingingSetDAOImpl;
import dao.impl.TrainingDAOImpl;
import main.Main;
import models.Equipment;
import models.Exercise;
import models.ExerciseCategory;
import models.Training;
import models.TrainingSet;
import models.enums.Difficulty;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class TrainingCreator extends JDialog {
	public Training result;

	public enum ViewType {
		UPDATE, INSERT, VIEW
	}

	class TrainingSetTableModel extends AbstractTableModel {

		private List<TrainingSet> data;
		private boolean[] columnEditables = new boolean[] { true, true, true, true, true };
		private String[] header = new String[] { "Exercises", "Repetition", "Series", "RestDuration", "Note" };

		public TrainingSetTableModel() {
			this.data = new ArrayList<>();
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}

		@Override
		public String getColumnName(int column) {
			return header[column];
		}

		@Override
		public int getColumnCount() {
			return header.length;
		}

		public List<TrainingSet> getData() {
			return this.data;

		}

		public void updateData(List<TrainingSet> data) {
			this.data = data;
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			TrainingSet obj = data.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return (obj.getExerciseName() != null ? obj.getExerciseName() : "/");
			case 1:
				return obj.getRepetition();
			case 2:
				return obj.getSeries();
			case 3:
				return obj.getRestDuration();
			case 4:
				return (obj.getNote() != null ? obj.getNote() : "/");

			default:
				return null;
			}
		}

		@Override
		public void setValueAt(Object data, int rowIndex, int columnIndex) {
			TrainingSet obj = this.data.get(rowIndex);
			if (columnIndex == 0) {
				var exercise = (Exercise) data;
				if (exercise != null) {
					obj.setExerciseId((int) exercise.getId());
					obj.setExerciseName(exercise.getName());
				}
			} else if (columnIndex == 1) {
				int rep = 0;
				try {
					rep = Integer.parseInt((String) data);
					obj.setRepetition(rep);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Nepravilan format broja ponavljanja", "Greška",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (columnIndex == 2) {
				int series = 0;
				try {
					series = Integer.parseInt((String) data);
					obj.setSeries(series);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Nepravilan format broja serija", "Greška",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (columnIndex == 3) {
				int rest = 0;
				try {
					rest = Integer.parseInt((String) data);
					obj.setRestDuration(rest);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Nepravilan format vremena odmora", "Greška",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (columnIndex == 4) {
				obj.setNote((String) data);
			}

		}

		public void addRow(TrainingSet e) {
			data.add(e);
			fireTableRowsInserted(data.size() - 1, data.size() - 1);
		}

		public TrainingSet getAt(int row) {
			return this.data.get(row);
		}

		public void deleteRow(int row) {
			this.data.remove(row);
			fireTableRowsDeleted(row, row);
		}

		public void updateRow(int index, TrainingSet e) {
			this.data.set(index, e);
			fireTableRowsUpdated(index, index);
		}
	}

	private JPanel contentPane;
	private JTextField nametextField;
	private JTextField durationTextField;
	private JTable table;
	private JTextArea descTextArea;
	private JComboBox<Difficulty> comboBox;
	private JComboBox<Exercise> exeComboBox;
	private ViewType type;
	private TrainingDAO trainingDAO;
	private ExerciseDAO exerciseDAO;
	private TraingingSetDAO traingingSetDAO;
	private List<Exercise> exercises;
	private List<TrainingSet> sets;
	private TrainingSetTableModel model;

	public TrainingCreator(ViewType type) {
		this.type = type;
		this.trainingDAO = new TrainingDAOImpl();
		this.exerciseDAO = new ExerciseDAOImpl();
		this.traingingSetDAO = new TraingingSetDAOImpl();
		init(type);

	}

	private void init(ViewType type) {
		setBackground(new Color(255, 255, 255));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 739, 654);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(81, 85, 123));
		panel.setBounds(0, 0, 723, 86);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblTrainingManager = new JLabel("Training ");
		lblTrainingManager.setForeground(Color.WHITE);
		lblTrainingManager.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblTrainingManager.setBackground(Color.WHITE);
		lblTrainingManager.setBounds(303, 21, 203, 42);
		panel.add(lblTrainingManager);

		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(40, 98, 65, 19);
		contentPane.add(lblNewLabel_1);

		nametextField = new JTextField();
		nametextField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		nametextField.setColumns(10);
		nametextField.setBounds(155, 97, 150, 20);
		contentPane.add(nametextField);

		JLabel lblNewLabel_1_1 = new JLabel("Difficulty");
		lblNewLabel_1_1.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(40, 181, 65, 19);
		contentPane.add(lblNewLabel_1_1);

		comboBox = new JComboBox(Difficulty.values());
		comboBox.setBounds(154, 181, 150, 22);
		contentPane.add(comboBox);

		JLabel lblNewLabel_1_2 = new JLabel("Duration");
		lblNewLabel_1_2.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_2.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(40, 140, 65, 19);
		contentPane.add(lblNewLabel_1_2);

		durationTextField = new JTextField();
		durationTextField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		durationTextField.setColumns(10);
		durationTextField.setBounds(155, 139, 150, 20);
		contentPane.add(durationTextField);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("Description");
		lblNewLabel_1_1_2_1.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_1_2_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_1_2_1.setBounds(348, 140, 97, 19);
		contentPane.add(lblNewLabel_1_1_2_1);

		descTextArea = new JTextArea();
		descTextArea.setLineWrap(true);
		descTextArea.setFont(new Font("Century", Font.PLAIN, 14));
		descTextArea.setBorder(new LineBorder(UIManager.getColor("TextField.shadow")));
		descTextArea.setBounds(455, 97, 220, 103);
		contentPane.add(descTextArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 271, 723, 269);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Century Gothic", Font.BOLD, 14));
		table.setRowHeight(25);
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new Color(30, 144, 255));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		table.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(table);
		model = new TrainingSetTableModel();
		table.setBorder(null);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(true);
		table.setSelectionBackground(new Color(107, 182, 255));

		JButton btnInsertrow = new JButton("InsertRow");
		btnInsertrow.addActionListener(e -> insertRowBtnClick(e));
		btnInsertrow.setForeground(Color.WHITE);
		btnInsertrow.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnInsertrow.setFocusPainted(false);
		btnInsertrow.setBackground(new Color(68, 157, 68));
		btnInsertrow.setBounds(40, 230, 114, 30);
		contentPane.add(btnInsertrow);

		JButton btnDeleterow = new JButton("DeleteRow");
		btnDeleterow.addActionListener(e -> deleteRowBtnClick(e));
		btnDeleterow.setForeground(Color.WHITE);
		btnDeleterow.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnDeleterow.setFocusPainted(false);
		btnDeleterow.setBackground(new Color(201, 48, 44));
		btnDeleterow.setBounds(189, 230, 114, 30);
		contentPane.add(btnDeleterow);
		if (type == ViewType.VIEW) {
			btnInsertrow.setVisible(false);
			btnDeleterow.setVisible(false);
		}

		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(e -> insertUpdateClick(e));
		if (type == ViewType.INSERT)
			btnInsert.setText("Insert");
		else if (type == ViewType.UPDATE)
			btnInsert.setText("Update");
		else
			btnInsert.setVisible(false);
		btnInsert.setForeground(Color.WHITE);
		btnInsert.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnInsert.setFocusPainted(false);
		btnInsert.setBackground(new Color(68, 157, 68));
		btnInsert.setBounds(201, 551, 114, 42);
		contentPane.add(btnInsert);

		JButton btnCancel = new JButton("Cancel");
		if (type == ViewType.VIEW)
			btnCancel.setVisible(false);
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnCancel.setFocusPainted(false);
		btnCancel.setBackground(new Color(201, 48, 44));
		btnCancel.setBounds(401, 551, 114, 42);
		contentPane.add(btnCancel);

		exeComboBox = new JComboBox<>();// dodati vjezbe
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(exeComboBox));

		exerciseInit();
	}

	private void insertUpdateClick(ActionEvent e) {
		if (type == ViewType.INSERT)
			insertTrainingClick();
		else if (type == ViewType.UPDATE)
			updateTrainingClick();
	}

	public void viewTraining(Training t) {
		this.result = t;
		nametextField.setText(t.getName());
		durationTextField.setText("" + t.getDuration());
		comboBox.setSelectedItem(t.getDifficulty());
		descTextArea.setText(t.getDescription());
		initTrainingSet(t.getId());
	}

	private void initTrainingSet(int id) {
		Main.threadPool.execute(() -> {
			try {
				this.sets = this.traingingSetDAO.findAllByTraingId(id);
				SwingUtilities.invokeLater(() -> {
					this.model.updateData(sets);
				});
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
						"Greška", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private Training checkAndCreate() {
		String name = nametextField.getText();
		if (name.isBlank()) {
			JOptionPane.showMessageDialog(null, "Naziv vjezbe ne moze biti prazan", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		int duration = 0;
		try {
			duration = Integer.parseInt(durationTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Trajanje treninga nije ispravnog formata", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		var diff = (Difficulty) comboBox.getSelectedItem();
		if (diff == null) {
			JOptionPane.showMessageDialog(null, "Nije odabrana tezina vjezbe", "Greška", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		var desc = descTextArea.getText();
		if (type == ViewType.UPDATE)
			return new Training(result.getId(), name, desc, duration, diff, Main.CLIENT_ID);
		else
			return new Training(name, desc, duration, diff, Main.CLIENT_ID);
	}

	private void exerciseInit() {
		Main.threadPool.execute(() -> {
			try {
				this.exercises = this.exerciseDAO.findAll();
				SwingUtilities.invokeLater(() -> {
					exerciseComboBoxInit();
				});
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
						"Greška", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void exerciseComboBoxInit() {
		exeComboBox.setRenderer(new javax.swing.DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value instanceof Exercise) {
					Exercise objekt = (Exercise) value;
					value = objekt.getName();
				}
				return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			}
		});
		DefaultComboBoxModel model = new DefaultComboBoxModel<>(this.exercises.toArray());
		exeComboBox.setModel(model);
	}

	private void insertTrainingClick() {
		Training training = checkAndCreate();
		if (training == null)
			return;
		try {
			trainingDAO.insert(training);
			Main.threadPool.execute(() -> {
				try {
					traingingSetDAO.insert(this.model.getData(), training.getId());// transakcija
				} catch (SQLException e) {
					if ("45000".equals(e.getSQLState()))
						JOptionPane.showMessageDialog(null, "Prekoračeno ukupno vrijeme odmora u treningu!", "Greška",
								JOptionPane.ERROR_MESSAGE);
					else
						JOptionPane.showMessageDialog(null,
								"Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
								JOptionPane.ERROR_MESSAGE);
				}
			});
		} catch (SQLException a) {
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.result = training;
		dispose();
	}

	private void updateTrainingClick() {
		Training training = checkAndCreate();
		if (training == null)
			return;
		Main.threadPool.execute(()->{
		try {
			if (trainingDAO.update(training))
				this.result = training;
			traingingSetDAO.update(this.model.getData(),training.getId());
		} catch (SQLException a) {
			System.out.println(a.getMessage());
			if ("45000".equals(a.getSQLState()))
				JOptionPane.showMessageDialog(null, "Prekoračeno ukupno vrijeme odmora u treningu!", "Greška",
						JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	});
		dispose();
	}

	public void insertRowBtnClick(ActionEvent e) {
		model.addRow(new TrainingSet());
	}

	public void deleteRowBtnClick(ActionEvent e) {
		int num = this.table.getSelectedRow();
		if (num != -1) {
			var set = model.getAt(num);
			if (type==ViewType.UPDATE && set.getId() != 0) {
				try {
					traingingSetDAO.delete(set);
					this.model.getData().remove(num);
				} catch (SQLException a) {
					System.out.println(a.getMessage());
					JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
							"Greška", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			model.deleteRow(num);
		}
	}
}
