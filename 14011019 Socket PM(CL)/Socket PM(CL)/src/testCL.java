import java.net.*;
import java.util.Base64.Encoder;
import java.util.Base64;
import java.io.*;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/*
 -mail send program-
 계정이 존재하는 서버에 로그인을 한후  
esmtp를 이용하여 메일을 보내는 프로그램입니다.
로그인에 실패시 메일을 보낼수 없는 구조이며
로그인시에는 해당계정으로 메일을 지속적으로 보낼수 있습니다.
*/


public class testCL extends JFrame {
	
	public String smtpServer;
	public String sender;
	public String recipient;
	public String subject;
	public String content;
	public String id;
	public  String pass;
	
	public testCL() {

		setTitle("Send Mail Program");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(null);

		JTextField txserver = new JTextField("");
		txserver.setLocation(130, 50);
		txserver.setSize(120, 25);
		c.add(txserver);

		JTextField txsender = new JTextField("");
		txsender.setLocation(130, 80);
		txsender.setSize(120, 25);
		c.add(txsender);

		JTextField txpass = new JTextField("");
		txpass.setLocation(130, 110);
		txpass.setSize(120, 25);
		c.add(txpass);

		JLabel laserver = new JLabel("서버 주소 :");
		laserver.setLocation(35, 55);
		laserver.setSize(100, 15);
		c.add(laserver);

		JLabel lasender = new JLabel("ID: ");
		lasender.setLocation(35, 85);
		lasender.setSize(100, 15);
		c.add(lasender);

		JLabel lapass = new JLabel("password : ");
		lapass.setLocation(35, 115);
		lapass.setSize(100, 15);
		c.add(lapass);
		
		JButton bttlogin = new JButton("Login");
		bttlogin.setLocation(100, 160);
		bttlogin.setSize(80, 30);
		bttlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				smtpServer = txserver.getText();
				
				String Id = txsender.getText();
				byte[] IdBytes = Id.getBytes();
				Encoder encoder = Base64.getEncoder();
				byte[] encodedId = encoder.encode(IdBytes);
				sender = txsender.getText();
				id = new String(encodedId);
				
				String Pass = txpass.getText();
				byte[] IdBytesp = Pass.getBytes();
				Encoder encoderp = Base64.getEncoder();
				byte[] encodedIdp = encoderp.encode(IdBytesp);
				pass = new String(encodedIdp);
				
				System.out.println(id);
				System.out.println(pass);
				
				try {
					if (Login(smtpServer, sender, id, pass) == true) {
						c.remove(lasender);
						c.remove(laserver);
						c.remove(lapass);
						c.remove(txsender);
						c.remove(txserver);
						c.remove(txpass);
						c.remove(bttlogin);

						JTextField txrecipient = new JTextField("");
						txrecipient.setLocation(130, 70);
						txrecipient.setSize(120, 25);
						c.add(txrecipient);
						
						JTextField txsubject = new JTextField();
						txsubject.setLocation(130, 100);
						txsubject.setSize(120, 25);
						c.add(txsubject);
						
						//JTextArea txcontent = new JTextArea();
						JTextArea txcontent = new JTextArea();
						JScrollPane bar = new JScrollPane();
						txcontent.setLocation(130, 130);
						txcontent.setSize(120, 80);
						//c.add(new JScrollPane(txcontent));
						//c.add(txcontent);
						c.add(txcontent);
						
						JButton bttsend = new JButton("전송");
						bttsend.setLocation(105, 215);
						bttsend.setSize(60, 30);
						
						bttsend.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									System.out.println(id);
									System.out.println(pass);
									testCL.sendMail(smtpServer, sender, recipient, subject, content, id, pass);
									System.out.println("==========================");
									System.out.println("메일이 전송되었습니다.");
								} catch (Exception e1) {
									System.out.println("==========================");
									System.out.println("메일이 발송되지 않았습니다.");
									System.out.println(e1.toString());
								}
							}
							
						});
						
						bttsend.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								content = txcontent.getText();
								recipient = txrecipient.getText();
								subject = txsubject.getText();
							}
						});
						c.add(bttsend);
						
						JLabel lasubject = new JLabel("제목 : ");
						lasubject.setLocation(25, 105);
						lasubject.setSize(100, 15);
						c.add(lasubject);
						
						JLabel larecipient = new JLabel("수신 메일 주소 : ");
						larecipient.setLocation(25, 75);
						larecipient.setSize(100, 15);
						c.add(larecipient);
						
						JLabel lacontent = new JLabel("내용 : ");
						lacontent.setLocation(25, 135);
						lacontent.setSize(100, 15);
						c.add(lacontent);
						
						
						c.revalidate();
						c.repaint();
						System.out.println("==========================");
						System.out.println("Login");
						

					} else {
						System.out.print("ID or Password 오류 다시로그인해주세요.");
					}
				} catch (Exception e1) {
					System.out.println("서버가 옳지 않습니다.다시 로그인해주세요.");
				}
				
				

			}
		});
		c.add(bttlogin);
		
		setSize(300, 300);
		setVisible(true);
	}
