package model;

public class TestUserTable {
	public static void main(String[] args){
		UserTable ut = new UserTable();
		String pw = "111";
		String un = "bb@gmail.com";
		if(ut.validUserAccount(un, pw))
			System.out.println(ut.getLastSelected().getFullName());
		else System.out.println("failed");
	}
}
