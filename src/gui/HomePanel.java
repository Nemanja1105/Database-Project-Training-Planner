package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HomePanel extends JPanel {
	private int counter=1;

	
	public HomePanel() {
		setBounds(100, 100, 864, 565);
		this.setBackground(new Color(255, 255, 255));
		this.setLayout(new BorderLayout(0, 0));
		
		ImageIcon image1=new ImageIcon("resources/PRVI.jpg");
		image1.setImage(image1.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
		ImageIcon image2=new ImageIcon("resources/DRUGI.jpg");
		image2.setImage(image2.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
		ImageIcon image3=new ImageIcon("resources/TRECI.jpg");
		image3.setImage(image3.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(image1);
		this.add(imageLabel,BorderLayout.CENTER);
		
		Timer timer=new Timer(3000,(e)->{
			if(counter==0)
				imageLabel.setIcon(image1);
			else if(counter==1)
				imageLabel.setIcon(image2);
			else {
				imageLabel.setIcon(image3);
				counter=0;
			}
			counter++;
		});
		timer.start();

	}

}
