package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.multi.MultiButtonUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.google.protobuf.Internal.ListAdapter;

import dao.ExerciseCategoryDAO;
import dao.ExerciseDAO;
import dao.impl.ExerciseCategoryDAOImpl;
import dao.impl.ExerciseDAOImpl;
import main.Main;
import models.Exercise;
import models.ExerciseCategory;

import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.lang.invoke.VarHandle;
import java.security.DomainCombiner;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Exercises extends JPanel {

	// private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JComboBox<ExerciseCategory> categoryComboBox;
	private ExerciseTableModel model;
	private ExerciseDAO exerciseDAO;
	private ExerciseCategoryDAO exerciseCategoryDAO;
	private List<ExerciseCategory> categories;
	private List<Exercise> exercises;

	class ExerciseTableModel extends AbstractTableModel {

		private List<Exercise> data;
		private boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };
		private String[] header = new String[] { "Id", "Name", "Description", "Difficulty", "Category", "Equipment" };

		public ExerciseTableModel() {
			this.data = new ArrayList<Exercise>();
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
		
		public List<Exercise> getData(){
			return this.data;

		}
		
		public void updateData(List<Exercise>data) {
			this.data=data;
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return data.size();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Exercise obj = data.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return obj.getId();
			case 1:
				return obj.getName();
			case 2:
				return obj.getDescription();
			case 3:
				return obj.getDifficulty();
			case 4:
				return obj.getCategory().getName();
			case 5:
				return (obj.getEquipment() != null) ? obj.getEquipment().getName() : "/";
			default:
				return null;
			}
		}

		public void addRow(Exercise e) {
			data.add(e);
			fireTableRowsInserted(data.size() - 1, data.size() - 1);
		}

		public Exercise getAt(int row) {
			return this.data.get(row);
		}

		public void deleteRow(int row) {
			this.data.remove(row);
			fireTableRowsDeleted(row, row);
		}

		public void updateRow(int index, Exercise e) {
			this.data.set(index, e);
			fireTableRowsUpdated(index, index);
		}

	}

	public Exercises() {
		
		this.exerciseDAO = new ExerciseDAOImpl();
		this.exerciseCategoryDAO=new ExerciseCategoryDAOImpl();
		
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(81, 85, 123));
		panel.setBounds(0, 0, 866, 91);
		this.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Exercises manager");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(39, 23, 203, 42);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Delete");
		btnNewButton.setUI(new BasicButtonUI());
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnNewButton.setBackground(new Color(201, 48, 44));
		btnNewButton.setBounds(370, 25, 114, 42);
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(e -> deleteBtnClick(e));
		panel.add(btnNewButton);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> updateBtnClick(e));
		btnUpdate.setUI(new BasicButtonUI());
		btnUpdate.setForeground(Color.WHITE);
		btnUpdate.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdate.setFocusPainted(false);
		btnUpdate.setBackground(new Color(243, 156, 18));
		btnUpdate.setBounds(544, 25, 114, 42);
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

		categoryComboBox = new JComboBox<>();
		categoryComboBox.setBounds(544, 99, 150, 22);
		initCategory();
		this.add(categoryComboBox);

		JLabel lblNewLabel_1_1 = new JLabel("Category");
		lblNewLabel_1_1.setForeground(SystemColor.controlDkShadow);
		lblNewLabel_1_1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(469, 99, 65, 19);
		this.add(lblNewLabel_1_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 132, 828, 394);
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
		this.model = new ExerciseTableModel();
		table.setModel(model);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.setSelectionBackground(new Color(107, 182, 255));
		displayExercises();

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
		categoryComboBox.setSelectedIndex(model.getSize()-1);
		categoryComboBox.addActionListener(e->categoryChanged(e));
	}
	
	private void initCategory()
	{
		Main.threadPool.execute(()->{
			try {
				this.categories = exerciseCategoryDAO.findAll();
				this.categories.add(new ExerciseCategory("Sve"));
				SwingUtilities.invokeLater(()->{this.categoryComboBoxInit();});
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka", "Greška",
						JOptionPane.ERROR_MESSAGE);
			}
			
		});
	}

	private void displayExercises() {
		Main.threadPool.execute(() -> {
			try {
				this.exercises = this.exerciseDAO.findAll();
				SwingUtilities.invokeLater(() -> {
					exercises.stream().forEach(e -> model.addRow(e));
				});
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom komunikacije sa bazom podataka",
						"Greška", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void updateBtnClick(ActionEvent e) {
		var index = table.getSelectedRow();
		if (index != -1) {
			var exercise = this.model.getAt(index);
			ExercisesUpdateInsert form = new ExercisesUpdateInsert(false);
			form.updateInit(exercise);
			form.setLocationRelativeTo(this);
			form.setModal(true);
			form.setVisible(true);
			if (form.result != null)
				model.updateRow(index, form.result);
		}

	}

	private void insertBtnClick(ActionEvent e) {
		ExercisesUpdateInsert form = new ExercisesUpdateInsert(true);
		form.setLocationRelativeTo(this);
		form.setModal(true);
		form.setVisible(true);
		if (form.result != null)
			model.addRow(form.result);
	}

	private void deleteBtnClick(ActionEvent e) {
		var index = table.getSelectedRow();
		if (index != -1) {
			int result = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite obrisati vjezbu?",
					"Potvrda brisanja", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				var exercise = this.model.getAt(index);
				try {
					var status = exerciseDAO.delete(exercise);
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
	
	private void categoryChanged(ActionEvent e)
	{
		var category=(ExerciseCategory)categoryComboBox.getSelectedItem();
		List<Exercise> newList;
		if("Sve".equals(category.getName()))
			newList=this.exercises;
		else 
			newList=this.exercises.stream().filter((a)->a.getCategory().getName().equals(category.getName())).collect(Collectors.toList());
		model.updateData(newList);
	}
	
	private void textChange()
	{
		var text=textField.getText().toUpperCase();
		List<Exercise> newList;
		if(text.isBlank())
			newList=this.exercises;
		else
			newList=this.exercises.stream().filter((a)->a.getName().toUpperCase().startsWith(text)).collect(Collectors.toList());
		model.updateData(newList);
	}

}
