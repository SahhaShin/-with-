package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReservationConfirm extends JFrame implements ActionListener, ListSelectionListener {
	
	Container cp;

	 
	 //reservation 정보 입력하는 곳!!
	 JList lstreservation;
	 String reservation[]={"20211121 seoul1 id1 id2 id3 id4","20211121 seoul1 id1 id2 id3 id4","20211121 seoul1 id1 id2 id3 id4"};
	 
	 JScrollPane jsp;

	 
	 
	 public ReservationConfirm(){
		 setTitle("RESERVATION CONFIRM");
		  cp=this.getContentPane();
		  this.setDesign();
		  
		  this.setBounds(300,100,200,200);
		  cp.setBackground(new Color(255, 255, 200));
		  setSize(600,500);
		  this.setVisible(true);
		 }
		 
		 public void setDesign()
		 {
		  cp.setLayout(new BorderLayout());
		  JPanel pTop=new JPanel();	  
		  JLabel title = new JLabel("RESERVATION LIST");
		  title.setHorizontalAlignment(JLabel.CENTER);
		  pTop.add(title);
		  
		  pTop.setBackground(Color.CYAN);
		  cp.add("North",pTop);

		  lstreservation=new JList(reservation);
		  
		  //기능구현 !!!!!!!!!!!!!!!!!!!!!!!!
		  
		  //셀렉트이벤트 발생
		  lstreservation.addListSelectionListener(this);
		  
		  jsp=new JScrollPane(lstreservation);
		  cp.add("Center",jsp);
		  
	 }
		 @Override
		 public void actionPerformed(ActionEvent e)
		 {

		  }

		 @Override
		 public void valueChanged(ListSelectionEvent e)
		 {

		 }	 


}
