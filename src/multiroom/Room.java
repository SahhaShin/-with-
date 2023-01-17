package multiroom;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Room {
	private List<PrintWriter> userList = new ArrayList<>();
	private List<String> userNameList = new ArrayList<>();
	private String roomName;//중복되지 않는 이름
	
	public Room(String roomName) {
		this.roomName = roomName;
	}
	public void enterUser(PrintWriter newUser,String userName) {
		userList.add(newUser);
		userNameList.add(userName);
	}
	public void quitUser(PrintWriter newUser,String userName) {
		userList.remove(newUser);
		userNameList.remove(userName);
	}
	public boolean sameName(String name) {
		if(roomName.equals(name))
			return true;
		else
			return false;
	}
	public String printRoomName() {
		return roomName;
	}
	public List<PrintWriter> returnUserList(){
		return userList;
	}
	public List<String> returnUserNameList(){
		return userNameList;
	}
}
