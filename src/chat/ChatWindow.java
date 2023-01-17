package chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatWindow {
	

    private String name;
    private Frame frame;
    private Panel pannel;
    private Panel pannel_reservation;
    private Button buttonSend;
    private TextField textField;
    private TextArea textArea;
    private Button reservation;
    private Button reservation_confirm;

    private Socket socket;

    public ChatWindow(String name, Socket socket) {
        this.name = name;
        frame = new Frame(name);
        pannel = new Panel();
        pannel_reservation = new Panel();
        buttonSend = new Button("Send");
        textField = new TextField();
        textArea = new TextArea(30, 80);
        
        //reservation START
        reservation = new Button("RESERVATION");
        reservation_confirm = new Button("CONFIRM");
        reservation.setPreferredSize(new Dimension(500,50));
        reservation_confirm.setPreferredSize(new Dimension(500,50));
        
        
        this.socket = socket;
        

        new ChatClientReceiveThread(socket).start();
    }

    public void show() {
       
    	reservation.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
            	//서버에 예약 시작을 전달해줌
            	enterReservation();
            }
            
        });
    	
    	reservation_confirm.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
            	//서버에 예약 시작을 전달해줌
            	enterReservationConfirm();
            }
            
        });
    	
    	
    	
    	// Button
       
        buttonSend.setForeground(Color.BLACK);
        reservation_confirm.setForeground(Color.BLUE);
        reservation.setForeground(Color.RED);
        buttonSend.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                sendMessage();
            }
        });


        // Textfield
        textField.setColumns(80);
        textField.addKeyListener( new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char keyCode = e.getKeyChar();
                if (keyCode == KeyEvent.VK_ENTER) {
        
                    sendMessage();
                }
            }
        });

        // Pannel
        pannel_reservation.setBackground(Color.CYAN);
        pannel.setBackground(Color.CYAN);
        pannel.add(textField);
        pannel.add(buttonSend);
        frame.add(BorderLayout.SOUTH, pannel);

        // TextArea
        textArea.setEditable(false);
        frame.add(BorderLayout.CENTER, textArea);
        
        //reservation_START
        pannel_reservation.add(BorderLayout.WEST,reservation);
        pannel_reservation.add(BorderLayout.EAST,reservation_confirm);
        
        frame.add(BorderLayout.NORTH, pannel_reservation);

        // Frame
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PrintWriter pw1;
                try {
                    pw1 = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    String request = Protocol.ROOM_OUT_Y+"\r\n";
                    pw1.println(request);
                    System.exit(0);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
        frame.pack();
    }
    
    private void enterReservation() {
    	
            
        new Reservation();
       
    }
    
    
    private void enterReservationConfirm () {
    	
    	new ReservationConfirm();
    }
    
    // 쓰레드를 만들어서 대화를 보내기
    private void sendMessage() {
        PrintWriter pw1;
        BufferedReader buffereedReader;
        try {
        	
        	//메세지를 ChatServerProcessThread가 읽는다. -> doMessage
        	//ChatServerProcessThread에서 : 기준으로 split됨 
            pw1 = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            buffereedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            
            
            String message = textField.getText();
          
            //server -> client protocol
            String request = Protocol.MESSAGE_BROAD_Y + message + "\r\n";
            
            pw1.println(request);
            pw1.flush();
            textField.setText("");
            textField.requestFocus();
            
        }
        catch (IOException e) {
        	
        	//메세지 전송에 실패했습니다.
        	//server -> client protocol 스트림 연결 실패로 간주해 콘솔로 대체 
            e.printStackTrace();
            consoleLog("message: not send a message.");
        }
    }
    
    private void consoleLog(String log) {
        System.out.println("[System] : : "+log);
    }

    private class ChatClientReceiveThread extends Thread{
        Socket socket = null;

        ChatClientReceiveThread(Socket socket){
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
       
                while(true) {
                	String msg = br.readLine();
                    textArea.append(msg);
                    textArea.append("\n");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
