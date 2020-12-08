package niponapo.server.service;

import java.util.List;

import niponapo.server.vo.MemberVO;
import niponapo.server.dto.successDTO;

public interface MemberService {
	//회원가입
	public successDTO insertMember(MemberVO memberVo) throws Exception;
		
	//회원정보 수정
	public successDTO updateMember(MemberVO memberVo) throws Exception;
//		
	//로그인
	public MemberVO login(String id,String pw) throws Exception;
		
	//아이디 중복 체크
	public successDTO idChk(String id) throws Exception;
			
	//이메일 중복체크
	public successDTO emailChk(String email) throws Exception;
		
	//아이디 찾기
	public String find_id(String name,String email) throws Exception;
			
	//비밀번호 찾기
	public successDTO find_PW(String id,String name,String email) throws Exception;
			
	//user_key 변경
	public successDTO  alter_userKey(int user_pid) throws Exception;   
	
	//랜덤 비밀번호 생성
	public String getRamdomPassword(int len) throws Exception;

}
