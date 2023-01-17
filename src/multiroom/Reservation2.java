package multiroom;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;

public class Reservation2 extends JFrame implements ActionListener, ListSelectionListener {
	Container cp;
	JComboBox cb;
	Vector list;
	//파일에 가게정보 저장하기 -> String array에 추후 저장이 필요할 듯
	/*
	 * 
	 * 
	 * 
	 * */
	JList lstKorea;
	String Seoul[] = { "Seoul1", "Seoul2", "Seoul3", "Seoul4", "Seoul5" };
	String Busan[] = { "Busan2", "Busan2", "Busan3", "Busan4", "Busan5" };
	String Daegu[] = { "Daegu1", "Daegu2", "Daegu3", "Daegu4", "Daegu5" };
	String Incheon[] = { "Incheon1", "Incheon2", "Incheon3", "Incheon4", "Incheon5" };
	String Gwangju[] = { "Gwangju1", "Gwangju2", "Gwangju3", "Gwangju4", "Gwangju5" };
	String Daejeon[] = { "Daejeon1", "Daejeon2", "Daejeon3", "Daejeon4", "Daejeon5" };
	String Ulsan[] = { "Ulsan1", "Ulsan2", "Ulsan3", "Ulsan4", "Ulsan5" };

	JScrollPane jsp;
	JLabel lblOut;
	JButton btn1 = new JButton("Make a group vaccine pass");
	JButton btn2 = new JButton("◀"); // 이전버튼

	int year, month, day;
	Socket socket;
	String roomName;
	
	public Reservation2(int year, int month, int day, Socket socket,String roomName) {
		setTitle("RESERVATION LOCATION");
		cp = this.getContentPane();
		this.setDesign();

		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(300, 100, 200, 200);
		cp.setBackground(new Color(255, 255, 200));
		setSize(600, 500);
		this.setVisible(true);

		this.year = year;
		this.month = month;
		this.day = day;
		this.socket = socket;
		this.roomName = roomName;
	}

	public void setDesign() {
		cp.setLayout(new BorderLayout());
		JPanel pTop = new JPanel();

		// JPanel pBottom=new JPanel();

		list = new Vector();
		list.add("Seoul");
		list.add("Busan");
		list.add("Daegu");
		list.add("Incheon");
		list.add("Gwangju");
		list.add("Daejeon");
		list.add("Ulsan");

		// 콤보박스 생성
		cb = new JComboBox(list);

		// 콤보박스 액션이벤트 발생
		cb.addActionListener(this);

		pTop.add(btn2);
		pTop.add(new JLabel("Area Selection:"));
		pTop.add(cb);
		pTop.add(btn1);
		btn1.setForeground(Color.RED);
		pTop.setBackground(Color.CYAN);
		cp.add("North", pTop);

		lstKorea = new JList(Seoul);

		// 기능구현 !!!!!!!!!!!!!!!!!!!!!!!!

		// 단체 백신 패스만들기 ------------기능 받아서 구현해야함
		btn1.addActionListener(e -> {
			// 새로운 창!

			// 장소가 선택되지 않으면 경고메시지 출력
			if (lstKorea.getSelectedIndex() == -1) {
				JOptionPane alert = new JOptionPane();
				alert.showMessageDialog(null, "장소가 선택되지 않았습니다.", "alert", JOptionPane.ERROR_MESSAGE);
			} else { // 장소가 선택되었을때
				JOptionPane alert = new JOptionPane();
				Calendar reserve_date = Calendar.getInstance();
				reserve_date.set(year, month, day);

				if (reserve_date.after(Calendar.getInstance())) {
					String message = String.format("%d.%d.%d %s", year, month, day, lstKorea.getSelectedValue());
					alert.showMessageDialog(null, message, "alert", JOptionPane.ERROR_MESSAGE);

					try {
						PrintWriter pw;
						pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
								true);
						String request = "reservation_data:"+ roomName + ":" + message + "\r\n";
						pw.println(request);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					alert.showMessageDialog(null, "날짜가 과거입니다.", "alert", JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		// 날짜 선택 단계로 이동하기
		btn2.addActionListener(e -> {
			setVisible(false);
			new Reservation(socket,roomName);
		});

		// 셀렉트이벤트 발생
		lstKorea.addListSelectionListener(this);

		jsp = new JScrollPane(lstKorea);
		cp.add("Center", jsp);

		lblOut = new JLabel("select place");
		lblOut.setHorizontalAlignment(JLabel.CENTER);
		lblOut.setBackground(Color.CYAN);
		cp.add("South", lblOut);

	}

	public static void main(String[] args) {
		// new Reservation2();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 콤보박스 생성후 액션이벤트가 발생하였으므로 Object변수 ob를 생성하여 그 값이 무엇인지 판단

		Object ob = e.getSource();
		if (ob == cb) {
			int idx = cb.getSelectedIndex();

			switch (idx) {
			case 0:
				// SEOUL
				// lblOut.setForeground(Color.red);
				lstKorea.removeAll();
				for (int i = 0; i < Seoul.length; i++) {
					lstKorea.setListData(Seoul);
				}

				break;
			case 1:
				// Busan
				// lblOut.setForeground(Color.green);
				lstKorea.removeAll();
				for (int i = 0; i < Busan.length; i++) {
					lstKorea.setListData(Busan);
				}
				break;
			case 2:
				// Daegu
				// lblOut.setForeground(Color.darkGray);
				lstKorea.removeAll();
				for (int i = 0; i < Daegu.length; i++) {
					lstKorea.setListData(Daegu);
				}
				break;
			case 3:
				// Incheon
				// lblOut.setForeground(Color.pink);
				for (int i = 0; i < Incheon.length; i++) {
					lstKorea.setListData(Incheon);
				}
				break;
			case 4:
				// Gwangju
				// lblOut.setForeground(Color.blue);
				for (int i = 0; i < Gwangju.length; i++) {
					lstKorea.setListData(Gwangju);
				}
				break;
			case 5:
				// Daejeon
				// lblOut.setForeground(Color.blue);
				for (int i = 0; i < Daejeon.length; i++) {
					lstKorea.setListData(Daejeon);
				}
				break;
			case 6:
				// Ulsan
				// lblOut.setForeground(Color.blue);
				for (int i = 0; i < Ulsan.length; i++) {
					lstKorea.setListData(Ulsan);
				}
				break;
			}
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// 선택한 리스트의 항목을 선택할 경우 이벤트 발생
		String item = (String) lstKorea.getSelectedValue(); // Object로 받아야 되므로 캐스팅을 해준다.
		lblOut.setText(item);
	}

}

