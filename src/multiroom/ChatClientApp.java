package multiroom;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ChatClientApp {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        String id = null;
        String wantMakeRoom = null;
        String roomName = null;
        String makeRoom = null;
        String request = "userinputerror:" + "no data";
        Scanner scanner = new Scanner(System.in);
        PrintWriter pw;
        BufferedReader br;
        //connect socket
        Socket socket = new Socket();
        try {
            socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT));
            
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            
            //login gui -> try login -> success login (connect socket)
            LoginGUI window = new LoginGUI(pw);
            //receive the id / password
           
            
            //send to server with id / password
            //receive the protocol
            while(true) {
	            String login= br.readLine();
	            //System.out.println("statement = "+login);
	            String[] tokens = login.split(":");
	            
	            //login success
	            if("ok".equals(tokens[0])) {
	            	consoleLog(tokens[1]);
	            	//make the room -> enter the room
	            	
	            }
	            
	            else if("m_question".equals(tokens[0])) {
	            	//do you want making a room?
	            	//방 만들건지 물어보기 
            		//tokens[2] == id
            		//tokens[3] == password
	            	System.out.println("m_question tokens[2] = "+tokens[2]);
	            	System.out.println("m_question tokens[3] = "+tokens[3]);
	            	new MakeRoomGUI(pw,tokens[2],tokens[3]);
	            	
	            	
	            }
	            else if(tokens[0].equals("entersuccess")) {
            		consoleLog("entersuccess.");
            		//tokens[2] == id
            		//tokens[3] == pass
                   new ChatWindow(tokens[2],tokens[3],socket).show();
                   break; //break 없으면 홀수번째 메세지만 나옴 
            	}
	            
	            else if(tokens[0].equals("noGroupExist")) {
	            	//we can't find room 
	            	consoleLog("noGroupExist");
	            }
	            else if(tokens[0].equals("Available")) {
	            	//id double check pass
	            	
	            }
	            else if(tokens[0].equals("Impossible")) {
	            	//id double check fail
	            	
	            }
	            else if("file fail".equals(tokens[0])){
	            	
	            	JOptionPane.showMessageDialog(null, "Failed to open client list");
	            }
	            //server say : sign up ok
	            else if("added".equals(tokens[0])) {
	            	JOptionPane.showMessageDialog(null, "Sign up Completed");
	            }
	            //server say : sign up fail
	            else if("unsaved".equals(tokens[0])) {
	            	JOptionPane.showMessageDialog(null, tokens[1]);
	            }
	            else {
	            	//failed the login
	            	consoleLog(tokens[1]);
	            	
	            }
            }
            
           
            
           
        }
        catch (IOException e) {
            e.printStackTrace();
        }
            
    }
        
        

//        Socket socket = new Socket();
//        try {
//            socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT));
//
//            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
//            if(wantMakeRoom.equals("Y")) {
//            	request = "makeroom:" + id + " " + roomName + "\r\n";
//            }
//            else if(wantMakeRoom.equals("N")){
//            	request = "joinroom:" + id + " " + roomName + "\r\n";
//            }
//            pw.println(request);//����
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//            //while(true) {
//                String msg = br.readLine();
//                String[] tokens = msg.split(":");
//                if(tokens[0].equals("entersuccess")) {
//                	consoleLog("entersuccess.");
//                    new ChatWindow(id,roomName,socket).show();
//                }
//                //else if(tokens[0].equals("broadcast"))
//                else {//error exists
//                	consoleLog("error exists");
//                }
//            //}
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void consoleLog(String log) {
        System.out.println(log);
    }
}
