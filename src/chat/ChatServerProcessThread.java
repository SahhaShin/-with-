package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerProcessThread extends Thread{


	
	//public static final int PORT = 5000;
	
	
    private String nickname = null;
    private Socket socket = null;
    List<PrintWriter> listWriters = null;
    List<PrintWriter> listReservationer = null;
    
    public ChatServerProcessThread(Socket socket,String id, List<PrintWriter> listWriters) {
        this.socket = socket;
        this.nickname=id;
        this.listWriters = listWriters;
        
       
        
    }

    @Override
    public void run() {
    	
        try {
        	// 1. Connect the stream. (with Communication socket.)
            BufferedReader buffereedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            //임시추가 
            addWriter(printWriter);
            
            while(true) {
            	
            	// 2. Read the client's message.
                String request = buffereedReader.readLine();
                
                String[] tokens = request.split(":");
               
             
                // 3. If there is no message from the client, it is considered a binding failure.
//                if(request == null) {
//                    doQuit(tokens[1], printWriter2);
//                    
//                    break;
//                }
                
                
                if("join".equals(tokens[0])) {
                	
                    doJoin(tokens[2],tokens[1], printWriter);
                    
                   
                }
                else if("message".equals(tokens[0])) {
                
                    doMessage(tokens[2]);
                   
                }
                else if("quit".equals(tokens[0])) {
                	
                    doQuit(tokens[1],printWriter);
               
                }
                else if("reservation".equals(tokens[0])) { 
                	
                	doEnterreservation(tokens[1],printWriter);
                	
                }
               

            }
        }
        catch(IOException e) {
        	
        	e.printStackTrace();
            consoleLog("#"+this.nickname+" has left");
        }
    }
    
    //Enter the reservation
    private void doEnterreservation(String sentence,PrintWriter writer) {
        removeWriter(writer);
        String data = "#"+this.nickname + sentence;
        broadcast(data);
    }


    private void doQuit(String sentence,PrintWriter writer) {
        removeWriter(writer);
        String data = "#"+this.nickname+sentence;
        broadcast(data);
    }

    private void removeWriter(PrintWriter writer) {
        synchronized (listWriters) {
            listWriters.remove(writer);
        }
    }

    private void doMessage(String data) {
    	
        broadcast(this.nickname + ":" + data);
    }

    private void doJoin(String nickname, String statement, PrintWriter writer) {
        this.nickname = nickname;
        
        //server->client : transmit the protocol
        String data = "#"+nickname+statement;
        broadcast(data);

        // writer pool에 저장
        addWriter(writer);
    }

    private void addWriter(PrintWriter writer) {
    	
        synchronized (listWriters) {
            listWriters.add(writer);
           
        }
    }
    
    private void addReservationer(PrintWriter reservationer) {
        synchronized (listReservationer) {
        	listReservationer.add(reservationer);
        }
    }


    private void broadcast(String data) {
    	
        synchronized (listWriters) {
            for(PrintWriter writer : listWriters) {
            	
                writer.println(data);
                writer.flush();
            }
        }
    }
    

    private void consoleLog(String log) {
        System.out.println("[System] : : "+log);
    }
}