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
		IDLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(IDLabel);
		
		JLabel PassWordLabel = new JLabel("PW : ");
		PassWordLabel.setBounds(71, 173, 66, 29);
		PassWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PassWordLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		panel.add(PassWordLabel);
		
		IDtext = new JTextField();
		IDtext.setBounds(149, 125, 145, 33);
		IDtext.setToolTipText("ID 입력");
		panel.add(IDtext);
		IDtext.setColumns(10);
		
		passwordtext = new JPasswordField();
		passwordtext.setBounds(149, 172, 145, 33);
		passwordtext.setToolTipText("PassWord 입력");
		panel.add(passwordtext);
		
		JLabel lblNewLabel = new JLabel("로그인 창");
		lblNewLabel.setBounds(12, 10, 361, 81);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("돋움체", Font.BOLD, 30));
		panel.add(lblNewLabel);
		
		JButton Loginbutton = new JButton("로그인");
		Loginbutton.setBackground(Color.WHITE);
		Loginbutton.setBounds(71, 243, 111, 47);
		Loginbutton.addActionListener(new ActionListener() { // 로그인 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent arg0) {
				String ID = IDtext.getText();
				char[] PS1 = passwordtext.getPassword();
				
				String PS = String.valueOf(PS1);

				int s = db.LoginTry(new LoginData(ID,PS));
				System.out.println(s);
				
				
				if(s == 1) {
				
				JOptionPane.showMessageDialog(null, "로그인 성공");
				frame.dispose(); // 로그인 GUI 창 종료
				
				WindowBuilder window = new WindowBuilder(); // 도서관리 GUI 실행
				window.frame.setVisible(true); // 도서관리 GUI 화면 나타남
				window.frame.setResizable(false); // GUI 창 크기 조절 불가능
				} else JOptionPane.showMessageDialog(null, "로그인 실패");
				
				
			}
		});
		Loginbutton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		panel.add(Loginbutton);
		
		JButton button = new JButton("회원 가입");
		button.setBackground(Color.WHITE);
		button.setBounds(211, 243, 111, 47);
		button.addActionListener(new ActionListener() { // 회원 가입 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent e) {
				
				String ID = IDtext.getText();
				char[] PS1 = passwordtext.getPassword();
				String PS = String.valueOf(PS1);
				
				System.out.println();
				System.out.println(passwordtext.getPassword());
				
				if(ID.length() != 0 && PS.length() != 0){
				db.InsertLogin(new LoginData(ID, PS));
				IDtext.setText(""); passwordtext.setText("");
				JOptionPane.showMessageDialog(null, "등록 완료");
				}else JOptionPane.showMessageDialog(null, "ID , PW 입력 바람.");
				
			}
		});
		button.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		panel.add(button);
	}
}