package niponapo.server.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import niponapo.server.vo.MemberVO;


@Repository("MemberDao")
public class MemberDaoImp implements MemberDao {
	
	@Autowired
	private SqlSession sql;
	
	private static String namespace="niponapo.server.mappers.MemberMapper"
			+ "";
	
	
	//회원가입
	@Override
	public void insertMember(MemberVO memberVo) throws Exception {
		sql.insert(namespace+".signup",memberVo);
		
	}
	//회원 정보 수정
	@Override
	public void updateMember(MemberVO memberVo) throws Exception {
		
		sql.update(namespace+".update",memberVo);
	}
	//로그인

	@Override
	public MemberVO login(String id,String pw) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("pw", pw);
		return sql.selectOne(namespace+".login",map);
	}
	//아이디 중복
	@Override
	public int idChk(String id) throws Exception {
			int result=sql.selectOne(namespace+".idChk",id);
			return result;
	}
	//이메일 중복
	@Override
	public int emailChk(String email) throws Exception {
		int result=sql.selectOne(namespace+".emailChk",email);
		return result;
	}
	
	//아이디 찾기
	@Override
	public String find_id(String name, String email) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", name);
		map.put("email", email);
		return sql.selectOne(namespace+".findId",map);
	}

	
	//비밀번호 찾기
	@Override
	public void find_PW(String id, String name, String email,String new_pw) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("name", name);
		map.put("email", email);
		map.put("pw",new_pw);
		sql.update(namespace+".findPw",map);
	}  
	
	//user_key 얻기
	@Override
	public int getKey(int user_pid, String user_key) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("user_pid", user_pid);
		map.put("user_key", user_key);
		int result=sql.update(namespace+".getKey",map);	
		return result;
	}
//	
	//user_key 변경하기
	@Override
	public void alter_userKey(int user_pid) throws Exception {
		sql.update(namespace+".alterUserKey",user_pid);
		return;
	}
	

	
	

}
