package LoginData_p;


public class LoginData {
	private String ID,Password;
	public LoginData() {}
	public LoginData(String ID, String Password){
		this.ID = ID;
		this.Password = Password;
	}
	
	public LoginData(String ID){
		this.ID = ID;
	}
	
	
	public String GetID(){
		return ID;
	}
	public void SetID(String Password){
		this.Password = Password;
	}
	public String GetPassword(){
		return Password;
	}
	public void SetPasswordD(String Password){
		this.Password = Password;
	}
	
}
