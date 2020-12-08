package niponapo.server.service;

import java.util.List;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import niponapo.server.dao.MemberDao;
import niponapo.server.vo.MemberVO;
import niponapo.server.dto.successDTO;
import niponapo.server.mail.MailSendService;
import niponapo.server.mail.TempKey;



@Service("MemberService")
//@Transactional
public class MemberServiceImp implements MemberService {
	@Autowired
	@Qualifier("MemberDao")
	MemberDao dao;
	
	@Inject
	private JavaMailSender mailSender;
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	private final successDTO success=new successDTO(true);
	private final successDTO fail=new successDTO(false);
	
	//회원가입
	@Override
	public successDTO insertMember(MemberVO memberVo) throws Exception {
		try {
		
			dao.insertMember(memberVo);
			
			TempKey tempKey=new TempKey();
			//인증키 생성
			String user_key=tempKey.getKey(false, 50); 
			dao.getKey(memberVo.getUser_pid(), user_key);
			MailSendService sendMail=new MailSendService(mailSender);
			sendMail.setSubject("[회원가입 이메일 인증 ]");
			sendMail.setText(
					new StringBuffer().append("<h1>[이메일 인증] </h1>")
					.append("<p> 아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
					//나중에 주소 변경하기
					.append("<a href=localhost:8080/server/member/emailConfirm/user_pid=")
					.append(memberVo.getUser_pid())
//					.append("&key=")
//					.append(user_key)
					.append(" 'target='_blenk'> 이메일 인증 확인 </a>")
					.toString());
			
			//보낸이 후에 변경하기
			sendMail.setFrom("niponapo@gmail.com", "니포내포");
			sendMail.setTo(memberVo.getEmail());
			sendMail.send();
			return success;
		}catch (Exception e) {
			return fail;
		}
		}
		//회원 정보 수정
		@Override
		public successDTO updateMember(MemberVO memberVo) throws Exception {
			try {
				dao.updateMember(memberVo);
				return success;
			}catch(Exception e) {
				return fail;
			}
		}
		//로그인 하기
		@Override
		public MemberVO login(String id, String pw) throws Exception {
			try {
				return dao.login(id,pw);
			}catch(Exception e) {
				return null;
			}
		}

		@Override
		public successDTO idChk(String id) throws Exception {
			int result=dao.idChk(id);
			if (result==0) {
				return success;
			}
			else 
				return fail;
		}

		@Override
		public successDTO emailChk(String email) throws Exception {
			int result=dao.emailChk(email);
			if(result==0) {
				return success;
			}
			return fail;
		}

		@Override
		public String find_id(String name, String email) throws Exception {
			String id=dao.find_id(name, email);
			if(id !=null) {
				return id;
			}
			return null;
		}

		@Override
		public successDTO find_PW(String id, String name, String email) throws Exception {
			try{String new_pw=getRamdomPassword(5);
			
			String pw=pwdEncoder.encode(new_pw);
			MailSendService sendMail=new MailSendService(mailSender);
			sendMail.setSubject("[임시 비밀번호 발급 ]");
			sendMail.setText(
					new StringBuffer().append("<h1>[임시 비밀번호 발급] </h1>")
					.append("<p> 회원님의 임시 비밀번호를 발급해드립니다.</p>")
					.append("<br/><p>보안을 위해 로그인 후 비밀번호를 변경해 주세요</p>")
					.append("<p> 임시 비밀 번호 : </p>")
					.append(new_pw)
					.toString());
			//보낸이 후에 변경하기
			sendMail.setFrom("niponapo@gmail.com", "니포내포");
			sendMail.setTo(email);
			sendMail.send();
			dao.find_PW(id,name,email,pw);
			return success;
			}catch(Exception e) {
				return fail;
			}
			
		}

		//user_key 변경
		@Override
		public successDTO alter_userKey(int user_pid) throws Exception {
			try {
				
				dao.alter_userKey(user_pid);
				return success;
			}catch(Exception e) {
				return fail;
			}
		}
		
		//랜덤 비밀번호 생성 
		@Override
		public String getRamdomPassword(int len) throws Exception {
			char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
			int idx=0;
			StringBuffer sb=new StringBuffer();
			
			for(int i=0;i<len; i++) {
				idx=(int)(charSet.length *Math.random());
				sb.append(charSet[idx]);
			}
			return sb.toString();
		}


	

}
