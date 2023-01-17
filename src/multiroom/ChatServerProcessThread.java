package multiroom;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;


public class ChatServerProcessThread extends Thread{
    private String id = null;
    private String roomname = null;
    private Socket socket = null;
    RoomManager roomManager = null;
    private String response = null;
    HashMap<String, String> clients= new HashMap<String, String> ();
    static String fileName="client_list.txt";

    public ChatServerProcessThread(Socket socket, RoomManager roomManager) {
        this.socket = socket;
        this.roomManager = roomManager;
    }

    @Override
    public void run() throws ArrayIndexOutOfBoundsException{
    	

    	
        try {
        	
        	
            BufferedReader buffereedReader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            while(true) {
                String request = buffereedReader.readLine();
  
                System.out.println(request);
                String[] tokens = request.split(":");
             
                if("LOGIN_CHECK".equals(tokens[0])){
             
                	//id_information[1] = protocol statement
                	//id_information[2] = getId
                	//id_information[3] = getPW
                	
                	//if we can find same id, send the protocol_y to the client
                	if(docheck_LOGIN(tokens[2],tokens[3], printWriter)) {
                		String data = Protocol.LOGIN_Y;
                		printWriter.println(data);
                		printWriter.flush();
                		//send the 'make the room question' protocol to the client
                		printWriter.println(Protocol.MAKE_ROOM_QUESTION+tokens[2]+":"+tokens[3]);
	                	printWriter.flush();       
                	}
                	//if we can't find same id, send the protocol_n to the client
                	else {
                		printWriter.println(Protocol.LOGIN_N+tokens[2]+":"+tokens[3]);
	                	printWriter.flush();                		                		
                	}           
                }
              //Sign up
				else if ("ADD".equalsIgnoreCase(tokens[0])) {
					int idok = 0;
					// token[2]=id / token[3]=pw / token[4]=pw2 / token[5]=name/toekn[6]=birth/toekn[7]=Y/N
					// check the password
					if (!tokens[3].equals(tokens[4])) {
						printWriter.println(Protocol.ADDED_N + "Passwords don't match:");
						printWriter.flush();
						idok = 1;
						System.out.println("idok=1 , Passwords don't match:");
					}
					// check the birth
					else if (!checkDate(tokens[6])) {
						printWriter.println(Protocol.ADDED_N + "Invalid date:");
						printWriter.flush();
						idok = 1;
						System.out.println("idok=1 , Invalid date:");
					}
					// check the vaccine
					else if (!tokens[7].equalsIgnoreCase("Y") && !tokens[7].equalsIgnoreCase("N")) {
						printWriter.println(Protocol.ADDED_N + "Incorrect vaccine info:");
						printWriter.flush();
						idok = 1;
						System.out.println("idok=1 , Incorrect vaccine info:");
					} 
					else {
						//check the id
						//if we can find same id, send the protocol_n to the client
						if(idcheck(tokens[2],printWriter)) {
							printWriter.println(Protocol.ADDED_N + tokens[2]);
							printWriter.flush();
							idok = 1;
							System.out.println("idok=1 , idcheck");
						}	
					}
					// When all conditions satisfied
					System.out.println("idok="+idok);
					if (idok == 0) {
						System.out.println("When all conditions satisfied");

						try {
							BufferedWriter buffWrite = new BufferedWriter(new FileWriter(fileName, true));
							String info = tokens[2] + " " + tokens[3] + " " + tokens[5] + " " + tokens[6] + " "
									+ tokens[7];
							System.out.println(info);

							buffWrite.append(info);
							buffWrite.append("\n");

							buffWrite.close();
							printWriter.println(Protocol.ADDED_Y + tokens[2]);
							printWriter.flush();
						} catch (Exception e) {
							e.printStackTrace();
							consoleLog(Protocol.FILE_FAIL);
							printWriter.println(Protocol.FILE_FAIL);
							printWriter.flush();
						}

					}

				}
                
                else if("makeroom".equals(tokens[0])) {
                	
                	//data[2] : id, data[3]:roomname
                	if(roomManager.findRoomReuturnBool(tokens[3])) { //cant make new room
                		response = "cannotMakeRoom:" + tokens[2] + "already exist"; 
                		printWriter.println(response);
                		printWriter.flush();
                	}
                	else {//make new room
                		System.out.println("tokens[2]= "+tokens[2]);
                		System.out.println("tokens[3]= "+tokens[3]);
                		makeGroup(tokens[2],tokens[3],printWriter);
                		//response = Protocol.ENTER_Y +":"+tokens[2]+":"+tokens[3]; 
                		//printWriter.println(response);
                		//printWriter.flush();
                		
                	}
                }
                else if("joinroom".equals(tokens[0])) {
                	//tokens[2] : id, tokens[3]:roomname
                	if(roomManager.findRoomReuturnBool(tokens[3])){ 
                		joinGroup(tokens[2],tokens[3],printWriter);
                	}
                	else {
                		response = "noGroupExist:" + tokens[2] + "���� �����߽��ϴ�."; 
                		printWriter.println(response);
                		printWriter.flush();
                	}
                		
                }
                else if("message".equals(tokens[0])) {
                	System.out.println("<<else if>>");
                    doMessage(tokens[1],tokens[2],tokens[3]);//data[0] : id, data[1]:roomName, data[2]:message
                }
                else if("quit".equals(tokens[0])) {
                	quitGroup(tokens[1],tokens[2],printWriter);
                	//System.exit(0);
                }
                else if("reservation_data".equals(tokens[0])) { 
                	
                	doEnterreservation(tokens[1],tokens[2],printWriter);
                	
                }
            }
        }
        catch(IOException e) {
        	consoleLog("ä�ù��� �������ϴ�.");
        }
    }
    //Enter the reservation
    private synchronized void doEnterreservation(String roomName,String reservationInfo,PrintWriter writer) {
        //remove�� �ʿ��Ѱ�?
    	//removeWriter(writer);
        //String data = "#"+this.nickname + sentence;
    	//String request = "reservation_data:"+ roomName + ":" + message + "\r\n";
        Room nowRoom = roomManager.findRoomReuturnRoom(roomName);
        List<String> userNameList = nowRoom.returnUserNameList();
        for(String temp:userNameList) {
            System.out.println("namelist" + temp);
        }
        //���Ͽ� id�� ����ؼ� id���� ��� ������ ã�´� -> �������� reservationinfo�� �����Ѵ� -> ���Ͽ� ����..??
        /*
         * 
         * 
         * */
        String data = "broadcast:" + "reservationsuccess";
        //id 없어서 roomName 임시로 2개 해둠 
        broadcast(data,roomName);
    }
    
