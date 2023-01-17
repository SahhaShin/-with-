package multiroom;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
	private List<Room> roomList = new ArrayList<>();
	
	public void createRoom(Room newRoom) {
		roomList.add(newRoom);
	}
	
	public boolean findRoomReuturnBool(String findRoomName) {
		for(Room temp : roomList) {
			if(temp.sameName(findRoomName)) {
				return true;
			}
		}
		return false;
	}
	
	public Room findRoomReuturnRoom(String findRoomName) {
		for(Room temp : roomList) {
			if(temp.sameName(findRoomName)) {
				return temp;
			}
		}
		return null;
	}
	
	public void printAllRoomName() {
		if(!roomList.isEmpty()) {
			for(Room temp : roomList) {
				System.out.print(temp.printRoomName() + "//");
				System.out.println();
			}
		}
		else
			System.out.println("room list is null");
	}
}
