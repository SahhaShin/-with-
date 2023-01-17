package multiroom;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class N_MakeRoomGUI extends JFrame {
	//new room
		private JFrame frame;
		private JTextField origin_room_name;
		
		public N_MakeRoomGUI(PrintWriter pw, String id, String password) {
			setTitle("Enter origin room");
			frame = new JFrame();
			frame.getContentPane().setBackground(Color.CYAN);
			frame.setBounds(50, 100, 500, 300);
			frame.setLayout(null);
			
			Font fnt = new Font("굴림체", Font.BOLD, 20);
			JLabel makeroom = new JLabel("Please enter your origin room name!");
			makeroom.setFont(fnt);
			makeroom.setBounds(70, 50, 500, 50);
			frame.add(makeroom);
			
			origin_room_name = new JTextField("here!");
			origin_room_name.setBounds(70, 100, 350, 29);
			frame.getContentPane().add(origin_room_name);
			origin_room_name.setColumns(10);
			
			JButton create = new JButton("ENTER");
			create.setBounds(180, 160, 120, 50);
			frame.add(create);
			frame.setVisible(true);
			
			
			//button function
			create.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// 텍스트 필드의 텍스트를 서버로 보내야함		
					String room_name = origin_room_name.getText();
					//protocol 과 룸이름 사이에 id있어야 함 
					String getMemberInfo = Protocol.JOIN_ROOM+id+":"+room_name;
					
					//send to server about making room
					pw.println(getMemberInfo);
					frame.setVisible(false);
					return;
		
				}
					
			});
		}

	public static void main(String[] args) {
		

	}

}
