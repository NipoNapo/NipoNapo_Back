package niponapo.server.dao;

import java.util.List;

import niponapo.server.vo.MemberVO;

public interface MemberDao {
	
	//회원가입
	public void insertMember(MemberVO memberVo) throws Exception;
		
	//회원정보수정
	public void updateMember(MemberVO memberVo) throws Exception;
	
	//로그인
	public MemberVO login(String id,String pw) throws Exception;
		
	//아이디 중복 체크
	public int idChk(String id) throws Exception;
		
	//이메일 중복체크
	public int emailChk(String email) throws Exception;
		
	//아이디 찾기
	public String find_id(String name,String email) throws Exception;
		
	//비밀번호 찾기
	public void find_PW(String id,String name,String email,String new_pw) throws Exception;
		
	//user_key 얻기
	public int getKey(int user_pid,String user_key) throws Exception;
		
	//user_key 변경
	public void alter_userKey(int user_pid) throws Exception;


}
