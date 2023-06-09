package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.GridLayout;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class CalendarFrame extends JPanel{

//	private JPanel contentPane;
	private JPanel panel;
	private JLabel currDateLabel;
	private ArrayList<CalendarItem> items=new ArrayList<>();
	private Calendar calendar=Calendar.getInstance();
	private int currentYear;
	private int currentMonth;
	private Runnable callback;
	
	private CalendarItem selectedItem;
	
	public CalendarFrame() {
		//setResizable(false);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 914, 782);
	//	contentPane = new JPanel();
		this.setBackground(new Color(255, 255, 255));
		//this.setBorder(new EmptyBorder(5, 5, 5, 5));

	//	setContentPane(contentPane);
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(113, 170, 240));
		this.add(panel_1,BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("PREV");
		btnNewButton.setUI(new BasicButtonUI());
		btnNewButton.setForeground(Color.white);
		btnNewButton.setBackground(new Color(145, 127, 179));
		btnNewButton.addActionListener(e->prevMonth());
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(btnNewButton, BorderLayout.WEST);
		
		JButton btnNewButton_1 = new JButton("NEXT");
		panel_1.add(btnNewButton_1, BorderLayout.EAST);
		btnNewButton_1.setUI(new BasicButtonUI());
		btnNewButton_1.setForeground(Color.white);
		btnNewButton_1.setBackground(new Color(145, 127, 179));
		
		currDateLabel=new JLabel("Date");
		panel_1.add(currDateLabel, BorderLayout.CENTER);
		//currDateLabel.setBackground(new Color(255, 255, 255));
		currDateLabel.setFont(new Font("Century Gothic", Font.BOLD, 17));
		currDateLabel.setForeground((new Color(255,255,255)));
		currDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		btnNewButton_1.addActionListener(e->nextMonth());
		
		panel = new JPanel();
		//panel_1.add(panel, BorderLayout.NORTH);
		this.add(panel,BorderLayout.CENTER);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBorder(new LineBorder(new Color(81, 85, 123), 2));
		panel.setLayout(new GridLayout(7,7, 2, 2));//42

		Calendar calendar=Calendar.getInstance();
		this.currentYear=calendar.get(Calendar.YEAR);
		this.currentMonth=calendar.get(Calendar.MONTH)+1;	
		init();
		drawCalendar();
	}
	
	public void setCallback(Runnable action)
	{
		this.callback=action;
	}
	
	
	public Date getSelectedDate()
	{
		if(this.selectedItem!=null)
			return this.selectedItem.date;
		else
			return new Date();
	}
	
	private void setCurrentDate()
	{
		SimpleDateFormat format=new SimpleDateFormat("MMMM-YYYY");
		this.currDateLabel.setText(format.format(calendar.getTime()));
	}
	
	private void setCalendarDate()
	{
		calendar.set(Calendar.YEAR, this.currentYear);
		calendar.set(Calendar.MONTH, this.currentMonth-1);
	}
	
	private void init()
	{
		initHeader();
		for(int i=1;i<=42;i++)
		{
			CalendarItem item=new CalendarItem();
			item.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	calendarCellClick(e);
	            	if(callback!=null)
	            		callback.run();
	            	
	            }
	        });
			panel.add(item);
			items.add(item);
		}
	}
	
	private void initHeader()
	{
		CalendarItem sun=new CalendarItem(); 
		sun.setText("SUN"); 
		sun.setForegroundColor(new Color(201, 48, 44)); 
		panel.add(sun); 
		CalendarItem mon=new CalendarItem(); 
		mon.setText("MON"); 
		panel.add(mon);
		CalendarItem tue=new CalendarItem(); 
		tue.setText("TUE"); 
		panel.add(tue);
		CalendarItem wed=new CalendarItem(); 
		wed.setText("WED"); 
		panel.add(wed);
		CalendarItem thu=new CalendarItem(); 
		thu.setText("THU"); 
		panel.add(thu);
		CalendarItem fri=new CalendarItem(); 
		fri.setText("FRI"); 
		panel.add(fri);
		CalendarItem sat=new CalendarItem(); 
		sat.setText("SAT"); 
		panel.add(sat);
	}
	
	private void drawCalendar()
	{
		this.setCalendarDate();
		this.setCurrentDate();
		calendar.set(Calendar.DATE, 1);
		int startDay=calendar.get(Calendar.DAY_OF_WEEK)-1;
		calendar.add(Calendar.DATE, -startDay);
		for(var item:this.items)
		{
			item.setDate(calendar.getTime());
			item.setText(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
			item.isCurrentMonth(calendar.get(Calendar.MONTH)==(currentMonth-1));
			calendar.add(Calendar.DATE, 1);
		}
	}
	
	private void nextMonth()
	{
		if(this.currentMonth==12)
		{
			this.currentMonth=1;
			this.currentYear++;
		}
		else
			this.currentMonth++;
		drawCalendar();
	}
	
	private void prevMonth()
	{
		if(this.currentMonth==1)
		{
			this.currentMonth=12;
			this.currentYear--;
		}
		else 
			this.currentMonth--;
		drawCalendar();
	}
	
	private void calendarCellClick(MouseEvent e)
	{
		CalendarItem item=(CalendarItem)e.getSource();
		if(item!=this.selectedItem)
		{
			if(selectedItem!=null)
				this.selectedItem.setSelected(false);
			this.selectedItem=item;
			item.setSelected(true);
		}
		
	}

}
