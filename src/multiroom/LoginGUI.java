package multiroom;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class LoginGUI extends JFrame {
	
	//ChatServer 연결을 위한 토대 
//	private static final String SERVER_IP = "127.0.0.1";
//    private static final int SERVER_PORT = 5000;
//    Socket socket = new Socket();
	
	private JFrame frame;
	private JTextArea title;
	private JTextField inputID;
	private JTextField inputPW;
	
	
	public LoginGUI(PrintWriter pw) {
		setTitle("Login");
		String getMemberInfo = null;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//main title
		Font fnt = new Font("굴림체", Font.BOLD, 40);
		title = new JTextArea("GROUP VACCINE PASS");
		Border lineBorder = BorderFactory.createLineBorder(Color.CYAN, 3);
		title.setBorder(BorderFactory.createCompoundBorder());
		title.setBackground(Color.CYAN);
		title.setEditable(false);
		title.setBounds(150, 100, 500, 50);
		title.setFont(fnt);
		frame.getContentPane().add(title);
		//inputID.setColumns(10);
		
		inputID = new JTextField();
		inputID.setBounds(289, 292, 138, 29);
		frame.getContentPane().add(inputID);
		inputID.setColumns(10);
		
		JLabel lblID = new JLabel("ID");
		lblID.setBounds(233, 292, 26, 29);
		frame.getContentPane().add(lblID);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(202, 345, 75, 29);
		frame.getContentPane().add(lblPassword);
		
		inputPW = new JPasswordField();
		inputPW.setColumns(10);
		inputPW.setBounds(289, 345, 138, 29);
		frame.getContentPane().add(inputPW);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setBounds(377, 408, 95, 23);
		frame.getContentPane().add(btnSignIn);
		
		JButton btnLogIn = new JButton("LogIn");
		btnLogIn.setBounds(233, 408, 95, 23);
		frame.getContentPane().add(btnLogIn);
		frame.setVisible(true);
		
		
		/* LogIn 버튼을 눌렀을때의 ActionListener
		 * 서버 연결 시도해야한다.
		 * 서버에선 계정 정보가 적힌 텍스트를 불러내고 id, pw textfield에 적힌 내용을 읽어서 비교한다.
		 * 파일을 다 읽을 때 까지 돌리고 계정 정보와 일치한다면 게임 대기 화면으로 넘어감
		 * 아닐 경우 상황에 맞게 오류 메세지 출력
		*/
		
		
		btnLogIn.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			// 텍스트 필드의 텍스트를 서버로 보내야함		
			String getId = inputID.getText();
			String getPW = inputPW.getText();
			String getMemberInfo = Protocol.LOGIN_CHECK+getId +":"+ getPW;
			
			//send to server with id / password
			pw.println(getMemberInfo);
			
			
		
	
				
		}
			
	});
		
		//회원가입 버튼 눌렀을 경우 
		btnSignIn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//SigninGUI signWindow = new SigninGUI(socket);
				//signWindow.setVisible(true);
				SIgnUpGUI signWindow = new SIgnUpGUI(pw);
			}
			
		});

		
	}
	

	private static void consoleLog(String log) {
        System.out.println("[System] : : "+log);
    }
	
	
	
	
	public static void main(String[] args) {
		//LoginGUI window = new LoginGUI();
	}
	
	



}
