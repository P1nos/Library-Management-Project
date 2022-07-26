package LoginDB_p;

import java.sql.*;

import LoginData_p.LoginData;

public class LoginDB {
	private Connection conn = null;
	private ResultSet rs = null;
	private Statement st = null;
	private PreparedStatement ps = null;
	int count = 0;
	
	public LoginDB() { // 생성자로 데이터베이스 연결
		
		final String url = "jdbc:mariadb://localhost:3306/bookdb"; 
		final String id = "root";
		final String pw = "1234";
		try{
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

	public void DBClose(){
		try{
			if(rs != null) rs.close();
			if(st != null) st.close();
			if(ps != null) ps.close();
		}catch(Exception e) { System.out.println(e + "=> DBClose 실패");}
	}
	
	//회원 정보 저장
	public void InsertLogin(LoginData logindata){
		String sql = "insert into login_management values(?, ?, ?)";
		
		try{
			ps = conn.prepareStatement(sql);
			st = conn.createStatement();
			rs = st.executeQuery("Select * From login_management order by position*1");
			while(rs.next())
			{
				int Number1 = rs.getInt("position");
				if(Number1 == count) count++;
				}
			ps.setInt(1, count);
			ps.setString(2, logindata.GetID());
			ps.setString(3, logindata.GetPassword());
			ps.executeUpdate();
			count++;
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}finally 
		{
			DBClose();
		}
	}

	//회원 정보 삭제
	public void Delete(String ID){ // login_management table -> Login ID data delete
		String sql = "Delete from login_management where ID =?";
		
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, ID);
			ps.executeUpdate();
		}catch(SQLException e)
		{	
			e.printStackTrace();
		} finally 
		{
			DBClose();
		}
	}
	
	// ID, Password 확인 즉, 로그인 시도 시 회원 확인
	public int LoginTry(LoginData logindata){  // login_management table -> Login ID, Password Confirm
		String sql = "select * from login_management where ID = ? and Password = ?";
		
		try{
		ps = conn.prepareStatement(sql);
		ps.setString(1, logindata.GetID());
		ps.setString(2, logindata.GetPassword());
		rs = ps.executeQuery();
		
		if(rs.next())
		{
			return 1;		
		}	
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public int LoginOX(LoginData logindata){  // login_management table -> Login ID Confirm
		String sql = "select * from login_management where ID = ?";
		try{
		ps = conn.prepareStatement(sql);
		ps.setString(1, logindata.GetID());
		rs = ps.executeQuery();
		if(rs.next()) 
		{
			return 1;
		}
		
		}catch(Exception e)
		{ 
			e.printStackTrace();
		}
		return -1;
	}

}