/*
	private class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println(id);
				System.out.println(pass);
				testCL.sendMail(smtpServer, sender, recipient, subject, content, id, pass);
				System.out.println("==========================");
				System.out.println("메일이 전송되었습니다.");
			} catch (Exception e1) {
				System.out.println("==========================");
				System.out.println("메일이 발송되지 않았습니다.");
				System.out.println(e1.toString());
			}
		}
	}
	*/
	public boolean Login(String smtpServer, String sender, String id, String pass) throws Exception {

		SocketFactory factory = SSLSocketFactory.getDefault();
		Socket socket = factory.createSocket(smtpServer, 465);

		BufferedReader inFormUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

		System.out.println("서버에 연결되었습니다.");

		String line = inFormUser.readLine();
		String lineid;
		String linepass;

		System.out.println("응답:" + line);

		System.out.println("HELO 명령을 전송합니다.");
		outToServer.writeBytes("HELO 127.0.0.1" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("AUTH LOGIN 명령을 전송합니다.");
		outToServer.writeBytes("AUTH LOGIN" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("id");
		outToServer.writeBytes(id + "\r\n");
		lineid = inFormUser.readLine();
		System.out.println("응답:" + lineid);

		System.out.println("pass");
		outToServer.writeBytes(pass + "\r\n");
		linepass = inFormUser.readLine();
		System.out.println("응답:" + linepass);

		outToServer.writeBytes("quit\r\n");

		inFormUser.close();
		socket.close();
		
		if (lineid.indexOf("334") >= 0 && linepass.indexOf("235") >= 0) {
			JOptionPane.showMessageDialog(null, "Login");
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Login faild");
			return false;
		}
	}
	public static void sendMail(String smtpServer, String sender, String recipient, String subject, String content,
			String id, String pass) throws Exception {

		SocketFactory factory = SSLSocketFactory.getDefault();
		Socket socket = factory.createSocket(smtpServer, 465);
		System.out.println("서버에 연결되었습니다.");

		BufferedReader inFormUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

		System.out.println("서버에 연결되었습니다.");

		String line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("HELO 명령을 전송합니다.");
		outToServer.writeBytes("HELO 127.0.0.1" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("AUTH LOGIN 명령을 전송합니다.");
		outToServer.writeBytes("AUTH LOGIN" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("id");
		outToServer.writeBytes(id + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("pass");
		outToServer.writeBytes(pass + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("MAIL FROM 명령을 전송합니다.");
		outToServer.writeBytes("MAIL FROM:<" + sender + ">" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("RCPT 명령을 전송합니다.");
		outToServer.writeBytes("RCPT TO:<" + recipient + ">" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("DATA 명령을 전송합니다.");
		outToServer.writeBytes("DATA\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("본문을 전송합니다.");
		outToServer.writeBytes("Subject: " + subject + "\n\r\n" + content + "\r\n");
		outToServer.writeBytes(".\r\n");
		line = inFormUser.readLine();
		System.out.println("응답:" + line);

		System.out.println("접속 종료합니다.");
		outToServer.writeBytes("quit\r\n");

		inFormUser.close();
		socket.close();
	}

	public static void main(String args[]) {
		
		new testCL();
	}
}
