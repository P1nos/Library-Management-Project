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
	
	String colNames[] = {"���� ��ȣ","������","����","����","�Ⱓ","�뿩 ��Ȳ"}; 
	private DefaultTableModel model = new DefaultTableModel(colNames, 0);

	public WindowBuilder() {
		initialize();
		select();
		KeyF5();
	}
	
	
	BookDataDB db = new BookDataDB();
	
	public void select() { // ���� ��� ���
			Vector<BookData> Ar = new Vector<BookData>();
			Ar = db.Booklist();
		
			for(int i=0; i< Ar.size();i++)
			{
			model.addRow(new Object[]{Ar.get(i).GetNum(),Ar.get(i).Getname(),Ar.get(i).GetAuthor(),Ar.get(i).Getpublish1(),Ar.get(i).Getpublish2(),Ar.get(i).GetBorrowing()});
			}	
			
	}
	
	public void KeyF5(){ // F5 Ŭ�� �� ���ΰ�ħ
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
		scrollPane.setFont(new Font("���ʷչ���", Font.PLAIN, 18));
		scrollPane.setBounds(306, 106, 745, 466);
		panel.add(scrollPane);
		
		table = new JTable(model){
			public boolean isCellEditable(int row, int column) { // Ŭ�� ��Ȱ��ȭ
		        return false;
			}
		};
		table.addMouseListener(new MouseAdapter() { // ���̺� ���콺 ���� Ŭ�� �� �������� �Է�ĭ�� ���� ��Ÿ��
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
		
		JButton InputGetbookButton = new JButton("���� ���");
		InputGetbookButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		InputGetbookButton.addActionListener(new ActionListener() { // ���� ��� ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent arg0) {
				
				int confirmbook = db.ConfirmBook(new BookData(Integer.parseInt(Numtext.getText()),""));
				if(confirmbook == 1) {
					JOptionPane.showMessageDialog(null, "���� ��ȣ�� �ߺ� �Ǿ����ϴ�.");
					return;
				}
				
				if(Numtext.getText().length() == 0 || Nametext.getText().length() == 0 || 
						Authortext.getText().length() == 0 || publish1text.getText().length() == 0 || publish2text.getText().length() == 0) 
				{
					JOptionPane.showMessageDialog(null, "�Է��� ����� �Ǿ� ���� �ʽ��ϴ�.");
					return;
				}
				
				
				db.InsertBook(new BookData(Integer.parseInt(Numtext.getText()), Nametext.getText(), Authortext.getText(),
						publish1text.getText(), publish2text.getText(), "�뿩 ����"));
	
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null, "��� �Ϸ�");
				Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
				select();
			}
		});
		InputGetbookButton.setBounds(377, 11, 107, 33);
		panel.add(InputGetbookButton);
		
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
		
		JButton InputUpdatebutton = new JButton("���� ����");
		InputUpdatebutton.setFont(new Font("���� ���", Font.PLAIN, 12));
		InputUpdatebutton.addActionListener(new ActionListener() { // ���� ���� ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				int confirmborrowing = db.ConfirmBorrowing((new BookData(Integer.parseInt(Numtext.getText()),"�뿩 �Ұ�"))); //�뿩 ���� ���� Ȯ��
				
				if(Numtext.getText().length() ==  0|| Nametext.getText().length() ==  0 || 
						Authortext.getText().length() == 0 || publish1text.getText().length() == 0 || publish2text.getText().length() == 0) // �������� �� �Է� Ȯ��
				{
					JOptionPane.showMessageDialog(null, "�Է��� ����� �Ǿ� ���� �ʽ��ϴ�.");
					return;
					
				}
				
				System.out.println("nUM: "+Numtext.getText().length());
				
				if(confirmborrowing == 1) // ConfirmBorrowing �޼ҵ� ��ȯ ���� 1�̸� ��, �뿩�� �����̸� �뿩 �Ұ��� ���� ���� ������Ʈ
				{	
					
					
					
					db.UpdateBook(new BookData(Integer.parseInt(Numtext.getText()), Nametext.getText(), Authortext.getText(), 
							publish1text.getText(), publish2text.getText(), "�뿩 �Ұ�"));
					
					Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
					model.setRowCount(0);
					JOptionPane.showMessageDialog(null, "���� �Ϸ�");
					select();
					return;
				} else { db.UpdateBook(new BookData(Integer.parseInt(Numtext.getText()), Nametext.getText(), Authortext.getText(), 
						publish1text.getText(), publish2text.getText(), "�뿩 ����"));
				
				Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
				model.setRowCount(0);
				JOptionPane.showMessageDialog(null, "���� �Ϸ�");
				select(); }
				
				
				
			}
		});
		InputUpdatebutton.setBounds(496, 10, 107, 34);
		panel.add(InputUpdatebutton);
		
		JButton TableDeleteButton = new JButton("���� ����");
		TableDeleteButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		TableDeleteButton.addActionListener(new ActionListener() { // ���� ���� ��ư Ŭ�� ��
			public void actionPerformed(ActionEvent e) {
				
				int result = JOptionPane.showConfirmDialog(null, "�帻 ���� �Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION);
				int confirmborrowing = db.ConfirmBorrowing(new BookData(Integer.parseInt(Numtext.getText()),"�뿩 �Ұ�"));
				
				
				if(confirmborrowing == 1) // �뿩 ���� ���� �̸�
				{
					JOptionPane.showMessageDialog(null, "�뿩 ���̹Ƿ� ���� �Ұ��� �մϴ�.");
					return;
				}else
				
				if(result == JOptionPane.CANCEL_OPTION) { // �˾� ����ϸ�
					return;
				}
				else if(result == JOptionPane.YES_NO_OPTION){
					
					System.out.println(e.getActionCommand());
					DefaultTableModel model2 = (DefaultTableModel)table.getModel();
					
					db.Delete((int)model2.getValueAt(table.getSelectedRow(), 0));
					model2.removeRow(table.getSelectedRow());
					model.setRowCount(0);
					Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
					JOptionPane.showMessageDialog(null, "���� �Ϸ�");
					select();
				}
				else {
					JOptionPane.showMessageDialog(null, "���� ���");
				}
				
				
			}
		});
		
		TableDeleteButton.setBounds(615, 10, 107, 35);
		panel.add(TableDeleteButton);
		
		JButton exitButton = new JButton("����");
		exitButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		exitButton.addActionListener(new ActionListener() { // ���� ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "���� ���� �Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.YES_NO_OPTION);
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
		
		JButton textFieldResetButton = new JButton("�Է� �ʱ�ȭ");
		textFieldResetButton.setFont(new Font("���� ���", Font.PLAIN, 12));
		textFieldResetButton.addActionListener(new ActionListener() { // �Է� �ʱ�ȭ ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				Numtext.setText(""); Nametext.setText(""); Authortext.setText(""); publish1text.setText(""); publish2text.setText("");
			}
		});
		textFieldResetButton.setBounds(17, 10, 107, 35);
		panel.add(textFieldResetButton);
		
		JLabel Numlabel = new JLabel("���� ��ȣ");
		Numlabel.setFont(new Font("���� ���", Font.BOLD, 20));
		Numlabel.setBounds(12, 160, 93, 23);
		panel.add(Numlabel);
		
		JLabel Namelabel = new JLabel("������");
		Namelabel.setFont(new Font("���� ���", Font.BOLD, 20));
		Namelabel.setBounds(33, 209, 60, 23);
		panel.add(Namelabel);
		
		Numtext = new JTextField();
		
		Numtext.setToolTipText("���� ��ȣ �Է�");
		Numtext.setFont(new Font("���� ���", Font.PLAIN, 17));
		Numtext.setColumns(10);
		Numtext.setBounds(105, 157, 186, 30);
		panel.add(Numtext);
		
		Nametext = new JTextField();
		Nametext.setToolTipText("������ �Է�");
		Nametext.setFont(new Font("���� ���", Font.PLAIN, 17));
		Nametext.setColumns(10);
		Nametext.setBounds(105, 210, 186, 30);
		panel.add(Nametext);
		
		Authortext = new JTextField();
		Authortext.setFont(new Font("���� ���", Font.PLAIN, 17));
		Authortext.setToolTipText("���� �Է�");
		Authortext.setColumns(10);
		Authortext.setBounds(105, 260, 186, 30);
		panel.add(Authortext);
		
		JLabel Authorlabel = new JLabel("����");
		Authorlabel.setHorizontalAlignment(SwingConstants.CENTER);
		Authorlabel.setFont(new Font("���� ���", Font.BOLD, 20));
		Authorlabel.setBounds(33, 259, 60, 23);
		panel.add(Authorlabel);
		
		JLabel Publish1label = new JLabel("����");
		Publish1label.setHorizontalAlignment(SwingConstants.CENTER);
		Publish1label.setFont(new Font("���� ���", Font.BOLD, 20));
		Publish1label.setBounds(33, 308, 60, 23);
		panel.add(Publish1label);
		
		publish1text = new JTextField();
		publish1text.setFont(new Font("���� ���", Font.PLAIN, 17));
		publish1text.setToolTipText("���� �Է�");
		publish1text.setColumns(10);
		publish1text.setBounds(105, 309, 186, 30);
		panel.add(publish1text);
		
		publish2text = new JTextField();
		publish2text.setFont(new Font("���� ���", Font.PLAIN, 17));
		publish2text.setToolTipText("�Ⱓ �Է�");
		publish2text.setColumns(10);
		publish2text.setBounds(105, 360, 186, 30);
		panel.add(publish2text);
		
		JLabel Publish2label = new JLabel("�Ⱓ");
		Publish2label.setHorizontalAlignment(SwingConstants.CENTER);
		Publish2label.setFont(new Font("���� ���", Font.BOLD, 20));
		Publish2label.setBounds(33, 359, 60, 23);
		panel.add(Publish2label);
		
		JButton button = new JButton("�뿩���α׷�");
		button.addActionListener(new ActionListener() { // �뿩���α׷� ��ư Ŭ�� �� ����
			public void actionPerformed(ActionEvent e) {
				
				Borrowing_ window = new Borrowing_();  // �뿩���α׷� GUI ����
				window.frame.setVisible(true); // �뿩���α׷� GUI ��Ÿ��
				window.frame.setResizable(false); // GUI â ũ�� ���� �Ұ���
				
			}
		});
		button.setFont(new Font("���� ���", Font.PLAIN, 12));
		button.setBounds(377, 54, 107, 33);
		panel.add(button);

	}
}