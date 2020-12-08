package niponapo.server.mail;

import java.util.Random;

public class TempKey {
	
	private boolean lowerCheck;
	private int size;
	
	//난수 생성
	private String init() {
		Random ran=new Random();
		StringBuffer sb=new StringBuffer();
		int num=0;
		do {
			num=ran.nextInt(75)+48;
			if((num>=48&&num<=57)|| (num>=65&&num<=90)||(num>=97&&num<=122))
				sb.append((char)num);
			else {
				continue;
			}	
		}while(sb.length()<size);
		if(lowerCheck) {
			return sb.toString().toLowerCase();
		}
		return sb.toString();
	}
	
	public String getKey(boolean lowerCheck, int size) {
		this.lowerCheck=lowerCheck;
		this.size=size;
		return init();
	}

}
