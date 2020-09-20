package niponapo.server.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import niponapo.server.vo.MemberVO;

import niponapo.server.mail.MailSendService;

import niponapo.server.service.MemberService;

import niponapo.server.dto.successDTO;

@RestController
@RequestMapping(value="/member/*")
public class MemberController {
	@Autowired
	private MemberService service;
	
	@Autowired 
	private JWTService JWTService;
	
	@Autowired
	private MailSendService mailsender;
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;
	
	//회원가입 (완)

	@RequestMapping(value="signUp",method=RequestMethod.POST)
	public successDTO insertUser(HttpServletRequest request, @RequestBody MemberVO memberVo) throws Exception{
		try {

			String inputPw=memberVo.getPw();
			String pw=pwdEncoder.encode(inputPw);
			memberVo.setPw(pw);
			return service.insertMember(memberVo);
		}catch(Exception e) {
			return null;
		}
			
			
	}
		//이메일 인증 코드 검증 (완)
		@RequestMapping(value="emailConfirm/user_pid={user_pid}", method=RequestMethod.GET)
		public successDTO emailConfirm(@PathVariable int user_pid, Model model, RedirectAttributes rttr) throws Exception{
			try {
				return service.alter_userKey(user_pid);
			}catch(Exception e) {
				return null;
			}
			
		}
		//회원 정보 수정 ()
		@RequestMapping(value="update/user_pid={user_pid}",method=RequestMethod.PUT)
		public @ResponseBody successDTO update(@PathVariable int user_pid, HttpServletRequest request, @RequestBody MemberVO memberVo) throws Exception{
			String inputPw=memberVo.getPw();
			String pw=pwdEncoder.encode(inputPw);
			memberVo.setUser_pid(user_pid);
			memberVo.setPw(pw);
			return service.updateMember(memberVo);
			
		}
		
		//로그인 
		@RequestMapping(value="login",method=RequestMethod.POST)
		public Map login(HttpServletResponse response, @RequestBody MemberVO memberVo) throws Exception {
			
			MemberVO login =service.login(memberVo.getId(), memberVo.getPw());
			boolean pwdMatch;
			if(login!=null) {
				pwdMatch=pwdEncoder.matches(memberVo.getPw(), login.getPw());
			}else {
				pwdMatch=false;
			}
			if(login!=null && pwdMatch==true) {
				String key =JWTService.generateKey();
				String token=JWTService.create(key,memberVo);
				response.setHeader("Authorization", token);
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("user_pid", memberVo.getUser_pid());
				map.put("id", memberVo.getId());
				map.put("pw", memberVo.getPw());
				map.put("name", memberVo.getName());
				map.put("email", memberVo.getEmail());
				map.put("idol_pid",memberVo.getIdol_pid());
				map.put("how", memberVo.getHow());
				map.put("jwt", token);
				return map;
			}else {
				return null;
			}
			
			
		}
		


	//아이디 중복 체크(완)

	@RequestMapping(value="idChk",method=RequestMethod.GET)
	public successDTO idChk(String id)throws Exception{
		
		return service.idChk(id);
	}
	
	//이메일 중복체크(완)
	@RequestMapping(value="emailChk",method=RequestMethod.GET)
	public successDTO emailChk(String email) throws Exception{	
		return service.emailChk(email);
	}
	
	//아이디 찾기
	@RequestMapping(value="findId",method=RequestMethod.POST)
	public String findId(String name,String email) throws Exception{
		return "";
	}
	
	//비밀번호 찾기
	@RequestMapping(value="findPw",method=RequestMethod.POST)
	public successDTO findPw(String id,String name,String email) throws Exception{
		try {

			return service.find_PW(id,name,email);
		}catch(Exception e) {
			return null;
		}
		
	}
}
