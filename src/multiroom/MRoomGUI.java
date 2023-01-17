package multiroom;
import java.awt.Color;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.net.Socket;

import java.net.UnknownHostException;



import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JList;

import javax.swing.JOptionPane;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.border.TitledBorder;



public class MRoomGUI extends JFrame implements ActionListener, Runnable {
	JList<String> roomInfo,roomInwon,waitInfo;

	   JScrollPane sp_roomInfo, sp_roomInwon, sp_waitInfo;

	   JButton bt_create, bt_enter, bt_exit;

	   

	   JPanel p;


	   //소켓 입출력객체

	   BufferedReader in;

	   OutputStream out;

	   

	   String selectedRoom;
	   
	   public MRoomGUI() {
		   setTitle("WAITING ROOM");

			 

			 roomInfo = new JList<String>(); //--ROOM LIST
			 roomInwon = new JList<String>(); //룸에 있는 사람 정보 리스트 

			 roomInfo.setBorder(new TitledBorder("ROOM INFORMATION"));

			   

			 roomInfo.addMouseListener(new MouseAdapter() {

			     @Override

			    public void mouseClicked(MouseEvent e) {
			    	 //방을 클릭하면 방에 속한 인원 정보를 가져온다.
			    	 String room_info = roomInfo.getSelectedValue();
			    	 
			    	 //방이 없으면 종료 
			    	 if(room_info==null)return;

					 
					 //방이 있으면 좌측 인원 정보 출력 
					 //미구현 
					 String people = roomInwon.getSelectedValue();

					       

			    }	 

			 });

			   

			   
			
			 //룸에 있는 특정 인원 정보 
			 roomInwon.setBorder(new TitledBorder("PEOPLE INFORMATION"));


		       

		     sp_roomInfo = new JScrollPane(roomInfo);

		     sp_roomInwon = new JScrollPane(roomInwon);


		     
		     //버튼들 : 방만들기 / 방입장 / 방퇴장 
		     bt_create = new JButton("CREATE");

		     bt_enter = new JButton("ENTER");

		     bt_exit = new JButton("EXIT");     

		     

		     p = new JPanel();

		     

		     sp_roomInfo.setBounds(10, 10, 300, 450);

		     sp_roomInwon.setBounds(320, 10, 180, 300);


		     

		     bt_create.setBounds(320,330,180,30);

		     bt_enter.setBounds(320,370,180,30);

		     bt_exit.setBounds(320,410,180,30);

		     

		     p.setLayout(null);

		     p.setBackground(Color.CYAN);

		     p.add(sp_roomInfo);

		     p.add(sp_roomInwon);

		     //p.add(sp_waitInfo);

		     p.add(bt_create);

		     p.add(bt_enter);

		     p.add(bt_exit);

		     

		     add(p);

		     setBounds(300,200, 510, 500);

		     setVisible(true);

		     setDefaultCloseOperation(EXIT_ON_CLOSE); 



		     

		     eventUp();

		   }//생성자

		   

		   private void eventUp(){//이벤트소스-이벤트처리부 연결

		

			   bt_create.addActionListener(this);

			   bt_enter.addActionListener(this);

			   bt_exit.addActionListener(this);

			   
		   }

		   

		   @Override

		   public void actionPerformed(ActionEvent e) {

			  	  

			  

		   }//actionPerformed 
	   

	

	
	
	public static void main(String[] args) {
		new MRoomGUI();

	}



	@Override
	public void run() {
		
		
	}

}
