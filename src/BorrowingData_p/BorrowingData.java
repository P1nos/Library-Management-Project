package BorrowingData_p;

public class BorrowingData { //MemberID ȸ������, Book �����̸�, num ������ȣ, RetalDate �뿩��¥, ReturnDate �ݳ���¥
	private String MemberID;
	private String Book;
	private int num;
	private String RetalDate;
	private String ReturnDate;
	public BorrowingData() {}
	public BorrowingData(String MemberID, int num, String Book, String RetalDate, String ReturnDate){
		this.MemberID = MemberID;
		this.Book = Book;
		this.num = num;
		this.RetalDate = RetalDate;
		this.ReturnDate = ReturnDate;
	}
	
	public int GetNum(){
		return num;
	}
	public void SetNum(int num){
		this.num = num;
	}
	
	public String GetMemberID() {
		return MemberID;
	}
	public void SetMenberID(String MemberID) {
		this.MemberID = MemberID;
	}
	public String GetBookD() {
		return Book;
	}
	public void SetBook(String Book) {
		this.Book = Book;
	}
	public String GetRetalDate() {
		return RetalDate;
	}
	public void SetRetalDate(String RetalDate) {
		this.RetalDate = RetalDate;
	}
	public String GetReturnDate() {
		return ReturnDate;
	}
	public void SetReturnDate(String ReturnDate) {
		this.ReturnDate = ReturnDate;
	}
	

}
