package gui;

import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import dao.TrainingDAO;
import dao.impl.TrainingDAOImpl;
import gui.TrainingCreator.ViewType;
import main.Main;
import models.Exercise;
import models.ExerciseCategory;
import models.Training;
import models.enums.Difficulty;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.spi.AbstractResourceBundleProvider;
import java.util.stream.Collectors;

public class TrainingPanel extends JPanel {

	// private JPanel contentPane;

	private JTextField textField;
	private JTable table;
	private TrainingTableModel model;
	private JComboBox<Object> difficultycomboBox;
	private List<Training> trainings;

	private TrainingDAO trainingDAO;

	class TrainingTableModel extends AbstractTableModel {

		private List<Training> data;
		private boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };
		private String[] header = new String[] { "Id", "Name", "Description", "Duration[m]", "Difficulty",
				"Creator Id" };

		public TrainingTableModel() {
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

		public List<models.Training> getData() {
			return this.data;

		}

		public void updateData(List<models.Training> data) {
			this.data = data;
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			models.Training obj = data.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return obj.getId();
			case 1:
				return obj.getName();
			case 2:
				return obj.getDescription();
			case 3:
				return obj.getDuration();
			case 4:
				return obj.getDifficulty();
			case 5:
				return obj.getCreatorId();

			default:
				return null;
			}
		}

		public void addRow(models.Training e) {
			data.add(e);
			fireTableRowsInserted(data.size() - 1, data.size() - 1);
		}

		public models.Training getAt(int row) {
			return this.data.get(row);
		}

		public void deleteRow(int row) {
			this.data.remove(row);
			fireTableRowsDeleted(row, row);
		}

		public void updateRow(int index, models.Training e) {
			this.data.set(index, e);
			fireTableRowsUpdated(index, index);
		}

	}

	public TrainingPanel() {
		this.trainingDAO = new TrainingDAOImpl();

		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 864, 565);
		// contentPane = new JPanel();
		this.setBackground(new Color(255, 255, 255));
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		// setContentPane(contentPane);
		this.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(81, 85, 123));
		panel.setBounds(0, 0, 864, 91);
		this.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Training manager");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(39, 23, 203, 42);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Delete");
		btnNewButton.setUI(new BasicButtonUI());
		btnNewButton.addActionListener(e->deleteBtnClick(e));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBackground(new Color(201, 48, 44));
		btnNewButton.setBounds(419, 25, 114, 42);
		btnNewButton.setFocusPainted(false);
		panel.add(btnNewButton);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> updateBtnClick(e));
		btnUpdate.setUI(new BasicButtonUI());
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBackground(new Color(243, 156, 18));
		btnUpdate.setBounds(566, 25, 114, 42);
		panel.add(btnUpdate);

		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(e -> insertBtnClick(e));
		btnInsert.setUI(new BasicButtonUI());
		btnInsert.setForeground(Color.WHITE);
		btnInsert.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnInsert.setFocusPainted(false);
		btnInsert.setBackground(new Color(68, 157, 68));
		btnInsert.setBounds(712, 25, 114, 42);
		panel.add(btnInsert);

		JButton btnView = new JButton("View");
		btnView.addActionListener(e -> viewBtnClick(e));
		btnView.setForeground(Color.WHITE);
		btnView.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnView.setFocusPainted(false);
		btnView.setBackground(new Color(30, 144, 255));
		btnView.setBounds(270, 25, 114, 42);
		panel.add(btnView);

		JLabel lblNewLabel_1 = new JLabel("Search");
		lblNewLabel_1.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(179, 102, 65, 19);
		this.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		textField.setBounds(254, 101, 150, 20);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				textChange();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				textChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				textChange();
			}
		});

		this.add(textField);
		textField.setColumns(10);

		difficultycomboBox = new JComboBox(Difficulty.values());
		initDiffCombo();
		difficultycomboBox.setBounds(544, 99, 150, 22);
		this.add(difficultycomboBox);

		JLabel lblNewLabel_1_1 = new JLabel("Difficulty");
		lblNewLabel_1_1.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(469, 99, 65, 19);
		this.add(lblNewLabel_1_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 132, 827, 394);
		this.add(scrollPane);

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
		table.setBorder(null);
		this.model = new TrainingTableModel();
		table.setModel(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		// table.getColumnModel().getColumn(5).setResizable(false);
		table.setSelectionBackground(new Color(107, 182, 255));
		displayTrainings();

	}

	private void initDiffCombo() {
		difficultycomboBox.addItem("ALL");
		difficultycomboBox.setSelectedItem("ALL");
		difficultycomboBox.addActionListener(e -> diffChanged(e));
	}

	private void displayTrainings() {
		Main.threadPool.execute(() -> {
			try {
				this.trainings = this.trainingDAO.findAll();
				SwingUtilities.invokeLater(() -> {
					model.updateData(trainings);
				});
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
						"Greška", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	public void updateBtnClick(ActionEvent e) {
		var index = table.getSelectedRow();
		if (index != -1) {
			var training = this.model.getAt(index);
			TrainingCreator form = new TrainingCreator(ViewType.UPDATE);
			form.viewTraining(training);
			form.setLocationRelativeTo(this);
			form.setModal(true);
			form.setVisible(true);
			if (form.result != null)
				model.updateRow(index, form.result);
		}
	}

	public void insertBtnClick(ActionEvent e) {
		TrainingCreator form = new TrainingCreator(ViewType.INSERT);
		form.setLocationRelativeTo(this);
		form.setModal(true);
		form.setVisible(true);
		if (form.result != null)
			this.model.addRow(form.result);
	}
	
	private void deleteBtnClick(ActionEvent e) {
		var index = table.getSelectedRow();
		if (index != -1) {
			int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite obrisati vjezbu?",
					"Potvrda brisanja", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				var training = this.model.getAt(index);
				try {
					var status = trainingDAO.delete(training);
					if (status)
						this.model.deleteRow(index);
				} catch (SQLException a) {
					System.out.println(a.getMessage());
					JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
							"Greška", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	

	public void viewBtnClick(ActionEvent e) {
		var index = table.getSelectedRow();
		if (index != -1) {
			var training = model.getAt(index);
			TrainingCreator form = new TrainingCreator(ViewType.VIEW);
			form.viewTraining(training);
			form.setLocationRelativeTo(this);
			form.setModal(true);
			form.setVisible(true);
		}
	}

	private void diffChanged(ActionEvent e) {

		var diff = difficultycomboBox.getSelectedItem();
		List<Training> newList;
		if ("ALL".equals(diff))
			newList = this.trainings;
		else
			newList = this.trainings.stream().filter((a) -> a.getDifficulty().equals(diff))
					.collect(Collectors.toList());
		model.updateData(newList);
	}

	private void textChange() {
		var text = textField.getText().toUpperCase();
		List<Training> newList;
		if (text.isBlank())
			newList = this.trainings;
		else
			newList = this.trainings.stream().filter((a) -> a.getName().toUpperCase().startsWith(text))
					.collect(Collectors.toList());
		model.updateData(newList);
	}

}
