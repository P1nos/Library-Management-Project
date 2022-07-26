package borrowing_management;

import java.awt.Color;
import java.awt.EventQueue;
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

import BorrowingData_p.BorrowingData;
import BookData_p.BookData;
import BorrowingDB_p.BorrowingDB;
import LoginDB_p.LoginDB;
import LoginData_p.LoginData;
import Login_WindowBuilder.WindowBuilder_Login;
import BookDataDB_p.BookDataDB;

public class Borrowing_ {
	
	public JFrame frame;
	private JTable table;
	private JTextField IDtext;
	private JTextField Numtext;
	
	String colNames[] = {"ȸ�� ID","���� ��ȣ","������","�뿩 ��¥","�ݳ� ��¥"};
	private DefaultTableModel model = new DefaultTableModel(colNames, 0);
	public Borrowing_() {
		initialize();
		select();
	}
	
	BorrowingDB BorrowingDB = new BorrowingDB(); 
	LoginDB LoginDB = new LoginDB();
	BookDataDB BookDB = new BookDataDB();
	
	public void select() { // �뿩 ��� ���
			Vector<BorrowingData> Ar = new Vector<BorrowingData>();
			Ar = BorrowingDB.Booklist();
		
			for(int i=0; i< Ar.size();i++)
			{
			model.addRow(new Object[]{Ar.get(i).GetMemberID(),Ar.get(i).GetNum(),Ar.get(i).GetBookD(),Ar.get(i).GetRetalDate(),Ar.get(i).GetReturnDate()});
			}	
			
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1091, 491);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1075, 452);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("���ʷչ���", Font.PLAIN, 18));
		scrollPane.setBounds(306, 106, 757, 329);
		panel.add(scrollPane);
		
		table = new JTable(model){
			public boolean isCellEditable(int row, int column) { // Ŭ�� ��Ȱ��ȭ
		        return false;
			}
		};
		table.setFillsViewportHeight(true);
		table.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e) { // ���̺� ���콺 ���� Ŭ�� �� �뿩���� �Է�ĭ�� ���� ��Ÿ��
				DefaultTableModel model2 = (DefaultTableModel)table.getModel();
				int row = table.getSelectedRow();
				
					IDtext.setText(String.valueOf(model2.getValueAt(row, 0)));
					Numtext.setText(String.valueOf(model2.getValueAt(row, 1))); 
			}
		});
		
		table.setBackground(Color.WHITE);
		table.setFont(new Font("���ʷչ���", Font.PLAIN, 16));
		
		//���̺� ��� ����
		DefaultTableCellRenderer cell = new DefaultTableCellRenderer();
		cell.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel centerModel = table.getColumnModel();
		for(int i=0;i < centerModel.getColumnCount(); i++) centerModel.getColumn(i).setCellRenderer(cell);

		//���̺� �÷��� �̵��� ����
		table.getTableHeader().setReorderingAllowed(false);      
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(162);
		table.getColumnModel().getColumn(3).setPreferredWidth(40);

		scrollPane.setViewportView(table);
		
		JButton BorrowingBookButton = new JButton("���� �뿩");
		BorrowingBookButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		BorrowingBookButton.addActionListener(new ActionListener() { // ���� �뿩 ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent arg0) {
				int confirmborrowing = BookDB.ConfirmBorrowing((new BookData(Integer.parseInt(Numtext.getText()),"�뿩 �Ұ�")));  // �뿩�������� Ȯ��
				int confirmbook = BookDB.ConfirmBook(new BookData(Integer.parseInt(Numtext.getText()),"")); // ������ȣ Ȯ��
				int loginox = LoginDB.LoginOX(new LoginData(IDtext.getText(),"")); // ȸ�� ID Ȯ��
				if(confirmborrowing == 1) {
					
					JOptionPane.showMessageDialog(null, "�뿩���� ���� �Դϴ�.");
					return;
				}
				
				if(confirmbook != 1) {
					
					JOptionPane.showMessageDialog(null, "������ȣ �Է��� �߸� �Ǿ����ϴ�.");
					return;
				}
				
				if(loginox != 1) {
					
					JOptionPane.showMessageDialog(null, "ȸ�� ID �� ���� �ʽ��ϴ�.");
					return;
				}
				BorrowingDB.InsertBorrowing((new BorrowingData(IDtext.getText(), Integer.parseInt(Numtext.getText()),"","","")));
				model.setRowCount(0);
				
				JOptionPane.showMessageDialog(null, "��� �Ϸ�");
				IDtext.setText(""); Numtext.setText("");
				
				select();
			}
		});
		BorrowingBookButton.setBounds(473, 11, 107, 33);
		panel.add(BorrowingBookButton);
		
		JButton BooklistButton = new JButton("���ΰ�ħ");
		BooklistButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		BooklistButton.addActionListener(new ActionListener() { // ���ΰ�ħ ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent arg0) { 
				model.setRowCount(0);
				select();
			}
		});
		BooklistButton.setBounds(136, 10, 107, 35);
		panel.add(BooklistButton);
		
		JButton ReturnBookButton = new JButton("���� �ݳ�");
		ReturnBookButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		ReturnBookButton.addActionListener(new ActionListener() {  // ���� �ݳ� Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				
				int result = JOptionPane.showConfirmDialog(null, "�ݳ� �Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION); // �˾�
				
				if(result == JOptionPane.CANCEL_OPTION) { // �˾� ��� ��ư Ŭ��
					return;
				}
				else if(result == JOptionPane.YES_NO_OPTION){
					
					System.out.println(e.getActionCommand());
					DefaultTableModel model2 = (DefaultTableModel)table.getModel();
					
					BorrowingDB.ReturnBook((int)model2.getValueAt(table.getSelectedRow(), 1));
					System.out.println((int)model2.getValueAt(table.getSelectedRow(), 1));
					model2.removeRow(table.getSelectedRow());
					model.setRowCount(0);
					IDtext.setText(""); Numtext.setText("");
					JOptionPane.showMessageDialog(null, "�ݳ� �Ϸ�");
					select();
				}
				else {
					JOptionPane.showMessageDialog(null, "���");
				}
			}
		});
		
		ReturnBookButton.setBounds(602, 10, 107, 35);
		panel.add(ReturnBookButton);
		
		JButton exitButton = new JButton("����");
		exitButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		exitButton.addActionListener(new ActionListener() { //  // ���� ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "���� ���� �Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.CANCEL_OPTION) {
					return;
				}
				else if(result == JOptionPane.YES_NO_OPTION){
					frame.dispose();
				}
				else {
					return;
				}

			}
		});
		exitButton.setBounds(966, 10, 97, 35);
		panel.add(exitButton);
		
		JButton textFieldResetButton = new JButton("�Է� �ʱ�ȭ");
		textFieldResetButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		textFieldResetButton.addActionListener(new ActionListener() {  // �Է� �ʱ�ȭ ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				IDtext.setText(""); Numtext.setText("");
			}
		});
		
		textFieldResetButton.setBounds(17, 10, 107, 35);
		panel.add(textFieldResetButton);
		
		JLabel IDlabe = new JLabel("ȸ�� ID");
		IDlabe.setHorizontalAlignment(SwingConstants.CENTER);
		IDlabe.setFont(new Font("���� ���", Font.BOLD, 20));
		IDlabe.setBounds(12, 202, 93, 23);
		panel.add(IDlabe);
		
		JLabel Namelabel = new JLabel("������ȣ");
		Namelabel.setFont(new Font("���� ���", Font.BOLD, 20));
		Namelabel.setBounds(17, 254, 81, 23);
		panel.add(Namelabel);
		
		IDtext = new JTextField();
		
		IDtext.setToolTipText("���� ��ȣ �Է�");
		IDtext.setFont(new Font("���� ���", Font.PLAIN, 17));
		IDtext.setColumns(10);
		IDtext.setBounds(105, 199, 186, 30);
		panel.add(IDtext);
		
		Numtext = new JTextField();
		Numtext.setToolTipText("������ȣ �Է�");
		Numtext.setFont(new Font("���� ���", Font.PLAIN, 17));
		Numtext.setColumns(10);
		Numtext.setBounds(105, 252, 186, 30);
		panel.add(Numtext);
	}
}