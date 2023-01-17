package multiroom;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ChatWindow {
    private String id;
    private String roomName;
    private Frame frame;
    private Panel pannel;
    private Button buttonSend;
    private Button buttonReservation;
    private TextField textField;
    private TextArea textArea;
    
    private Socket socket;

    public ChatWindow(String id,String roomName, Socket socket) throws IOException {
        this.id = id;
        this.roomName = roomName;
        frame = new Frame(roomName + ":" + id);
        pannel = new Panel();
        buttonReservation = new Button("RESERVATION");
        buttonSend = new Button("Send");
        textField = new TextField();
        textArea = new TextArea(30, 80);
        this.socket = socket;

        new ChatClientReceiveThread(socket).start();
    }

    public void show() {
    	// ButtonReservation
    	buttonReservation.setBackground(Color.GRAY);
    	buttonReservation.setForeground(Color.RED);
    	buttonReservation.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent actionEvent ) {
                enterReservation();
            }
        });
        
        // ButtonSend
        buttonSend.setBackground(Color.GRAY);
        buttonSend.setForeground(Color.BLACK);
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
        pannel.setBackground(Color.LIGHT_GRAY);
        pannel.add(textField);
        pannel.add(buttonSend);
        frame.add(BorderLayout.SOUTH, pannel);

        // TextArea
        textArea.setEditable(false);
        frame.add(BorderLayout.CENTER, textArea);
        frame.add(BorderLayout.NORTH, buttonReservation);

        // Frame
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                PrintWriter pw;
                try {
                    pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    String request = "quit:" + id + ":" + roomName + "\r\n";
                    pw.println(request);
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
    	PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            String request = "reservation start!";
            pw.println(request);

            textField.setText("");
            textField.requestFocus();
            
            new Reservation(socket,roomName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // �����带 ���� ��ȭ�� ������
    private void sendMessage() {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            String message = textField.getText();
            String request = "message:" + id + ":" + roomName + ":" + message + "\r\n";
            pw.println(request);
            System.out.println("\n<<sendMessage>>");
           
            textField.setText("");
            textField.requestFocus();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
                    System.out.println("msg from server : " + msg);
                    msg = msg.substring(msg.indexOf(":") + 1);
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
