package niponapo.server.vo;

public class MemberVO {
	private int user_pid;
	private String id;
	private String pw;
	private String name;
	private String email;
	private int idol_pid;
	private String how;
	
	public int getUser_pid() {
		return user_pid;
	}
	public void setUser_pid(int user_pid) {
		this.user_pid = user_pid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdol_pid() {
		return idol_pid;
	}
	public void setIdol_pid(int idol_pid) {
		this.idol_pid = idol_pid;
	}
	public String getHow() {
		return how;
	}
	public void setHow(String how) {
		this.how = how;
	}
	
	@Override
	public String toString() {
		return "MemberVO [user_pid="+user_pid+", id="+id+", pw="+pw+", name="+name
				+",email="+email+",idol_pid="+idol_pid+", how="+how+"]";
	}
	
	
	

}