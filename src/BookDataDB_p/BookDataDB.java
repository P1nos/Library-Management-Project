package BookDataDB_p;

import java.util.*;
import java.sql.*;

import BookData_p.BookData;

public class BookDataDB{
	private Connection conn = null;
	private ResultSet rs = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	
	public BookDataDB() { // 생성자로 데이터베이스 연결 
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
	
	
	public void DBClose() { //커넥션 연결 종료
		try {
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(ps != null) ps.close();
		} catch (Exception e) {
			System.out.println(e + " => DBClose fail");
		}
	}
	
	//도서정보 저장
	public void InsertBook(BookData bookdata) { // book_management table -> book data insert
		try {
			String sql = "insert into book_management values(?, ?, ?, ?, ?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bookdata.GetNum());
			ps.setString(2, bookdata.Getname());
			ps.setString(3, bookdata.GetAuthor());
			ps.setString(4, bookdata.Getpublish1());
			ps.setString(5, bookdata.Getpublish2());
			ps.setString(6, bookdata.GetBorrowing());
			ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose();
		}
	}
	
	//도서 정보 목록
	public Vector<BookData> Booklist()  // book_management table -> book data list
	{
		Vector<BookData> Ar = new Vector<BookData>();
		 
		 try{
			 st = conn.createStatement();
			 
			 String sql = "Select * From book_management order by Number*1";
			 rs = st.executeQuery(sql);
			 
			 while (rs.next()) {
				 Ar.add(new BookData(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
			 }
		 }catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBClose();
			}
		 return Ar;
		 
	}

	// 도서정보 업데이트
	public void UpdateBook(BookData bookdata)  // book_management table -> book data update
	{
		try {
			String Updata = "update book_management set Book=? , author =?, publish1=?,publish2=?, B =? where Number = ?;";
			ps = conn.prepareStatement(Updata);
			ps.setString(1, bookdata.Getname());
			ps.setString(2, bookdata.GetAuthor());
			ps.setString(3, bookdata.Getpublish1());
			ps.setString(4, bookdata.Getpublish2());
			ps.setString(5, bookdata.GetBorrowing());
			ps.setInt(6, bookdata.GetNum());
			ps.executeUpdate();
			}catch(SQLException e){	
				e.printStackTrace();
			} finally {
				DBClose();
			}
	}

	// 도서 정보 삭제
	public void Delete(int Num)  // book_management table -> book data delete
	{
		String Delete = "delete from book_management where Number = ?;";
		try {
			ps = conn.prepareStatement(Delete);
			ps.setInt(1, Num);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
				DBClose();
		}	
	}
	
	//도서 대여 정보 저장
	public void BorrowingInsert(BookData bookdata)  // book_management table -> borrowing book data insert
	{
		try {
			String Updata = "update book_management set B =? where Number=?;";
			ps = conn.prepareStatement(Updata);
			ps.setString(1, bookdata.GetBorrowing());
			ps.setInt(2, bookdata.GetNum());
			ps.executeUpdate();
			}catch(SQLException e){	
				e.printStackTrace();
			} finally {
				DBClose();
			}
	}
	
	//도서 대여 확인
	public int ConfirmBorrowing(BookData bookdata){  // book_management table -> borrowing book data confirm
		String sql = "select * from book_management where Number = ? and B = ?";
		try{
			
		ps = conn.prepareStatement(sql);
		ps.setInt(1, bookdata.GetNum());
		ps.setString(2, bookdata.GetBorrowing());
		rs = ps.executeQuery();
		
		if(rs.next()) {
			
			return 1;		
		}else 
		{
			 
		}
		
		}catch(Exception e) { e.printStackTrace();}
		return -1;
	}
	
	// 도서번호 확인
	public int ConfirmBook(BookData bookdata){  // book_management table -> borrowing book data confirm
		String sql = "select * from book_management where Number = ?";
		try{
			
		ps = conn.prepareStatement(sql);
		ps.setInt(1, bookdata.GetNum());
		rs = ps.executeQuery();
		
		if(rs.next()) 
		{
			return 1;
			
		}
		
		}catch(Exception e) { e.printStackTrace();}
		return -1;
	}
	
	// 도서명 확인
	public String Bringbookname(BookData bookdata){ // book_management table -> borrowing bookname data Bring 
		String sql = "select * from book_management where Number = ?";
		try{
			
		ps = conn.prepareStatement(sql);
		ps.setInt(1, bookdata.GetNum());
		rs = ps.executeQuery();
		
		if(rs.next()) 
		{
			
			
			System.out.println(rs.getString(2));
			return rs.getString(2);
		}
		
		
		}catch(Exception e) { e.printStackTrace();}
		return "";
	}
}
	

