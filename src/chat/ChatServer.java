package chat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class ChatServer {
	
	static String fileName="client_list.txt";
	Scanner inputStream;
	

    public static final int PORT = 5000;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        List<PrintWriter> listWriters = new ArrayList<PrintWriter>();
        
       
        

        try {
        	// 1. Create a server socket.
            serverSocket = new ServerSocket();

            // 2. binding.
            serverSocket.bind( new InetSocketAddress("127.0.0.1", PORT));
            
            int whilego=1;
            // 3. Waiting for requests.
            while(true) {
            	
                
                //LoginGUI에서 회원 정보를 읽어 와야 함
                //클라이언트 파일을 열어서 읽는다.
                
				while(whilego==1) {
					Socket socket = serverSocket.accept();
	                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
	                BufferedReader buffereedReader=new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
	                if(whilego==0) {
						printWriter.close();
						buffereedReader.close();
	                	break;
	                }
					
					
						try {
							HashMap<String, String> clients= new HashMap<String, String> ();
							Scanner inputStream=new Scanner(new File(fileName));
							
							
							//인원이 몇명인지는 모름 
							while (inputStream.hasNext()) {
								
								String[] info = (inputStream.nextLine()).split(" ");
								//info[0] : id / info[1] : password
								clients.put(info[0], info[1]);
										
							}
							
							inputStream.close();
							
							//정보 비교
							//LoginGUI, SigninGUI 에서 회원 정보를 읽어 와야 함
			                
			               
			                String login_data = buffereedReader.readLine();
			                String[] id_information = login_data.split(":");
			           
			                if("ID_CHECK".equals(id_information[0])) {
			                	int isok = 1;
			                	for(Entry<String, String> elem : clients.entrySet()){ 
			                		if(docheck_ID(id_information[2],elem.getKey(), printWriter)) {
			                			isok=0;
			                			break;
			                		}
			                	}
			                	if(isok==1) {
			                		printWriter.println(Protocol.ID_CHECK_Y);
		
				                	printWriter.flush();
			                	}
			                	
			                	
			                	
			                   
			                   
			                }
			                
			                else if("sign_check".equals(id_information[0])) {
			                	
			                	int isok = 1;
			                	//id_information[1] = protocol statement
			                	//id_information[2] = newId
			                	//id_information[3] = newpw1
			                	for(Entry<String, String> elem : clients.entrySet()){ 
			                		if(docheck_registration(id_information[2],elem.getKey(), printWriter)) {
			                			//id가 똑같으면 가입 못함 
			                			isok=0;
			                	
			                			break;
			                			
			                		}
			                	}
			                		
			                   
			                	if(isok==1) {
			                		
				                	//아이디가 같지 않으면 회원 가입 성공 
		                			try {
		                				
		                				//정보 txt 파일에 저장 
		                				FileWriter fw = new FileWriter(fileName,true);
		            					PrintWriter pw = new PrintWriter(fw);
		            					
		            					pw.println(id_information[2]+" "+id_information[3]);
		            					
		            					pw.close();
		            					fw.close();
		            					
		            					printWriter.println(Protocol.ADDED_Y);
			                			
					                	printWriter.flush();
		                				
		                			}catch(FileNotFoundException e) {
		                				e.printStackTrace();
		                			}
			                	}
			                	
			                	
			                }
			              
			                else if("LOGIN_CHECK".equals(id_information[0])) {
			                	int isok = 0;
			                	//id_information[1] = protocol statement
			                	//id_information[2] = getId
			                	//id_information[3] = getPW
			                	for(Entry<String, String> elem : clients.entrySet()){ 
			                		if(docheck_LOGIN(id_information[2],elem.getKey(),id_information[3],elem.getValue(), printWriter)) {
			                			isok=1;
			                			whilego=0;
			                			
			                			new ChatServerProcessThread(socket,id_information[2],listWriters).start();
			                			break;
			                		}
			                	}
			                   
			                	if(isok==0) {
			                		printWriter.println(Protocol.ADDED_N);
		
				                	printWriter.flush();
			                	}
			                	
			                	
			                }
			                		
			                
		
			                
			              
			                
						}
						catch(Exception e) {
							//file 을 읽지 못한다.
			              
							e.printStackTrace();
							consoleLog("Failed to read the member file.");
						}
				}
	              
	              
	                
	            }
	            
	        }
	        catch (IOException e) {
	        	//소켓 연결이 안된다.
	            e.printStackTrace();
	            consoleLog("Communication socket connection failed.");
	        }
	        finally {
	            try {
	            	// 6. Close the server socket.
	                if( serverSocket != null && !serverSocket.isClosed() ) {
	                    serverSocket.close();
	                 
	                }
	            } catch (IOException e) {
	            	// 7. Failed to close the server socket.            	
	                e.printStackTrace();
	                consoleLog("Failed to close the server socket.");
	                
	             
	            }
	        }
	    }
    
    
    private static boolean docheck_ID(String id, String isid, PrintWriter writer) {
    	
    	if(id.equals(isid)) {
    		
    		String data = Protocol.ID_CHECK_N;
    		writer.println(data);

    		writer.flush();
    		
    		return true;
    
    	}
		return false;

    }
    
    private static boolean docheck_registration(String id, String isid, PrintWriter writer) {
    	
    	if(id.equals(isid)) {
    		
    		String data = Protocol.ADDED_N;
    		writer.println(data);

    		writer.flush();
    		
    		return true;
    
    	}
		return false;

    }
    
 
    
    private static boolean docheck_LOGIN(String id, String isid, String pass, String ispass, PrintWriter writer) {
    	
    	if(id.equals(isid) && pass.equals(ispass)) {
    		
    		String data = Protocol.LOGIN_Y;
    		writer.println(data);

    		writer.flush();
    		
    		return true;
    
    	}
		return false;

    }
    
    //8. Type the log to the console.
    private static void consoleLog(String log) {
        System.out.println(Thread.currentThread().getId() + log);
    }
}