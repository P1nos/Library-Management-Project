package BookData_p;


public class BookData{
	private String name,author, publish1, publish2; // name 책이름, author 저자, num 도서번호, publish1 출판, publish2 출간, Borrowing 대여확인
	private int num;
	private String Borrowing;
	public BookData() {}
	
	public BookData(String name, String Borrowing){
		this.name = name;
		this.Borrowing = Borrowing;
	}
	
	public BookData(int num, String Borrowing){
		this.num = num;
		this.Borrowing = Borrowing;
	}
	public BookData(int num, String name, String author, String publish1, String publish2, String Borrowing) 
	{
		this.num = num;
		this.name = name;
		this.author = author;
		this.publish1 = publish1;
		this.publish2 = publish2;
		this.Borrowing = Borrowing;
	}
	public int GetNum() 
	{
		return num;
	}
	public void SetNum(int num)
	{
		this.num = num;
	}
	public String Getname()
	{
		return name;
	}
	public void Setname(String name)
	{
		this.name = name;
	}
	public String GetAuthor() 
	{
		return author;
	}
	public void Setauthor(String author)
	{
		this.author = author;
	}
	public String Getpublish1() 
	{
		return publish1;
	}
	public void Setpublish1(String publish1) 
	{
		this.publish1 = publish1;
	}
	public String Getpublish2() 
	{
		return publish2;
	}
	public void Setpublish2(String publish2) 
	{
		this.publish2 = publish2;
	}
	public String GetBorrowing() 
	{
		return Borrowing;
	}
	public void SetBorrowing(String publish2) 
	{
		this.Borrowing = publish2;
	}
}
