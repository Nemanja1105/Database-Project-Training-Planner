package gui;

import java.util.Date;
import java.util.spi.AbstractResourceBundleProvider;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.time.LocalDate;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

public class CalendarItem extends JLabel {

	public Date date;


	
	public CalendarItem() {
		
		//dateLabel = new JLabel("12");
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setFont(new Font("Century Gothic", Font.BOLD, 14));
		this.setForeground(new Color(81, 85, 123));
		this.setBorder(BorderFactory.createLineBorder(new Color(81, 85, 123), 2));
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		//this.setLayout(new BorderLayout(0, 0));
		//add(dateLabel, BorderLayout.SOUTH);
	}
	
	public void setDate(Date date)
	{
		this.date=date;
	}
	
	public void setSelected(boolean status)
	{
		if(!status)
			this.setBackground(Color.WHITE);
		else
			this.setBackground(new Color(229, 190, 236));

	}
	
	
	public void setText(String text)
	{
		super.setText(text);
	}
	
	public void setForegroundColor(Color color)
	{
		this.setForeground(color);
	}
	
	public void isCurrentMonth(boolean status)
	{
		if(status)
			this.setForeground(new Color(81, 85, 123));
		else 
			this.setForeground(new Color(169, 169, 169));
	}
	
	/* @Override
	  protected void paintComponent(Graphics g) 
	 {
	            if(today)
	            {
	            g.setColor(new Color(81, 85, 123));
	            g.fillOval(0, 0, getWidth(), getHeight());
	            }
	            super.paintComponent(g);
	    }*/
}