    private synchronized void makeGroup(String name,String roomName,PrintWriter writer) {
    	Room newRoom = new Room(roomName);
    	roomManager.createRoom(newRoom);
    	newRoom.enterUser(writer,name);
    	String data = Protocol.ENTER_Y+name+":"+roomName;
    	System.out.println("makeGroup = "+data);
        broadcast(data,roomName);
    }
    private synchronized void joinGroup(String name,String roomName,PrintWriter writer) {
    	Room nowRoom = roomManager.findRoomReuturnRoom(roomName);
    	nowRoom.enterUser(writer,name);
    	String data = Protocol.ENTER_Y+ name+":"+roomName;
        broadcast(data, roomName);
    }
    private synchronized void quitGroup(String name,String roomName,PrintWriter writer) {
    	String data = "quitsuccess:" + name + "����" + roomName + "���� �����߽��ϴ�.";
        broadcast(data, roomName);
    	Room nowRoom = roomManager.findRoomReuturnRoom(roomName);
    	nowRoom.quitUser(writer,name);
    }
    private synchronized void doMessage(String name,String roomName,String message) {
        broadcast("broadcast:" + name + ":" + message, roomName);
    }
    private synchronized void broadcast(String data, String roomName) {
    	List<PrintWriter> listWriters = (roomManager.findRoomReuturnRoom(roomName)).returnUserList();
            for(PrintWriter writer : listWriters) {
            	System.out.println("<<broadcast>>");
                writer.println(data);
                writer.flush();
            }
    }
    
    //login check
    private static boolean docheck_LOGIN(String id, String pass, PrintWriter writer) {
    	HashMap<String, String> clients_copy= new HashMap<String, String> ();
    	//read the client information file
    	clients_copy=clientfileopen();
    	
    	//check the same id / password
    	for(Entry<String, String> elem : clients_copy.entrySet()){ 
    		if((id.equals(elem.getKey()) && pass.equals(elem.getValue()))) {
    			//if we can find same login information        		
        		return true;
    			
    		}
    	}
		return false;

    }
    
    //회원가입 아이디 체크 
    private static boolean idcheck(String id, PrintWriter writer) {
    	HashMap<String, String> clients_copy= new HashMap<String, String> ();
    	//read the client information file
    	clients_copy=clientfileopen();
    	
    	//check the same id / password
    	for(Entry<String, String> elem : clients_copy.entrySet()){ 
    		if((id.equals(elem.getKey()))){
    			System.out.println("id = "+id);
    			System.out.println("getkey = "+elem.getKey());
    			//if we can find same id	
        		return true;
    			
    		}
    	}
		return false;


    }
  //check the reservation
  	private synchronized static boolean checkDate(String checkDate) {
  		try {
  			// Set the date format to verify.
  			SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy/MM/dd");
  			
  			//In the case of false, an error occurs when the value entered at the time of processing is in an incorrect format.
  			dateFormatParser.setLenient(false);
  			
  			//Check if it applies to the target value format.
  			dateFormatParser.parse(checkDate);
  			return true;
  		} catch (Exception e) {
  			return false;
  		}
  	}

    //clients file open
    private static HashMap<String, String> clientfileopen() {
    	HashMap<String, String> clients_copy= new HashMap<String, String> ();
		try {			
			Scanner inputStream=new Scanner(new File(fileName));
	
			//read the file
			while (inputStream.hasNext()) {
				
				String[] info = (inputStream.nextLine()).split(" ");
				//info[0] : id / info[1] : password
				clients_copy.put(info[0], info[1]);				
			}
			inputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed to read the member file.");
			
		}
		return clients_copy;
    }
    

    private void consoleLog(String log) {
        System.out.println(log);
    }
}