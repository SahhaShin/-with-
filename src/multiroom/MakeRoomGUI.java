package multiroom;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class MakeRoomGUI extends JFrame {
	private JFrame frame;
	
	
	public MakeRoomGUI(PrintWriter pw, String id, String password){
		setTitle("MakeRoom");
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(50, 100, 500, 300);
		frame.setLayout(null);
		
		Font fnt = new Font("굴림체", Font.BOLD, 20);
		JLabel makeroom = new JLabel("Do you want making a room?");
		makeroom.setFont(fnt);
		makeroom.setBounds(100, 50, 500, 50);
		frame.add(makeroom);
		
		JButton yes = new JButton("YES");
		yes.setBounds(100, 150, 100, 50);
		frame.add(yes);
		
		
		JButton no = new JButton("NO");
		no.setBounds(300, 150, 100, 50);
		frame.add(no);
		frame.setVisible(true);
		
		
		//button function
		
		//1. yes
		yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 텍스트 필드의 텍스트를 서버로 보내야함	
				
				// making room yes -> y_makeroomgui
				new Y_MakeRoomGUI(pw, id, password);
				frame.setVisible(false);
				
		
					
			}
				
		});
		
		
		//2. no
				no.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// 텍스트 필드의 텍스트를 서버로 보내야함	
						
						// making room yes -> y_makeroomgui
						new N_MakeRoomGUI(pw, id, password);
						frame.setVisible(false);
						
				
							
					}
						
				});

	}
	
	
	
	

	public static void main(String[] args) {
		

	}

}
