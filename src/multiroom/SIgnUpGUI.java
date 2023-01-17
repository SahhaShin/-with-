package multiroom;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SIgnUpGUI extends JFrame{
	private JFrame frame;
	private JTextArea title;
	private JTextField newID;
	private JTextField newPW1;
	private JTextField newPW2;
	private JTextField newName;
	private JTextField newBirth;
	private JTextField newVaccine;
	
	public SIgnUpGUI(PrintWriter pw) {
		
		String signupInfo = null;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Font fnt = new Font("굴림체", Font.BOLD, 40);
		
		Border lineBorder = BorderFactory.createLineBorder(Color.CYAN, 3);
		
		//prev button
		JButton prev = new JButton("BACK"); // prev button '<'
		//back button function -> move the reservation.java
		prev.addActionListener(e -> {
			setVisible(false);
			new LoginGUI(pw);
		});
		prev.setBounds(0, 0, 90, 60);
		frame.getContentPane().add(prev);
		
		
		//Labels
		//ID
		JLabel lblID = new JLabel("ID");
		//            (x, y, width, height)
		lblID.setBounds(250, 100, 40, 29);
		frame.getContentPane().add(lblID);
		//pw1
		JLabel lblPW1 = new JLabel("Password");
		lblPW1.setBounds(250, 140, 100, 29);
		frame.getContentPane().add(lblPW1);
		//Pw2
		JLabel lblPW2 = new JLabel("Password");
		lblPW2.setBounds(250, 180, 100, 29);
		frame.getContentPane().add(lblPW2);
		//Birth
		JLabel lblBirth = new JLabel("Birth");
		lblBirth.setBounds(250, 220, 100, 29);
		frame.getContentPane().add(lblBirth);
		//Real name
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(250, 260,80, 29);
		frame.getContentPane().add(lblName);
		//Vaccine
		JLabel lblVC = new JLabel("Vaccine(Y/N)");
		lblVC.setBounds(250, 300, 80, 29);
		frame.getContentPane().add(lblVC);
		
		
		
		
		// Text fields
		newID = new JTextField();
		newID.setBounds(350, 100, 150, 29);
		frame.getContentPane().add(newID);
		newID.setColumns(10);
		
		
		newPW1 = new JPasswordField();
		newPW1.setBounds(350, 140, 150, 29);
		frame.getContentPane().add(newPW1);
		newPW1.setColumns(10);
		
		newPW2 = new JPasswordField();
		newPW2.setBounds(350,180 , 150, 29);
		frame.getContentPane().add(newPW2);
		newPW2.setColumns(10);
		
		newBirth = new JTextField();
		newBirth.setBounds(350, 220, 150, 29);
		frame.getContentPane().add(newBirth);
		newBirth.setColumns(10);
		
		newName = new JTextField();
		newName.setBounds(350, 260, 150, 29);
		frame.getContentPane().add(newName);
		newBirth.setColumns(10);
		
		newVaccine = new JTextField();
		newVaccine.setBounds(350, 300, 150, 29);
		frame.getContentPane().add(newVaccine);
		newVaccine.setColumns(10);
		
		JButton btnIdCheck = new JButton("Check");
		btnIdCheck.setBounds(500, 100, 95, 23);
		frame.getContentPane().add(btnIdCheck);
		frame.setVisible(true);
		
		JButton btnSignUp = new JButton("SignUp");
		btnSignUp.setBounds(300, 400, 95, 23);
		frame.getContentPane().add(btnSignUp);
		frame.setVisible(true);

		//ID_chek 중복확인 과정 버튼 -> server Thread -> ID 비교 -> return
		btnIdCheck.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String getId = newID.getText();
				String IdDoubleCheck = Protocol.ID_CHECK +getId;
				
				//[0]ID_CHECK: [1]Please check the ID:[2]id
				//send to server with id  -> process thread에 아이디 중복 처리하는 코드 작성하기
				pw.println(IdDoubleCheck);
				//response from server??
			}
		
		});
		
		btnSignUp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String getId = newID.getText();
				String getPw1 = newPW1.getText();
				String getPw2 = newPW2.getText();
				String getName = newName.getText();
				String getBirth = newBirth.getText();
				String getVaccine = newVaccine.getText();
				String getNewInfo = Protocol.ADD +getId+":"+getPw1+":"+getName+":"+getBirth+":"+getVaccine;
				//send to server with id  -> process thread에 아이디 중복 처리하는 코드 작성하기
				if(!getPw1.equals(getPw2)) {
					JOptionPane.showMessageDialog(null, "Passwords don't match");
				}
				//else if ( !getVaccine.equals("Y")||!getVaccine.equals("N")) {
				//	JOptionPane.showMessageDialog(null, "Plese type Y or N");
				//}
				else {
					consoleLog(getNewInfo);
					pw.println(getNewInfo);				
				}
				
				
				
				frame.dispose();
				
			}
		
		});
	
		
		
	}
	
	private void showMessageDialog() {
		// TODO Auto-generated method stub
		
	}

	private static void consoleLog(String log) {
        System.out.println("[System] : : "+log);
    }



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
