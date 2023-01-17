package multiroom;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    public static final int PORT = 5000;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        RoomManager roomManager = new RoomManager();
        Socket socket = null;
        try {
// 1. make the server socket
            serverSocket = new ServerSocket();

// 2. binding
            String hostAddress = "127.0.0.1";
            serverSocket.bind( new InetSocketAddress(hostAddress, PORT) );
            consoleLog("hostAddress - " + hostAddress + ":" + PORT);

// 3. make the communition socket
            while(true) {
                socket = serverSocket.accept();
				new ChatServerProcessThread(socket, roomManager).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if( serverSocket != null && !serverSocket.isClosed() ) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consoleLog(String log) {
        System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
    }
}
