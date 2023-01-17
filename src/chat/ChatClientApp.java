package chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClientApp {
	

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 5000;
    
    //프로그램 시작과 동시에 서버 연결 

    public static void main(String[] args) {
//        String name = null;
//        Scanner scanner = new Scanner(System.in);

//        while( true ) {
//
//            System.out.println("[System] Enter your nickname");
//            System.out.print(">>> ");
//            name = scanner.nextLine();
//
//            if (name.isEmpty() == false ) {
//                break;
//            }
//
//            System.out.println("[System] Enter a nickname with more than one letter.\n");
//        }

//        scanner.close();

        Socket socket = new Socket();
        try {
            socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT) );
            new LoginGUI(socket);
            //new ChatWindow(name, socket).show();

            //PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            //String request = Protocol.JOIN_ROOM_Y + name + "\r\n";
            //pw.println(request);
        }
        catch (IOException e) {
            e.printStackTrace();
            //consoleLog("fail: not enter the room.");
        }
    }

//    private static void consoleLog(String log) {
//        System.out.println("[System] : : "+log);
//    }
}
