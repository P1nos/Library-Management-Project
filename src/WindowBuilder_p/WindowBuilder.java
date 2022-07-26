package WindowBuilder_p;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import BookData_p.BookData;
import BookDataDB_p.BookDataDB;
import borrowing_management.Borrowing_;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WindowBuilder {
	
	
	
	public JFrame frame;
	private JTable table;
	private JTextField Numtext;
	private JTextField Nametext;
	private JTextField Authortext;
	private JTextField publish1text;
	private JTextField publish2text;
	
	String colNames[] = {"도서 번호","도서명","저자","출판","출간","대여 현황"}; 
	private DefaultTableModel model = new DefaultTableModel(colNames, 0);

	public WindowBuilder() {
		initialize();
		select();
		KeyF5();
	}
	
	
	BookDataDB db = new BookDataDB();
	
	public void select() { // 도서 목록 출력
			Vector<BookData> Ar = new Vector<BookData>();
			Ar = db.Booklist();
		
			for(int i=0; i< Ar.size();i++)
			{
			model.addRow(new Object[]{Ar.get(i).GetNum(),Ar.get(i).Getname(),Ar.get(i).GetAuthor(),Ar.get(i).Getpublish1(),Ar.get(i).Getpublish2(),Ar.get(i).GetBorrowing()});
			}	
			
	}
	
	public void KeyF5(){ // F5 클릭 시 새로고침
		frame.getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				System.out.println(keyCode);
				System.out.println(KeyEvent.VK_F5);
				if(keyCode==KeyEvent.VK_F5) 
				{
					model.setRowCount(0);
					select();
					
				}
			}
		});
		frame.getContentPane().setFocusable(true);
		frame.getContentPane().requestFocus();
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1079, 645);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1063, 606);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("함초롱바탕", Font.PLAIN, 18));
		scrollPane.setBounds(306, 106, 745, 466);
		panel.add(scrollPane);
		
		table = new JTable(model){
			public boolean isCellEditable(int row, int column) { // 클릭 비활성화
		        return false;
			}
		};
		table.addMouseListener(new MouseAdapter() { // 테이블 마우스 더블 클릭 시 도서정보 입력칸에 값이 나타남
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model2 = (DefaultTableModel)table.getModel();
				int row = table.getSelectedRow();
				
					Numtext.setText(String.valueOf(model2.getValueAt(row, 0)));
					Nametext.setText(String.valueOf(model2.getValueAt(row, 1))); 
					Authortext.setText(String.valueOf(model2.getValueAt(row, 2))); 
					publish1text.setText(String.valueOf(model2.getValueAt(row, 3))); 
					publish2text.setText(String.valueOf(model2.getValueAt(row, 4)));
			}
		});
		table.setBackground(Color.WHITE);
		table.setFont(new Font("함초롱바탕", Font.PLAIN, 16));
		
		//테이블 가운데 정렬
		DefaultTableCellRenderer cell = new DefaultTableCellRenderer();
		cell.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel centerModel = table.getColumnModel();
		for(int i=0;i < centerModel.getColumnCount(); i++) centerModel.getColumn(i).setCellRenderer(cell);
		
		//테이블 컬럼의 이동을 방지
				table.getTableHeader().setReorderingAllowed(false);      
				table.getColumnModel().getColumn(0).setPreferredWidth(20);
				table.getColumnModel().getColumn(0).setResizable(false);
				table.getColumnModel().getColumn(1).setPreferredWidth(162);
				table.getColumnModel().getColumn(3).setPreferredWidth(40);
				
		scrollPane.setViewportView(table);
		
		JButton InputGetbookButton = new JButton("도서 등록");
		InputGetbookButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		InputGetbookButton.addActionListener(new ActionListener() { // 도서 등록 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent arg0) {
				
				int confirmbook = db.ConfirmBook(new BookData(Integer.parseInt(Numtext.getText()),""));
				if(confirmbook == 1) {
					JOptionPane.showMessageDialog(null, "도서 번호가 중복 되었습니다.");
					return;
				}
				
				if(Numtext.getText().length() == 0 || Nametext.getText().length() == 0 || 
						Authortext.getText().length() == 0 || publish1text.getText().length() == 0 || publish2text.getText().length() == 0) 
				{
					JOptionPane.showMessageDialog(null, "입력이 제대로 되어 있지 않습니다.");
					return;
				}
				
				
				db.InsertBook(new BookData(Integer.parseInt(Numtext.getText()), Nametext.getText(), Authortext.getText(),
						publish1text.getText(), publish2text.getText(), "대여 가능"));
	
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null, "등록 완료");
				Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
				select();
			}
		});
		InputGetbookButton.setBounds(377, 11, 107, 33);
		panel.add(InputGetbookButton);
		
		JButton BooklistButton = new JButton("새로고침"); 
		BooklistButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		BooklistButton.addActionListener(new ActionListener() { // 새로고침 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);
				select();
			}
		});
		BooklistButton.setBounds(136, 10, 107, 35);
		panel.add(BooklistButton);
		
		JButton InputUpdatebutton = new JButton("도서 수정");
		InputUpdatebutton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		InputUpdatebutton.addActionListener(new ActionListener() { // 도서 수정 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent e) {
				int confirmborrowing = db.ConfirmBorrowing((new BookData(Integer.parseInt(Numtext.getText()),"대여 불가"))); //대여 가능 도서 확인
				
				if(Numtext.getText().length() ==  0|| Nametext.getText().length() ==  0 || 
						Authortext.getText().length() == 0 || publish1text.getText().length() == 0 || publish2text.getText().length() == 0) // 도서정보 미 입력 확인
				{
					JOptionPane.showMessageDialog(null, "입력이 제대로 되어 있지 않습니다.");
					return;
					
				}
				
				System.out.println("nUM: "+Numtext.getText().length());
				
				if(confirmborrowing == 1) // ConfirmBorrowing 메소드 반환 값이 1이면 즉, 대여된 도서이면 대여 불가로 도서 정보 업데이트
				{	
					
					
					
					db.UpdateBook(new BookData(Integer.parseInt(Numtext.getText()), Nametext.getText(), Authortext.getText(), 
							publish1text.getText(), publish2text.getText(), "대여 불가"));
					
					Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
					model.setRowCount(0);
					JOptionPane.showMessageDialog(null, "수정 완료");
					select();
					return;
				} else { db.UpdateBook(new BookData(Integer.parseInt(Numtext.getText()), Nametext.getText(), Authortext.getText(), 
						publish1text.getText(), publish2text.getText(), "대여 가능"));
				
				Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null, "수정 완료");
				select(); }
				
				
				
			}
		});
		InputUpdatebutton.setBounds(496, 10, 107, 34);
		panel.add(InputUpdatebutton);
		
		JButton TableDeleteButton = new JButton("도서 삭제");
		TableDeleteButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		TableDeleteButton.addActionListener(new ActionListener() { // 도서 삭제 버튼 클릭 시
			public void actionPerformed(ActionEvent e) {
				
				int result = JOptionPane.showConfirmDialog(null, "장말 삭제 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
				int confirmborrowing = db.ConfirmBorrowing(new BookData(Integer.parseInt(Numtext.getText()),"대여 불가"));
				
				
				if(confirmborrowing == 1) // 대여 중인 도서 이면
				{
					JOptionPane.showMessageDialog(null, "대여 중이므로 삭제 불가능 합니다.");
					return;
				}else
				
				if(result == JOptionPane.CANCEL_OPTION) { // 팝업 취소하면
					return;
				}
				else if(result == JOptionPane.YES_NO_OPTION){
					
					System.out.println(e.getActionCommand());
					DefaultTableModel model2 = (DefaultTableModel)table.getModel();
					
					db.Delete((int)model2.getValueAt(table.getSelectedRow(), 0));
					model2.removeRow(table.getSelectedRow());
					model.setRowCount(0);
					Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
					JOptionPane.showMessageDialog(null, "삭제 완료");
					select();
				}
				else {
					JOptionPane.showMessageDialog(null, "삭제 취소");
				}
				
				
			}
		});
		
		TableDeleteButton.setBounds(615, 10, 107, 35);
		panel.add(TableDeleteButton);
		
		JButton exitButton = new JButton("종료");
		exitButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		exitButton.addActionListener(new ActionListener() { // 종료 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "정말 종료 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.CANCEL_OPTION) {
					return;
				}
				else if(result == JOptionPane.YES_NO_OPTION){
					System.exit(0);
				}
				else {
					return;
				}

			}
		});
		exitButton.setBounds(954, 10, 97, 35);
		panel.add(exitButton);
		
		JButton textFieldResetButton = new JButton("입력 초기화");
		textFieldResetButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		textFieldResetButton.addActionListener(new ActionListener() { // 입력 초기화 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent e) {
				Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
			}
		});
		textFieldResetButton.setBounds(17, 10, 107, 35);
		panel.add(textFieldResetButton);
		
		JLabel Numlabel = new JLabel("도서 번호");
		Numlabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Numlabel.setBounds(12, 160, 93, 23);
		panel.add(Numlabel);
		
		JLabel Namelabel = new JLabel("도서명");
		Namelabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Namelabel.setBounds(33, 209, 60, 23);
		panel.add(Namelabel);
		
		Numtext = new JTextField();
		
		Numtext.setToolTipText("도서 번호 입력");
		Numtext.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		Numtext.setColumns(10);
		Numtext.setBounds(105, 157, 186, 30);
		panel.add(Numtext);
		
		Nametext = new JTextField();
		Nametext.setToolTipText("도서명 입력");
		Nametext.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		Nametext.setColumns(10);
		Nametext.setBounds(105, 210, 186, 30);
		panel.add(Nametext);
		
		Authortext = new JTextField();
		Authortext.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		Authortext.setToolTipText("저자 입력");
		Authortext.setColumns(10);
		Authortext.setBounds(105, 260, 186, 30);
		panel.add(Authortext);
		
		JLabel Authorlabel = new JLabel("저자");
		Authorlabel.setHorizontalAlignment(SwingConstants.CENTER);
		Authorlabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Authorlabel.setBounds(33, 259, 60, 23);
		panel.add(Authorlabel);
		
		JLabel Publish1label = new JLabel("출판");
		Publish1label.setHorizontalAlignment(SwingConstants.CENTER);
		Publish1label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Publish1label.setBounds(33, 308, 60, 23);
		panel.add(Publish1label);
		
		publish1text = new JTextField();
		publish1text.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		publish1text.setToolTipText("출판 입력");
		publish1text.setColumns(10);
		publish1text.setBounds(105, 309, 186, 30);
		panel.add(publish1text);
		
		publish2text = new JTextField();
		publish2text.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		publish2text.setToolTipText("출간 입력");
		publish2text.setColumns(10);
		publish2text.setBounds(105, 360, 186, 30);
		panel.add(publish2text);
		
		JLabel Publish2label = new JLabel("출간");
		Publish2label.setHorizontalAlignment(SwingConstants.CENTER);
		Publish2label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Publish2label.setBounds(33, 359, 60, 23);
		panel.add(Publish2label);
		
		JButton button = new JButton("대여프로그램");
		button.addActionListener(new ActionListener() { // 대여프로그램 버튼 클릭 시 동작
			public void actionPerformed(ActionEvent e) {
				
				Borrowing_ window = new Borrowing_();  // 대여프로그램 GUI 실행
				window.frame.setVisible(true); // 대여프로그램 GUI 나타남
				window.frame.setResizable(false); // GUI 창 크기 조절 불가능
				
			}
		});
		button.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		button.setBounds(377, 54, 107, 33);
		panel.add(button);

	}
}