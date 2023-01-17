package chat;

public class Protocol {
	//ChatServer
	//0-1)로그인이 성공적으로 완료되었다.
	public static final String LOGIN_Y = "ok: The login was successfully completed:";
	
	//0-2)로그인에 실패했습니다.
	public static final String LOGIN_N = "fail: Login failed:";
	
	//ChatClientApp
	//1-1)유저가 방입장에 성공했을 경우 (통신소켓 생성 성공)
	public static final String JOIN_ROOM_Y = "join: enter the room:";

	
	//ChatWindow
	//2-1)유저가 메세지를 보내는 것에 성공했을 경우
	public static final String MESSAGE_BROAD_Y = "message: send a message:";

	//3-1)유저가 방퇴장에 성공했을 경우
	public static final String ROOM_OUT_Y = "quit: has left:";


	//Reservation2
	//4-1)유저의 예약 요청 정상적으로 접수되었을 경우
	public static final String RESERVATION_Y = "reservation: complete the reservation:";

	//4-2)유저의 예약 요청 거절되었을 경우 (백신 서버와 관련된 프로토콜?)
	public static final String RESERVATION_N = "reservation: failed the reservation:";
	
	
	//LoginGUI
	public static final String LOGIN_CHECK = "LOGIN_CHECK: Please check the LOGIN information:"; //로그인 정보 체크 
	
	//SignUpGUI 
	public static final String ID_CHECK = "ID_CHECK: Please check the ID:"; //ID 체크해주세요.
	
	public static final String ID_CHECK_Y = "Available: available ID:";
	
	public static final String ID_CHECK_N = "Impossible: Please enter a different ID:";
	
	public static final String SIGN_CHECK = "sign_check: Please check the ID and PASSWORD:";
		
	public static final String ADDED_Y = "added: new account enrolled:";
		
	public static final String ADDED_N = "exist: Already exists:";

	
	
	


}
