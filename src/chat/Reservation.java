package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Reservation extends JFrame implements ItemListener, ActionListener {
	private Socket socket;
	//------------외형구현---------------
	Calendar cal; //Calendar
	int year, month, date;

	JPanel pane = new JPanel();
		
	//위에 버튼 추가
	JButton btn1 = new JButton("◀");  //이전버튼
	JButton btn2 = new JButton("▶"); //다음버튼
			
	//위에 라벨추가
	JLabel yearlb = new JLabel("Year");
	JLabel monthlb = new JLabel("Month");
			
	//년월 추가
	JComboBox<Integer> yearCombo = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
	JComboBox<Integer> monthCombo = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();
			
	//패널추가
	JPanel pane2 = new JPanel(new BorderLayout());
	JPanel title = new JPanel(new GridLayout(1, 7));
	String titleStr[] = {"일", "월", "화", "수", "목", "금", "토"};
	JPanel datePane = new JPanel(new GridLayout(0, 7));

			
			
	public Reservation() {
		
		Font fnt = new Font("굴림체", Font.BOLD, 20);
		setTitle("RESERVATION DATE");

		//------년도 월 구하기------------
		cal = Calendar.getInstance(); //현재날짜
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		date = cal.get(Calendar.DATE);
				
		//년
		for(int i=year-100; i<=year+50; i++){
			yearModel.addElement(i);
		}
		yearCombo.setModel(yearModel);
		yearCombo.setSelectedItem(year);
		
		//월
		for(int i=1; i<=12; i++) {
			monthModel.addElement(i);
		}
		monthCombo.setModel(monthModel);
		monthCombo.setSelectedItem(month);
		
		//월화수목금토일
		for(int i=0; i<titleStr.length; i++){
			JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
			if(i == 0){
				lbl.setForeground(Color.red);
			}else if(i == 6){
				lbl.setForeground(Color.blue);
			}
			title.add(lbl);
		}
		//날짜 출력
		day(year, month);
		
		//----------------------------
		setTitle("Reservation");
		
		pane.add(btn1);
		pane.add(yearCombo);
		pane.add(yearlb);
		pane.add(monthCombo);
		pane.add(monthlb);
		pane.add(btn2);
		pane.setBackground(Color.CYAN);
		add(BorderLayout.NORTH, pane);
		
		pane2.add(title,"North");
		pane2.add(datePane);
		add(BorderLayout.CENTER, pane2);
		
		JLabel question = new JLabel("When is the appointment date?");
		question.setHorizontalAlignment(JLabel.CENTER);
		question.setOpaque(true);
		question.setBackground(Color.CYAN);
		add(BorderLayout.SOUTH,question);

				
		//각종 명령어
        setVisible(true);
        setSize(500,500);
        setResizable(false);
       
        
        //----------기능구현----------
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        yearCombo.addActionListener(this);
        monthCombo.addActionListener(this);
		
		
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//기능구현
		public void actionPerformed(ActionEvent e) {
			Object eventObj = e.getSource();
			if(eventObj instanceof JComboBox) {
				datePane.setVisible(false);	//보여지는 패널을 숨킨다.
				datePane.removeAll();	//라벨 지우기
				day((Integer)yearCombo.getSelectedItem(), (Integer)monthCombo.getSelectedItem());
				datePane.setVisible(true);	//패널 재출력
			}else if(eventObj instanceof JButton) {
				JButton eventBtn = (JButton) eventObj;
				int yy = (Integer)yearCombo.getSelectedItem();
				int mm = (Integer)monthCombo.getSelectedItem();
				if(eventBtn.equals(btn1)){	//전달
					if(mm==1){
						yy--; mm=12;
					}else{
						mm--;
					}				
				}else if(eventBtn.equals(btn2)){	//다음달
					if(mm==12){
						yy++; mm=1;
					}else{
						mm++;
					}
				}
				yearCombo.setSelectedItem(yy);
				monthCombo.setSelectedItem(mm);
			}	
		}
		
		//날짜출력
		public void day (int year, int month){
			Calendar date = Calendar.getInstance();//오늘날짜 + 시간
			date.set(year, month-1, 1);
			int week = date.get(Calendar.DAY_OF_WEEK);
			int lastDay = date.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			//공백출력
			for(int space=1; space<week; space++) {
				datePane.add(new JLabel("\t"));
			}
			
			//날짜 출력
			for (int day = 1; day <= lastDay; day++) {
				JLabel lbl = new JLabel(String.valueOf(day), JLabel.CENTER);
				cal.set(year, month-1, day);
				int Week = cal.get(Calendar.DAY_OF_WEEK);
				if(Week==1){
					lbl.setForeground(Color.red);				
				}else if(Week==7){
					lbl.setForeground(Color.BLUE);
				}
				datePane.add(lbl);
				lbl.addMouseListener(new MouseListener() {
					
					//날짜를 누르면 다음 선택지로 넘어간다.
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						setVisible(false);
						new Reservation2();
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					


					
				});
			}
			
				
			
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		public static void main(String[] args)
		 {
		
		 }

	
}
