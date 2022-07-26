package Login_WindowBuilder;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import LoginDB_p.LoginDB;
import LoginData_p.LoginData;
import WindowBuilder_p.WindowBuilder;
import java.awt.Color;

public class WindowBuilder_Login {

	private JFrame frame;
	private JTextField IDtext;
	private JPasswordField passwordtext;
	
	LoginDB db = new LoginDB();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { 
			
			public void run() {
				try {
					WindowBuilder_Login window = new WindowBuilder_Login();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WindowBuilder_Login() 
	{
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 401, 346);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 385, 307);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel IDLabel = new JLabel("ID : ");
		IDLabel.setBounds(71, 122, 66, 33);
		IDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IDLabel.setFont(new Font("���� ���", Font.BOLD, 18));
		panel.add(IDLabel);
		
		JLabel PassWordLabel = new JLabel("PW : ");
		PassWordLabel.setBounds(71, 173, 66, 29);
		PassWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PassWordLabel.setFont(new Font("���� ���", Font.BOLD, 18));
		panel.add(PassWordLabel);
		
		IDtext = new JTextField();
		IDtext.setBounds(149, 125, 145, 33);
		IDtext.setToolTipText("ID �Է�");
		panel.add(IDtext);
		IDtext.setColumns(10);
		
		passwordtext = new JPasswordField();
		passwordtext.setBounds(149, 172, 145, 33);
		passwordtext.setToolTipText("PassWord �Է�");
		panel.add(passwordtext);
		
		JLabel lblNewLabel = new JLabel("�α��� â");
		lblNewLabel.setBounds(12, 10, 361, 81);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("����ü", Font.BOLD, 30));
		panel.add(lblNewLabel);
		
		JButton Loginbutton = new JButton("�α���");
		Loginbutton.setBackground(Color.WHITE);
		Loginbutton.setBounds(71, 243, 111, 47);
		Loginbutton.addActionListener(new ActionListener() { // �α��� ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent arg0) {
				String ID = IDtext.getText();
				char[] PS1 = passwordtext.getPassword();
				
				String PS = String.valueOf(PS1);

				int s = db.LoginTry(new LoginData(ID,PS));
				System.out.println(s);
				
				
				if(s == 1) {
				
				JOptionPane.showMessageDialog(null, "�α��� ����");
				frame.dispose(); // �α��� GUI â ����
				
				WindowBuilder window = new WindowBuilder(); // �������� GUI ����
				window.frame.setVisible(true); // �������� GUI ȭ�� ��Ÿ��
				window.frame.setResizable(false); // GUI â ũ�� ���� �Ұ���
				} else JOptionPane.showMessageDialog(null, "�α��� ����");
				
				
			}
		});
		Loginbutton.setFont(new Font("���� ���", Font.PLAIN, 15));
		panel.add(Loginbutton);
		
		JButton button = new JButton("ȸ�� ����");
		button.setBackground(Color.WHITE);
		button.setBounds(211, 243, 111, 47);
		button.addActionListener(new ActionListener() { // ȸ�� ���� ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				
				String ID = IDtext.getText();
				char[] PS1 = passwordtext.getPassword();
				String PS = String.valueOf(PS1);
				
				System.out.println();
				System.out.println(passwordtext.getPassword());
				
				if(ID.length() != 0 && PS.length() != 0){
				db.InsertLogin(new LoginData(ID, PS));
				IDtext.setText(""); passwordtext.setText("");
				JOptionPane.showMessageDialog(null, "��� �Ϸ�");
				}else JOptionPane.showMessageDialog(null, "ID , PW �Է� �ٶ�.");
				
			}
		});
		button.setFont(new Font("���� ���", Font.PLAIN, 15));
		panel.add(button);
	}
}