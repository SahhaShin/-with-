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
	//���Ͽ� �������� �����ϱ� -> String array�� ���� ������ �ʿ��� ��
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
	JButton btn2 = new JButton("��"); // ������ư

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

		// �޺��ڽ� ����
		cb = new JComboBox(list);

		// �޺��ڽ� �׼��̺�Ʈ �߻�
		cb.addActionListener(this);

		pTop.add(btn2);
		pTop.add(new JLabel("Area Selection:"));
		pTop.add(cb);
		pTop.add(btn1);
		btn1.setForeground(Color.RED);
		pTop.setBackground(Color.CYAN);
		cp.add("North", pTop);

		lstKorea = new JList(Seoul);

		// ��ɱ��� !!!!!!!!!!!!!!!!!!!!!!!!

		// ��ü ��� �н������ ------------��� �޾Ƽ� �����ؾ���
		btn1.addActionListener(e -> {
			// ���ο� â!

			// ��Ұ� ���õ��� ������ ���޽��� ���
			if (lstKorea.getSelectedIndex() == -1) {
				JOptionPane alert = new JOptionPane();
				alert.showMessageDialog(null, "��Ұ� ���õ��� �ʾҽ��ϴ�.", "alert", JOptionPane.ERROR_MESSAGE);
			} else { // ��Ұ� ���õǾ�����
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
					alert.showMessageDialog(null, "��¥�� �����Դϴ�.", "alert", JOptionPane.ERROR_MESSAGE);

				}

			}
		});

		// ��¥ ���� �ܰ�� �̵��ϱ�
		btn2.addActionListener(e -> {
			setVisible(false);
			new Reservation(socket,roomName);
		});

		// ����Ʈ�̺�Ʈ �߻�
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
		// �޺��ڽ� ������ �׼��̺�Ʈ�� �߻��Ͽ����Ƿ� Object���� ob�� �����Ͽ� �� ���� �������� �Ǵ�

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
		// ������ ����Ʈ�� �׸��� ������ ��� �̺�Ʈ �߻�
		String item = (String) lstKorea.getSelectedValue(); // Object�� �޾ƾ� �ǹǷ� ĳ������ ���ش�.
		lblOut.setText(item);
	}

}

