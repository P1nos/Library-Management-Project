package BorrowingDB_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import BorrowingData_p.BorrowingData;
import BookData_p.BookData;
import BookDataDB_p.BookDataDB;

public class BorrowingDB{
	private Connection conn = null;
	private ResultSet rs = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	
	SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy년 MM월 dd일 ");
	BookDataDB mb = new BookDataDB();
	
	public BorrowingDB() { // 생성자로 데이터베이스 연결 
		try {
			final String url = "jdbc:mariadb://localhost:3306/bookdb";
			final String id = "root";
			final String pw = "1234";
			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);
			
		}catch(ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패:"+ cnfe.toString());
		}catch(SQLException sqle){
			System.out.println("DB 접속실패"+ sqle.toString());
		}catch(Exception e){
			System.out.println("Unkown error");
			e.printStackTrace();
		}
	}
	
	public void DBClose() { // 커넥션 연결 종료
		try {
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(ps != null) ps.close();
		} catch (Exception e) {
			System.out.println(e + " => DBClose fail");
		}
	}
	
	//도서대여 정보 저장
	public void InsertBorrowing(BorrowingData borrowingdata) { // borrowing_management -> borrowing book data insert
		BookData bookdata = new BookData();
		Calendar cal = Calendar.getInstance();
		Date t1 = new Date();
		String time1 = sdf.format(t1);
		cal.add(Calendar.DAY_OF_MONTH,+14);
		try {
			String sql = "insert into borrowing_management values(?, ?, ?, ?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, borrowingdata.GetMemberID());
			ps.setInt(2, borrowingdata.GetNum());
			ps.setString(3, mb.Bringbookname(new BookData(borrowingdata.GetNum(), "")));
			ps.setString(4, time1);
			ps.setString(5, sdf.format(cal.getTime()));
			mb.BorrowingInsert(new BookData(borrowingdata.GetNum(),"대여 불가"));
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose();
		}
	}
	
	//도서대여 정보 목록
	public Vector<BorrowingData> Booklist()  // borrowing_management -> borrowing book data list
	{
		Vector<BorrowingData> Ar = new Vector<BorrowingData>();
		 try{
			 st = conn.createStatement();
			 String sql = "Select * From borrowing_management order by MemberID*1";
			 rs = st.executeQuery(sql);
			 while (rs.next())
			 {
				 Ar.add(new BorrowingData(rs.getString("MemberID"), rs.getInt("Number") ,rs.getString("Book") ,rs.getString("RentalDate"), rs.getString("ReturnDate")));
			 }
		 }catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBClose();
			}
		 return Ar; 
	}
	
	//도서 반납
	public void ReturnBook(int num){  // borrowing_management -> borrowing book data delete
		String Del = "delete from borrowing_management where Number = ?";
		try{
			ps = conn.prepareStatement(Del);
			ps.setInt(1, num);
			mb.BorrowingInsert(new BookData(num,"","","","","대여 가능"));
			System.out.println(num);
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			DBClose();
		}
	}
}