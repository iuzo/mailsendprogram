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
 ������ �����ϴ� ������ �α����� ����  
esmtp�� �̿��Ͽ� ������ ������ ���α׷��Դϴ�.
�α��ο� ���н� ������ ������ ���� �����̸�
�α��νÿ��� �ش�������� ������ ���������� ������ �ֽ��ϴ�.
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

		JLabel laserver = new JLabel("���� �ּ� :");
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
						
						JButton bttsend = new JButton("����");
						bttsend.setLocation(105, 215);
						bttsend.setSize(60, 30);
						
						bttsend.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									System.out.println(id);
									System.out.println(pass);
									testCL.sendMail(smtpServer, sender, recipient, subject, content, id, pass);
									System.out.println("==========================");
									System.out.println("������ ���۵Ǿ����ϴ�.");
								} catch (Exception e1) {
									System.out.println("==========================");
									System.out.println("������ �߼۵��� �ʾҽ��ϴ�.");
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
						
						JLabel lasubject = new JLabel("���� : ");
						lasubject.setLocation(25, 105);
						lasubject.setSize(100, 15);
						c.add(lasubject);
						
						JLabel larecipient = new JLabel("���� ���� �ּ� : ");
						larecipient.setLocation(25, 75);
						larecipient.setSize(100, 15);
						c.add(larecipient);
						
						JLabel lacontent = new JLabel("���� : ");
						lacontent.setLocation(25, 135);
						lacontent.setSize(100, 15);
						c.add(lacontent);
						
						
						c.revalidate();
						c.repaint();
						System.out.println("==========================");
						System.out.println("Login");
						

					} else {
						System.out.print("ID or Password ���� �ٽ÷α������ּ���.");
					}
				} catch (Exception e1) {
					System.out.println("������ ���� �ʽ��ϴ�.�ٽ� �α������ּ���.");
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
				System.out.println("������ ���۵Ǿ����ϴ�.");
			} catch (Exception e1) {
				System.out.println("==========================");
				System.out.println("������ �߼۵��� �ʾҽ��ϴ�.");
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

		System.out.println("������ ����Ǿ����ϴ�.");

		String line = inFormUser.readLine();
		String lineid;
		String linepass;

		System.out.println("����:" + line);

		System.out.println("HELO ����� �����մϴ�.");
		outToServer.writeBytes("HELO 127.0.0.1" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("AUTH LOGIN ����� �����մϴ�.");
		outToServer.writeBytes("AUTH LOGIN" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("id");
		outToServer.writeBytes(id + "\r\n");
		lineid = inFormUser.readLine();
		System.out.println("����:" + lineid);

		System.out.println("pass");
		outToServer.writeBytes(pass + "\r\n");
		linepass = inFormUser.readLine();
		System.out.println("����:" + linepass);

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
		System.out.println("������ ����Ǿ����ϴ�.");

		BufferedReader inFormUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());

		System.out.println("������ ����Ǿ����ϴ�.");

		String line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("HELO ����� �����մϴ�.");
		outToServer.writeBytes("HELO 127.0.0.1" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("AUTH LOGIN ����� �����մϴ�.");
		outToServer.writeBytes("AUTH LOGIN" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("id");
		outToServer.writeBytes(id + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("pass");
		outToServer.writeBytes(pass + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("MAIL FROM ����� �����մϴ�.");
		outToServer.writeBytes("MAIL FROM:<" + sender + ">" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("RCPT ����� �����մϴ�.");
		outToServer.writeBytes("RCPT TO:<" + recipient + ">" + "\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("DATA ����� �����մϴ�.");
		outToServer.writeBytes("DATA\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("������ �����մϴ�.");
		outToServer.writeBytes("Subject: " + subject + "\n\r\n" + content + "\r\n");
		outToServer.writeBytes(".\r\n");
		line = inFormUser.readLine();
		System.out.println("����:" + line);

		System.out.println("���� �����մϴ�.");
		outToServer.writeBytes("quit\r\n");

		inFormUser.close();
		socket.close();
	}

	public static void main(String args[]) {
		
		new testCL();
	}
}
